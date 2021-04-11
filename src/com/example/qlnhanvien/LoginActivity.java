package com.example.qlnhanvien;

import android.support.v7.app.ActionBarActivity;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class LoginActivity extends ActionBarActivity {
	final String DATABASE_NAME="QLNV.db";
	SQLiteDatabase database;
	Button btnDangNhap,btnDMK;
	EditText edtUsername,edtPassword;
	@SuppressLint("NewApi") @Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);
		btnDangNhap=(Button) findViewById(R.id.btnLogin);
		btnDMK=(Button) findViewById(R.id.btnDMK);
		edtUsername=(EditText) findViewById(R.id.edtUsername);
		edtPassword=(EditText) findViewById(R.id.edtPassword);
   	 	
		btnDangNhap.setOnClickListener(new View.OnClickListener() {
			

			@SuppressLint("NewApi") @Override
			public void onClick(View v) {
				String username= edtUsername.getText().toString();
				String password= edtPassword.getText().toString();
				
				if(username.isEmpty()){
					Toast.makeText(getApplicationContext(), "Xin vui lòng nhập tài khoản", Toast.LENGTH_LONG).show();
					edtUsername.requestFocus();
				}
				else if(password.isEmpty()){
					Toast.makeText(getApplicationContext(), "Xin vui lòng nhập mật khẩu", Toast.LENGTH_LONG).show();
					edtPassword.requestFocus();
				}
				else if(isUser(edtUsername.getText().toString(), edtPassword.getText().toString())){
					Intent intent = new Intent(LoginActivity.this, PhongBanActivity.class);
					startActivity(intent);
				}
					else{
						Toast.makeText(getApplication(),"Tài khoản hoặc mật khẩu không tồn tại", Toast.LENGTH_LONG).show();
					}
				
			}
		});
		btnDMK.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent intent=new Intent(LoginActivity.this,DoiMatKhauActivity.class);
				startActivity(intent);
				
			}
		});
	}
	private boolean isUser(String username, String password){
		try{
			database=Database.initDatabase(this, DATABASE_NAME);
			Cursor c = database.rawQuery("select * from TaiKhoan where TaiKhoan=? and MatKhau=?", new String[] {username, password});
			c.moveToFirst();
			if(c.getCount()>0)
				return true;
		}catch(Exception ex){
			Toast.makeText(this,"Lỗi hệ thống", Toast.LENGTH_LONG).show();
		}
		return false;
	}
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.login, menu);
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
