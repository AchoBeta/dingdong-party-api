package com.dingdong.party.user;

import com.dingdong.party.user.entity.PartyGroup;
import org.springframework.boot.test.context.SpringBootTest;
import org.junit.Test;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @program: service-base
 * @author: retraci
 * @create: 2021-08-31 16:36
 **/
@SpringBootTest
public class CustomTest {
    final String str = "{\n" +
            "  \"msg\": \"success\",\n" +
            "  \"error_code\": 0,\n" +
            "  \"request_url\": \"\\/Party\\/party\\/v1\\/console\\/count\",\n" +
            "  \"data\": '[\n" +
            "    {\n" +
            "      \"id\": 1,\n" +
            "      \"name\": \"入党申请人\",\n" +
            "      \"description\": \"\",\n" +
            "      \"create_time\": null,\n" +
            "      \"update_time\": null,\n" +
            "      \"delete_time\": null,\n" +
            "      \"general_branch_id\": 1,\n" +
            "      \"order\": 1,\n" +
            "      \"total\": 37\n" +
            "    },\n" +
            "    {\n" +
            "      \"id\": 2,\n" +
            "      \"name\": \"入党积极分子\",\n" +
            "      \"description\": \"\",\n" +
            "      \"create_time\": null,\n" +
            "      \"update_time\": null,\n" +
            "      \"delete_time\": null,\n" +
            "      \"general_branch_id\": 1,\n" +
            "      \"order\": 2,\n" +
            "      \"total\": 27\n" +
            "    },\n" +
            "    {\n" +
            "      \"id\": 3,\n" +
            "      \"name\": \"发展对象\",\n" +
            "      \"description\": \"\",\n" +
            "      \"create_time\": null,\n" +
            "      \"update_time\": null,\n" +
            "      \"delete_time\": null,\n" +
            "      \"general_branch_id\": 1,\n" +
            "      \"order\": 3,\n" +
            "      \"total\": 15\n" +
            "    },\n" +
            "    {\n" +
            "      \"id\": 4,\n" +
            "      \"name\": \"预备党员\",\n" +
            "      \"description\": \"\",\n" +
            "      \"create_time\": null,\n" +
            "      \"update_time\": null,\n" +
            "      \"delete_time\": null,\n" +
            "      \"general_branch_id\": 1,\n" +
            "      \"order\": 4,\n" +
            "      \"total\": 16\n" +
            "    },\n" +
            "    {\n" +
            "      \"id\": 5,\n" +
            "      \"name\": \"正式党员\",\n" +
            "      \"description\": \"\",\n" +
            "      \"create_time\": null,\n" +
            "      \"update_time\": null,\n" +
            "      \"delete_time\": null,\n" +
            "      \"general_branch_id\": 1,\n" +
            "      \"order\": 5,\n" +
            "      \"total\": 0\n" +
            "    },\n" +
            "    {\n" +
            "      \"id\": 6,\n" +
            "      \"name\": \"党员\",\n" +
            "      \"description\": null,\n" +
            "      \"create_time\": null,\n" +
            "      \"update_time\": null,\n" +
            "      \"delete_time\": null,\n" +
            "      \"general_branch_id\": 1,\n" +
            "      \"order\": 6,\n" +
            "      \"total\": 0\n" +
            "    }\n" +
            "  ]'\n" +
            "}";


    @Test
    public void run() {
        final Pattern pattern = Pattern.compile("/(\"[^\"]+\":)/g");
        final Matcher matcher = pattern.matcher(str);
        System.out.println(matcher.group(0));
    }
}
