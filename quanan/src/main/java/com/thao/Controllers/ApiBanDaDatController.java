/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.thao.Controllers;

import com.thao.pojo.Ban;
import com.thao.pojo.BanDaDat;
import com.thao.service.BanService;
import com.thao.service.EmailService;
import com.thao.service.NguoiDungService;
import jakarta.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author Chung Vu
 */
@RestController
@RequestMapping("/api")
public class ApiBanDaDatController {
    @Autowired
    private BanService banSer;
    @Autowired
    private EmailService emailSer;
    @Autowired
    private NguoiDungService ndSer;
    
    public static HttpSession sessionTmp;
    
    private ArrayList<BanDaDat> listBanDaDat;
    
    @PostMapping(value = "/datban/", consumes = MediaType.APPLICATION_JSON_VALUE)
    @CrossOrigin
    public ResponseEntity<BanDaDat> datBan(HttpSession session, @RequestBody BanDaDat ban){
        Map<String, BanDaDat> listBan;
        if(sessionTmp == null){
            listBan = (Map<String, BanDaDat>) session.getAttribute("listBan");
        } else{
            listBan = (Map<String, BanDaDat>) sessionTmp.getAttribute("listBan");
        } if(listBan == null){
            listBan = new HashMap<>();
            listBanDaDat = new ArrayList<>();
        }
        boolean check = false;
        if(!listBan.containsKey(ban.getIdBan())){
            listBan.put(ban.getIdBan(), ban);
            check = true;
        }
        
        session.setAttribute("listBan", listBan);
        if(sessionTmp == null){
            sessionTmp = session;
        }
        sessionTmp.setAttribute("listBan", listBan);
        if(!check){
            return new ResponseEntity(ban, HttpStatus.ACCEPTED);
        }
        this.emailSer.sendSimpleMessage(this.ndSer.getNguoiDungById(Long.parseLong(Integer.toString(ban.getIdNguoiDat()))).getEmail(), "Thông báo đặt bàn thành công", "Bạn đã đặt bàn thành công");
        return new ResponseEntity(ban, HttpStatus.OK);
    }
    
    @CrossOrigin
    @GetMapping("/ban/{id}/")
    public ResponseEntity<List<Ban>> list(@PathVariable(value = "id") int id){
        return new ResponseEntity<>(this.banSer.getBanTheoChiNhanh(id), HttpStatus.OK);
    }
    
    @CrossOrigin
    @GetMapping("/thongtinban/{id}/")
    public ResponseEntity<Ban> ban(@PathVariable(value = "id") Long id){
        return new ResponseEntity<>(this.banSer.getBanById(id), HttpStatus.OK);
    }
}
