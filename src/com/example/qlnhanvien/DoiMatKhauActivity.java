package com.example.qlnhanvien;

import android.support.v7.app.ActionBarActivity;
import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

@TargetApi(Build.VERSION_CODES.GINGERBREAD) @SuppressLint("NewApi") public class DoiMatKhauActivity extends ActionBarActivity {
	final String DATABASE_NAME="QLNV.db";
	SQLiteDatabase database;
	Button btnDoiMatKhau;
	EditText edtUsername,edtPassword,edtNewPassword,edtConfimPassword;
	@SuppressLint("NewApi") @Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_doi_mat_khau);
		btnDoiMatKhau=(Button) findViewById(R.id.btnDoiMatKhau);
		edtNewPassword=(EditText) findViewById(R.id.edtNewPassword);
		edtUsername=(EditText) findViewById(R.id.edtUsername);
		edtPassword=(EditText) findViewById(R.id.edtPassword);
		edtConfimPassword=(EditText) findViewById(R.id.edtConfimPassword);
		btnDoiMatKhau.setOnClickListener(new View.OnClickListener() {
			
			@SuppressLint("NewApi") @Override
			public void onClick(View v) {
				DoiMatKhau();
			
				
			}
		});
	}
	
	@TargetApi(Build.VERSION_CODES.GINGERBREAD) @SuppressLint("NewApi") protected void DoiMatKhau() {
		String username= edtUsername.getText().toString();
		String password= edtPassword.getText().toString();
		String confimpassword=edtConfimPassword.getText().toString();
		String newpassword=edtNewPassword.getText().toString();
		if(username.isEmpty()){
			Toast.makeText(getApplicationContext(), "Xin vui lòng nhập tài khoản", Toast.LENGTH_LONG).show();
			edtUsername.requestFocus();
		}
		else if(password.isEmpty()){
			Toast.makeText(getApplicationContext(), "Xin vui lòng nhập mật khẩu", Toast.LENGTH_LONG).show();
			edtPassword.requestFocus();
		}
		else if(newpassword.isEmpty()){
			Toast.makeText(getApplicationContext(), "Xin vui lòng nhập mật khẩu mới", Toast.LENGTH_LONG).show();
			edtNewPassword.requestFocus();
		}
		else if(confimpassword.isEmpty()){
			Toast.makeText(getApplicationContext(), "Xin vui lòng nhập mật khẩu mới", Toast.LENGTH_LONG).show();
			edtConfimPassword.requestFocus();
		}
		else if(confimpassword.compareTo(newpassword)!=0){
			Toast.makeText(getApplicationContext(), "Mật khẩu nhập lại không khớp", Toast.LENGTH_LONG).show();
			edtConfimPassword.requestFocus();
		}
		else if(isUser(edtUsername.getText().toString(), edtPassword.getText().toString())){
			SQLiteDatabase database = Database.initDatabase(this, DATABASE_NAME);
			ContentValues contentValues=new ContentValues();
			contentValues.put("MatKhau", newpassword);
			database.update("TaiKhoan", contentValues, "TaiKhoan=?", new String[]{username});
			Intent intent = new Intent(DoiMatKhauActivity.this, LoginActivity.class);
			startActivity(intent);
		}
			else{
				Toast.makeText(getApplication(),"Tài khoản hoặc mật khẩu không tồn tại", Toast.LENGTH_LONG).show();
			}
		
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
		getMenuInflater().inflate(R.menu.doi_mat_khau, menu);
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
