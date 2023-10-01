/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.thao.pojo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.io.Serializable;
import java.util.Date;
import java.util.Set;
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
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import jakarta.persistence.Transient;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;

/**
 *
 * @author Chung Vu
 */
@Entity
@Table(name = "chi_nhanh")
@NamedQueries({
    @NamedQuery(name = "ChiNhanh.findAll", query = "SELECT c FROM ChiNhanh c"),
    @NamedQuery(name = "ChiNhanh.findById", query = "SELECT c FROM ChiNhanh c WHERE c.id = :id"),
    @NamedQuery(name = "ChiNhanh.findByDiaChi", query = "SELECT c FROM ChiNhanh c WHERE c.diaChi = :diaChi"),
    @NamedQuery(name = "ChiNhanh.findByCreatedDate", query = "SELECT c FROM ChiNhanh c WHERE c.createdDate = :createdDate"),
    @NamedQuery(name = "ChiNhanh.findByImage", query = "SELECT c FROM ChiNhanh c WHERE c.image = :image")})
public class ChiNhanh implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Size(max = 255)
    @NotEmpty(message = "Thiếu địa chỉ")
    @NotBlank(message = "Thiếu địa chỉ")
    @Column(name = "dia_chi")
    private String diaChi;
    @Column(name = "created_date")
    @Temporal(TemporalType.TIMESTAMP)
    @DateTimeFormat(iso=ISO.DATE)
    private Date createdDate;
    @Size(max = 255)
//    @NotEmpty(message = "Thiếu ảnh")
    @Column(name = "image")
    private String image;
    @JsonIgnore
    @OneToMany(mappedBy = "idChiNhanh")
    private Set<ThucAn> thucAnSet;
    @JsonIgnore
    @OneToMany(mappedBy = "idChiNhanh")
    private Set<DanhGia> danhGiaSet;
    @JoinColumn(name = "id_nguoi_dung", referencedColumnName = "id")
    @ManyToOne
    @NotNull(message = "Thiếu chủ chi nhánh")
    private NguoiDung idNguoiDung;
    @JsonIgnore
    @OneToMany(mappedBy = "idChiNhanh")
    private Set<Ban> banSet;
    @JsonIgnore
    @OneToMany(mappedBy = "idChiNhanh")
    private Set<QuyDinhVanChuyen> quyDinhVanChuyenSet;
    @Transient
    private MultipartFile file;

    public ChiNhanh() {
    }

    public ChiNhanh(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDiaChi() {
        return diaChi;
    }

    public void setDiaChi(String diaChi) {
        this.diaChi = diaChi;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public Set<ThucAn> getThucAnSet() {
        return thucAnSet;
    }

    public void setThucAnSet(Set<ThucAn> thucAnSet) {
        this.thucAnSet = thucAnSet;
    }

    public Set<DanhGia> getDanhGiaSet() {
        return danhGiaSet;
    }

    public void setDanhGiaSet(Set<DanhGia> danhGiaSet) {
        this.danhGiaSet = danhGiaSet;
    }

    public NguoiDung getIdNguoiDung() {
        return idNguoiDung;
    }

    public void setIdNguoiDung(NguoiDung idNguoiDung) {
        this.idNguoiDung = idNguoiDung;
    }

    public Set<Ban> getBanSet() {
        return banSet;
    }

    public void setBanSet(Set<Ban> banSet) {
        this.banSet = banSet;
    }

    public Set<QuyDinhVanChuyen> getQuyDinhVanChuyenSet() {
        return quyDinhVanChuyenSet;
    }

    public void setQuyDinhVanChuyenSet(Set<QuyDinhVanChuyen> quyDinhVanChuyenSet) {
        this.quyDinhVanChuyenSet = quyDinhVanChuyenSet;
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
        if (!(object instanceof ChiNhanh)) {
            return false;
        }
        ChiNhanh other = (ChiNhanh) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.thao.pojo.ChiNhanh[ id=" + id + " ]";
    }

    /**
     * @return the file
     */
    public MultipartFile getFile() {
        return file;
    }

    /**
     * @param file the file to set
     */
    public void setFile(MultipartFile file) {
        this.file = file;
    }
    
}
