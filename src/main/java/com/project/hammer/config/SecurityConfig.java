package com.project.hammer.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    //    @Autowired
//    private CustomUserDetailsService customUserDetailsService;
//
//    @Bean
//    public AuthenticationProvider authProvider() {
//        DaoAuthenticationProvider provider=new DaoAuthenticationProvider();
//        provider.setUserDetailsService(customUserDetailsService);
//        provider.setPasswordEncoder(new BCryptPasswordEncoder(12));
//        return provider;
//    } //custom authenication manager //

    @Autowired
    private JwtFilter jwtFilter;

    public final String[] WHITELISTENDPOINTS = {
            "/v3/api-docs/**",
            "/swagger-ui.html",
            "/swagger-ui/**",
            "/swagger-resources/**",
            "/webjars/**",
            "/hammer/v1/api/sign",
            "/hammer/v1/api/login",
            "/swagger-ui/index.html#/"};

    public final String[] USERENDPOINTS = {
            "/products/get/all",
            "/products/update"
    };

    public final String[] SUPERADMINENDPOINTS = {
            "/products/",
            "/category/",
            "/hammer/v1/api/all/users",
    };

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity.csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(request ->
                        request.requestMatchers(WHITELISTENDPOINTS).permitAll()
                                .requestMatchers(USERENDPOINTS).hasRole("USER")
                                .requestMatchers(SUPERADMINENDPOINTS).hasRole("SUPERADMIN")
                                .anyRequest().authenticated()
                )
                .sessionManagement(sesssion ->
                        sesssion.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
        httpSecurity.addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);
        return httpSecurity.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean // for authendiation and validate user name and pass
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }


//BASIC AUTH REFERENCE
//    @Bean
//    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
//        http
//                .csrf(csrf->csrf.disable())
//                .authorizeHttpRequests(request->
//                        request.anyRequest().authenticated())
//                .httpBasic(Customizer.withDefaults())
//                .sessionManagement(session->
//                        session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
//
//            return http.build();
//    }
//
//    @Bean
//    public UserDetailsService userDetailsService(){
//        UserDetails user= User.withDefaultPasswordEncoder()
//                .username("mani")
//                .password("mani")
//                .roles("USER")
//                .build();
//
//        UserDetails admin= User.withDefaultPasswordEncoder()
//                .username("meera")
//                .password("meera")
//                .roles("ADMIN")
//                .build();
//
//        return new InMemoryUserDetailsManager(user,admin);
//    }

}