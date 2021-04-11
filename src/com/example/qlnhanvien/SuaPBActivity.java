package com.example.qlnhanvien;

import android.support.v7.app.ActionBarActivity;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class SuaPBActivity extends ActionBarActivity {
	final String DATABASE_NAME="QLNV.db";
	Button btnLuuPB,btnHuyPB;
	EditText edtTenPB;
	int id=-1;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_sua_pb);
		Intent intent=getIntent();
		id=intent.getIntExtra("ID", -1);
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
				Intent intent=new Intent(SuaPBActivity.this,PhongBanActivity.class);
				startActivity(intent);
				
			}
		});
		
	}

	protected void insert() {
		
		String TenPB=edtTenPB.getText().toString();
		ContentValues contentValues=new ContentValues();
		contentValues.put("TenPB", TenPB);
		SQLiteDatabase database=Database.initDatabase(this, DATABASE_NAME);
		database.update("PhongBan",contentValues,"ID=?",new String[]{id+""});
		Intent intent=new Intent(SuaPBActivity.this,PhongBanActivity.class);
		startActivity(intent);
		
	}

	private void addControls() {
		btnHuyPB=(Button) findViewById(R.id.btnHuyPB);
		btnLuuPB=(Button) findViewById(R.id.btnLuuPB);
		edtTenPB=(EditText) findViewById(R.id.edtTenPB);
		SQLiteDatabase database=Database.initDatabase(this, DATABASE_NAME);
		database = Database.initDatabase(this, DATABASE_NAME);
		Cursor cursor=database.rawQuery("select* from PhongBan where ID=?", new String[]{id+""});
		cursor.moveToFirst();
		String tenpb=cursor.getString(1);
		edtTenPB.setText(tenpb);
		
	}
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.sua_pb, menu);
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
