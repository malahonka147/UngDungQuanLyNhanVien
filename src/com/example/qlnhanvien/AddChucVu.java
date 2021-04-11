package com.example.qlnhanvien;

import java.util.ArrayList;

import android.support.v7.app.ActionBarActivity;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ContextMenu.ContextMenuInfo;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.AdapterView.OnItemLongClickListener;

public class AddChucVu extends ActionBarActivity {
	final String DATABASE_NAME="QLNV.db";
	SQLiteDatabase database;
	ListView listView;
	ArrayList<NhanVien> list;
	NhanVien NVSelected;
	AdapterNhanVien adapter;
	Button btnBack;
	int id=-1;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_add_chuc_vu);

        addControls();
        readData();
	}
	private void readData(){
   	 Intent intent=getIntent();
	 id=intent.getIntExtra("IDPB", -1);
   	 database = Database.initDatabase(this, DATABASE_NAME);
   	 Cursor cursor=database.rawQuery("select* from NhanVien where IDPB=?", new String[]{id+""});
   	 list.clear();
        for(int i=0;i<cursor.getCount();i++)
        {
       	 cursor.moveToPosition(i);
       	 int id=cursor.getInt(0);
       	 String ten= cursor.getString(1);
       	 String sdt=cursor.getString(2);
       	 byte[] anh =cursor.getBlob(3);
       	 int idPB=cursor.getInt(4);
       	 list.add(new NhanVien(id, sdt, ten, anh, idPB));
        }
        adapter.notifyDataSetChanged();
   }
    public void addControls(){
    	listView =(ListView) findViewById(R.id.listViewCV);
    	list=new ArrayList<>();
    	adapter=new AdapterNhanVien(this,list);
    	listView.setAdapter(adapter);
    	
    	registerForContextMenu(listView);
    	listView.setOnItemLongClickListener(new OnItemLongClickListener() {

			@Override
			public boolean onItemLongClick(AdapterView<?> parent, View view,
					int position, long id) {
				 NVSelected=list.get(position);
				return false;
			}

		});
    	btnBack=(Button) findViewById(R.id.btnBack);
    	btnBack.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent intent=new Intent(AddChucVu.this,PhongBanActivity.class);
				startActivity(intent);
				
			}
		});
    	
    }
    @Override
    public void onCreateContextMenu(ContextMenu menu, View v,
    		ContextMenuInfo menuInfo) {
    	getMenuInflater().inflate(R.menu.add_chuc_vu, menu);
    	super.onCreateContextMenu(menu, v, menuInfo);
    }
    @Override
	public boolean onContextItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.TP:
			ThemTP();
			break;
		case R.id.PP:
			ThemPP();
			break;
		default:
			break;
		}
		return super.onContextItemSelected(item);
	}
	private void ThemTP() {
		// TODO Auto-generated method stub
		int idNV=NVSelected.id;
		
		ContentValues contentValues=new ContentValues();
		contentValues.put("IDTruongPhong", idNV);
		
		SQLiteDatabase database=Database.initDatabase(this, DATABASE_NAME);
		database.update("PhongBan", contentValues, "ID=?", new String[]{id+""});
		Intent intent=new Intent(this,PhongBanActivity.class);
		startActivity(intent);
		
	}
	private void ThemPP() {
		int idNV=NVSelected.id;
		
		ContentValues contentValues=new ContentValues();
		contentValues.put("IDPhoPhong", idNV);
		
		SQLiteDatabase database=Database.initDatabase(this, DATABASE_NAME);
		database.update("PhongBan", contentValues, "id=?", new String[]{id+""});	
		Intent intent=new Intent(this,PhongBanActivity.class);
		startActivity(intent);
		
	}
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.add_chuc_vu, menu);
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
