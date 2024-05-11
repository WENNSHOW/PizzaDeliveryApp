package yarosh.vlad.pizzaapp.config;

import yarosh.vlad.pizzaapp.domain.constant.RoleEnum;
import yarosh.vlad.pizzaapp.repository.UserRepository;
import yarosh.vlad.pizzaapp.service.CustomUserDetailsService;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.servlet.util.matcher.MvcRequestMatcher;
import org.springframework.web.servlet.handler.HandlerMappingIntrospector;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http, MvcRequestMatcher.Builder mvc) throws Exception {
        http
                .authorizeHttpRequests(authorize ->
                        authorize
                                .requestMatchers(PathRequest.toStaticResources().atCommonLocations()).permitAll()
                                // everyone
                                .requestMatchers(mvc.pattern("/")).permitAll()
                                .requestMatchers(mvc.pattern("/menu/")).permitAll()
                                .requestMatchers(mvc.pattern("/menu/**")).permitAll()
                                .requestMatchers(mvc.pattern("/contact")).permitAll()
                                .requestMatchers(mvc.pattern("/users/profile")).permitAll()
                                // anonymous users
                                .requestMatchers(mvc.pattern("/users/login")).anonymous()
                                .requestMatchers(mvc.pattern("/users/register")).anonymous()
                                .requestMatchers(mvc.pattern("/users/login-error")).anonymous()
                                // user
                                .requestMatchers(mvc.pattern("/closed")).hasRole(RoleEnum.USER.name())
                                .requestMatchers(mvc.pattern("/users/edit/**")).hasAnyRole(RoleEnum.USER.name(), RoleEnum.WORKER.name())
                                .requestMatchers(mvc.pattern("/orders/finalize")).hasRole(RoleEnum.USER.name())
                                .requestMatchers(mvc.pattern("/orders/history")).hasRole(RoleEnum.USER.name())
                                .requestMatchers(mvc.pattern("/orders/details/**")).hasAnyRole(RoleEnum.USER.name(), RoleEnum.WORKER.name())
                                .requestMatchers(mvc.pattern("/cart")).hasRole(RoleEnum.USER.name())
                                // admin
                                .requestMatchers(mvc.pattern("/products/add")).hasRole(RoleEnum.ADMIN.name())
                                .requestMatchers(mvc.pattern("/products/edit/**")).hasRole(RoleEnum.ADMIN.name())
                                .requestMatchers(mvc.pattern("/orders/all/history")).hasAnyRole(RoleEnum.ADMIN.name(), RoleEnum.WORKER.name())
                                .requestMatchers(mvc.pattern("/users/all")).hasRole(RoleEnum.ADMIN.name())
                                .requestMatchers(mvc.pattern("/users/change/**")).hasRole(RoleEnum.ADMIN.name())
                                .requestMatchers(mvc.pattern("/users/profile/**")).hasRole(RoleEnum.ADMIN.name())
                                .anyRequest().authenticated())
                .formLogin(login ->
                        login.loginPage("/users/login")
                                .usernameParameter("email")
                                .passwordParameter(UsernamePasswordAuthenticationFilter.SPRING_SECURITY_FORM_PASSWORD_KEY)
                                .defaultSuccessUrl("/")
                                .failureForwardUrl("/users/login-error"))
                .logout(logout ->
                        logout.logoutUrl("/users/logout")
                                .logoutSuccessUrl("/")
                                .invalidateHttpSession(true))
                .rememberMe(rememberMe ->
                        rememberMe.key("yellowKey")
                                .tokenValiditySeconds(24 * 60 * 60));

        return http.build();
    }

    @Bean
    MvcRequestMatcher.Builder mvc(HandlerMappingIntrospector introspector) {
        return new MvcRequestMatcher.Builder(introspector);
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public UserDetailsService userDetailsService(UserRepository userRepository) {
        return new CustomUserDetailsService(userRepository);
    }
}
