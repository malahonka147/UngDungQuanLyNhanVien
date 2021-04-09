package com.example.qlnhanvien;

import android.support.v7.app.ActionBarActivity;
import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class AddPBActitivy extends ActionBarActivity {
	final String DATABASE_NAME="QLNhanVien.db";
	Button btnLuuPB,btnHuyPB;
	EditText edtTenPB;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_add_pbactitivy);
		addControls();
		addEvent();
	}

	private void addEvent() {
		btnLuuPB.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				insert();
				
			}
		});
		btnHuyPB.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent intent=new Intent(AddPBActitivy.this,PhongBanActivity.class);
				startActivity(intent);
				
			}
		});
		
	}

	protected void insert() {

		String TenPB=edtTenPB.getText().toString();
		ContentValues contentValues=new ContentValues();
		contentValues.put("TenPB", TenPB);
		SQLiteDatabase database=Database.initDatabase(this, DATABASE_NAME);
		database.insert("PhongBan", null,contentValues);
		Intent intent=new Intent(AddPBActitivy.this,PhongBanActivity.class);
		startActivity(intent);
		
	}

	private void addControls() {
		btnHuyPB=(Button) findViewById(R.id.btnHuyPB);
		btnLuuPB=(Button) findViewById(R.id.btnLuuPB);
		edtTenPB=(EditText) findViewById(R.id.edtTenPB);
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.add_pbactitivy, menu);
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
