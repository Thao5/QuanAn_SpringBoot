/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.thao.validation;

import com.thao.pojo.NguoiDung;
import com.thao.repository.CustomNguoiDungRepository;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

/**
 *
 * @author Chung Vu
 */
public class NguoiDungDistinctValidator implements ConstraintValidator<NguoiDungDistinct, NguoiDung>{
    @Autowired
    private CustomNguoiDungRepository cusND;

    @Override
    public void initialize(NguoiDungDistinct constraintAnnotation) {
        SpringBeanAutowiringSupport.processInjectionBasedOnCurrentContext(this);    
    }

    @Override
    public boolean isValid(NguoiDung t, ConstraintValidatorContext cvc) {
        if(t.getId() == null)
            return !this.cusND.isAlreadyHave(t);
        return true;
    }
    
}
