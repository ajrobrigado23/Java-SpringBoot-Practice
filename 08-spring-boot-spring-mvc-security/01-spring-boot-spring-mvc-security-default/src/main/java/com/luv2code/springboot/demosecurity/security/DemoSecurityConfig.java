package com.luv2code.springboot.demosecurity.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

import javax.sql.DataSource;

@Configuration
public class DemoSecurityConfig {

    // add support for JDBC ... no more hardcoded users :)

    // Inject data source Auto-configured by Spring Boot
    @Bean
    public UserDetailsManager userDetailsManager(DataSource dataSource) {

        // Tell Spring Security to use JDBC authentication with our data source
        return new JdbcUserDetailsManager(dataSource);
    }

    // Customize our own LOGIN FORM
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        http.authorizeHttpRequests(configurer->
                        configurer
                                // Restrict Access based on Roles '/**' -> means all subdirectories
                                .requestMatchers("/").hasRole("EMPLOYEE")
                                .requestMatchers("/leaders/**").hasRole("MANAGER")
                                .requestMatchers("/systems/**").hasRole("ADMIN")
                                // Any requests coming to the app must be authenticated (the user must be login)
                                .anyRequest().authenticated()
                )
                // Customizing the form login process
                .formLogin(form ->
                        form
                                // Show our custom form at the request mapping (Need to create a controller)
                                .loginPage("/showMyLoginPage")
                                // Login form should POST data to this URL for processing (check user id and password)
                                .loginProcessingUrl("/authenticateTheUser")
                                // Allow everyone to see login page (No need to be logged in)
                                .permitAll()

                )
                // Add logout support for default URL
                .logout(logout-> logout.permitAll()
                        )

                // Custom page for access denied
                .exceptionHandling(configurer ->
                        configurer.accessDeniedPage("/access-denied")
                );

        return http.build();
    }
}

    /*

    @Bean
    public InMemoryUserDetailsManager userDetailsManager() {

        UserDetails john = User.builder()
                .username("john")
                // noop -> means plain text
                .password("{noop}test123")
                .roles("EMPLOYEE")
                .build();

        UserDetails mary = User.builder()
                .username("mary")
                // noop -> means plain text
                .password("{noop}test123")
                .roles("EMPLOYEE", "MANAGER")
                .build();

        UserDetails susan = User.builder()
                .username("susan")
                // noop -> means plain text
                .password("{noop}test123")
                .roles("EMPLOYEE", "MANAGER", "ADMIN")
                .build();

        return new InMemoryUserDetailsManager(john, mary, susan);
    }

     */
