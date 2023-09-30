/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.thao.Controllers;

import com.thao.pojo.NguoiDung;
import com.thao.service.NguoiDungService;
import jakarta.validation.Valid;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
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
public class NguoiDungController {

    @Autowired
    private NguoiDungService ndSer;

    @RequestMapping("/nguoidung")
    public String list(Model model, @RequestParam Map<String,String> params) {
        model.addAttribute("nds", this.ndSer.getNDCus(params));
        return "nguoidung";
    }

    @GetMapping("/addorupdatenguoidung")
    public String add(Model model) {
        model.addAttribute("nd", new NguoiDung());
        return "addorupdatenguoidung";
    }

    @GetMapping("/addorupdatenguoidung/{id}")
    public String update(Model model, @PathVariable("id") Long id) {
        model.addAttribute("nd", this.ndSer.getNguoiDungById(id));
        return "addorupdatenguoidung";
    }

    @PostMapping("/addorupdatenguoidung")
    public String addOrUpdate(@ModelAttribute(value = "nd") @Valid NguoiDung nd, BindingResult rs) {
        if (!rs.hasErrors()) {
            nd.setActive(Boolean.TRUE);
            this.ndSer.save(nd);
            return "redirect:/admin/nguoidung";
        }
        return "addorupdatenguoidung";
    }

    @RequestMapping("/deletenguoidung/{id}/")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable("id") Long id) {
        NguoiDung nd = this.ndSer.getNguoiDungById(id);
        nd.setActive(Boolean.FALSE);
        this.ndSer.save(nd);
    }
    
    
}
