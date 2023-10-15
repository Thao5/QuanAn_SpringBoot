/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.thao.repository;

import com.thao.pojo.NguoiDung;
import java.util.List;
import java.util.Map;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.multipart.MultipartFile;

/**
 *
 * @author Chung Vu
 */
public interface CustomNguoiDungRepository {
    NguoiDung getNDByUsername(String username);
    boolean authNguoiDung(String taiKhoan, String matKhau, BCryptPasswordEncoder passEncoder);
    List<NguoiDung> getNDCus(Map<String, String> params);
    Boolean isAlreadyHave(NguoiDung nd);
    String changePasswordByEmail(Map<String,String> params, BCryptPasswordEncoder passwordEncoder);
}
