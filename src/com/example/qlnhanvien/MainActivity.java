package com.example.qlnhanvien;

import java.sql.Blob;
import java.util.ArrayList;

import android.support.v7.app.ActionBarActivity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;


public class MainActivity extends ActionBarActivity {

	final String DATABASE_NAME="QLNhanVien.db";
	SQLiteDatabase database;
	ListView listView;
	ArrayList<NhanVien> list;
	AdapterNhanVien adapter;
	Button btnAdd;
	Button btnBack;
	int id=-1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
       
        addControls();
        readData();
    }
   
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
        
    }
    public void addControls(){
    	listView =(ListView) findViewById(R.id.listView);
    	list=new ArrayList<>();
    	adapter=new AdapterNhanVien(this,list);
    	listView.setAdapter(adapter);
    	btnAdd=(Button) findViewById(R.id.btnAdd);
    	
    	btnAdd.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent intent=new Intent(MainActivity.this,AddActivity.class);
				intent.putExtra("IDPB", id);
				startActivity(intent);
			}
		});
    	btnBack=(Button) findViewById(R.id.btnBack);
    	btnBack.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent intent=new Intent(MainActivity.this,PhongBanActivity.class);
				startActivity(intent);
				
			}
		});
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
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.SrollView) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
