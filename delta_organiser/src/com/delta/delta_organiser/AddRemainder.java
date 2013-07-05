package com.delta.delta_organiser;

import java.util.Calendar;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.Dialog;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

public class AddRemainder extends Activity implements OnClickListener {
	EditText etTitle,etdesc;
	TextView date,title,setTime,tvdesc;
	//TimePicker time;
	Button changeTime,addRem,viewRem;
	int hh,mm;
	String str;
	final int DIALOG_ID=123;
	AlarmManager am;
	PendingIntent pi;
	String[] months={"January", "February", "March","April", "May", "June", "July", "August", "September","October", "November", "December"};
		@Override
		protected void onCreate(Bundle savedInstanceState) {
			// TODO Auto-generated method stub
			super.onCreate(savedInstanceState);
			setContentView(R.layout.add_reminder);
			etTitle=(EditText)findViewById(R.id.etTitle);
			etdesc=(EditText)findViewById(R.id.etdesc);
			title=(TextView)findViewById(R.id.title);
			setTime=(TextView)findViewById(R.id.setTime);
			tvdesc=(TextView)findViewById(R.id.tvdesc);
			date=(TextView)findViewById(R.id.date);
			changeTime=(Button)findViewById(R.id.changeTime);
			addRem=(Button)findViewById(R.id.addRem);
			viewRem=(Button)findViewById(R.id.viewRem);
			am=(AlarmManager)getSystemService(ALARM_SERVICE);
			//time=(TimePicker)findViewById(R.id.timePicker1);
			Bundle bundle=getIntent().getExtras();
			str=bundle.getString("key");
			date.setText(str);
			changeTime.setOnClickListener(this);
			addRem.setOnClickListener(this);
			viewRem.setOnClickListener(this);
			
			
			
		}
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			switch(v.getId()){
			case R.id.changeTime:
				showDialog(DIALOG_ID);
				break;
			case R.id.addRem:
				int hh = 0,mm=0;
				SQLReminders entry=new SQLReminders(this);
				boolean check=true;
				try{
				String event=etTitle.getText().toString();
				String desc=etdesc.getText().toString();
				String time=setTime.getText().toString();
				String[] t=time.split(":");
				hh=Integer.parseInt(t[0]);
				mm=Integer.parseInt(t[1]);
				entry.open();
				entry.insertData(str,time,event,desc);
				entry.close();
				}catch(Exception e){
					check = false;
					Dialog d = new Dialog(this);
					d.setTitle("SQL DATABASE");
					TextView tv = new TextView(this);
					tv.setText(e.toString());
					d.setContentView(tv);
					d.show();
				}finally{
					if (check) {
						Toast.makeText(this, "Success!", Toast.LENGTH_SHORT).show();
						Intent myIntent = new Intent(this, NotifyService.class);
				        myIntent.putExtra("date", str);
						pi = PendingIntent.getService(this, 0,myIntent, 0);
				        String[] ddmmyy=str.split("-");
				        int day=Integer.parseInt(ddmmyy[0]);
				        int year=Integer.parseInt(ddmmyy[2]);
				        int month=0;
				        for(int i=0;i<months.length;i++)
				        	if(months[i].equals(ddmmyy[1])){
				        		month=i;
				        		break;
				        	}				        		
				        
				        Calendar cl = Calendar.getInstance();
				        cl.set(year, month, day, hh, mm);
				        
				        
				        Log.v("alarm", "time for alarm trigger:" + cl.getTime().toString());
				        am.set(AlarmManager.RTC_WAKEUP, cl.getTimeInMillis(), pi);

					}
				}
				break;
			case R.id.viewRem:
				Intent i=new Intent();
				i.setClass(AddRemainder.this, ViewAllReminders.class);
				startActivity(i);
				break;
			}
		}
		@Override
		protected Dialog onCreateDialog(int id) {
			// TODO Auto-generated method stub
			
			switch (id) {
			case DIALOG_ID:
				// set time picker as current time
				return new TimePickerDialog(this,timePickerListener, hh, mm,false);
				
			}
			return null;
		}
		
		private TimePickerDialog.OnTimeSetListener timePickerListener = 
	            new TimePickerDialog.OnTimeSetListener() {
			public void onTimeSet(TimePicker view, int selectedHour,int selectedMinute) {
				hh = selectedHour;
				mm = selectedMinute;
	 
				// set current time into textview
				setTime.setText(new StringBuilder().append(adjust(hh))
						.append(":").append(adjust(mm)));
	 
								
			}

			private String adjust(int x) {
				// TODO Auto-generated method stub
				if(x>10)
					return String.valueOf(x);
				return "0"+String.valueOf(x);
			}

			
		};
}
