package com.example.qlnhanvien;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Context;
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
		TextView txtPP=(TextView) row.findViewById(R.id.txtPB);
		
		final PhongBan phongBan=listPB.get(position);
		txtidPB.setText(txtidPB.getText()+" "+phongBan.id+"");
		txtTenPB.setText(txtTenPB.getText()+" "+phongBan.TenPB+"");
		if(phongBan.idTP==0){
			txtTP.setText(txtTP.getText()+" Chưa có");
		}else{
			txtTP.setText(txtTP.getText()+" "+phongBan.idTP+"");
		}
		if(phongBan.idTP==0){
			txtPP.setText(txtPP.getText()+" Chưa có");
		}else{
			txtPP.setText(txtPP.getText()+" "+phongBan.idPP+"");
		}
		return row;
	}

}
