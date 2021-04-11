package com.example.qlnhanvien;

import java.util.ArrayList;

import android.R.string;
import android.app.Activity;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView.FindListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

public class PhongBanAdapter extends BaseAdapter {
	final String DATABASE_NAME="QLNV.db";
	Activity context;
	ArrayList<PhongBan> listPB;
	ListView listViewPB;
	public PhongBanAdapter(Activity context, ArrayList<PhongBan> listPB) {
		super();
		this.context = context;
		this.listPB = listPB;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return listPB.size();
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
		View row=inflater.inflate(R.layout.listview_pb, null);
		TextView txtidPB=(TextView) row.findViewById(R.id.txtIDPB);
		TextView txtTenPB=(TextView) row.findViewById(R.id.txtTenPB);
		TextView txtTP=(TextView) row.findViewById(R.id.txtTP);
		TextView txtPP=(TextView) row.findViewById(R.id.txtPP);
		
		final PhongBan phongBan=listPB.get(position);
		txtidPB.setText(" "+phongBan.id+"");
		txtTenPB.setText(" "+phongBan.TenPB);
		if(phongBan.idTP==0){
			txtTP.setText(" Chưa có");
		}else{
			SQLiteDatabase database=Database.initDatabase(context, DATABASE_NAME);
			Cursor cursor=database.rawQuery("select Ten from NhanVien where ID=?", new String[]{phongBan.idTP+""});
			cursor.moveToFirst();
			String tenTP=cursor.getString(0);
			txtTP.setText(" "+tenTP);
		}
		if(phongBan.idPP==0){
			txtPP.setText(" Chưa có");
		}else{
			SQLiteDatabase database=Database.initDatabase(context, DATABASE_NAME);
			Cursor cursor=database.rawQuery("select Ten from NhanVien where ID=?", new String[]{phongBan.idPP+""});
			cursor.moveToFirst();
			String tenPP=cursor.getString(0);
			txtPP.setText(" "+tenPP);
		}
		return row;
	}

}
