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
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.NamedQueries;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 *
 * @author Chung Vu
 */
@Entity
@Table(name = "ban")
@NamedQueries({
    @NamedQuery(name = "Ban.findAll", query = "SELECT b FROM Ban b"),
    @NamedQuery(name = "Ban.findById", query = "SELECT b FROM Ban b WHERE b.id = :id"),
    @NamedQuery(name = "Ban.findByMoTa", query = "SELECT b FROM Ban b WHERE b.moTa = :moTa"),
    @NamedQuery(name = "Ban.findByCreatedDate", query = "SELECT b FROM Ban b WHERE b.createdDate = :createdDate")})
public class Ban implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 6)
    @Column(name = "id")
    private String id;
    @Size(max = 255)
    @Column(name = "mo_ta")
    private String moTa;
    @Column(name = "created_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdDate;
    @JoinColumn(name = "id_chi_nhanh", referencedColumnName = "id")
    @ManyToOne
    private ChiNhanh idChiNhanh;

    public Ban() {
    }

    public Ban(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getMoTa() {
        return moTa;
    }

    public void setMoTa(String moTa) {
        this.moTa = moTa;
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

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Ban)) {
            return false;
        }
        Ban other = (Ban) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.thao.pojo.Ban[ id=" + id + " ]";
    }
    
}
