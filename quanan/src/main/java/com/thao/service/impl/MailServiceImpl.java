/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.thao.service.impl;

import com.thao.repository.MailRepository;
import com.thao.service.MailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author Thao
 */
@Service
public class MailServiceImpl implements MailService{
    @Autowired
    private MailRepository mailRepo;
    
    @Override
    public void sendMail(String email, String name) {
        this.mailRepo.sendMail(email, name);
    }
}
