/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.thao.service.impl;

import com.thao.pojo.NguoiDung;
import com.thao.repository.CustomNguoiDungRepository;
import com.thao.repository.NguoiDungRepository;
import com.thao.service.NguoiDungService;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

/**
 *
 * @author Chung Vu
 */
@Service("userDetailsService")
public class NguoiDungServiceImpl implements NguoiDungService{
    
    @Autowired
    private NguoiDungRepository ndRepo;
    @Autowired
    private BCryptPasswordEncoder passwordEncoder;
    @Autowired
    private CustomNguoiDungRepository cndRepo;

    @Override
    public List<NguoiDung> getNDs() {
        return this.ndRepo.findAll();
    }

    @Override
    public void save(NguoiDung nd) {
        if(!nd.getMatKhau().equals(this.ndRepo.getReferenceById(Long.parseLong(nd.getId().toString())).getMatKhau())){
            nd.setMatKhau(this.passwordEncoder.encode(nd.getMatKhau()));
        }
        this.ndRepo.save(nd);
    }

    @Override
    public void delete(Long id) {
        NguoiDung nd = this.ndRepo.getReferenceById(id);
        this.ndRepo.delete(nd);
    }

    @Override
    public NguoiDung getNguoiDungById(Long id) {
        return this.ndRepo.getReferenceById(id);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        NguoiDung nd = this.cndRepo.getNDByUsername(username);
        if (nd == null) {
            throw new UsernameNotFoundException("Invalid");
        }
        Set<GrantedAuthority> authorities = new HashSet<>();
        authorities.add(new SimpleGrantedAuthority(nd.getVaiTro()));
        return new org.springframework.security.core.userdetails.User(
                nd.getTaiKhoan(), nd.getMatKhau(), authorities);
    }

    @Override
    public NguoiDung getNguoiDungByUsername(String username) {
        return this.cndRepo.getNDByUsername(username);
    }

    @Override
    public boolean authNguoiDung(String taiKhoan, String matKhau) {
        return this.cndRepo.authNguoiDung(taiKhoan, matKhau, passwordEncoder);
    }

    @Override
    public List<NguoiDung> getNDCus(Map<String, String> params) {
        return this.cndRepo.getNDCus(params);
    }

    @Override
    public NguoiDung addUser(Map<String, String> params, MultipartFile avatar) {
        NguoiDung u = new NguoiDung();
        u.setFirstName(params.get("firstName"));
        u.setLastName(params.get("lastName"));
        u.setPhone(params.get("phone"));
        u.setEmail(params.get("email"));
        u.setTaiKhoan(params.get("username"));
        u.setMatKhau(this.passwordEncoder.encode(params.get("password")));
        u.setVaiTro("CUSTOMER");
        u.setAvatar("https://res.cloudinary.com/dtlqyvkvu/image/upload/v1691990852/uyaxwbdtxbrrefc3qt7j.png");
        this.ndRepo.save(u);
        return u;
    }
    
    
}
