/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.thao.repository.impl;

import com.thao.repository.MailRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author Thao
 */
@Repository
@Transactional
public class MailRepositoryImpl implements MailRepository{
    @Autowired
    private JavaMailSender javaMailSender;
    
    @Override
    public void sendMail(String email, String name) {
        SimpleMailMessage newEmail =  new SimpleMailMessage();
        newEmail.setTo(email);
        newEmail.setSubject(name + " oi! Ban da dang ky nguoi dung thanh cong");
        newEmail.setText("Chuc mung "  + name + " da dang ky thanh cong tai khoan, \nChuc ban co mot trai nghiem vui ve tai FoodApp");
        
        javaMailSender.send(newEmail);
    }
    
}
