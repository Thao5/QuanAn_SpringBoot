/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.thao.service;

import com.thao.pojo.NguoiDung;
import java.util.List;
import java.util.Map;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.multipart.MultipartFile;

/**
 *
 * @author Chung Vu
 */
public interface NguoiDungService extends UserDetailsService{
    List<NguoiDung> getNDs();
    void save(NguoiDung nd);
    void delete(Long id);
    NguoiDung getNguoiDungById(Long id);
    NguoiDung getNguoiDungById2(Long id);
    NguoiDung getNguoiDungByUsername(String username);
    boolean authNguoiDung(String taiKhoan, String matKhau);
    List<NguoiDung> getNDCus(Map<String, String> params);
    NguoiDung addUser(Map<String, String> params, MultipartFile avatar);
    String changePasswordByEmail(Map<String,String> params);
    NguoiDung changePassword(Map<String,String> params);
    Boolean isAlreadyHave(NguoiDung nd);
}
