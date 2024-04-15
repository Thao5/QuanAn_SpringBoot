/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.thao.Controllers;

import com.thao.pojo.ThucAn;
import com.thao.service.ChiNhanhService;
import com.thao.service.FoodService;
import java.util.Date;
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
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

/**
 *
 * @author Chung Vu
 */
@RestController
@RequestMapping("/api")
public class ApiThucAnController {

    @Autowired
    private FoodService foodSer;
    @Autowired
    private ChiNhanhService cnSer;

    @CrossOrigin
    @GetMapping("/thucan/")
    public ResponseEntity<List<ThucAn>> list(@RequestBody Map<String, String> params) {
        return new ResponseEntity<>(this.foodSer.getThucAnByChiNhanh(Integer.parseInt(params.get("cnId"))), HttpStatus.OK);
    }

    @CrossOrigin
    @GetMapping("/food/")
    public ResponseEntity<List<ThucAn>> listFood() {
        Map<String, String> tmp = new HashMap<>();
        return new ResponseEntity<>(this.foodSer.getThucAns(tmp), HttpStatus.OK);
    }
    
    @CrossOrigin
    @GetMapping("/foodall/")
    public ResponseEntity<List<ThucAn>> listFoodAll() {
        Map<String, String> tmp = new HashMap<>();
        return new ResponseEntity<>(this.foodSer.getThucAns(), HttpStatus.OK);
    }

    @CrossOrigin
    @GetMapping("/food/{id}/")
    public ResponseEntity<ThucAn> foodDetail(@PathVariable("id") Long id) {
        return new ResponseEntity<>(this.foodSer.getThucAnById2(id), HttpStatus.OK);
    }

    @CrossOrigin
    @PostMapping(path = "/food/addfood/", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE},
            produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<ThucAn> foodAdd(@RequestParam Map<String, String> params, @RequestPart MultipartFile avatar) {
        ThucAn food = this.foodSer.addFood(params, avatar);
        return new ResponseEntity<>(food, HttpStatus.OK);
    }

    @CrossOrigin
    @DeleteMapping("/food/delete/{id}/")
    public ResponseEntity<Long> foodDel(@PathVariable("id") Long id) {
        ThucAn food = this.foodSer.getThucAnById2(id);
        food.setActive(Boolean.FALSE);
        this.foodSer.save(food);
        return new ResponseEntity<>(id, HttpStatus.NO_CONTENT);
    }

    @CrossOrigin
    @PatchMapping(path = "/food/patch/{id}/")
    public ResponseEntity<ThucAn> foodPut(@PathVariable("id") Long id, @RequestParam Map<String, String> params) {
        return new ResponseEntity<>(this.foodSer.updateFood(id, params), HttpStatus.OK);
    }
    
    @CrossOrigin
    @PatchMapping(path = "/food/patchavatar/{id}/", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE},
            produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<ThucAn> foodPutAvatar(@PathVariable("id") Long id, @RequestPart MultipartFile avatar) {
        return new ResponseEntity<>(this.foodSer.updateFoodAvatar(id, avatar), HttpStatus.OK);
    }
}
