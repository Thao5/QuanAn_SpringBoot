/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.thao.Controllers;

import com.thao.pojo.HoaDonTaiCho;
import com.thao.service.BanService;
import com.thao.service.HoaDonChiTietTaiChoService;
import com.thao.service.HoaDonTaiChoService;
import jakarta.validation.Valid;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 *
 * @author Chung Vu
 */
@Controller
@RequestMapping("/admin")
public class HoaDonTaiChoController {
    @Autowired
    private HoaDonTaiChoService hdSer;
    @Autowired
    private BanService banSer;
    @Autowired
    private HoaDonChiTietTaiChoService hdctSer;
    @Autowired
    private Environment env;
    
    @RequestMapping("/hoadontaicho")
    public String list(Model model, @RequestParam Map<String,String> params) {
        Map<String,String> tmp = new HashMap<>();
        List<HoaDonTaiCho> listPages = this.hdSer.getHDs(tmp);
        int pageSize = Integer.parseInt(this.env.getProperty("PAGE_SIZE"));
        model.addAttribute("hds", this.hdSer.getHDs(params));
        model.addAttribute("hdcts", this.hdctSer.getHDs());
        model.addAttribute("pages", Math.ceil(listPages.size()*1.0/pageSize));
        if (params != null) {
            if(params.get("page") != null && !params.get("page").isEmpty())
                model.addAttribute("currentPage", Integer.parseInt(params.get("page")));
            else 
                model.addAttribute("currentPage", 0);
        }
        return "hoadontaicho";
    }

    @GetMapping("/addorupdatehoadontaicho")
    public String add(Model model) {
        model.addAttribute("hd", new HoaDonTaiCho());
        model.addAttribute("bans", this.banSer.getBans());
        return "addorupdatehoadontaicho";
    }

    @GetMapping("/addorupdatehoadontaicho/{id}")
    public String update(Model model, @PathVariable("id") Long id) {
        model.addAttribute("hd", this.hdSer.getHoaDonById(id));
        model.addAttribute("bans", this.banSer.getBans());
        return "addorupdatehoadontaicho";
    }

    @PostMapping("/addorupdatehoadontaicho")
    public String addOrUpdate(Model model,@ModelAttribute(value = "hd") @Valid HoaDonTaiCho hd, BindingResult rs) {
        if (!rs.hasErrors()) {
            if (hd.getId() == null) {
                hd.setCreatedDate(new Date());
            }
            this.hdSer.save(hd);
            return "redirect:/admin/hoadontaicho";
        }
        
        model.addAttribute("bans", this.banSer.getBans());
        return "addorupdatehoadontaicho";
    }

    @RequestMapping("/deletehoadontaicho/{id}/")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable("id") Long id) {
        this.hdSer.delete(id);
    }
}
