/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.thao.pojo;

import java.io.Serializable;
import jakarta.persistence.Basic;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.NamedQueries;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

/**
 *
 * @author Chung Vu
 */
@Entity
@Table(name = "quy_dinh_van_chuyen")
@NamedQueries({
    @NamedQuery(name = "QuyDinhVanChuyen.findAll", query = "SELECT q FROM QuyDinhVanChuyen q"),
    @NamedQuery(name = "QuyDinhVanChuyen.findById", query = "SELECT q FROM QuyDinhVanChuyen q WHERE q.id = :id"),
    @NamedQuery(name = "QuyDinhVanChuyen.findByKhoangCach", query = "SELECT q FROM QuyDinhVanChuyen q WHERE q.khoangCach = :khoangCach"),
    @NamedQuery(name = "QuyDinhVanChuyen.findByPrice", query = "SELECT q FROM QuyDinhVanChuyen q WHERE q.price = :price")})
public class QuyDinhVanChuyen implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Basic(optional = false)
    @NotNull(message = "Thiếu khoảng cách")
    @NotBlank(message = "Thiếu khoảng cách")
    @Column(name = "khoang_cach")
    private double khoangCach;
    @Basic(optional = false)
    @NotNull(message = "Thiếu giá")
    @NotBlank(message = "Thiếu giá")
    @Column(name = "price")
    private long price;
    @JoinColumn(name = "id_chi_nhanh", referencedColumnName = "id")
    @ManyToOne
    @NotNull(message = "Thiếu chi nhánh")
    private ChiNhanh idChiNhanh;

    public QuyDinhVanChuyen() {
    }

    public QuyDinhVanChuyen(Integer id) {
        this.id = id;
    }

    public QuyDinhVanChuyen(Integer id, double khoangCach, long price) {
        this.id = id;
        this.khoangCach = khoangCach;
        this.price = price;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public double getKhoangCach() {
        return khoangCach;
    }

    public void setKhoangCach(double khoangCach) {
        this.khoangCach = khoangCach;
    }

    public long getPrice() {
        return price;
    }

    public void setPrice(long price) {
        this.price = price;
    }

    public ChiNhanh getIdChiNhanh() {
        return idChiNhanh;
    }

    public void setIdChiNhanh(ChiNhanh idChiNhanh) {
        this.idChiNhanh = idChiNhanh;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof QuyDinhVanChuyen)) {
            return false;
        }
        QuyDinhVanChuyen other = (QuyDinhVanChuyen) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.thao.pojo.QuyDinhVanChuyen[ id=" + id + " ]";
    }
    
}
