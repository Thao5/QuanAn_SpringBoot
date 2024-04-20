/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.thao.Controllers;

import com.thao.service.StatsService;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author Chung Vu
 */
@RestController
@RequestMapping("/api")
public class ApiStatsController {
    @Autowired
    private StatsService statsSer;
    
    @GetMapping("/stats/")
    @CrossOrigin
    public ResponseEntity<List<Object[]>> list(Model model, @RequestParam Map<String,String> params){
        return new ResponseEntity<>(this.statsSer.getTongTienMoiThucAn(params), HttpStatus.OK);
    }
    
    @GetMapping("/stats/offline/")
    @CrossOrigin
    public ResponseEntity<List<Object[]>> listOff(Model model, @RequestParam Map<String,String> params){
        return new ResponseEntity<>(this.statsSer.getTongTienMoiThucAnOff(params), HttpStatus.OK);
    }
}
