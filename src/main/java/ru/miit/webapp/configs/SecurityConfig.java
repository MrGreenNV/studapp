package ru.miit.webapp.configs;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.context.DelegatingSecurityContextRepository;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.security.web.context.RequestAttributeSecurityContextRepository;
import org.springframework.security.web.context.SecurityContextRepository;
import ru.miit.webapp.repositories.GroupRepository;
import ru.miit.webapp.repositories.StudentRepository;
import ru.miit.webapp.services.StudentService;

@Configuration
@RequiredArgsConstructor
public class SecurityConfig {

    private final StudentRepository studentRepository;
    private final GroupRepository groupRepository;
    private final ModelMapper modelMapper;

    @Bean
    public SecurityContextRepository getSecurityContextRepository() {
        return new DelegatingSecurityContextRepository(
                new RequestAttributeSecurityContextRepository(),
                new HttpSessionSecurityContextRepository()
        );
    }

    @Bean
    public UserDetailsService userDetailsService() {
        return new StudentService(studentRepository, groupRepository, modelMapper);
    }

    @Bean
    public String getDefaultPassword(@Value("${app.default.pass}") String defaultPassword) {
        return defaultPassword;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http, SecurityContextRepository securityContextRepository) throws Exception {
        http
                .userDetailsService(userDetailsService())
                .authorizeHttpRequests(
                        authorizeHttpRequests ->
                                authorizeHttpRequests
                                        .requestMatchers(PathRequest.toStaticResources().atCommonLocations())
                                        .permitAll()
                                        .requestMatchers("/pic/**")
                                        .permitAll()
                                        .requestMatchers("/", "/users/register", "/users/login", "/users/login-error", "/test")
                                        .permitAll()
                                        .requestMatchers("/groups/add", "groups/group-details/", "groups/all", "/users/user-delete/", "/groups/group-delete/", "users/profile")
                                        .authenticated()
                                        .anyRequest().authenticated()
                )
                .formLogin(
                        (formLogin) ->
                                formLogin.
                                        loginPage("/users/login").
                                        usernameParameter(UsernamePasswordAuthenticationFilter.SPRING_SECURITY_FORM_USERNAME_KEY).
                                        passwordParameter(UsernamePasswordAuthenticationFilter.SPRING_SECURITY_FORM_PASSWORD_KEY).
                                        defaultSuccessUrl("/").
                                        failureForwardUrl("/users/login-error")
                )
                .logout((logout) ->
                        logout.logoutUrl("/users/logout").
                                logoutSuccessUrl("/").
                                invalidateHttpSession(true)
                )
                .securityContext(
                        securityContext ->
                                securityContext
                                        .securityContextRepository(securityContextRepository)
                );

        return http.build();
    }
}
