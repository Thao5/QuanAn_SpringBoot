/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.thao.Controllers;

import com.thao.pojo.ThucAn;
import com.thao.service.FoodService;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author Chung Vu
 */
@RestController
@RequestMapping("/api")
public class ApiThucAnController {
    @Autowired
    private FoodService foodSer;
    
    @CrossOrigin
    @GetMapping("/thucan/")
    public ResponseEntity<List<ThucAn>> list(@RequestParam Map<String,String> params){
        return new ResponseEntity<>(this.foodSer.getThucAnByChiNhanh(Integer.parseInt(params.get("cnId"))), HttpStatus.OK);
    }
}
