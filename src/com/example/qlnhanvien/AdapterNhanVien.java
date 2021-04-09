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
		TextView txtid=(TextView) row.findViewById(R.id.txtIDPB);
		TextView txtTen=(TextView) row.findViewById(R.id.txtTen);
		TextView txtSDT=(TextView) row.findViewById(R.id.txtSDT);
		Button btnSua=(Button) row.findViewById(R.id.btnSua);
		Button btnXoa=(Button) row.findViewById(R.id.btnXoa);
		
		final NhanVien nhanVien=list.get(position);
		idPB=nhanVien.idPB;
		txtid.setText(txtid.getText()+" "+nhanVien.id+"");
		txtTen.setText(txtTen.getText()+" "+nhanVien.ten);
		txtSDT.setText(txtSDT.getText()+" "+nhanVien.sdt);
		Bitmap bmHinhDaiDien=BitmapFactory.decodeByteArray(nhanVien.anh, 0, nhanVien.anh.length);
		imgHinhDaiDien.setImageBitmap(bmHinhDaiDien);
		btnSua.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent=new Intent (context,UpdateActivity.class);
				intent.putExtra("ID", nhanVien.id);
				context.startActivity(intent);
			}
		});
		btnXoa.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				AlertDialog.Builder builder =new AlertDialog.Builder(context);
				builder.setIcon(android.R.drawable.ic_delete);
				builder.setTitle("Xác nhận xóa");
				builder.setMessage("Bạn có chắc chắn xóa nhân viên này?");
				builder.setPositiveButton("Có", new DialogInterface.OnClickListener() {
					
					@Override
					public void onClick(DialogInterface dialog, int which) {
						delete(nhanVien.id);
						
					}
				});
				builder.setNegativeButton("Không", new DialogInterface.OnClickListener() {
					
					@Override
					public void onClick(DialogInterface dialog, int which) {
						// TODO Auto-generated method stub
						
					}
				});
				AlertDialog dialog=builder.create();
				dialog.show();
				
			}
		});
		return row;
	
	}
	private void delete(int idNV){
		SQLiteDatabase database=Database.initDatabase(context, "QLNhanVien.db");
		database.delete("NhanVien", "ID=?", new String[]{idNV+""});
		list.clear();
		Cursor cursor=database.rawQuery("select * from NhanVien where idPB=? ", new String[]{idPB+""});
		while(cursor.moveToNext()){
			int id = cursor.getInt(0);
			String ten=cursor.getString(1);
			String SDT=cursor.getString(2);
			byte[] anh =cursor.getBlob(3);
			int idPB=cursor.getInt(4);
			list.add(new NhanVien(id, ten, SDT, anh,idPB));
			
		}
		notifyDataSetChanged();
	}
	

}
