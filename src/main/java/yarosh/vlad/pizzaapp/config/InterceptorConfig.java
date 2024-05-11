package yarosh.vlad.pizzaapp.config;

import yarosh.vlad.pizzaapp.web.interceptor.ClosedInterceptor;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@AllArgsConstructor
public class InterceptorConfig implements WebMvcConfigurer {

    private final ClosedInterceptor closedInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        WebMvcConfigurer.super.addInterceptors(registry);
        registry.addInterceptor(closedInterceptor);
    }
}
