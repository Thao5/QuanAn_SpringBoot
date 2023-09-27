/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.thao.Controllers;

import com.thao.pojo.ChiNhanh;
import com.thao.service.ChiNhanhService;
import com.thao.service.NguoiDungService;
import java.util.Date;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 *
 * @author Chung Vu
 */
@Controller
@RequestMapping("/admin")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class ChiNhanhController {
    @Autowired
    private ChiNhanhService storeService;
    @Autowired
    private NguoiDungService ndSer;
    
    @RequestMapping("/chinhanh")
    public String list(Model model){
        model.addAttribute("stores", this.storeService.getChiNhanhs());
        return "chinhanh";
    }
    
    @GetMapping("/addorupdatechinhanh")
    public String add(Model model){
        model.addAttribute("cns", new ChiNhanh());
        model.addAttribute("nds", this.ndSer.getNDs());
        return "addorupdatechinhanh";
    }
    
    @GetMapping("/addorupdatechinhanh/{id}")
    public String update(Model model, @PathVariable("id") Long id){
        model.addAttribute("cns", this.storeService.getChiNhanhById(id));
        model.addAttribute("nds", this.ndSer.getNDs());
        return "addorupdatechinhanh";
    }
    
    @PostMapping("/addorupdatechinhanh")
    public String addOrUpdate(@ModelAttribute(value="cns") ChiNhanh cn){
        if(cn.getId() == null) cn.setCreatedDate(new Date());
        this.storeService.save(cn);
        return "redirect:/admin/chinhanh";
    }
    
    @CrossOrigin
    @RequestMapping("/deletechinhanh/{id}/")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable("id") Long id){
        this.storeService.delete(id);
    }
}
