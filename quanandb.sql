use quanan;

create table nguoi_dung
(
	id int primary key auto_increment,
    first_name nvarchar(45),
    last_name nvarchar(45),
    tai_khoan nvarchar(45) not null,
    mat_khau nvarchar(100) not null,
    email nvarchar(45),
    phone nvarchar(45),
    avatar nvarchar(255),
    vai_tro nvarchar(10),
    active bit(1)
);

create table chi_nhanh
(
	id int primary key auto_increment,
    dia_chi nvarchar(255),
    created_date datetime default now(),
	image nvarchar(255),
    id_nguoi_dung int,
    foreign key(id_nguoi_dung) references nguoi_dung(id)
);

create table hoa_don
(
	id int primary key auto_increment,
    created_date datetime default now(),
    id_nguoi_dung int,
    foreign key(id_nguoi_dung) references nguoi_dung(id)
);

create table category
(
	id int primary key auto_increment,
    name nvarchar(45)
);

create table thuc_an
(
	id int primary key auto_increment,
    name nvarchar(45),
    so_luong int,
    price decimal,
    created_date datetime default now(),
    image nvarchar(255),
    id_loai int,
    foreign key(id_loai) references category(id),
    id_chi_nhanh int,
    foreign key(id_chi_nhanh) references chi_nhanh(id)
);


create table hoa_don_chi_tiet
(
	id int primary key auto_increment,
    so_luong_mua int,
    gia_van_chuyen decimal,
    tong_tien decimal,
    created_date datetime default now(),
    id_hoa_don int,
    foreign key(id_hoa_don) references hoa_don(id),
    id_thuc_an int,
    foreign key(id_thuc_an) references thuc_an(id)
);

create table danh_gia
(
	id int primary key auto_increment,
    noi_dung nvarchar(255),
    danh_gia int,
    created_date datetime default now(),
    id_nguoi_dung int,
    foreign key(id_nguoi_dung) references nguoi_dung(id),
    id_chi_nhanh int,
    foreign key(id_chi_nhanh) references chi_nhanh(id)
);

create table ban
(
	id nvarchar(6) primary key,
    mo_ta nvarchar(255),
    created_date datetime default now(),
    id_chi_nhanh int,
    foreign key(id_chi_nhanh) references chi_nhanh(id)
);

create table quy_dinh_van_chuyen
(
	id int primary key auto_increment,
    khoang_cach double not null,
    price decimal not null,
    id_chi_nhanh int,
    foreign key(id_chi_nhanh) references chi_nhanh(id)
);

drop table danh_gia;
drop table hoa_don_chi_tiet;
drop table hoa_don;
drop table thuc_an;
drop table cua_hang;
drop table nguoi_dung;
drop table category;