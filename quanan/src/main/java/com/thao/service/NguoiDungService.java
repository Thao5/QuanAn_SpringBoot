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

/**
 *
 * @author Chung Vu
 */
public interface NguoiDungService extends UserDetailsService{
    List<NguoiDung> getNDs();
    void save(NguoiDung nd);
    void delete(Long id);
    NguoiDung getNguoiDungById(Long id);
    NguoiDung getNguoiDungByUsername(String username);
    boolean authNguoiDung(String taiKhoan, String matKhau);
    List<NguoiDung> getNDCus(Map<String, String> params);
}
