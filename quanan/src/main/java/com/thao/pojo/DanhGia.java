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
import javax.validation.constraints.Size;
import org.springframework.format.annotation.DateTimeFormat;

/**
 *
 * @author Chung Vu
 */
@Entity
@Table(name = "danh_gia")
@NamedQueries({
    @NamedQuery(name = "DanhGia.findAll", query = "SELECT d FROM DanhGia d"),
    @NamedQuery(name = "DanhGia.findById", query = "SELECT d FROM DanhGia d WHERE d.id = :id"),
    @NamedQuery(name = "DanhGia.findByNoiDung", query = "SELECT d FROM DanhGia d WHERE d.noiDung = :noiDung"),
    @NamedQuery(name = "DanhGia.findByDanhGia", query = "SELECT d FROM DanhGia d WHERE d.danhGia = :danhGia"),
    @NamedQuery(name = "DanhGia.findByCreatedDate", query = "SELECT d FROM DanhGia d WHERE d.createdDate = :createdDate")})
public class DanhGia implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Size(max = 255)
    @NotEmpty(message = "Thiếu nội dung")
    @NotBlank(message = "Thiếu nội dung")
    @Column(name = "noi_dung")
    private String noiDung;
    @NotNull(message = "Thiếu đánh giá")
    @Column(name = "danh_gia")
    private Integer danhGia;
    @Column(name = "created_date")
    @DateTimeFormat(iso=DateTimeFormat.ISO.DATE)
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdDate;
    @JoinColumn(name = "id_chi_nhanh", referencedColumnName = "id")
//    @NotNull(message = "Thiếu chi nhánh")
    @ManyToOne
    private ChiNhanh idChiNhanh;
    @JoinColumn(name = "id_nguoi_dung", referencedColumnName = "id")
    @NotNull(message = "Thiếu người đánh giá")
    @ManyToOne
    private NguoiDung idNguoiDung;
    @JoinColumn(name = "id_thuc_an", referencedColumnName = "id")
    @NotNull(message = "Thiếu món ăn được đánh giá")
    @ManyToOne
    private ThucAn idThucAn;

    public DanhGia() {
    }

    public DanhGia(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNoiDung() {
        return noiDung;
    }

    public void setNoiDung(String noiDung) {
        this.noiDung = noiDung;
    }

    public Integer getDanhGia() {
        return danhGia;
    }

    public void setDanhGia(Integer danhGia) {
        this.danhGia = danhGia;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public ChiNhanh getIdChiNhanh() {
        return idChiNhanh;
    }

    public void setIdChiNhanh(ChiNhanh idChiNhanh) {
        this.idChiNhanh = idChiNhanh;
    }

    public NguoiDung getIdNguoiDung() {
        return idNguoiDung;
    }

    public void setIdNguoiDung(NguoiDung idNguoiDung) {
        this.idNguoiDung = idNguoiDung;
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
        if (!(object instanceof DanhGia)) {
            return false;
        }
        DanhGia other = (DanhGia) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.thao.pojo.DanhGia[ id=" + id + " ]";
    }

    /**
     * @return the idThucAn
     */
    public ThucAn getIdThucAn() {
        return idThucAn;
    }

    /**
     * @param idThucAn the idThucAn to set
     */
    public void setIdThucAn(ThucAn idThucAn) {
        this.idThucAn = idThucAn;
    }
    
}
