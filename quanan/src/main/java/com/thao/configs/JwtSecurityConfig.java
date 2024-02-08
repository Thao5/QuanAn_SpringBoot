/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.thao.configs;

import com.thao.filters.CustomAccessDeniedHandler;
import com.thao.filters.JwtAuthenticationTokenFilter;
import com.thao.filters.RestAuthenticationEntryPoint;
import jakarta.servlet.http.HttpServletRequest;
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

import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;
import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

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

    public static String vnp_PayUrl = "https://sandbox.vnpayment.vn/paymentv2/vpcpay.html";
    public static String vnp_ReturnUrl = "http://localhost:8080/quanan/admin/nguoidung";
    public static String vnp_TmnCode = "F08ACHP7";
    public static String secretKey = "KRNOBXWYMVKGUMTTTEDOQSGUORCTVUUQ";
    public static String vnp_Version = "2.1.0";
    public static String vnp_Command = "pay";
    public static String vnp_ApiUrl = "https://sandbox.vnpayment.vn/merchant_webapi/api/transaction";

    public static String md5(String message) {
        String digest = null;
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] hash = md.digest(message.getBytes("UTF-8"));
            StringBuilder sb = new StringBuilder(2 * hash.length);
            for (byte b : hash) {
                sb.append(String.format("%02x", b & 0xff));
            }
            digest = sb.toString();
        } catch (UnsupportedEncodingException ex) {
            digest = "";
        } catch (NoSuchAlgorithmException ex) {
            digest = "";
        }
        return digest;
    }

    public static String Sha256(String message) {
        String digest = null;
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] hash = md.digest(message.getBytes("UTF-8"));
            StringBuilder sb = new StringBuilder(2 * hash.length);
            for (byte b : hash) {
                sb.append(String.format("%02x", b & 0xff));
            }
            digest = sb.toString();
        } catch (UnsupportedEncodingException ex) {
            digest = "";
        } catch (NoSuchAlgorithmException ex) {
            digest = "";
        }
        return digest;
    }

    //Util for VNPAY
    public static String hashAllFields(Map fields) {
        List fieldNames = new ArrayList(fields.keySet());
        Collections.sort(fieldNames);
        StringBuilder sb = new StringBuilder();
        Iterator itr = fieldNames.iterator();
        while (itr.hasNext()) {
            String fieldName = (String) itr.next();
            String fieldValue = (String) fields.get(fieldName);
            if ((fieldValue != null) && (fieldValue.length() > 0)) {
                sb.append(fieldName);
                sb.append("=");
                sb.append(fieldValue);
            }
            if (itr.hasNext()) {
                sb.append("&");
            }
        }
        return hmacSHA512(secretKey, sb.toString());
    }

    public static String hmacSHA512(final String key, final String data) {
        try {

            if (key == null || data == null) {
                throw new NullPointerException();
            }
            final Mac hmac512 = Mac.getInstance("HmacSHA512");
            byte[] hmacKeyBytes = key.getBytes();
            final SecretKeySpec secretKey = new SecretKeySpec(hmacKeyBytes, "HmacSHA512");
            hmac512.init(secretKey);
            byte[] dataBytes = data.getBytes(StandardCharsets.UTF_8);
            byte[] result = hmac512.doFinal(dataBytes);
            StringBuilder sb = new StringBuilder(2 * result.length);
            for (byte b : result) {
                sb.append(String.format("%02x", b & 0xff));
            }
            return sb.toString();

        } catch (Exception ex) {
            return "";
        }
    }

    public static String getIpAddress(HttpServletRequest request) {
        String ipAdress;
        try {
            ipAdress = request.getHeader("X-FORWARDED-FOR");
            if (ipAdress == null) {
                ipAdress = request.getRemoteAddr();
            }
        } catch (Exception e) {
            ipAdress = "Invalid IP:" + e.getMessage();
        }
        return ipAdress;
    }

    public static String getRandomNumber(int len) {
        Random rnd = new Random();
        String chars = "0123456789";
        StringBuilder sb = new StringBuilder(len);
        for (int i = 0; i < len; i++) {
            sb.append(chars.charAt(rnd.nextInt(chars.length())));
        }
        return sb.toString();
    }

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

//    @Bean
//    public WebSecurityCustomizer webSecurityCustomizer() {
//        return (web) -> web.ignoring()
//            .requestMatchers(new AntPathRequestMatcher("/api/**"));
//    }
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
//        http.csrf(csrf -> csrf.ignoringRequestMatchers(new AntPathRequestMatcher("/api/**")));
//        http.authorizeHttpRequests(rmr -> rmr.requestMatchers(new AntPathRequestMatcher("/api/login/"),
//                 new AntPathRequestMatcher("/api/food/"),
//                 new AntPathRequestMatcher("/api/cates/"),
//                 new AntPathRequestMatcher("/api/dangky/")).permitAll()).csrf(csrf -> csrf.disable());
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
        http.userDetailsService(userDetailsService).authorizeHttpRequests(rmr -> {
            try {
                rmr.requestMatchers(new AntPathRequestMatcher("/api/login/"),
                        new AntPathRequestMatcher("/api/food/"),
                        new AntPathRequestMatcher("/api/cates/"),
                        new AntPathRequestMatcher("/api/dangky/"),
                        new AntPathRequestMatcher("/api/current-user/"),
                        new AntPathRequestMatcher("/api/pay/"),
                        new AntPathRequestMatcher("/api/payoffline/"),
                        new AntPathRequestMatcher("/api/datban/"),
                        new AntPathRequestMatcher("/api/ban/**"),
                        new AntPathRequestMatcher("/api/comments/**"),
                        new AntPathRequestMatcher("/api/stores/**"),
                        new AntPathRequestMatcher("/api/thongtinban/**"),
                        new AntPathRequestMatcher("/api/quenmatkhau/**"),
                        new AntPathRequestMatcher("/api/doimatkhau/**")).permitAll()
                        .requestMatchers(new AntPathRequestMatcher("/api/**", "GET")).hasAnyAuthority("ADMIN", "OWNER", "CUSTOMER")
                        .requestMatchers(new AntPathRequestMatcher("/api/**", "POST")).hasAnyAuthority("ADMIN", "OWNER", "CUSTOMER")
                        .requestMatchers(new AntPathRequestMatcher("/api/**", "DELETE")).hasAnyAuthority("ADMIN", "OWNER", "CUSTOMER")
                        .and()
                        .httpBasic(b -> b.authenticationEntryPoint(restServicesEntryPoint()))
                        //                        .sessionManagement(s -> s.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                        .addFilterBefore(jwtAuthenticationTokenFilter(), UsernamePasswordAuthenticationFilter.class)
                        .exceptionHandling(e -> e.accessDeniedHandler(customAccessDeniedHandler())).csrf(csrf -> csrf.ignoringRequestMatchers(new AntPathRequestMatcher("/api/**")));
            } catch (Exception ex) {
                Logger.getLogger(SpringSecurityConfig.class.getName()).log(Level.SEVERE, null, ex);
            }
        });

        http.userDetailsService(userDetailsService).authorizeHttpRequests(rmr -> {
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
