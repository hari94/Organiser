package com.delta.delta_organiser;

import java.util.ArrayList;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

public class ViewAllReminders extends Activity implements OnItemClickListener {
TextView tvDATE,tvEVENT,tvTIME;
String data;
String[] dataList;
ListView lv;
@Override
protected void onCreate(Bundle savedInstanceState) {
	// TODO Auto-generated method stub
	super.onCreate(savedInstanceState);
	setContentView(R.layout.all_rem);
	lv=(ListView)findViewById(R.id.lvAllRem);
	tvDATE=(TextView)findViewById(R.id.tvDATE);
	tvEVENT=(TextView)findViewById(R.id.tvEVENT);
	tvTIME=(TextView)findViewById(R.id.tvTIME);
	SQLReminders info=new SQLReminders(this);
	info.open();
	data=info.getData();
	dataList=data.split("::");
	ArrayList<String> list=new ArrayList<String>();
	for(int i=0;i<dataList.length;i++)
		list.add(dataList[i]);
	ArrayAdapter<String> adapter=new ArrayAdapter<String>(this, R.layout.single_all_rem, R.id.tvSingleAllRem, list);
	lv.setAdapter(adapter);
	lv.setOnItemClickListener(this);
	info.close();
	
}
@Override
public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
	// TODO Auto-generated method stub
	
}

}
