package com.rizomm.filemanager.business.configuration;

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
public class SwaggerConfiguration {

    @Bean
    public Docket createUserDocumentation() {
        return serviceDocumentationBuildFrom(
                "Users",
                "com.rizomm.filemanager.controllers.user");
    }

    @Bean
    public Docket createMinioDocumentation() {
        return serviceDocumentationBuildFrom(
                "Minio",
                "com.rizomm.filemanager.controllers.minio"
        );
    }

    @Bean
    public Docket createSftpDocumentation() {
        return serviceDocumentationBuildFrom(
                "SFTP",
                "com.rizomm.filemanager.controllers.sftp"
        );
    }

    private Docket serviceDocumentationBuildFrom(String serviceName, String basePackage) {
        return new Docket(DocumentationType.SWAGGER_2)
                .groupName(serviceName)
                .select()
                .apis(RequestHandlerSelectors.basePackage(basePackage))
                .paths(PathSelectors.any())
                .build().apiInfo(customApiInfoBuild());
    }

    private ApiInfo customApiInfoBuild() {
        return new ApiInfoBuilder()
                .title("FileManager API")
                .description("API REST")
                .contact(new Contact("Redha /Hichem/ Miguel", "test", "test"))
                .build();
    }
}
