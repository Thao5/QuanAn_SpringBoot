/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.thao.service.impl;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.thao.pojo.NguoiDung;
import com.thao.repository.CustomNguoiDungRepository;
import com.thao.repository.NguoiDungRepository;
import com.thao.service.NguoiDungService;
import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
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
public class NguoiDungServiceImpl implements NguoiDungService {

    @Autowired
    private NguoiDungRepository ndRepo;
    @Autowired
    private BCryptPasswordEncoder passwordEncoder;
    @Autowired
    private CustomNguoiDungRepository cndRepo;
    @Autowired
    private Cloudinary cloudinary;

    @Override
    public List<NguoiDung> getNDs() {
        return this.ndRepo.findAll();
    }

    @Override
    public void save(NguoiDung nd) {
        if (nd.getId() != null) {
            if (!nd.getMatKhau().equals(this.ndRepo.getReferenceById(Long.parseLong(nd.getId().toString())).getMatKhau())) {
                nd.setMatKhau(this.passwordEncoder.encode(nd.getMatKhau()));
            }
        } else{
            if(nd.getLoaiNguoiDung() != null)
                nd.setLoaiNguoiDung("NORMAL");
            nd.setMatKhau(this.passwordEncoder.encode(nd.getMatKhau()));
        }

        if (nd.getFile() != null && !nd.getFile().isEmpty()) {
            Map res;
            try {
                res = this.cloudinary.uploader().upload(nd.getFile().getBytes(),
                        ObjectUtils.asMap("resource_type", "auto"));
                nd.setAvatar(res.get("secure_url").toString());
            } catch (IOException ex) {
                Logger.getLogger(NguoiDungServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
            }
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
    public NguoiDung getNguoiDungById2(Long id) {
        return this.ndRepo.findById(id).get();
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
        u.setActive(Boolean.TRUE);
        if (!avatar.isEmpty()) {
            Map res;
            try {
                res = this.cloudinary.uploader().upload(avatar.getBytes(),
                        ObjectUtils.asMap("resource_type", "auto"));
                u.setAvatar(res.get("secure_url").toString());
            } catch (IOException ex) {
                Logger.getLogger(NguoiDungServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
            }

        }
        this.ndRepo.save(u);
        return u;
    }

    @Override
    public String changePasswordByEmail(Map<String, String> params) {
        return this.cndRepo.changePasswordByEmail(params, passwordEncoder);
    }

    @Override
    public NguoiDung changePassword(Map<String, String> params) {
        NguoiDung nd = this.cndRepo.getNDByUsername(params.get("taiKhoan"));
        nd.setMatKhau(passwordEncoder.encode(params.get("matKhauMoi")));
        
        this.ndRepo.save(nd);
        return nd;
    }

    @Override
    public Boolean isAlreadyHave(NguoiDung nd) {
        return this.cndRepo.isAlreadyHave(nd);
    }

}
