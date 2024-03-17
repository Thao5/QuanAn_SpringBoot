/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.thao.pojo;

import com.fasterxml.jackson.annotation.JsonIgnore;
//import com.thao.validation.FoodDistinct;
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
import jakarta.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import org.hibernate.validator.constraints.UniqueElements;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.multipart.MultipartFile;

/**
 *
 * @author Chung Vu
 */
@Entity
@Table(name = "thuc_an")
@NamedQueries({
    @NamedQuery(name = "ThucAn.findAll", query = "SELECT t FROM ThucAn t"),
    @NamedQuery(name = "ThucAn.findById", query = "SELECT t FROM ThucAn t WHERE t.id = :id"),
    @NamedQuery(name = "ThucAn.findByName", query = "SELECT t FROM ThucAn t WHERE t.name = :name"),
    @NamedQuery(name = "ThucAn.findBySoLuong", query = "SELECT t FROM ThucAn t WHERE t.soLuong = :soLuong"),
    @NamedQuery(name = "ThucAn.findByPrice", query = "SELECT t FROM ThucAn t WHERE t.price = :price"),
    @NamedQuery(name = "ThucAn.findByCreatedDate", query = "SELECT t FROM ThucAn t WHERE t.createdDate = :createdDate"),
    @NamedQuery(name = "ThucAn.findByImage", query = "SELECT t FROM ThucAn t WHERE t.image = :image")})
//@FoodDistinct(message = "Chi nhánh đã có món này rồi")
public class ThucAn implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Size(max = 45)
    @NotNull(message = "Thiếu tên thức ăn")
    @NotBlank(message = "Thiếu tên thức ăn")
    @Column(name = "name")
    private String name;
    @NotNull(message = "Thiếu số lượng")
//    @NotBlank(message = "Thiếu số lượng")
    @Column(name = "so_luong")
    private Integer soLuong;
    @Column(name = "price")
    @NotNull(message = "Thiếu giá")
//    @NotBlank(message = "Thiếu giá")
    private Long price;
    @Column(name = "created_date")
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdDate;
    @Size(max = 255)
//    @NotNull(message = "Thiếu ảnh")
//    @NotBlank(message = "Thiếu ảnh")
    @Column(name = "image")
    private String image;
    @JoinColumn(name = "id_loai", referencedColumnName = "id")
    @ManyToOne
    @NotNull(message = "Thiếu loại")
    private Category idLoai;
    @JoinColumn(name = "id_chi_nhanh", referencedColumnName = "id")
//    @NotNull(message = "Thiếu chi nhánh")
    @ManyToOne
    private ChiNhanh idChiNhanh;
    @Column(name = "active")
    private Boolean active;
    @JsonIgnore
    @OneToMany(mappedBy = "idThucAn")
    private Set<HoaDonChiTiet> hoaDonChiTietSet;
    @Transient
    private MultipartFile file;

    public ThucAn() {
    }

    public ThucAn(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getSoLuong() {
        return soLuong;
    }

    public void setSoLuong(Integer soLuong) {
        this.soLuong = soLuong;
    }

    public Long getPrice() {
        return price;
    }

    public void setPrice(Long price) {
        this.price = price;
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

    public Category getIdLoai() {
        return idLoai;
    }

    public void setIdLoai(Category idLoai) {
        this.idLoai = idLoai;
    }

    public ChiNhanh getIdChiNhanh() {
        return idChiNhanh;
    }

    public void setIdChiNhanh(ChiNhanh idChiNhanh) {
        this.idChiNhanh = idChiNhanh;
    }

    public Set<HoaDonChiTiet> getHoaDonChiTietSet() {
        return hoaDonChiTietSet;
    }

    public void setHoaDonChiTietSet(Set<HoaDonChiTiet> hoaDonChiTietSet) {
        this.hoaDonChiTietSet = hoaDonChiTietSet;
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
        if (!(object instanceof ThucAn)) {
            return false;
        }
        ThucAn other = (ThucAn) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.thao.pojo.ThucAn[ id=" + id + " ]";
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

    /**
     * @return the active
     */
    public Boolean getActive() {
        return active;
    }

    /**
     * @param active the active to set
     */
    public void setActive(Boolean active) {
        this.active = active;
    }

}
