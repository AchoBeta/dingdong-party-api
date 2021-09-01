package com.dingdong.party.gateway.config;



import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import java.util.Arrays;

/**
 * 统一解决跨域
 */
@Configuration
//@Order(Ordered.HIGHEST_PRECEDENCE)
public class CorsConfig {

    @Bean
    public FilterRegistrationBean filterRegistrationBean() {
      final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
      final CorsConfiguration config = new CorsConfiguration();

      config.setAllowCredentials(true);
      config.setAllowedOrigins(Arrays.asList("*"));
      config.setAllowedHeaders(Arrays.asList("*"));
      config.setAllowedMethods(Arrays.asList("*"));
      config.setMaxAge(300L);

      source.registerCorsConfiguration("/**", config);
      CorsFilter corsFilter = new CorsFilter(source);
      FilterRegistrationBean filterRegistrationBean = new FilterRegistrationBean(corsFilter);
      // 设置为 Ordered.HIGHEST_PRECEDENCE
      filterRegistrationBean.setOrder(Ordered.HIGHEST_PRECEDENCE);
      return filterRegistrationBean;
    }
}
