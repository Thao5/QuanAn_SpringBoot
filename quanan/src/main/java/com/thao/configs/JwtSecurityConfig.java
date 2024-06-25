/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.thao.configs;

import com.paypal.base.rest.APIContext;
import com.paypal.base.rest.OAuthTokenCredential;
import com.paypal.base.rest.PayPalRESTException;
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
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;
import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.config.Customizer;
import static org.springframework.security.config.Customizer.withDefaults;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtDecoders;

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
    "com.thao.validation",
    "com.thao.configs"
})
@EnableMethodSecurity(securedEnabled = true)
public class JwtSecurityConfig {

//    @Autowired
//    private OAuth2LoginSuccessHandler oAuth2LoginSuccessHandler;
    @Autowired
    private UserDetailsService userDetailsService;
    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    public static String vnp_PayUrl = "https://sandbox.vnpayment.vn/paymentv2/vpcpay.html";
    public static String vnp_ReturnUrl = "http://localhost:8080/quanan/api/pay/";
    public static String vnp_TmnCode = "F08ACHP7";
    public static String secretKey = "KRNOBXWYMVKGUMTTTEDOQSGUORCTVUUQ";
    public static String vnp_Version = "2.1.0";
    public static String vnp_Command = "pay";
    public static String vnp_ApiUrl = "https://sandbox.vnpayment.vn/merchant_webapi/api/transaction";

    //paypal
    @Value("${paypal.client.app}")
    private String clientId;
    @Value("${paypal.client.secret}")
    private String clientSecret;
    @Value("${paypal.mode}")
    private String mode;

    public enum PaypalPaymentIntent {
        sale, authorize, order
    }

    public enum PaypalPaymentMethod {
        credit_card, paypal
    }

    @Bean
    public Map<String, String> paypalSdkConfig() {

        Map<String, String> sdkConfig = new HashMap<>();

        sdkConfig.put("mode", mode);
        return sdkConfig;
    }

    @Bean
    public OAuthTokenCredential authTokenCredential() {
        return new OAuthTokenCredential(clientId, clientSecret, paypalSdkConfig());
    }

    @Bean
    public APIContext apiContext() throws PayPalRESTException {
        APIContext apiContext = new APIContext(authTokenCredential().getAccessToken());
        apiContext.setConfigurationMap(paypalSdkConfig()
        );
        return apiContext;
    }

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

//    @Bean
//    public JwtDecoder jwtDecoder() {
//        return JwtDecoders.fromOidcIssuerLocation("http://127.0.0.1:8080/quanan/oauth2/authorization/github");
//    }
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
        http.anonymous(a -> a.disable());

//        http.authorizeHttpRequests(rmr ->{
//           rmr.requestMatchers(new AntPathRequestMatcher("https://sandbox.vnpayment.vn/**")).permitAll();
//        });
        http.authorizeHttpRequests(rmr -> {
            rmr.requestMatchers(new AntPathRequestMatcher("/api/login/"),
                    new AntPathRequestMatcher("/api/food/**"),
                    new AntPathRequestMatcher("/api/cates/"),
                    new AntPathRequestMatcher("/api/dangky/"),
                    new AntPathRequestMatcher("/api/doimatkhau/**"),
                    new AntPathRequestMatcher("/api/current-user/"),
                    new AntPathRequestMatcher("/api/login/google/")
            ).permitAll();
        });

