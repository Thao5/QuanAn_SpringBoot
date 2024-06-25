/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.thao.Controllers;

import com.thao.pojo.DanhGia;
import com.thao.service.ChiNhanhService;
import com.thao.service.DanhGiaService;
import com.thao.service.FoodService;
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
public class DanhGiaController {

    @Autowired
    private DanhGiaService dgSer;
    @Autowired
    private NguoiDungService ndSer;
    @Autowired
    private ChiNhanhService cnSer;
    @Autowired
    private FoodService foodSer;
    @Autowired
    private Environment env;
    
    @RequestMapping("/danhgia")
    public String list(Model model, @RequestParam Map<String, String> params){
        Map<String,String> tmp = new HashMap<>();
        List<DanhGia> listCommentPages = this.dgSer.getCommentsByUser(tmp);
        int pageSize = Integer.parseInt(this.env.getProperty("PAGE_SIZE"));
        model.addAttribute("dgs", this.dgSer.getCommentsByUser(params));
        model.addAttribute("pages", Math.ceil(listCommentPages.size()*1.0/pageSize));
        if (params != null) {
            if(params.get("page") != null && !params.get("page").isEmpty())
                model.addAttribute("currentPage", Integer.parseInt(params.get("page")));
            else 
                model.addAttribute("currentPage", 0);
        }
        return "danhgia";
    }
    
    @GetMapping("/addorupdatedanhgia")
    public String update(Model model) {
        Map<String, String> tmp = new HashMap<>();
        tmp.put("vaiTro", "CUSTOMER");
        model.addAttribute("dg", new DanhGia());
        model.addAttribute("nds", this.ndSer.getNDCus(tmp));
        model.addAttribute("foods", this.foodSer.getThucAns(tmp));
        return "addorupdatedanhgia";
    }

    @GetMapping("/addorupdatedanhgia/{id}")
    public String update(Model model, @PathVariable("id") Long id) {
        Map<String, String> tmp = new HashMap<>();
        tmp.put("vaiTro", "CUSTOMER");
        model.addAttribute("dg", this.dgSer.getDanhGiaById(id));
        model.addAttribute("nds", this.ndSer.getNDCus(tmp));
        model.addAttribute("foods", this.foodSer.getThucAns(tmp));
        return "addorupdatedanhgia";
    }

    @PostMapping("/addorupdatedanhgia")
    public String addOrUpdate(Model model, @ModelAttribute(value = "dg") @Valid DanhGia dg, BindingResult rs) {
        if (!rs.hasErrors()) {
            if (dg.getId() == null) {
                dg.setCreatedDate(new Date());
                dg.setIdChiNhanh(this.cnSer.getChiNhanhById(Long.parseLong(String.valueOf(4))));
            }
            this.dgSer.save(dg);
            return "redirect:/admin/danhgia";
        }
        Map<String, String> tmp = new HashMap<>();
        tmp.put("vaiTro", "CUSTOMER");
        model.addAttribute("nds", this.ndSer.getNDCus(tmp));
        model.addAttribute("foods", this.foodSer.getThucAns(tmp));
        return "addorupdatedanhgia";
    }

    @RequestMapping("/deletedanhgia/{id}/")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable("id") Long id) {
        this.dgSer.delete(id);
    }
}
