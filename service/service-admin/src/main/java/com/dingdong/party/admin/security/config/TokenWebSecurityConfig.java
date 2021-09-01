package com.dingdong.party.admin.security.config;


import com.dingdong.party.admin.security.server.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.session.SessionInformationExpiredStrategy;

/**
 * Security 配置类
 */
@Configuration
@EnableWebSecurity
public class TokenWebSecurityConfig extends WebSecurityConfigurerAdapter {


    @Autowired
    private DefaultPasswordEncoder defaultPasswordEncoder;

    // 登录成功处理器
    @Autowired
    private AuthenticationSuccessHandler authenticationSuccessHandler;

    // 登录失败处理器
    @Autowired
    private AuthenticationFailHandlerImpl authenticationFailHandler;

    // 未登录处理器
    @Autowired
    private AuthenticationEntryPointImpl authenticationEntryPoint;

    // 权限不足处理器
    @Autowired
    private AccessDeniedHandlerImpl accessDeniedHandler;

    // 注销成功处理器
    @Autowired
    private LogoutSuccessHandlerImpl logoutSuccessHandler;

    // 账号异地登录下线
    @Autowired
    private SessionInformationExpiredStrategy sessionInformationExpiredStrategy;

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private CheckTokenFilter checkTokenFilter;

    @Value("${application.login-url}")
    private String loginUrl;

    @Value("${application.logout-url}")
    private String logoutUrl;

    /**
     * 配置设置
     * @param http
     * @throws Exception
     */
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable()// 关闭 csrf 防护
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)   // 不通过 session 判断登录信息，通过 token
                .and()
                .cors()  // 解决跨域
                .and()
                .formLogin().permitAll().successHandler(authenticationSuccessHandler)   // 登录成功处理
                .failureHandler(authenticationFailHandler)   // 登录失败处理
                .loginProcessingUrl(loginUrl)
                .and()
                .logout().permitAll()   // 允许注销操作
                .logoutUrl(logoutUrl)
                .logoutSuccessHandler(logoutSuccessHandler)  // 注销成功操作
                .deleteCookies("JSESSIONID")   // 登出后删除 cookie
                .and()
                .exceptionHandling()
                .authenticationEntryPoint(authenticationEntryPoint)  // 未登录访问资源处理
                .accessDeniedHandler(accessDeniedHandler)  // 权限不足处理器
                .and()
                .sessionManagement().maximumSessions(1)  // 限制一个账号只能登录一次，会被挤下线
                .expiredSessionStrategy(sessionInformationExpiredStrategy);  //会话信息过期策略会话信息过期策略(账号被挤下线)

        // 授权，开启 httpBasic 认证
        http.authorizeRequests()
                .anyRequest().hasAuthority("admin");  // 需要管理员权限才能访问
        http.addFilterBefore(checkTokenFilter, UsernamePasswordAuthenticationFilter.class);
    }

    /**
     * 通过 auth 可以在内存中构建虚拟的用户名和密码
     * @param auth
     * @throws Exception
     */
    public void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService)
                .passwordEncoder(defaultPasswordEncoder);
    }

    /**
     * 配置哪些请求不拦截
     * @param web
     * @throws Exception
     */
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers(
                "/swagger-ui.html",
                "/v2/api-docs", // swagger api json
                "/swagger-resources/configuration/ui", // 用来获取支持的动作
                "/swagger-resources", // 用来获取api-docs的URI
                "/swagger-resources/configuration/security", // 安全选项
                "/swagger-resources/**",
                //补充路径，近期在搭建swagger接口文档时，通过浏览器控制台发现该/webjars路径下的文件被拦截，故加上此过滤条件即可。
                "/webjars/**",
                "/api-docs",
                "/doc.html", "/image-code"
        );
    }
}
