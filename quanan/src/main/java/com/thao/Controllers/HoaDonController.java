/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.thao.Controllers;

import com.thao.pojo.HoaDon;
import com.thao.service.HoaDonService;
import com.thao.service.NguoiDungService;
import java.util.Date;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
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
public class HoaDonController {

    @Autowired
    private HoaDonService hdSer;
    @Autowired
    private NguoiDungService ndSer;
    
    @RequestMapping("/hoadon")
    public String list(Model model){
        model.addAttribute("hds", this.hdSer.getHoaDons());
        return "hoadon";
    }
    
    @GetMapping("/addorupdatehoadon")
    public String add(Model model){
        model.addAttribute("hd", new HoaDon());
        model.addAttribute("nds", this.ndSer.getNDs());
        return "addorupdatehoadon";
    }
    
    @GetMapping("/addorupdatehoadon/{id}")
    public String update(Model model, @PathVariable("id") Long id){
        model.addAttribute("hd", this.hdSer.getHoaDonById(id));
        model.addAttribute("nds", this.ndSer.getNDs());
        return "addorupdatehoadon";
    }
    
    @PostMapping("/addorupdatehoadon")
    public String addOrUpdate(@ModelAttribute(value = "hd") HoaDon hd){
        if(hd.getId() == null) hd.setCreatedDate(new Date());
        this.hdSer.save(hd);
        return "redirect:/";
    }

    @RequestMapping("/deletehoadon/{id}/")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable("id") Long id) {
        this.hdSer.delete(id);
    }
}
