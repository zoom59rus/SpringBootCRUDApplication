package com.nazarov.springrestapi.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI openApi(){

        return new OpenAPI().info(
                new Info().title("RESTfull SpringBoot application on module_2_5.")
                .version("1.0.0")
                .contact(new Contact()
                        .email("anton_1@bk.ru")
                        .name("Anton Nazarov")
                        .url("https://github.com/zoom59rus/SpringBootCRUDApplication")));
    }
}
