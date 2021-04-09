package com.example.qlnhanvien;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;

import android.support.v7.app.ActionBarActivity;
import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

public class AddActivity extends ActionBarActivity {
	private static final int REQUEST_TAKE_PHOTO = 0;
	final String DATABASE_NAME="QLNhanVien.db";
	final int RESQUEST_TAKE_PHOTO=123;
	final int REQUEST_CHOOSE_PHOTO=321;
	int id=-1;
	Button btnChonHinh,btnChupHinh,btnLuu,btnHuy;
	EditText edtTen,edtSDT;
	ImageView imgHinhDaiDien;
	private void addControls() {
		btnChonHinh=(Button)findViewById(R.id.btnChonHinh);
		btnChupHinh=(Button)findViewById(R.id.btnChupHinh);
		btnLuu=(Button)findViewById(R.id.btnLuu);
		btnHuy=(Button)findViewById(R.id.btnHuy);
		edtTen=(EditText) findViewById(R.id.edtTen);
		edtSDT=(EditText) findViewById(R.id.edtSdt);
		imgHinhDaiDien=(ImageView) findViewById(R.id.imgHinhDaiDien);
		 Intent intent=getIntent();
		 id=intent.getIntExtra("IDPB", -1);
	}
	private void takePictrue(){
		Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
		startActivityForResult(intent, REQUEST_TAKE_PHOTO);
	}
	private void choosePictrue(){
		Intent intent = new Intent(Intent.ACTION_PICK);
		intent.setType("image/*");
		startActivityForResult(intent, REQUEST_CHOOSE_PHOTO);
	}
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if(resultCode==RESULT_OK){
			if(requestCode==REQUEST_CHOOSE_PHOTO){
				try {
					Uri imageUri=data.getData();
					InputStream is=getContentResolver().openInputStream(imageUri);
					Bitmap bitmap = BitmapFactory.decodeStream(is);
					imgHinhDaiDien.setImageBitmap(bitmap);
				} catch (Exception e) {
					e.printStackTrace();
				}
				
			}else if(requestCode==REQUEST_TAKE_PHOTO){
				Bitmap bitmap=(Bitmap) data.getExtras().get("data");
				imgHinhDaiDien.setImageBitmap(bitmap);
			}
		}

	}
	
	private void addEvent(){
		btnChupHinh.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				takePictrue();
			}
		});
		btnChonHinh.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				choosePictrue();
				
			}
		});
		btnLuu.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				insert();
			}
		});
		btnHuy.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				cancel();
				
			}
		});
			
	}
	private void cancel() {
			Intent intent=new Intent(this,MainActivity.class);
			intent.putExtra("IDPB", id);
			startActivity(intent);
			
		}
	
	private void insert(){
		String ten=edtTen.getText().toString();
		String sdt=edtSDT.getText().toString();
		byte[] anh=getByteArrayFromImageView(imgHinhDaiDien);
		int idPB=id;
		ContentValues contentValues=new ContentValues();
		contentValues.put("Ten", ten);
		contentValues.put("SDT", sdt);
		contentValues.put("Anh", anh);
		contentValues.put("idPB", idPB);
		SQLiteDatabase database=Database.initDatabase(this, "QLNhanVien.db");
		database.insert("NhanVien", null,contentValues);
		Intent intent=new Intent(this,MainActivity.class);
		intent.putExtra("IDPB", id);
		startActivity(intent);
	}
	private byte[] getByteArrayFromImageView(ImageView imgv){
        
        BitmapDrawable drawable = (BitmapDrawable) imgv.getDrawable();
        Bitmap bmp = drawable.getBitmap();

        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.PNG, 100, stream);
        byte[] byteArray = stream.toByteArray();
        return byteArray;
}
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_add);
		addControls();
		addEvent();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.add, menu);
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
