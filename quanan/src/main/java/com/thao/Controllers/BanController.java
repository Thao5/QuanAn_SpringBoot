/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.thao.Controllers;

import com.thao.pojo.Ban;
import com.thao.service.BanService;
import com.thao.service.ChiNhanhService;
import jakarta.validation.Valid;
import java.util.Date;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
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
public class BanController {

    @Autowired
    private BanService banSer;
    @Autowired
    private ChiNhanhService cnSer;

    @RequestMapping("/ban")
    public String list(Model model) {
        model.addAttribute("listBan", this.banSer.getBans());
        return "ban";
    }

    @GetMapping("/addorupdateban")
    public String update(Model model) {
        model.addAttribute("ban", new Ban());
        return "addorupdateban";
    }

    @GetMapping("/addorupdateban/{id}")
    public String update(Model model, @PathVariable("id") Long id) {
        model.addAttribute("ban", this.banSer.getBanById(id));
        return "addorupdateban";
    }

    @PostMapping("/addorupdateban")
    public String addOrUpdate(Model model, @ModelAttribute(value = "ban") @Valid Ban ban, BindingResult rs) {
        if (!rs.hasErrors()) {
            if (ban.getId() == null) {
                ban.setCreatedDate(new Date());
                ban.setIdChiNhanh(this.cnSer.getChiNhanhById(Long.parseLong(String.valueOf(4))));
            }
            this.banSer.save(ban);
            return "redirect:/admin/ban";
        }
        return "addorupdateban";
    }

    @RequestMapping("/deleteban/{id}/")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable("id") Long id) {
        this.banSer.delete(id);
    }
}
