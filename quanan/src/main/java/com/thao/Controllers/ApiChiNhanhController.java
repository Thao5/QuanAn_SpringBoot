/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.thao.Controllers;

import com.thao.pojo.ChiNhanh;
import com.thao.service.ChiNhanhService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author Chung Vu
 */
@RestController
@RequestMapping("/api")
public class ApiChiNhanhController {
    @Autowired
    private ChiNhanhService cnSer;
    
    @CrossOrigin
    @GetMapping("/chinhanh/")
    public ResponseEntity<List<ChiNhanh>> list(){
        return new ResponseEntity<>(this.cnSer.getChiNhanhs(), HttpStatus.OK);
    }
    
    @CrossOrigin
    @GetMapping("/chinhanh/{id}/")
    public ResponseEntity<ChiNhanh> list(@PathVariable(value = "id") Long id){
        return new ResponseEntity<>(this.cnSer.getChiNhanhById2(id), HttpStatus.OK);
    }
    
}
