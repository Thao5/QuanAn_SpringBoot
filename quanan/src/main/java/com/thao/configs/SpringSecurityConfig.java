/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.thao.configs;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.thao.filters.CustomAccessDeniedHandler;
import com.thao.filters.JwtAuthenticationTokenFilter;
import com.thao.filters.RestAuthenticationEntryPoint;
import java.text.SimpleDateFormat;
import java.util.logging.Level;
import java.util.logging.Logger;
import jakarta.annotation.Resource;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.hibernate.validator.HibernateValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.annotation.Order;
import org.springframework.core.env.Environment;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import static org.springframework.security.authorization.AuthorityAuthorizationManager.hasAnyAuthority;
import static org.springframework.security.config.Customizer.withDefaults;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.servlet.util.matcher.MvcRequestMatcher;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;
import org.springframework.validation.beanvalidation.MethodValidationPostProcessor;
import org.springframework.validation.beanvalidation.SpringConstraintValidatorFactory;
import org.springframework.web.servlet.handler.HandlerMappingIntrospector;

/**
 *
 * @author Chung Vu
 */
@Configuration
@EntityScan("com.thao.pojo")
@EnableJpaRepositories("com.thao.repository")
@ComponentScan({
    "com.thao.repository.impl",
    "com.thao.service",
    "com.thao.Controllers",
    "com.thao.components",
    "com.thao.validation"})
@EnableWebSecurity
@EnableMethodSecurity(securedEnabled = true)
@PropertySource("classpath:configs.properties")
//@Order(2)
public class SpringSecurityConfig {

    @Autowired
    private UserDetailsService userDetailsService;

//    @Autowired
//    private AutowireCapableBeanFactory autowireCapableBeanFactory;
    @Resource
    private Environment env;

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public Cloudinary cloudinary() {
        Cloudinary cloudinary
                = new Cloudinary(ObjectUtils.asMap(
                        "cloud_name", this.env.getProperty("cloudinary.cloud_name"),
                        "api_key", this.env.getProperty("cloudinary.api_key"),
                        "api_secret", this.env.getProperty("cloudinary.api_secret"),
                        "secure", true));
        return cloudinary;
    }

//    @Bean
//    protected void configure(AuthenticationManagerBuilder auth)
//            throws Exception {
//        auth.userDetailsService(userDetailsService)
//                .passwordEncoder(passwordEncoder());
//    }
//    @Bean
//    public InMemoryUserDetailsManager userDetailsServices() {
//        UserDetails user = User.withDefaultPasswordEncoder()
//            .username("user")
//            .password("password")
//            .roles("USER")
//            .build();
//        return new InMemoryUserDetailsManager(user);
//    }
    @Bean
    public SimpleDateFormat simpleDateFormat() {
        return new SimpleDateFormat("yyyy-MM-dd");
    }

//    @Bean
//    protected SecurityFilterChain filterChain(HttpSecurity http)
//            throws Exception {
////        http.formLogin()
////                .usernameParameter("username")
////                .passwordParameter("password");
////        http.formLogin().defaultSuccessUrl("/");
////                        .failureUrl("/login?error");
////                http.logout().logoutSuccessUrl("/login");
////                http.exceptionHandling()
////                        .accessDeniedPage("/login?accessDenied");
//
//        //        http.authorizeRequests().antMatchers("/").permitAll()
//        //                .antMatchers("/admin/**")
//        //                .access("hasAuthority('ADMIN')");
//        //        http.csrf().disable();
////        http.httpBasic();
////        http.authorizeHttpRequests()
////                .requestMatchers(HttpMethod.GET, "/couponapi/coupons/**")
////                .hasAnyRole("USER", "ADMIN")
////                .requestMatchers(HttpMethod.POST, "/couponapi/coupons")
////                .hasRole("ADMIN")
////                .and().csrf().disable();
//
//        http.formLogin(form -> form
//                .loginPage("/login").loginProcessingUrl("/login")
//                .permitAll()
//        ).userDetailsService(userDetailsService).logout(logout -> logout
//                .permitAll());
////        http.securityMatcher("/**").authorizeHttpRequests(rmr -> rmr
////                .requestMatchers(AntPathRequestMatcher.antMatcher(HttpMethod.GET, "/admin/**")).authenticated()
////                .requestMatchers(AntPathRequestMatcher.antMatcher(HttpMethod.GET, "/**")).permitAll()
////        ).sessionManagement(smc -> smc
////                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
////        );
//        http.authorizeHttpRequests(rmr->rmr.anyRequest().permitAll()).csrf(csrf -> csrf.disable());
//                
//
//        return http.build();
//    }
//    @Bean
//    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
//
//        http.userDetailsService(userDetailsService).authorizeHttpRequests(rmr -> {
//            try {
//                rmr.requestMatchers(new AntPathRequestMatcher("/admin/**"))
//                        .hasAnyAuthority("ADMIN").requestMatchers(new AntPathRequestMatcher("/js/**")).hasAnyAuthority("ADMIN")
//                        .requestMatchers(new AntPathRequestMatcher("/")).authenticated()
//                        .requestMatchers(new AntPathRequestMatcher("/bandatchinhanh/**")).hasAnyAuthority("ADMIN", "OWNER")
//                        .and()
//                        .formLogin(lg -> lg.loginPage("/login").permitAll().loginProcessingUrl("/login")
//                        .successForwardUrl("/"))
//                        .logout(lo -> lo.permitAll()
//                        .logoutRequestMatcher(new AntPathRequestMatcher("/logout")).logoutSuccessUrl("/login"));
//            } catch (Exception ex) {
//                Logger.getLogger(SpringSecurityConfig.class.getName()).log(Level.SEVERE, null, ex);
//            }
//        }).csrf(csrf -> csrf.disable());
//
//        return http.build();
//    }

    @Bean
    public Validator validator(final AutowireCapableBeanFactory autowireCapableBeanFactory) {

        ValidatorFactory validatorFactory = Validation.byProvider(HibernateValidator.class)
                .configure().constraintValidatorFactory(new SpringConstraintValidatorFactory(autowireCapableBeanFactory))
                .buildValidatorFactory();
        Validator validator = validatorFactory.getValidator();

        return validator;
    }

//    public AutowireCapableBeanFactory getAutowireCapableBeanFactory() {
//        return autowireCapableBeanFactory;
//    }
//
//    public void setAutowireCapableBeanFactory(AutowireCapableBeanFactory autowireCapableBeanFactory) {
//        this.autowireCapableBeanFactory = autowireCapableBeanFactory;
//    }
//    @Bean
//    public MethodValidationPostProcessor methodValidationPostProcessor(Validator validator) {
//        MethodValidationPostProcessor methodValidationPostProcessor = new MethodValidationPostProcessor();
//        methodValidationPostProcessor.setValidator(validator);
//        return methodValidationPostProcessor;
//    }
//    @Bean
//    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
//        return authenticationConfiguration.getAuthenticationManager();
//    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();

        daoAuthenticationProvider.setUserDetailsService(userDetailsService);
        daoAuthenticationProvider.setPasswordEncoder(passwordEncoder());

        return daoAuthenticationProvider;
    }
}
