package lebib.team.config;

import io.github.cdimascio.dotenv.Dotenv;
import jakarta.annotation.PostConstruct;
import org.springframework.context.annotation.Configuration;

@Configuration
public class EnvConfig {

    @PostConstruct
    public void init() {
        Dotenv env = Dotenv.load();
        env.entries().forEach(entry ->
                System.getProperty(entry.getKey(), entry.getValue()));
    }

}
