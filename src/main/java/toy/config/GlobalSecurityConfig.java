package toy.config;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.stereotype.Service;

import jakarta.annotation.PostConstruct;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import toy.config.domain.Member;
import toy.config.service.LoginService;

@Configuration
@EnableWebSecurity
public class GlobalSecurityConfig {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

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

    // @Bean
    // public UserDetailsService userDetailsService() {
    // return new InMemoryUserDetailsManager(
    // User.builder()
    // .username("user")
    // .password("{noop}password")
    // .authorities("ROLE_user")
    // .build());
    // }

    @Service
    @RequiredArgsConstructor
    public class SecurityService implements UserDetailsService {

        @Autowired
        private final LoginService loginService;
        @Autowired
        private final PasswordEncoder passwordEncoder;

        @PostConstruct
        public void init() {
            loginService.save(createUser("admin", "admin", passwordEncoder));
        }

        @Override
        public UserDetails loadUserByUsername(String insertedUsername) throws UsernameNotFoundException {
            Optional<Member> findOne = loginService.findOne(insertedUsername);
            Member newMember = findOne.orElseThrow(() -> new UsernameNotFoundException("Unregistered ID."));

            return User.builder()
                    .username(newMember.getUsername())
                    .password(newMember.getPassword())
                    .build();
        }

        public Member createUser(String username, String password, PasswordEncoder passwordEncoder) {
            Member newMember = new Member();
            newMember.setId(null);
            newMember.setUsername(username);
            newMember.setPassword(passwordEncoder.encode(password));
            return newMember;
        }

    }
}
