package net.ikwa.likwapuecommerce.Config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class ConfigurationClass { // Name updated to avoid confusion with @Configuration

    @Bean
    public WebMvcConfigurer webConfigurer() {
        return new WebMvcConfigurer() {

            // CORS configuration (your existing setup)
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**")
                        .allowedOrigins("https://laceand-legacy1.vercel.app", "http://localhost:3000")
                        .allowedMethods("*")  // Or specify: "GET", "POST", etc.
                        .allowedHeaders("*")
                        .allowCredentials(true); // If you use cookies/auth
            }

            // Static resource handler (for serving uploaded files)
            @Override
            public void addResourceHandlers(ResourceHandlerRegistry registry) {
                // This maps URL path /uploads/** to the local uploads/ folder
                registry.addResourceHandler("/uploads/**")
                        .addResourceLocations("file:uploads/");
            }
        };
    }
}
