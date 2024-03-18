package dev.renvl.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.servers.Server;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@TestPropertySource(properties = {
        "renvl.openapi.url=http://localhost:8080"
})
class OpenAPIConfigTest {

    @Autowired
    private OpenAPI myOpenAPI;

    @Test
    void testOpenAPIConfiguration() {
        assertThat(myOpenAPI).isNotNull();

        Server server = myOpenAPI.getServers().get(0);
        assertThat(server).isNotNull();
        assertThat(server.getUrl()).isEqualTo("http://localhost:8080");
        assertThat(server.getDescription()).isEqualTo("Server URL in Development environment");

        assertThat(myOpenAPI.getInfo()).isNotNull();
        assertThat(myOpenAPI.getInfo().getTitle()).isEqualTo("Management API");
        assertThat(myOpenAPI.getInfo().getVersion()).isEqualTo("1.0");
    }
}