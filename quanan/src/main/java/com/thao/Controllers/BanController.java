/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.thao.Controllers;

import com.thao.pojo.Ban;
import com.thao.service.BanService;
import com.thao.service.ChiNhanhService;
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
public class BanController {
    @Autowired
    private BanService banSer;
    @Autowired
    private ChiNhanhService cnSer;
    
    @GetMapping("/addorupdateban/{id}")
    public String update(Model model, @PathVariable("id") Long id){
        model.addAttribute("ban", this.banSer.getBanById(id));
        model.addAttribute("cns", this.cnSer.getChiNhanhs());
        return "addorupdateban";
    }
    
    @PostMapping("/addorupdateban")
    public String addOrUpdate(@ModelAttribute(value = "ban") Ban ban){
        if(ban.getId() == null) ban.setCreatedDate(new Date());
        this.banSer.save(ban);
        return "redirect:/";
    }
    
    @RequestMapping("/deleteban/{id}/")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable("id") Long id){
        this.banSer.delete(id);
    }
}
