package com.educacional.schoolacademicperiods.infra.configuration;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Arrays;
import java.util.List;

@Configuration
@OpenAPIDefinition
public class OpenApiConfiguration {

    @Value("${swagger-config.version}")
    private String version;

    @Value("${swagger-config.title}")
    private String title;

    @Value("${swagger-config.description}")
    private String description;

    @Value("${swagger-config.servers.develop.url}")
    private String developUrl;

    @Value("${swagger-config.servers.develop.description}")
    private String developDescription;

    @Value("${swagger-config.servers.staging.url}")
    private String stagingUrl;

    @Value("${swagger-config.servers.staging.description}")
    private String stagingDescription;

    @Value("${swagger-config.servers.qa.url}")
    private String qaUrl;

    @Value("${swagger-config.servers.qa.description}")
    private String qaDescription;

    @Value("${swagger-config.servers.production.url}")
    private String productionUrl;

    @Value("${swagger-config.servers.production.description}")
    private String productionDescription;

    @Bean
    public OpenAPI openAPIDefinition() {
        return new OpenAPI().info(getInfo()).servers(getServers());
    }

    private Info getInfo() {
        return new Info().version(version).title(title).description(description);
    }

    private List<Server> getServers() {
        return Arrays.asList(
                createServer(developUrl, developDescription),
                createServer(stagingUrl, stagingDescription),
                createServer(qaUrl, qaDescription),
                createServer(productionUrl, productionDescription));
    }

    private Server createServer(String url, String description) {
        Server server = new Server();
        server.setUrl(url);
        server.setDescription(description);
        return server;
    }
}