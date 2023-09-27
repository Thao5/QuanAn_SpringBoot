/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.thao.service.impl;

import com.thao.pojo.DanhGia;
import com.thao.pojo.NguoiDung;
import com.thao.repository.CommentRepository;
import com.thao.repository.UserRepository;
import com.thao.service.CommentService;
import java.util.Date;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

/**
 *
 * @author Thao
 */
@Service
public class CommentServiceImpl implements CommentService{
    @Autowired
    private CommentRepository commentRepo;
    @Autowired
    private UserRepository userRepo;

    @Override
    public List<DanhGia> getComments(int storeId) {
        return this.commentRepo.getComments(storeId);
    }

    @Override
    public DanhGia addComment(DanhGia c) {
        c.setCreatedDate(new Date());
        
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        NguoiDung u = this.userRepo.getUserByUsername(authentication.getName());
        c.setIdNguoiDung(u);
        
        return this.commentRepo.addComment(c);
    }
    
}
