/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.thao.Controllers;

import com.thao.pojo.HoaDon;
import com.thao.service.HoaDonChiTietService;
import com.thao.service.HoaDonService;
import com.thao.service.NguoiDungService;
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
public class HoaDonController {

    @Autowired
    private HoaDonService hdSer;
    @Autowired
    private NguoiDungService ndSer;
    @Autowired
    private HoaDonChiTietService hdctSer;
    @Autowired
    private Environment env;

    @RequestMapping("/hoadon")
    public String list(Model model, @RequestParam Map<String,String> params) {
        Map<String,String> tmp = new HashMap<>();
        List<HoaDon> listPages = this.hdSer.getHDs(tmp);
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
        return "hoadon";
    }

    @GetMapping("/addorupdatehoadon")
    public String add(Model model) {
        Map<String, String> tmp = new HashMap<>();
        tmp.put("vaiTro", "CUSTOMER");
        model.addAttribute("hd", new HoaDon());
        model.addAttribute("nds", this.ndSer.getNDCus(tmp));
        return "addorupdatehoadon";
    }

    @GetMapping("/addorupdatehoadon/{id}")
    public String update(Model model, @PathVariable("id") Long id) {
        Map<String, String> tmp = new HashMap<>();
        tmp.put("vaiTro", "CUSTOMER");
        model.addAttribute("hd", this.hdSer.getHoaDonById(id));
        model.addAttribute("nds", this.ndSer.getNDCus(tmp));
        return "addorupdatehoadon";
    }

    @PostMapping("/addorupdatehoadon")
    public String addOrUpdate(Model model,@ModelAttribute(value = "hd") @Valid HoaDon hd, BindingResult rs) {
        if (!rs.hasErrors()) {
            if (hd.getId() == null) {
                hd.setCreatedDate(new Date());
            }
            this.hdSer.save(hd);
            return "redirect:/admin/hoadon";
        }
        Map<String, String> tmp = new HashMap<>();
        tmp.put("vaiTro", "CUSTOMER");
        model.addAttribute("nds", this.ndSer.getNDCus(tmp));
        return "addorupdatehoadon";
    }

    @RequestMapping("/deletehoadon/{id}/")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable("id") Long id) {
        this.hdSer.delete(id);
    }
}
