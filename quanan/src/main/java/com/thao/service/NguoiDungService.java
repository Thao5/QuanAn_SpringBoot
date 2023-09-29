/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.thao.service;

import com.thao.pojo.NguoiDung;
import java.util.List;
import org.springframework.security.core.userdetails.UserDetailsService;

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
}
