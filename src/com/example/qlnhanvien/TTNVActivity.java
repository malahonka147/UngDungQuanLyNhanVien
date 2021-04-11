package com.example.qlnhanvien;

import java.util.ArrayList;

import android.R.integer;
import android.R.string;
import android.support.v7.app.ActionBarActivity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.ImageView;
import android.widget.ListView;

public class TTNVActivity extends ActionBarActivity {

	final String DATABASE_NAME="QLNV.db";
	SQLiteDatabase database;
	ListView listView;
	ArrayList<NhanVien> list;
	NhanVien NVSelected;
	AdapterNhanVien adapter;
	int id=-1;
	int idPB;
	Button btnBack;
	TextView edtID,edtTen,edtSDT,edtEmail,edtDiaChi,edtQueQuan;
	ImageView imgHinhDaiDien;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_ttnv);
		addControls();
		initUI();
	}
	private void initUI() {
		Intent intent=getIntent();
		id=intent.getIntExtra("ID", -1);
		SQLiteDatabase database = Database.initDatabase(this, DATABASE_NAME);
   	 	Cursor cursor=database.rawQuery("select * from NhanVien where ID=?", new String[]{id+""});
		cursor.moveToFirst();
		int id=cursor.getInt(0);
		String ten=cursor.getString(1);
		String sdt=cursor.getString(2);
		byte[] anh=cursor.getBlob(3);
		idPB=cursor.getInt(4);
		String email=cursor.getString(5);
		String DiaChi=cursor.getString(6);
		String Quequan=cursor.getString(7);
		Bitmap bitmap=BitmapFactory.decodeByteArray(anh, 0, anh.length);
		imgHinhDaiDien.setImageBitmap(bitmap);
		edtID.setText(""+id);
		edtTen.setText(ten);
		edtSDT.setText(sdt);
		edtEmail.setText(email);
		edtDiaChi.setText(DiaChi);
		edtQueQuan.setText(Quequan);
	}

	private void addControls() {
		edtID=(TextView) findViewById(R.id.edtID);
		edtTen=(TextView) findViewById(R.id.edtTen);
		edtSDT=(TextView) findViewById(R.id.edtSDT);
		edtEmail=(TextView) findViewById(R.id.edtEmail);
		edtDiaChi=(TextView) findViewById(R.id.edtDiaChi);
		edtQueQuan=(TextView) findViewById(R.id.edtQueQuan);
		imgHinhDaiDien=(ImageView) findViewById(R.id.imgHinhDaiDien);
		btnBack=(Button) findViewById(R.id.btnBack);
		btnBack.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent intent=new Intent(TTNVActivity.this,MainActivity.class);
				intent.putExtra("IDPB", idPB);
				startActivity(intent);
				
			}
		});

	}
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.ttnv, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
}
