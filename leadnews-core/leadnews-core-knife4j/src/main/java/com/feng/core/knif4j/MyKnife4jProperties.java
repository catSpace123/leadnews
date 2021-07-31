package com.feng.core.knif4j;


import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author ljh
 * @version 1.0
 * @date 2021/5/30 13:32
 * @description 标题
 * @package com.itheima.core.knif4j
 */

@ConfigurationProperties("steven.knife4j")
@Data
public class MyKnife4jProperties {

    private String basePackage = "com.feng";

    private String description = "默认描述";

    private String title = "默认标题";

    private String contact = "联系人";

    private String version = "1.0";

    private String serviceUrl = "http://www.itheima.com";


}
