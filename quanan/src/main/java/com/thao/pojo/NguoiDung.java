/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.thao.pojo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.thao.validation.NguoiDungDistinct;
import java.io.Serializable;
import java.util.Set;
import jakarta.persistence.Basic;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.NamedQueries;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import org.springframework.web.multipart.MultipartFile;

/**
 *
 * @author Chung Vu
 */
@Entity
@Table(name = "nguoi_dung")
@NamedQueries({
    @NamedQuery(name = "NguoiDung.findAll", query = "SELECT n FROM NguoiDung n"),
    @NamedQuery(name = "NguoiDung.findById", query = "SELECT n FROM NguoiDung n WHERE n.id = :id"),
    @NamedQuery(name = "NguoiDung.findByFirstName", query = "SELECT n FROM NguoiDung n WHERE n.firstName = :firstName"),
    @NamedQuery(name = "NguoiDung.findByLastName", query = "SELECT n FROM NguoiDung n WHERE n.lastName = :lastName"),
    @NamedQuery(name = "NguoiDung.findByTaiKhoan", query = "SELECT n FROM NguoiDung n WHERE n.taiKhoan = :taiKhoan"),
    @NamedQuery(name = "NguoiDung.findByMatKhau", query = "SELECT n FROM NguoiDung n WHERE n.matKhau = :matKhau"),
    @NamedQuery(name = "NguoiDung.findByEmail", query = "SELECT n FROM NguoiDung n WHERE n.email = :email"),
    @NamedQuery(name = "NguoiDung.findByPhone", query = "SELECT n FROM NguoiDung n WHERE n.phone = :phone"),
    @NamedQuery(name = "NguoiDung.findByAvatar", query = "SELECT n FROM NguoiDung n WHERE n.avatar = :avatar"),
    @NamedQuery(name = "NguoiDung.findByVaiTro", query = "SELECT n FROM NguoiDung n WHERE n.vaiTro = :vaiTro"),
    @NamedQuery(name = "NguoiDung.findByActive", query = "SELECT n FROM NguoiDung n WHERE n.active = :active")})
@NguoiDungDistinct(message = "Tài khoản hoặc email hoặc số điện thoại đã tồn tại")
public class NguoiDung implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Size(max = 45)
    @NotEmpty(message = "Thiếu tên")
    @NotBlank(message = "Thiếu tên")
    @Column(name = "first_name")
    private String firstName;
    @Size(max = 45)
    @NotEmpty(message = "Thiếu họ")
    @NotBlank(message = "Thiếu họ")
    @Column(name = "last_name")
    private String lastName;
    @Basic(optional = false)
    @NotNull(message = "Thiếu tài khoản")
    @NotBlank(message = "Thiếu tài khoản")
    @Size(min = 1, max = 45)
    @Column(name = "tai_khoan")
    private String taiKhoan;
    @Basic(optional = false)
    @NotNull(message = "Thiếu mật khẩu")
    @NotBlank(message = "Thiếu mật khẩu")
    @Size(min = 1, max = 100)
    @Column(name = "mat_khau")
    private String matKhau;
    // @Pattern(regexp="[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*@(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?", message="Invalid email")//if the field contains email address consider using this annotation to enforce field validation
    @Size(max = 45)
    @NotNull(message = "Thiếu email")
    @NotBlank(message = "Thiếu email")
    @Column(name = "email")
    private String email;
    @Pattern(regexp="^\\(?(\\d{3})\\)?[- ]?(\\d{3})[- ]?(\\d{4})$", message="Invalid phone/fax format, should be as xxx-xxx-xxxx")//if the field contains phone or fax number consider using this annotation to enforce field validation
    @Size(max = 45)
//    @NotNull(message = "Thiếu số điện thoại")
//    @NotBlank(message = "Thiếu số điện thoại")
    @Column(name = "phone")
    private String phone;
    @Size(max = 255)
//    @NotNull(message = "Thiếu ảnh")
//    @NotBlank(message = "Thiếu ảnh")
    @Column(name = "avatar")
    private String avatar;
    @Size(max = 10)
    @NotNull(message = "Thiếu vai trò")
    @NotBlank(message = "Thiếu vai trò")
    @Column(name = "vai_tro")
    private String vaiTro;
    @Column(name = "active")
    private Boolean active;
    @Column(name = "loai_nguoi_dung")
    private String loaiNguoiDung;
    @JsonIgnore
    @OneToMany(mappedBy = "idNguoiDung")
    private Set<DanhGia> danhGiaSet;
    @JsonIgnore
    @OneToMany(mappedBy = "idNguoiDung")
    private Set<HoaDon> hoaDonSet;
    @JsonIgnore
    @OneToMany(mappedBy = "idNguoiDung")
    private Set<ChiNhanh> chiNhanhSet;
    @Transient
    private MultipartFile file;

    public NguoiDung() {
    }

    public NguoiDung(Integer id) {
        this.id = id;
    }

    public NguoiDung(Integer id, String taiKhoan, String matKhau) {
        this.id = id;
        this.taiKhoan = taiKhoan;
        this.matKhau = matKhau;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getTaiKhoan() {
        return taiKhoan;
    }

    public void setTaiKhoan(String taiKhoan) {
        this.taiKhoan = taiKhoan;
    }

    public String getMatKhau() {
        return matKhau;
    }

    public void setMatKhau(String matKhau) {
        this.matKhau = matKhau;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getVaiTro() {
        return vaiTro;
    }

    public void setVaiTro(String vaiTro) {
        this.vaiTro = vaiTro;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public Set<DanhGia> getDanhGiaSet() {
        return danhGiaSet;
    }

    public void setDanhGiaSet(Set<DanhGia> danhGiaSet) {
        this.danhGiaSet = danhGiaSet;
    }

    public Set<HoaDon> getHoaDonSet() {
        return hoaDonSet;
    }

    public void setHoaDonSet(Set<HoaDon> hoaDonSet) {
        this.hoaDonSet = hoaDonSet;
    }

    public Set<ChiNhanh> getChiNhanhSet() {
        return chiNhanhSet;
    }

    public void setChiNhanhSet(Set<ChiNhanh> chiNhanhSet) {
        this.chiNhanhSet = chiNhanhSet;
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
        if (!(object instanceof NguoiDung)) {
            return false;
        }
        NguoiDung other = (NguoiDung) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.thao.pojo.NguoiDung[ id=" + id + " ]";
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
     * @return the loaiNguoiDung
     */
    public String getLoaiNguoiDung() {
        return loaiNguoiDung;
    }

    /**
     * @param loaiNguoiDung the loaiNguoiDung to set
     */
    public void setLoaiNguoiDung(String loaiNguoiDung) {
        this.loaiNguoiDung = loaiNguoiDung;
    }
    
}
