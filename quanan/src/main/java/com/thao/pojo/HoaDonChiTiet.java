/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.thao.pojo;

import java.io.Serializable;
import java.util.Date;
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
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import org.springframework.format.annotation.DateTimeFormat;

/**
 *
 * @author Chung Vu
 */
@Entity
@Table(name = "hoa_don_chi_tiet")
@NamedQueries({
    @NamedQuery(name = "HoaDonChiTiet.findAll", query = "SELECT h FROM HoaDonChiTiet h"),
    @NamedQuery(name = "HoaDonChiTiet.findById", query = "SELECT h FROM HoaDonChiTiet h WHERE h.id = :id"),
    @NamedQuery(name = "HoaDonChiTiet.findBySoLuongMua", query = "SELECT h FROM HoaDonChiTiet h WHERE h.soLuongMua = :soLuongMua"),
    @NamedQuery(name = "HoaDonChiTiet.findByGiaVanChuyen", query = "SELECT h FROM HoaDonChiTiet h WHERE h.giaVanChuyen = :giaVanChuyen"),
    @NamedQuery(name = "HoaDonChiTiet.findByTongTien", query = "SELECT h FROM HoaDonChiTiet h WHERE h.tongTien = :tongTien"),
    @NamedQuery(name = "HoaDonChiTiet.findByCreatedDate", query = "SELECT h FROM HoaDonChiTiet h WHERE h.createdDate = :createdDate")})
public class HoaDonChiTiet implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @NotNull(message = "Thiếu số lượng mua")
//    @NotBlank(message = "Thiếu số lượng mua")
    @Column(name = "so_luong_mua")
    private Integer soLuongMua;
    @NotNull(message = "Thiếu giá vận chuyển")
//    @NotBlank(message = "Thiếu giá vận chuyển")
    @Column(name = "gia_van_chuyen")
    private Long giaVanChuyen;
    @NotNull(message = "Thiếu tổng tiền")
//    @NotBlank(message = "Thiếu tổng tiền")
    @Column(name = "tong_tien")
    private Long tongTien;
    @Column(name = "created_date")
    @DateTimeFormat(iso=DateTimeFormat.ISO.DATE)
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdDate;
    @JoinColumn(name = "id_hoa_don", referencedColumnName = "id")
    @ManyToOne
    @NotNull(message = "Thiếu hóa đơn")
    private HoaDon idHoaDon;
    @JoinColumn(name = "id_thuc_an", referencedColumnName = "id")
    @ManyToOne
    @NotNull(message = "Thiếu thức ăn")
    private ThucAn idThucAn;

    public HoaDonChiTiet() {
    }

    public HoaDonChiTiet(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getSoLuongMua() {
        return soLuongMua;
    }

    public void setSoLuongMua(Integer soLuongMua) {
        this.soLuongMua = soLuongMua;
    }

    public Long getGiaVanChuyen() {
        return giaVanChuyen;
    }

    public void setGiaVanChuyen(Long giaVanChuyen) {
        this.giaVanChuyen = giaVanChuyen;
    }

    public Long getTongTien() {
        return tongTien;
    }

    public void setTongTien(Long tongTien) {
        this.tongTien = tongTien;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public HoaDon getIdHoaDon() {
        return idHoaDon;
    }

    public void setIdHoaDon(HoaDon idHoaDon) {
        this.idHoaDon = idHoaDon;
    }

    public ThucAn getIdThucAn() {
        return idThucAn;
    }

    public void setIdThucAn(ThucAn idThucAn) {
        this.idThucAn = idThucAn;
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
        if (!(object instanceof HoaDonChiTiet)) {
            return false;
        }
        HoaDonChiTiet other = (HoaDonChiTiet) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.thao.pojo.HoaDonChiTiet[ id=" + id + " ]";
    }
    
}
