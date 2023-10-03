/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.thao.pojo;

import jakarta.validation.constraints.NotNull;
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
import jakarta.xml.bind.annotation.XmlRootElement;
import org.springframework.format.annotation.DateTimeFormat;

/**
 *
 * @author Chung Vu
 */
@Entity
@Table(name = "hoa_don_chi_tiet_tai_cho")
@NamedQueries({
    @NamedQuery(name = "HoaDonChiTietTaiCho.findAll", query = "SELECT h FROM HoaDonChiTietTaiCho h"),
    @NamedQuery(name = "HoaDonChiTietTaiCho.findById", query = "SELECT h FROM HoaDonChiTietTaiCho h WHERE h.id = :id"),
    @NamedQuery(name = "HoaDonChiTietTaiCho.findBySoLuongMua", query = "SELECT h FROM HoaDonChiTietTaiCho h WHERE h.soLuongMua = :soLuongMua"),
    @NamedQuery(name = "HoaDonChiTietTaiCho.findByTongTien", query = "SELECT h FROM HoaDonChiTietTaiCho h WHERE h.tongTien = :tongTien"),
    @NamedQuery(name = "HoaDonChiTietTaiCho.findByCreatedDate", query = "SELECT h FROM HoaDonChiTietTaiCho h WHERE h.createdDate = :createdDate")})
public class HoaDonChiTietTaiCho implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Column(name = "so_luong_mua")
    @NotNull(message = "Thiếu số lượng mua")
    private Integer soLuongMua;
    @Column(name = "tong_tien")
    @NotNull(message = "Thiếu tổng tiền")
    private Long tongTien;
    @Column(name = "created_date")
    @DateTimeFormat(iso=DateTimeFormat.ISO.DATE)
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdDate;
    @JoinColumn(name = "id_hoa_don", referencedColumnName = "id")
    @ManyToOne
    @NotNull(message = "Thiếu hóa đơn")
    private HoaDonTaiCho idHoaDon;
    @JoinColumn(name = "id_thuc_an", referencedColumnName = "id")
    @ManyToOne
    @NotNull(message = "Thiếu thức ăn")
    private ThucAn idThucAn;

    public HoaDonChiTietTaiCho() {
    }

    public HoaDonChiTietTaiCho(Integer id) {
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

    public HoaDonTaiCho getIdHoaDon() {
        return idHoaDon;
    }

    public void setIdHoaDon(HoaDonTaiCho idHoaDon) {
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
        if (!(object instanceof HoaDonChiTietTaiCho)) {
            return false;
        }
        HoaDonChiTietTaiCho other = (HoaDonChiTietTaiCho) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.thao.pojo.HoaDonChiTietTaiCho[ id=" + id + " ]";
    }
    
}
