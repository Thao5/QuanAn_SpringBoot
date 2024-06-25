/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.thao.Controllers;

import com.thao.pojo.NguoiDung;
import com.thao.service.EmailService;
import com.thao.service.NguoiDungService;
import jakarta.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
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
public class NguoiDungController {

    @Autowired
    private NguoiDungService ndSer;
    @Autowired
    private EmailService emailSer;
    @Autowired
    private Environment env;

    @RequestMapping("/nguoidung")
    public String list(Model model, @RequestParam Map<String, String> params) {
        Map<String,String> tmp = new HashMap<>();
        List<NguoiDung> listNDPages = this.ndSer.getNDCus(tmp);
        int pageSize = Integer.parseInt(this.env.getProperty("PAGE_SIZE"));
        model.addAttribute("nds", this.ndSer.getNDCus(params));
        model.addAttribute("pages", Math.ceil(listNDPages.size()*1.0/pageSize));
        if (params != null) {
            if(params.get("page") != null && !params.get("page").isEmpty())
                model.addAttribute("currentPage", Integer.parseInt(params.get("page")));
            else 
                model.addAttribute("currentPage", 0);
        }
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
            if (nd.getId() == null) {
                nd.setActive(Boolean.TRUE);
                this.emailSer.sendSimpleMessage(nd.getEmail(), "Đã tạo thành công người dùng", String.format("Đã tạo thành công người dùng %s", nd.getTaiKhoan()));
            } else{
                this.emailSer.sendSimpleMessage(nd.getEmail(), "Đã cập nhật thành công người dùng", String.format("Đã cập nhật thành công người dùng %s", nd.getTaiKhoan()));
            }
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
        this.emailSer.sendSimpleMessage(nd.getEmail(), "Đã ngừng hoạt động người dùng", String.format("Đã ngừng hoạt động người dùng %s", nd.getTaiKhoan()));
        this.ndSer.save(nd);
    }

}
