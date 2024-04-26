/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.thao.Controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.thao.pojo.DanhGia;
import com.thao.pojo.DanhGiaRes;
import com.thao.pojo.Sentiment;
import com.thao.service.FoodService;
import com.thao.service.NguoiDungService;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

/**
 *
 * @author Chung Vu
 */
@Controller
@RequestMapping("/admin")
public class SentimentController {

    @Autowired
    private NguoiDungService ndSer;
    @Autowired
    private FoodService foodSer;

    @RequestMapping(value = "/sentiment", method = {RequestMethod.POST, RequestMethod.GET})
    public String list(Model model) {
        final String uri = "http://127.0.0.1:5000/getcountsentiment/";

        RestTemplate restTemplate = new RestTemplate();
        List<HttpMessageConverter<?>> messageConverters = new ArrayList<HttpMessageConverter<?>>();
        //Add the Jackson Message converter
        MappingJackson2HttpMessageConverter converter = new MappingJackson2HttpMessageConverter();

        // Note: here we are making this converter to process any kind of response,
        // not only application/*json, which is the default behaviour
        converter.setSupportedMediaTypes(Collections.singletonList(MediaType.ALL));
        messageConverters.add(converter);
        restTemplate.setMessageConverters(messageConverters);
//        Object result = restTemplate.getForObject(uri, Object.class);
        ResponseEntity<Sentiment> responseEntity = restTemplate.getForEntity(uri, Sentiment.class);

        Sentiment objects = responseEntity.getBody();
        MediaType contentType = responseEntity.getHeaders().getContentType();
        HttpStatus statusCode = (HttpStatus) responseEntity.getStatusCode();

        if (objects.getPositive() != null) {
            if (objects.getPositive().getList_comment() != null && !objects.getPositive().getList_comment().isEmpty()) {
                for (DanhGiaRes sen : objects.getPositive().getList_comment()) {
                    sen.setId_nguoi_dung(this.ndSer.getNguoiDungById(Long.parseLong(sen.getId_nguoi_dung().getId().toString())));
                    sen.setId_thuc_an(this.foodSer.getThucAnById2(Long.parseLong(sen.getId_thuc_an().getId().toString())));
                }
            }
        }
        
        if (objects.getNeutral()!= null) {
            if (objects.getNeutral().getList_comment() != null && !objects.getNeutral().getList_comment().isEmpty()) {
                for (DanhGiaRes sen : objects.getNeutral().getList_comment()) {
                    sen.setId_nguoi_dung(this.ndSer.getNguoiDungById(Long.parseLong(sen.getId_nguoi_dung().getId().toString())));
                    sen.setId_thuc_an(this.foodSer.getThucAnById2(Long.parseLong(sen.getId_thuc_an().getId().toString())));
                }
            }
        }
        
        if (objects.getNegative()!= null) {
            if (objects.getNegative().getList_comment() != null && !objects.getNegative().getList_comment().isEmpty()) {
                for (DanhGiaRes sen : objects.getNegative().getList_comment()) {
                    sen.setId_nguoi_dung(this.ndSer.getNguoiDungById(Long.parseLong(sen.getId_nguoi_dung().getId().toString())));
                    sen.setId_thuc_an(this.foodSer.getThucAnById2(Long.parseLong(sen.getId_thuc_an().getId().toString())));
                }
            }
        }

        model.addAttribute("sentiment", objects);

        return "sentiment";
    }
}
