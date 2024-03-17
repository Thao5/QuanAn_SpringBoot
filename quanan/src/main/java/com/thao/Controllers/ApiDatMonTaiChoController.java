/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.thao.Controllers;

import com.lowagie.text.DocumentException;
import com.thao.components.PDFExporter;
import com.thao.pojo.FoodWrapper;
import com.thao.pojo.MonDatTaiCho;
import com.thao.service.ReceiptService;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author Chung Vu
 */
@RestController
@RequestMapping("/api")
public class ApiDatMonTaiChoController {

    @Autowired
    private ReceiptService receiptService;
    @Autowired
    private PDFExporter pdfExporter;
    
    public static HttpSession sessionTmp;
    
    private ArrayList<FoodWrapper> listFoodDaDat;

    @PostMapping("/payoffline/")
    @ResponseStatus(HttpStatus.OK)
    @CrossOrigin
    public void add(@RequestBody Map<String, MonDatTaiCho> carts, HttpServletResponse response) throws DocumentException, IOException {
        if (this.receiptService.addReceiptOff(carts)) {
            response.setContentType("application/pdf");
            DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss");
            String currentDateTime = dateFormatter.format(new Date());

            String headerKey = "Content-Disposition";
            String headerValue = "attachment; filename=hoadon_" + currentDateTime + ".pdf";
            response.setHeader(headerKey, headerValue);

            this.pdfExporter.export(response, carts);
        }
    }
    
    @PostMapping(value="/datmon/", consumes = MediaType.APPLICATION_JSON_VALUE)
    @CrossOrigin
    public ResponseEntity<List<String>> order(HttpSession session, @RequestBody FoodWrapper foods){
        Map<String, FoodWrapper> listFood;
        if(sessionTmp == null){
            listFood = (Map<String, FoodWrapper>) session.getAttribute("listFood");
        } else{
            listFood = (Map<String, FoodWrapper>) sessionTmp.getAttribute("listFood");
        } if(listFood == null){
            listFood = new HashMap<>();
            listFoodDaDat = new ArrayList<>();
        }
        boolean check = false;
        if(!listFood.containsKey(foods.getId())){
            listFood.put(foods.getId(), foods);
            check = true;
        }
        
        session.setAttribute("listFood", listFood);
        if(sessionTmp == null){
            sessionTmp = session;
        }
        sessionTmp.setAttribute("listFood", listFood);
        if(!check){
            return new ResponseEntity(foods, HttpStatus.ACCEPTED);
        }
        return new ResponseEntity(foods, HttpStatus.OK);
    }
}
