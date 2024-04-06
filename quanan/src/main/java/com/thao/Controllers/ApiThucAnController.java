/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.thao.Controllers;

import com.thao.pojo.ThucAn;
import com.thao.service.FoodService;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
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
    public ResponseEntity<List<ThucAn>> list(@RequestBody Map<String,String> params){
        return new ResponseEntity<>(this.foodSer.getThucAnByChiNhanh(Integer.parseInt(params.get("cnId"))), HttpStatus.OK);
    }
    
    @CrossOrigin
    @GetMapping("/food/")
    public ResponseEntity<List<ThucAn>> listFood(){
        Map<String,String> tmp = new HashMap<>();
        return new ResponseEntity<>(this.foodSer.getThucAns(tmp), HttpStatus.OK);
    }
    
    @CrossOrigin
    @GetMapping("/food/{id}/")
    public ResponseEntity<ThucAn> foodDetail(@PathVariable("id") Long id){
        return new ResponseEntity<>(this.foodSer.getThucAnById2(id), HttpStatus.OK);
    }
    
    @CrossOrigin
    @PostMapping("/food/addfood/")
    public ResponseEntity<ThucAn> foodAdd(@RequestBody ThucAn ta){
        this.foodSer.save(ta);
        return new ResponseEntity<>(ta, HttpStatus.OK);
    }
    
    @CrossOrigin
    @DeleteMapping("/food/delete/{id}/")
    public ResponseEntity<Long> foodDel(@PathVariable("id") Long id){
        this.foodSer.delete(id);
        return new ResponseEntity<>(id, HttpStatus.NOT_FOUND);
    }
    
    @CrossOrigin
    @PatchMapping(path= "/food/patch/{id}/", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE},
            produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<ThucAn> foodPut(@PathVariable("id") Long id, @RequestBody ThucAn ta){
        ThucAn food = this.foodSer.getThucAnById(id);
        food.setActive(ta.getActive());
        food.setIdLoai(ta.getIdLoai());
        food.setImage(ta.getImage());
        food.setPrice(ta.getPrice());
        food.setSoLuong(ta.getSoLuong());
        this.foodSer.save(food);
        return new ResponseEntity<>(food, HttpStatus.NOT_FOUND);
    }
}
