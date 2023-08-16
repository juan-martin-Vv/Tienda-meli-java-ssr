package com.jmvv.MpShop.config;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class securityConf {

    @Bean
    public AuthenticationManager authenticationManager(UserDetailsService userDetailsService){
        var authManager = new DaoAuthenticationProvider();
        authManager.setUserDetailsService(userDetailsService);
        return new ProviderManager(authManager);
    }
    @Bean
    public UserDetailsService userDetailsService(){
        return new InMemoryUserDetailsManager(
            User.builder()
                .username("pepe")
                .password("{noop}pass")
                .roles("admin")
                .build()
        );
    }

    
    @Bean
    public SecurityFilterChain securityfilterChain(HttpSecurity http) throws Exception {
        List<String> policies = new ArrayList<>();
            policies.add("frame-ancestors 'self' https://www.mercadopago.com.ar/;");
            policies.add("form-action 'self'");
        
        System.out.println();
        http.csrf().disable();
        http.cors().disable();
        http.headers(header-> header
        .xssProtection()
        .and()        
        .contentSecurityPolicy(poli -> poli        
        .policyDirectives(policies.stream().map(n->String.valueOf(n)).collect(Collectors.joining(" ")))
            )        
        );    
       
        http.authorizeHttpRequests((req)-> req
            .requestMatchers("/assets/**","/scripts/**","/css/**","/flavico.ico").permitAll()
            .requestMatchers("/","/store/**").permitAll()
            .requestMatchers("/auth/**").fullyAuthenticated()
            .anyRequest().authenticated());
        http.formLogin(form-> form
                        .loginPage("/login")
                        .permitAll()
                        .defaultSuccessUrl("/auth/hooks")
                        );
        return http.build();
    }

    
}
