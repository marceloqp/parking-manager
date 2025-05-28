package br.com.mpsolucoes.manager.configuration;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.servers.Server;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class SwaggerConfig {

    @Value("${spring.openapi.local-url}")
    private String localUrl;

    @Value("${spring.openapi.dev-url}")
    private String devUrl;

    @Bean
    public OpenAPI openApi() {
        final String securitySchemeName = "bearerAuth";
        Server localServer = new Server();
        localServer.setUrl(localUrl);
        localServer.setDescription("Server URL in Local environment");

        Server devServer = new Server();
        devServer.setUrl(devUrl);
        devServer.setDescription("Server URL in Development environment");

        Contact contact = new Contact();
        contact.setEmail("quadrelli.marcelo@gmail.com");
        contact.setName("Marcelo Pinheiro");
        contact.setUrl("https://github.com/marceloqp");

        Info info = new Info()
                .title("Parking Manager API")
                .version("1.0")
                .contact(contact)
                .description("This API exposes endpoints to parking manager");

        return new OpenAPI()
                .info(info)
                .addSecurityItem(new SecurityRequirement().addList(securitySchemeName))
                .components(new Components().addSecuritySchemes(securitySchemeName,
                        new SecurityScheme()
                                .type(SecurityScheme.Type.HTTP)
                                .scheme("bearer")
                                .bearerFormat("JWT")))
                .servers(List.of(localServer, devServer));
    }
}