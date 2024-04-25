/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.thao.Controllers;

import com.thao.pojo.DanhGia;
import com.thao.pojo.NguoiDung;
import com.thao.service.DanhGiaService;
import com.thao.service.NguoiDungService;
import java.security.Principal;
import java.util.Date;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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
public class ApiCommentController {
    
    @Autowired
    private DanhGiaService commentService;
    @Autowired
    private NguoiDungService ndSer;
    
    @GetMapping("/stores/{storeId}/comments/")
    @CrossOrigin
    public ResponseEntity<List<DanhGia>> listComments(@PathVariable(value = "storeId") int id) {
        return new ResponseEntity<>(this.commentService.getComments(id), HttpStatus.OK);
    }
    
    @GetMapping("/food/{foodId}/comments/")
    @CrossOrigin
    public ResponseEntity<List<DanhGia>> listCommentsFood(@PathVariable(value = "foodId") int id) {
        return new ResponseEntity<>(this.commentService.getCommentsFood(id), HttpStatus.OK);
    }
    
    @PostMapping(path="/comments/", produces = MediaType.APPLICATION_JSON_VALUE)
    @CrossOrigin
    public ResponseEntity<DanhGia> addComment(@RequestBody DanhGia comment, Principal user) {
        NguoiDung u = this.ndSer.getNguoiDungByUsername(user.getName());
        comment.setCreatedDate(new Date());
        comment.setIdNguoiDung(u);
        this.commentService.save(comment);
        
        return new ResponseEntity<>(comment, HttpStatus.CREATED);
    }
}
