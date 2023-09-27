/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.thao.service;

import com.thao.pojo.NguoiDung;
import java.util.Map;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.multipart.MultipartFile;

/**
 *
 * @author Thao
 */
public interface UserService extends UserDetailsService{
    NguoiDung getUserByUn(String username);
    boolean authUser(String username, String password);
    NguoiDung addUser(Map<String, String> params, MultipartFile avatar);
}
