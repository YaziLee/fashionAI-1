package ices.fashion.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class SwaggerConfig {

    @Bean
    public Docket createRestApi() {
        return new Docket(DocumentationType.SWAGGER_2).apiInfo(apiInfo()).select().apis(RequestHandlerSelectors.basePackage("ices.fashion.controller")).paths(PathSelectors.any()).build();
//        return new Docket(DocumentationType.SWAGGER_2);
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder().title("fashion 平台接口文件").description("fashion平台swagger接口后端").contact(new Contact("Euphonium", "https://github.com/euphonium1998/eupho-study", "814811223@qq.com")).version("1.0.0-SNAPSHOT").build();
    }
}
