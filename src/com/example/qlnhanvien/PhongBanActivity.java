package com.example.qlnhanvien;

import java.util.ArrayList;

import android.support.v7.app.ActionBarActivity;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.View.OnLongClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.AdapterViewFlipper;
import android.widget.Button;
import android.widget.ListView;

public class PhongBanActivity extends ActionBarActivity {
	final String DATABASE_NAME="QLNV.db";
	SQLiteDatabase database;
	ListView listViewPB;
	ArrayList<PhongBan> listPB;
	PhongBanAdapter PBadapter;
	Activity context;
	Button btnAddPB;
	PhongBan PBSelected;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_phong_ban);
		addControls();
		readData();
		
	}
	 public void addControls(){
	    	listViewPB =(ListView) findViewById(R.id.listViewPB);
	    	listPB=new ArrayList<>();
	    	PBadapter=new PhongBanAdapter(this,listPB);
	    	listViewPB.setAdapter(PBadapter);
	    	btnAddPB=(Button) findViewById(R.id.btnAddPB);
	    	btnAddPB.setOnClickListener(new View.OnClickListener() {
				
				@Override
				public void onClick(View v) {
					Intent intent=new Intent(PhongBanActivity.this,AddPBActitivy.class);
					startActivity(intent);
					
				}
			});
	    	registerForContextMenu(listViewPB);
	    	listViewPB.setClickable(true);
	    	listViewPB.setOnItemClickListener(new OnItemClickListener() {

				@Override
				public void onItemClick(AdapterView<?> parent, View view,
						int position, long id) {
					final PhongBan phongBan=listPB.get(position);
					Intent intent=new Intent (PhongBanActivity.this,MainActivity.class);
					intent.putExtra("IDPB", phongBan.id);
					startActivity(intent);
				}
			} );
	    	listViewPB.setOnItemLongClickListener(new OnItemLongClickListener() {

				@Override
				public boolean onItemLongClick(AdapterView<?> parent, View view,
						int position, long id) {
					 PBSelected=listPB.get(position);
					return false;
				}

			});
				
			
	    	
	    }
	private void readData(){
	    	 database = Database.initDatabase(this, DATABASE_NAME);
	    	 Cursor cursor=database.rawQuery("select* from PhongBan", null);
	    	 listPB.clear();
	         for(int i=0;i<cursor.getCount();i++)
	         {
	        	 cursor.moveToPosition(i);
	        	 int id=cursor.getInt(0);
	        	 String tenPB= cursor.getString(1);
	        	 int idTP =cursor.getInt(2);
	        	 int idPP =cursor.getInt(3); 
	        	 listPB.add(new PhongBan(id, tenPB, idTP, idPP));
	         }
	         PBadapter.notifyDataSetChanged();
	    }
	
	@Override
	public void onCreateContextMenu(ContextMenu menu, View v,
			ContextMenuInfo menuInfo) {
		getMenuInflater().inflate(R.menu.menu, menu);
		super.onCreateContextMenu(menu, v, menuInfo);
	}
	@Override
	public boolean onContextItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.ThemTP:
			ThemChucVu();
			break;
		case R.id.XoaPB:
			XoaPB();
			break;
		default:
			break;
		}
		return super.onContextItemSelected(item);
	}
	private void ThemChucVu() {
		Intent intent=new Intent(this,AddChucVu.class);
		intent.putExtra("IDPB", PBSelected.id);
		startActivity(intent);
		
	}
	public void XoaPB(){
		AlertDialog.Builder builder =new AlertDialog.Builder(this);
		builder.setIcon(android.R.drawable.ic_delete);
		builder.setTitle("Xác nhận xóa");
		builder.setMessage("Bạn có chắc chắn xóa nhân viên này?");
		builder.setPositiveButton("Có", new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				delete(PBSelected.id);
				
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
	protected void delete(int idPB) {
		SQLiteDatabase database=Database.initDatabase(this, DATABASE_NAME);
		database.delete("PhongBan", "ID=?", new String[]{idPB+""});
		readData();
		
	}
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.phong_ban, menu);
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
