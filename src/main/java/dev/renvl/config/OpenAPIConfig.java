package dev.renvl.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenAPIConfig {

    @Value("${renvl.openapi.url}")
    private String url;

    @Bean
    public OpenAPI myOpenAPI() {
        Server server = new Server();
        server.setUrl(url);
        server.setDescription("Server URL in Development environment");

        Contact contact = new Contact();
        contact.setEmail("renvl.dev@gmail.com");
        contact.setName("Renato");
        contact.setUrl("https://renatovelasquez.github.io/");

        License mitLicense = new License().name("MIT License").url("https://opensource.org/license/mit");

        Info info = new Info()
                .title("Management API")
                .version("1.0")
                .contact(contact)
                .description("This API exposes endpoints to manage.").termsOfService("https://snyk.io/learn/what-is-mit-license/#terms")
                .license(mitLicense);

        return new OpenAPI().info(info).addServersItem(server);
    }
}
