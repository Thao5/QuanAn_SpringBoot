/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.thao.Controllers;

import com.thao.pojo.DanhGia;
import com.thao.service.ChiNhanhService;
import com.thao.service.DanhGiaService;
import com.thao.service.NguoiDungService;
import jakarta.validation.Valid;
import java.util.Date;
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
public class DanhGiaController {

    @Autowired
    private DanhGiaService dgSer;
    @Autowired
    private NguoiDungService ndSer;
    @Autowired
    private ChiNhanhService cnSer;
    
    @GetMapping("/addorupdatedanhgia")
    public String update(Model model) {
        model.addAttribute("dg", new DanhGia());
        model.addAttribute("cns", this.cnSer.getChiNhanhs());
        model.addAttribute("nds", this.ndSer.getNDs());
        return "addorupdatedanhgia";
    }

    @GetMapping("/addorupdatedanhgia/{id}")
    public String update(Model model, @PathVariable("id") Long id) {
        model.addAttribute("dg", this.dgSer.getDanhGiaById(id));
        model.addAttribute("cns", this.cnSer.getChiNhanhs());
        model.addAttribute("nds", this.ndSer.getNDs());
        return "addorupdatedanhgia";
    }

    @PostMapping("/addorupdatedanhgia")
    public String addOrUpdate(Model model, @ModelAttribute(value = "dg") @Valid DanhGia dg, BindingResult rs) {
        if (!rs.hasErrors()) {
            if (dg.getId() == null) {
                dg.setCreatedDate(new Date());
            }
            this.dgSer.save(dg);
            return "redirect:/admin/chinhanh";
        }
        model.addAttribute("cns", this.cnSer.getChiNhanhs());
        model.addAttribute("nds", this.ndSer.getNDs());
        return "addorupdatedanhgia";
    }

    @RequestMapping("/deletedanhgia/{id}/")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable("id") Long id) {
        this.dgSer.delete(id);
    }
}
