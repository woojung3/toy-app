package toy.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import jakarta.servlet.http.HttpSession;

@Configuration
@EnableWebSecurity
public class GlobalSecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                .authorizeHttpRequests(authorizeConfig -> {
                    authorizeConfig.requestMatchers(
                            "/resources/**",
                            "/login",
                            "/logout",
                            "/register",
                            "/webjars/**").permitAll();
                    authorizeConfig.anyRequest().authenticated();
                })
                // .formLogin(withDefaults())
                .formLogin(login -> {
                    login.loginPage("/login");
                    login.defaultSuccessUrl("/");
                })
                .logout(logout -> {
                    logout.logoutRequestMatcher(new AntPathRequestMatcher("/logout"));
                    logout.logoutSuccessUrl("/login");
                    // Logout handler (invalidate session)
                    logout.addLogoutHandler((request, response, authentication) -> {
                        HttpSession session = request.getSession();
                        session.invalidate();
                    });
                    // Delete cookies
                    logout.deleteCookies("JSESSIONID", "access_token");
                })
                .build();
    }

    @Bean
    public UserDetailsService userDetailsService() {
        return new InMemoryUserDetailsManager(
                User.builder()
                        .username("user")
                        .password("{noop}password")
                        .authorities("ROLE_user")
                        .build());
    }
}
