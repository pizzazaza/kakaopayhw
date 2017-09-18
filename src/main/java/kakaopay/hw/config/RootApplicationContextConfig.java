package kakaopay.hw.config;

import org.springframework.context.annotation.*;


@Configuration
@ComponentScan(basePackages = {"kakaopay.hw.dao", "kakaopay.hw.service"})
@PropertySources({
        @PropertySource("classpath:/database.properties"),
        @PropertySource("classpath:/application.properties"),
        @PropertySource("classpath:/database.sqls.properties")
})
@Import({DbConfig.class})
public class RootApplicationContextConfig {
}
