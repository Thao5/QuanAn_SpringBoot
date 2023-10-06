/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.thao.configs;

import com.thao.filters.CustomAccessDeniedHandler;
import com.thao.filters.JwtAuthenticationTokenFilter;
import com.thao.filters.RestAuthenticationEntryPoint;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 *
 * @author Chung Vu
 */
@Configuration
@EnableWebSecurity
@EnableTransactionManagement
@EntityScan("com.thao.pojo")
@EnableJpaRepositories("com.thao.repository")
@ComponentScan({
    "com.thao.repository.impl",
    "com.thao.service",
    "com.thao.Controllers",
    "com.thao.components",
    "com.thao.validation"
})
@EnableMethodSecurity(securedEnabled = true)
public class JwtSecurityConfig {

    @Autowired
    private UserDetailsService userDetailsService;
    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Bean
    public JwtAuthenticationTokenFilter jwtAuthenticationTokenFilter() throws Exception {
        JwtAuthenticationTokenFilter jwtAuthenticationTokenFilter = new JwtAuthenticationTokenFilter();
        jwtAuthenticationTokenFilter.setAuthenticationManager(authenticationManager());
        return jwtAuthenticationTokenFilter;
    }

    @Bean
    public ProviderManager authManagerBean(AuthenticationProvider provider) {
        return new ProviderManager(provider);
    }

    @Bean
    protected AuthenticationManager authenticationManager() throws Exception {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService);
        authProvider.setPasswordEncoder(passwordEncoder);
        return new ProviderManager(authProvider);
    }

    @Bean
    public RestAuthenticationEntryPoint restServicesEntryPoint() {
        return new RestAuthenticationEntryPoint();
    }

    @Bean
    public CustomAccessDeniedHandler customAccessDeniedHandler() {
        return new CustomAccessDeniedHandler();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
//        http.formLogin()
//                .usernameParameter("username")
//                .passwordParameter("password");
//
//        http.formLogin().defaultSuccessUrl("/")
//                .failureUrl("/login?error");
//
//        http.logout().logoutSuccessUrl("/login");
//
//        http.exceptionHandling()
//                .accessDeniedPage("/login?accessDenied");

//        http.authorizeRequests().antMatchers("/").permitAll()
//            .antMatchers("/api/**")
//            .access("hasRole('ROLE_ADMIN')");
//        http.csrf().disable();
        // Disable crsf cho đường dẫn /rest/**
        http.csrf(csrf -> csrf.ignoringRequestMatchers(new AntPathRequestMatcher("/api/**")));

        http.authorizeHttpRequests(rmr -> rmr.requestMatchers(new AntPathRequestMatcher("/api/login/"), new AntPathRequestMatcher("/api/food/"), new AntPathRequestMatcher("/api/cates/")).permitAll());

//        http.securityMatcher(new AntPathRequestMatcher("/api/**"))
//                .httpBasic(b -> {
//            try {
//                b.authenticationEntryPoint(restServicesEntryPoint()).and()
//                        .sessionManagement(s -> s.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
//            } catch (Exception ex) {
//                Logger.getLogger(JwtSecurityConfig.class.getName()).log(Level.SEVERE, null, ex);
//            }
//        })
//                .authorizeHttpRequests(r -> r.requestMatchers(new AntPathRequestMatcher("/api/**", "GET")).hasAnyAuthority("ADMIN", "OWNER", "CUSTOMER")
//                                .requestMatchers(new AntPathRequestMatcher("/api/**", "POST")).hasAnyAuthority("ADMIN", "OWNER", "CUSTOMER")
//                                .requestMatchers(new AntPathRequestMatcher("/api/**", "DELETE")).hasAnyAuthority("ADMIN", "OWNER", "CUSTOMER"))
//                .addFilterBefore(jwtAuthenticationTokenFilter(), UsernamePasswordAuthenticationFilter.class)
//                .exceptionHandling(e -> {
//                
//                    try {
//                        e.accessDeniedHandler(customAccessDeniedHandler());
//                    } catch (Exception ex) {
//                        Logger.getLogger(JwtSecurityConfig.class.getName()).log(Level.SEVERE, null, ex);
//                    }
//                }
//                );
        http.authorizeHttpRequests(rmr -> {
                    try {
                        rmr.requestMatchers(new AntPathRequestMatcher("/api/**", "GET")).hasAnyAuthority("ADMIN", "OWNER", "CUSTOMER")
                                .requestMatchers(new AntPathRequestMatcher("/api/**", "POST")).hasAnyAuthority("ADMIN", "OWNER", "CUSTOMER")
                                .requestMatchers(new AntPathRequestMatcher("/api/**", "DELETE")).hasAnyAuthority("ADMIN", "OWNER", "CUSTOMER")
                                .and()
                                .httpBasic(b -> b.authenticationEntryPoint(restServicesEntryPoint()))
//                                .sessionManagement(s -> s.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                                .addFilterBefore(jwtAuthenticationTokenFilter(), UsernamePasswordAuthenticationFilter.class)
                                .exceptionHandling(e -> e.accessDeniedHandler(customAccessDeniedHandler()));
                    } catch (Exception ex) {
                        Logger.getLogger(SpringSecurityConfig.class.getName()).log(Level.SEVERE, null, ex);
                    }
                });
        

        http.userDetailsService(userDetailsService).authorizeHttpRequests(rmr->{
            try {
                rmr.requestMatchers(new AntPathRequestMatcher("/admin/**"))
                        .hasAnyAuthority("ADMIN").requestMatchers(new AntPathRequestMatcher("/js/**")).hasAnyAuthority("ADMIN")
                        .requestMatchers(new AntPathRequestMatcher("/")).authenticated()
                        .requestMatchers(new AntPathRequestMatcher("/bandatchinhanh/**")).hasAnyAuthority("ADMIN", "OWNER")
                        .and()
                        .formLogin(lg -> lg.loginPage("/login").permitAll().loginProcessingUrl("/login")
                                .successForwardUrl("/"))
                        .logout(lo -> lo.permitAll()
                                .logoutRequestMatcher(new AntPathRequestMatcher("/logout")).logoutSuccessUrl("/login"));
            } catch (Exception ex) {
                Logger.getLogger(SpringSecurityConfig.class.getName()).log(Level.SEVERE, null, ex);
            }
        }).csrf(csrf -> csrf.disable());
//        
//        
//        
//
//        
        return http.build();
    }

    //hasAuthority khac voi hasRole do hasRole se tu dong them ROLE_ vao dang truoc truong` role con hasAuthority thi khong
}
