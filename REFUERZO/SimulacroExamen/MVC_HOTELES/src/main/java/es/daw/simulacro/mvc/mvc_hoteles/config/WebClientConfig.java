package es.daw.simulacro.mvc.mvc_hoteles.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
//@PropertySource("classpath:config.properties")
public class WebClientConfig {

    @Value("${api.hoteles.url}")
    private String apiUrl;

    @Bean
    public WebClient webClientApi(WebClient.Builder builder){
        System.out.println("**********************************");
        System.out.println("* apiUrl:"+apiUrl);
        System.out.println("**********************************");
        try{
            return builder
                    .baseUrl(apiUrl.trim())
                    .build();

        } catch (Exception e) {
            System.out.println("**************** ERROR ***************");
            System.out.println(e.getMessage());
            System.out.println("***************************************");
        }
        return builder.build();
    }

}
