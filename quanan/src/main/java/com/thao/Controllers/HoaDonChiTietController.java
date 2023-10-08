/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.thao.Controllers;

import com.thao.pojo.HoaDonChiTiet;
import com.thao.service.FoodService;
import com.thao.service.HoaDonChiTietService;
import com.thao.service.HoaDonService;
import jakarta.validation.Valid;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
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
public class HoaDonChiTietController {

    @Autowired
    private HoaDonChiTietService hdSer;
    @Autowired
    private FoodService foodSer;
    @Autowired
    private HoaDonService hSer;
    
    @GetMapping("/addorupdatehoadonchitiet")
    public String add(Model model) {
        Map<String, String> tmp = new HashMap<>();
        model.addAttribute("hd", new HoaDonChiTiet());
        model.addAttribute("tas", this.foodSer.getThucAns(tmp));
        model.addAttribute("hs", this.hSer.getHoaDons());
        return "addorupdatehoadonchitiet";
    }

    @GetMapping("/addorupdatehoadonchitiet/{id}")
    public String update(Model model, @PathVariable("id") Long id) {
        Map<String, String> tmp = new HashMap<>();
        model.addAttribute("hd", this.hdSer.getHoaDonChiTietById(id));
        model.addAttribute("tas", this.foodSer.getThucAns(tmp));
        model.addAttribute("hs", this.hSer.getHoaDons());
        return "addorupdatehoadonchitiet";
    }

    @PostMapping("/addorupdatehoadonchitiet")
    public String addOrUpdate(Model model, @ModelAttribute(value = "hd") @Valid HoaDonChiTiet hd, BindingResult rs) {
        if (!rs.hasErrors()) {
            if (hd.getId() == null) {
                hd.setCreatedDate(new Date());
            }
            this.hdSer.save(hd);
            return "redirect:/admin/hoadon";
        }
        Map<String, String> tmp = new HashMap<>();
        model.addAttribute("tas", this.foodSer.getThucAns(tmp));
        model.addAttribute("hs", this.hSer.getHoaDons());
        return "addorupdatehoadonchitiet";
    }

    @RequestMapping("/deletehoadonchitiet/{id}/")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable("id") Long id) {
        this.hdSer.delete(id);
    }
}
