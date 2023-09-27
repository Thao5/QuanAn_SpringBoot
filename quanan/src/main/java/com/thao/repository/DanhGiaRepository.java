/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.thao.repository;

import com.thao.pojo.DanhGia;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 *
 * @author Chung Vu
 */
public interface DanhGiaRepository extends JpaRepository<DanhGia, Long>{
    
}
