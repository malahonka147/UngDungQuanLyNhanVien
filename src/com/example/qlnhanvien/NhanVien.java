package com.example.qlnhanvien;

import java.sql.Blob;

import android.R.string;

public class NhanVien {
	public int id; 
	public String sdt;
	public String ten;
	public byte[] anh;
	public int idPB;
	public NhanVien(int id, String sdt, String ten, byte[] anh, int idPB) {
		super();
		this.id = id;
		this.sdt = sdt;
		this.ten = ten;
		this.anh = anh;
		this.idPB = idPB;
	}


}
