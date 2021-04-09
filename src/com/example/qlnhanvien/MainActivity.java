package com.example.qlnhanvien;

import java.sql.Blob;
import java.util.ArrayList;

import android.support.v7.app.ActionBarActivity;
import android.app.AlertDialog;
import android.content.DialogInterface;
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


public class MainActivity extends ActionBarActivity {

	final String DATABASE_NAME="QLNV.db";
	SQLiteDatabase database;
	ListView listView;
	ArrayList<NhanVien> list;
	NhanVien NVSelected;
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
				Intent intent=new Intent(MainActivity.this,PhongBanActivity.class);
				startActivity(intent);
				
			}
		});
    }
    @Override
	public void onCreateContextMenu(ContextMenu menu, View v,
			ContextMenuInfo menuInfo) {
		getMenuInflater().inflate(R.menu.update, menu);
		super.onCreateContextMenu(menu, v, menuInfo);
	}
	@Override
	public boolean onContextItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.SuaNV:
			SuaNV();
			break;
		case R.id.XoaNV:
			XoaNV();
			break;
		default:
			break;
		}
		return super.onContextItemSelected(item);
	}
    private void XoaNV() {
		AlertDialog.Builder builder =new AlertDialog.Builder(this);
		builder.setIcon(android.R.drawable.ic_delete);
		builder.setTitle("Xác nhận xóa");
		builder.setMessage("Bạn có chắc chắn xóa nhân viên này?");
		builder.setPositiveButton("Có", new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				delete(NVSelected.id);
				
			}
		});
		builder.setNegativeButton("Không", new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				// TODO Auto-generated method stub
				
			}
		});
		AlertDialog dialog=builder.create();
		dialog.show();
		
	}


	protected void delete(int idNV) {
		SQLiteDatabase database=Database.initDatabase(this, DATABASE_NAME);
		database.delete("NhanVien", "ID=?", new String[]{idNV+""});
		readData();
		
	}



	private void SuaNV() {
		Intent intent=new Intent (MainActivity.this,UpdateActivity.class);
		intent.putExtra("ID", NVSelected.id);
		startActivity(intent);
		
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
