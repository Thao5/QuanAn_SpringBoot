/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.thao.Controllers;

import com.thao.pojo.QuyDinhVanChuyen;
import com.thao.service.ChiNhanhService;
import com.thao.service.QuyDinhVanChuyenService;
import jakarta.validation.Valid;
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
public class QuyDinhVanChuyenController {

    @Autowired
    private QuyDinhVanChuyenService qdSer;
    @Autowired
    private ChiNhanhService cnSer;

    @RequestMapping("/quydinh")
    public String list(Model model) {
        model.addAttribute("qds", this.qdSer.getQuyDinhs());
        return "quydinh";
    }

    @GetMapping("/addorupdatequydinh")
    public String add(Model model) {
        model.addAttribute("qd", new QuyDinhVanChuyen());
        model.addAttribute("cns", this.cnSer.getChiNhanhs());
        return "addorupdatequydinh";
    }

    @GetMapping("/addorupdatequydinh/{id}")
    public String update(Model model, @PathVariable("id") Long id) {
        model.addAttribute("qd", this.qdSer.getDinhVanChuyenById(id));
        model.addAttribute("cns", this.cnSer.getChiNhanhs());
        return "addorupdatequydinh";
    }

    @PostMapping("/addorupdatequydinh")
    public String addOrUpdate(Model model, @ModelAttribute(value = "qd") @Valid QuyDinhVanChuyen qd, BindingResult rs) {
        if (!rs.hasErrors()) {
            this.qdSer.save(qd);
            return "redirect:/";
        }
        model.addAttribute("cns", this.cnSer.getChiNhanhs());
        return "addorupdatequydinh";
    }

    @RequestMapping("/deletequydinh/{id}/")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable("id") Long id) {
        this.qdSer.delete(id);
    }
}
