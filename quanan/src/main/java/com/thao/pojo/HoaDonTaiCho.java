/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.thao.pojo;

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
import jakarta.xml.bind.annotation.XmlRootElement;
import jakarta.xml.bind.annotation.XmlTransient;
import org.springframework.format.annotation.DateTimeFormat;

/**
 *
 * @author Chung Vu
 */
@Entity
@Table(name = "hoa_don_tai_cho")
@NamedQueries({
    @NamedQuery(name = "HoaDonTaiCho.findAll", query = "SELECT h FROM HoaDonTaiCho h"),
    @NamedQuery(name = "HoaDonTaiCho.findById", query = "SELECT h FROM HoaDonTaiCho h WHERE h.id = :id"),
    @NamedQuery(name = "HoaDonTaiCho.findByCreatedDate", query = "SELECT h FROM HoaDonTaiCho h WHERE h.createdDate = :createdDate")})
public class HoaDonTaiCho implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Column(name = "created_date")
    @DateTimeFormat(iso=DateTimeFormat.ISO.DATE)
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdDate;
    @OneToMany(mappedBy = "idHoaDon")
    private Set<HoaDonChiTietTaiCho> hoaDonChiTietTaiChoSet;
    @JoinColumn(name = "id_ban", referencedColumnName = "id")
    @ManyToOne
    private Ban idBan;

    public HoaDonTaiCho() {
    }

    public HoaDonTaiCho(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    @XmlTransient
    public Set<HoaDonChiTietTaiCho> getHoaDonChiTietTaiChoSet() {
        return hoaDonChiTietTaiChoSet;
    }

    public void setHoaDonChiTietTaiChoSet(Set<HoaDonChiTietTaiCho> hoaDonChiTietTaiChoSet) {
        this.hoaDonChiTietTaiChoSet = hoaDonChiTietTaiChoSet;
    }

    public Ban getIdBan() {
        return idBan;
    }

    public void setIdBan(Ban idBan) {
        this.idBan = idBan;
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
        if (!(object instanceof HoaDonTaiCho)) {
            return false;
        }
        HoaDonTaiCho other = (HoaDonTaiCho) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.thao.pojo.HoaDonTaiCho[ id=" + id + " ]";
    }
    
}
