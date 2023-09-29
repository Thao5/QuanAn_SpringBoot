/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.thao.Controllers;

import com.thao.components.JwtService;
import com.thao.pojo.NguoiDung;
import com.thao.service.NguoiDungService;
import java.security.Principal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author Chung Vu
 */
@RestController
@RequestMapping("/api")
public class ApiNguoiDungController {
    @Autowired
    private NguoiDungService ndSer;
    @Autowired
    private JwtService jwtService;
    
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
}
