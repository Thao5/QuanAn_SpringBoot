/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.thao.pojo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.node.ObjectNode;
import lombok.Data;

/**
 *
 * @author Chung Vu
 */
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class Sentiment {
    PosNeuNeg positive;
    PosNeuNeg neutral;
    PosNeuNeg negative;
}