        http.userDetailsService(userDetailsService).authorizeHttpRequests(rmr -> {
            rmr.requestMatchers(
                    new AntPathRequestMatcher("/api/food/delete/**"),
                    new AntPathRequestMatcher("/api/food/patch/**"),
                    new AntPathRequestMatcher("/api/food/addfood/**"),
                    new AntPathRequestMatcher("/api/foodall/**"),
                    new AntPathRequestMatcher("/api/stats/**"),
                    new AntPathRequestMatcher("/api/chinhanh/**"),
                    new AntPathRequestMatcher("/api/sentiment/**")
            ).hasAnyAuthority("ADMIN", "OWNER"); //                        .requestMatchers(new AntPathRequestMatcher("/api/**", "GET")).hasAnyAuthority("ADMIN", "OWNER", "CUSTOMER")
            //                        .requestMatchers(new AntPathRequestMatcher("/api/**", "POST")).hasAnyAuthority("ADMIN", "OWNER", "CUSTOMER")
            //                        .requestMatchers(new AntPathRequestMatcher("/api/**", "DELETE")).hasAnyAuthority("ADMIN", "OWNER", "CUSTOMER");
        }).httpBasic(b -> b.authenticationEntryPoint(restServicesEntryPoint()))
//                .sessionManagement(s -> s.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .addFilterBefore(jwtAuthenticationTokenFilter(), UsernamePasswordAuthenticationFilter.class)
                .exceptionHandling(e -> e.accessDeniedHandler(customAccessDeniedHandler()))
                .csrf(csrf -> csrf.ignoringRequestMatchers(new AntPathRequestMatcher("/api/**"))).cors(Customizer.withDefaults());

//        http.userDetailsService(userDetailsService)
//                .csrf(AbstractHttpConfigurer::disable)
//                .authorizeHttpRequests(auth -> {
//                    auth.requestMatchers(
//                            new AntPathRequestMatcher("/api/login/github/"),
//                            new AntPathRequestMatcher("/api/testlogin/"))
//                            .permitAll();
//                })
//                .oauth2Login(lg -> {
//                    lg.loginPage("/login").permitAll();
//                    lg.successHandler(oAuth2LoginSuccessHandler);
//                });
//                .oauth2ResourceServer(o -> o.jwt(withDefaults()));
//                .oauth2Login(withDefaults());
        http.userDetailsService(userDetailsService).authorizeHttpRequests(rmr -> {
            rmr.requestMatchers(
                    new AntPathRequestMatcher("/api/pay/"),
                    new AntPathRequestMatcher("/api/payoffline/"),
                    new AntPathRequestMatcher("/api/datban/"),
                    new AntPathRequestMatcher("/api/ban/**"),
                    new AntPathRequestMatcher("/api/thongtinban/**"),
                    new AntPathRequestMatcher("/api/doimatkhau/**"),
                    new AntPathRequestMatcher("/api/datmon/**"),
                    new AntPathRequestMatcher("/api/stores/**"),
                    new AntPathRequestMatcher("/api/comments/**"),
                    new AntPathRequestMatcher("/api/create_payment_vnpay/"),
                    new AntPathRequestMatcher("/api/pay_paypal/"),
                    new AntPathRequestMatcher("/api/pay/pay_online/**")
            ).authenticated(); //                        .requestMatchers(new AntPathRequestMatcher("/api/**", "GET")).hasAnyAuthority("ADMIN", "OWNER", "CUSTOMER")
            //                        .requestMatchers(new AntPathRequestMatcher("/api/**", "POST")).hasAnyAuthority("ADMIN", "OWNER", "CUSTOMER")
            //                        .requestMatchers(new AntPathRequestMatcher("/api/**", "DELETE")).hasAnyAuthority("ADMIN", "OWNER", "CUSTOMER");
        })
                .httpBasic(b -> b.authenticationEntryPoint(restServicesEntryPoint()))
                //                                                .sessionManagement(s -> s.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .addFilterBefore(jwtAuthenticationTokenFilter(), UsernamePasswordAuthenticationFilter.class)
                .exceptionHandling(e -> e.accessDeniedHandler(customAccessDeniedHandler()))
                .csrf(csrf -> csrf.ignoringRequestMatchers(new AntPathRequestMatcher("/api/**"))).cors(Customizer.withDefaults());

        http.userDetailsService(userDetailsService).authorizeHttpRequests(rmr -> {
            rmr.requestMatchers(new AntPathRequestMatcher("/admin/**"))
                    .hasAnyAuthority("ADMIN", "OWNER").requestMatchers(new AntPathRequestMatcher("/js/**"), 
                            new AntPathRequestMatcher("/css/**"),
                            new AntPathRequestMatcher("/image/**")).hasAnyAuthority("ADMIN")
                    .requestMatchers(new AntPathRequestMatcher("/")).authenticated();
        })
                //                        .requestMatchers(new AntPathRequestMatcher("/bandatchinhanh/**")).hasAnyAuthority("ADMIN")
                .formLogin(lg -> lg.loginPage("/login").permitAll().loginProcessingUrl("/login")
                .successForwardUrl("/"))
                .logout(lo -> lo.permitAll()
                .logoutRequestMatcher(new AntPathRequestMatcher("/logout")).logoutSuccessUrl("/login"))
                .csrf(csrf -> csrf.disable());
//        
//        
//        
//
//        

        return http.build();
    }

    //hasAuthority khac voi hasRole do hasRole se tu dong them ROLE_ vao dang truoc truong` role con hasAuthority thi khong
}
