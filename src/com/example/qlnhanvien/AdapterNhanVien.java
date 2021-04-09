package com.example.qlnhanvien;

import java.util.ArrayList;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

public class AdapterNhanVien extends BaseAdapter{
	
	Activity context;
	ArrayList<NhanVien> list;
	int idPB;

	public AdapterNhanVien(Activity context, ArrayList<NhanVien> list) {
		this.context = context;
		this.list = list;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return list.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		LayoutInflater inflater=(LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View row=inflater.inflate(R.layout.listview_row, null);
		ImageView imgHinhDaiDien = (ImageView) row.findViewById(R.id.imgHinhDaiDien);
		TextView txtid=(TextView) row.findViewById(R.id.txtID);
		TextView txtTen=(TextView) row.findViewById(R.id.txtTen);
		TextView txtSDT=(TextView) row.findViewById(R.id.txtSDT);
		
		
		final NhanVien nhanVien=list.get(position);
		idPB=nhanVien.idPB;
		txtid.setText(txtid.getText()+" "+nhanVien.id+"");
		txtTen.setText(txtTen.getText()+" "+nhanVien.ten);
		txtSDT.setText(txtSDT.getText()+" "+nhanVien.sdt);
		Bitmap bmHinhDaiDien=BitmapFactory.decodeByteArray(nhanVien.anh, 0, nhanVien.anh.length);
		imgHinhDaiDien.setImageBitmap(bmHinhDaiDien);
		return row;
	
	}

}
