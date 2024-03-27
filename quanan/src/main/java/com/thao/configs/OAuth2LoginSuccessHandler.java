/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.thao.configs;

import com.thao.pojo.NguoiDung;
import com.thao.service.NguoiDungService;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.List;
import java.util.Map;
import java.util.Random;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

/**
 *
 * @author Chung Vu
 */
@Component
public class OAuth2LoginSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler {

    @Autowired
    private NguoiDungService ndSer;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws ServletException, IOException {

        OAuth2AuthenticationToken oAuth2AuthenticationToken = (OAuth2AuthenticationToken) authentication;
        if ("github".equals(oAuth2AuthenticationToken.getAuthorizedClientRegistrationId())) {
            DefaultOAuth2User principal = (DefaultOAuth2User) authentication.getPrincipal();
            Map<String, Object> attributes = principal.getAttributes();
            NguoiDung nd = new NguoiDung();
            String email = String.format("%s%s", attributes.getOrDefault("login", "").toString(), "@gmail.com");
            String name = attributes.getOrDefault("login", "").toString();
            nd.setEmail(email);
            nd.setTaiKhoan(name);
            if (ndSer.isAlreadyHave(nd)) {
                NguoiDung u = this.ndSer.getNguoiDungByUsername(nd.getTaiKhoan());
                DefaultOAuth2User newUser = new DefaultOAuth2User(List.of(new SimpleGrantedAuthority(u.getVaiTro())),
                        attributes, "id");
                Authentication securityAuth = new OAuth2AuthenticationToken(newUser, List.of(new SimpleGrantedAuthority(u.getVaiTro())),
                        oAuth2AuthenticationToken.getAuthorizedClientRegistrationId());
                SecurityContextHolder.getContext().setAuthentication(securityAuth);
            } else {
                nd.setFirstName(attributes.getOrDefault("login", "").toString());
                nd.setLastName(attributes.getOrDefault("login", "").toString());
                nd.setPhone(attributes.getOrDefault("phone","").toString());
                nd.setActive(true);
                nd.setAvatar("https://res.cloudinary.com/dtlqyvkvu/image/upload/v1691990852/uyaxwbdtxbrrefc3qt7j.png");
                nd.setVaiTro("CUSTOMER");
                byte[] array = new byte[7]; // length is bounded by 7
                new Random().nextBytes(array);
                String generatedString = new String(array, Charset.forName("UTF-8"));
                nd.setMatKhau(generatedString);
                ndSer.save(nd);
                DefaultOAuth2User newUser = new DefaultOAuth2User(List.of(new SimpleGrantedAuthority(nd.getVaiTro())),
                        attributes, "id");
                Authentication securityAuth = new OAuth2AuthenticationToken(newUser, List.of(new SimpleGrantedAuthority(nd.getVaiTro())),
                        oAuth2AuthenticationToken.getAuthorizedClientRegistrationId());
                SecurityContextHolder.getContext().setAuthentication(securityAuth);
            }
        }
        this.setDefaultTargetUrl("http://localhost:8080/quanan/api/login/github/");
        this.setAlwaysUseDefaultTargetUrl(true);
        super.onAuthenticationSuccess(request, response, authentication);
    }
}
