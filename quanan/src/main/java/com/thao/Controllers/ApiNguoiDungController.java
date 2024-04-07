/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.thao.Controllers;

import com.thao.components.JwtService;
import com.thao.pojo.NguoiDung;
import com.thao.pojo.UserGoogleLogin;
import com.thao.service.EmailService;
import com.thao.service.NguoiDungService;
import java.nio.charset.Charset;
import java.security.Principal;
import java.util.Map;
import java.util.Random;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

/**
 *
 * @author Chung Vu
 */
@RestController
@RequestMapping("/api")
@EnableAsync
//@EnableOAuth2Sso
//@EnableResourceServer
public class ApiNguoiDungController {

    @Autowired
    private NguoiDungService ndSer;
    @Autowired
    private JwtService jwtService;
    @Autowired
    private EmailService emailSer;

    @PostMapping("/login/")
    @CrossOrigin
    public ResponseEntity<String> login(@RequestBody NguoiDung user) {
        if (this.ndSer.authNguoiDung(user.getTaiKhoan(), user.getMatKhau()) == true) {
            String token = this.jwtService.generateTokenLogin(user.getTaiKhoan());

            return new ResponseEntity<>(token, HttpStatus.OK);
        }

        return new ResponseEntity<>("error", HttpStatus.BAD_REQUEST);
    }

    @GetMapping(path = "/current-user/", produces = MediaType.APPLICATION_JSON_VALUE)
    @CrossOrigin
    public ResponseEntity<NguoiDung> details(Principal user) {
        NguoiDung u = this.ndSer.getNguoiDungByUsername(user.getName());
        return new ResponseEntity<>(u, HttpStatus.OK);
    }

    @PostMapping(path = "/dangky/",
            consumes = {MediaType.MULTIPART_FORM_DATA_VALUE},
            produces = {MediaType.APPLICATION_JSON_VALUE})
    @CrossOrigin
    public ResponseEntity<NguoiDung> addUser(@RequestParam Map<String, String> params, @RequestPart MultipartFile avatar) {
        NguoiDung user = this.ndSer.addUser(params, avatar);
        this.emailSer.sendSimpleMessage(user.getEmail(), "Đăng ký thành công", String.format("Tài khoản %s đã đăng ký thành công", user.getTaiKhoan()));
        return new ResponseEntity<>(user, HttpStatus.CREATED);
    }

    @PostMapping("/quenmatkhau/")
    @CrossOrigin
    public ResponseEntity<String> quenMatKhau(@RequestBody Map<String, String> params) {
        String user = this.ndSer.changePasswordByEmail(params);
        if (user != null) {
            this.emailSer.sendSimpleMessage(params.get("email"), "Thông báo mật khẩu mới", String.format("Hệ thống đã đổi mật khẩu của bạn thành %s", user));
        }
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @PostMapping("/doimatkhau/")
    @CrossOrigin
    public ResponseEntity<NguoiDung> doiMatKhau(@RequestBody Map<String, String> params) {
        return new ResponseEntity<>(this.ndSer.changePassword(params), HttpStatus.OK);
    }

    @PostMapping("/login/google/")
    @CrossOrigin
    public ResponseEntity<String> loginGithub(@RequestBody UserGoogleLogin u) {
        NguoiDung nd = new NguoiDung();
        nd.setEmail(u.getEmail());
        nd.setTaiKhoan(u.getEmail());
        if (ndSer.isAlreadyHave(nd)) {
            NguoiDung us = this.ndSer.getNguoiDungByUsername(nd.getTaiKhoan());
            String token = this.jwtService.generateTokenLogin(us.getTaiKhoan());

            return new ResponseEntity<>(token, HttpStatus.OK);
        } else {
            nd.setFirstName(u.getFirstName());
            nd.setLastName(u.getLastName());
            nd.setPhone("");
            nd.setActive(true);
            nd.setAvatar(u.getAvatar());
            nd.setVaiTro("CUSTOMER");
            nd.setLoaiNguoiDung("GOOGLE");
            byte[] array = new byte[7]; // length is bounded by 7
            new Random().nextBytes(array);
            String generatedString = new String(array, Charset.forName("UTF-8"));
            nd.setMatKhau(generatedString);
            ndSer.save(nd);

            String token = this.jwtService.generateTokenLogin(nd.getTaiKhoan());
            return new ResponseEntity<>(token, HttpStatus.OK);
        }

//        return new ResponseEntity<>(
//                "error", HttpStatus.BAD_REQUEST);
    }
}
