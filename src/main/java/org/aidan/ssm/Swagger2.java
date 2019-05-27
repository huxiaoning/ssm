package org.aidan.ssm;

import com.google.common.base.Predicates;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * @author Aidan
 * @version V1.0
 * @Title: Swagger设置类
 * @Package
 * @date 2019年1月5日 下午2:32:23
 * 接口文档地址：http://localhost:8080/swagger-ui.html
 */
@Configuration
@ComponentScan(basePackages = {"org.aidan.*.controller"})
@EnableSwagger2
public class Swagger2 {

    @Bean
    public Docket createRestApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .select()
                .paths(Predicates.or(
                        //这里添加你需要展示的接口
                        PathSelectors.ant("/**")
                        )
                )
                .build();
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("录音小程序--接口文档")
                .description("录音小程序--接口文档")
                .version("1.0")
                .build();
    }
}
