package com.delta.delta_organiser;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Locale;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

public class Main extends Activity implements OnClickListener {
TextView currMonth;
ImageView prevMonth,nextMonth;
Calendar cal;
int month,year;
GridView calendarView,daysOfWeekTitle;
static String dateFormat="MMMM yyyy"; 
GridAdapter adapter;
Button im1,im2,im3,im4,im5,im6,im7;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		
		im1=(Button)findViewById(R.id.Button1);
		im2=(Button)findViewById(R.id.Button2);
		im3=(Button)findViewById(R.id.Button3);
		im4=(Button)findViewById(R.id.Button4);
		im5=(Button)findViewById(R.id.Button5);
		im6=(Button)findViewById(R.id.Button6);
		im7=(Button)findViewById(R.id.Button7);
		cal=Calendar.getInstance(Locale.getDefault());
		month=cal.get(Calendar.MONTH)+1;  //since index starts from zero
		year=cal.get(Calendar.YEAR);
		currMonth=(TextView)findViewById(R.id.currentMonth);
		nextMonth=(ImageView)findViewById(R.id.nextMonth);
		prevMonth=(ImageView)findViewById(R.id.prevMonth);
		nextMonth.setOnClickListener(this);
		prevMonth.setOnClickListener(this);
		currMonth.setText(DateFormat.format(dateFormat, cal.getTime()));

				
		calendarView=(GridView)findViewById(R.id.calendar);		
		adapter=new GridAdapter(getApplicationContext(), R.id.cell, month, year);
		calendarView.setAdapter(adapter);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public void onClick(View arg0) {
		// TODO Auto-generated method stub
		switch(arg0.getId())
		{
		case R.id.nextMonth:
			if(month==12)
			{
				month=1;
				year++;
			}
			else
				month++;
			changeMonth(month,year);
			break;
		case R.id.prevMonth:
			if(month==1)
			{
				month=12;
				year--;
			}
			else
				month--;
			changeMonth(month,year);
			break;
		}
		
	}
	
	private void changeMonth(int mnth, int yr) {
		// TODO Auto-generated method stub
		adapter=new GridAdapter(getApplicationContext(), R.id.cell, mnth, yr);
		cal.set(yr, mnth-1, Calendar.DAY_OF_MONTH);
		currMonth.setText(DateFormat.format(dateFormat, cal.getTime()));
		calendarView.setAdapter(adapter);
	}

	public class GridAdapter extends BaseAdapter implements OnClickListener, OnItemClickListener{
		private Context cntxt;
		private List<String> list;
		private String[] months={"January", "February", "March","April", "May", "June", "July", "August", "September","October", "November", "December"};
		private int[] daysOfMonth={31, 28, 31, 30, 31, 30, 31, 31, 30,31, 30, 31};
		private int daysInMonth;
		private int currentDayOfMonth;
		private Button cell;
		private Dialog dialog;
		private String dateTag; 
		String date;
		
		public GridAdapter(Context context,int ID,int month,int year) {
			// TODO Auto-generated constructor stub
			cntxt=context;
			list=new ArrayList<String>();
			setCurrentDayOfMonth(cal.get(Calendar.DAY_OF_MONTH));
			setCurrentWeekDay(cal.get(Calendar.DAY_OF_WEEK));
			
			display(month,year);
			
		}
		
		public void setCurrentDayOfMonth(int day){
			currentDayOfMonth=day;
		}
		
		public void setCurrentWeekDay(int day){
		}
		
		void display(int mm,int yy){
			int trailingSpaces = 0;
			int daysInPrevMonth = 0;
			int prevMonth = 0;
			int prevYear = 0;
			int nextMonth = 0;
			int nextYear = 0;

			int i = mm - 1;
			
			daysInMonth = daysOfMonth[i];
			//To get the first day of the current month!
			GregorianCalendar gcal=new GregorianCalendar(yy, i	, 1);
			
			if (i == 11) {
				prevMonth = i - 1;
				daysInPrevMonth = daysOfMonth[prevMonth];
				nextMonth = 0;
				prevYear = yy;
				nextYear = yy + 1;
				
			} else if (i == 0) {
				prevMonth = 11;
				prevYear = yy - 1;
				nextYear = yy;
				daysInPrevMonth = daysOfMonth[prevMonth];
				nextMonth = 1;
				
			} else {
				prevMonth = i - 1;
				nextMonth = i + 1;
				nextYear = yy;
				prevYear = yy;
				daysInPrevMonth = daysOfMonth[prevMonth];
				
			}
			int currentWeekDay = gcal.get(Calendar.DAY_OF_WEEK) - 1;
			trailingSpaces = currentWeekDay;
			if(gcal.isLeapYear(yy))
				if(mm==2)
					daysInMonth++;
				else if(mm==3)
					daysInPrevMonth++;
			//Spaces For Days in Previous Month
			for (int x = 0; x < trailingSpaces; x++) {
					list.add(String.valueOf((daysInPrevMonth - trailingSpaces + 1) + x)
						+ ";GREY"
						+ ";"
						+ months[prevMonth]
						+ ";"
						+ prevYear);
			}
			
			//Days in current month
			for (int x = 1; x <= daysInMonth; x++) {
				if(x==currentDayOfMonth)
					list.add(String.valueOf(x)+";BLUE"+";"+months[i]+";"+yy);
				else
					list.add(String.valueOf(x)+";WHITE"+";"+months[i]+";"+yy);					
			}
			
			//Days in the next month
			for (int x = 0; x < list.size() % 7; x++) {
				list.add(String.valueOf(x + 1) + ";GREY" + ";"	+ months[nextMonth] + ";" + nextYear);
			}
					
		}
		
		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return list.size();
		}

		@Override
		public Object getItem(int arg0) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public long getItemId(int pos) {
			// TODO Auto-generated method stub
			return pos;
		}

		@Override
		public View getView(int pos, View convertView, ViewGroup parent) {
			// TODO Auto-generated method stub
			View row=convertView;
			if (row == null) {
				LayoutInflater inflater = (LayoutInflater) cntxt.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
				row = inflater.inflate(R.layout.calendar_grid, parent, false);
			}
			
			cell = (Button) row.findViewById(R.id.cell);
			cell.setOnClickListener(this);
			String[] color=list.get(pos).split(";");
			String DAY=color[0];
			String MONTH=color[2];
			String YEAR=color[3];
			
			cell.setText(DAY);
			cell.setTag(DAY+"-"+MONTH+"-"+YEAR);
			 if(color[1].equals("GREY")){
				 cell.setTextColor(getResources().getColor(R.color.lightgray02));				
			 }
			 else if(color[1].equals("BLUE")){
				 cell.setTextColor(getResources().getColor(R.color.brown));
			 }
			 else{
				 cell.setTextColor(getResources().getColor(R.color.navy));
			 }					
				
					 
			return row;
		}

		@Override
		public void onClick(View arg0) {
			// TODO Auto-generated method stub
			
			dateTag=arg0.getTag().toString();
			ArrayList<String> dummies = new ArrayList<String>();

			dummies.add("Add Remainder");
			dummies.add("View Reminder");
			dummies.add("View all reminders");
			

			dialog = new Dialog(Main.this);
			dialog.setContentView(R.layout.list_dialog);
			dialog.setTitle("Options");
			ListView listView = (ListView) dialog.findViewById(R.id.lv);

			ArrayAdapter<String> adp = new ArrayAdapter<String>(
					Main.this, R.layout.single_item,
					R.id.singleItem, dummies);
			listView.setAdapter(adp);
			dialog.show();
			listView.setOnItemClickListener(this);
		}

		@Override
		public void onItemClick(AdapterView<?> arg0, View arg1, int pos,
				long arg3) {
			// TODO Auto-generated method stub
			Intent i=new Intent();
			switch(pos){
			case 0:
				i.setClass(Main.this, AddRemainder.class);
				i.putExtra("key", dateTag);
				startActivity(i);
				break;
			case 1:
				i.setClass(Main.this, ViewReminder.class);
				i.putExtra("key", dateTag);
				startActivity(i);
				break;
			case 2:
				i.setClass(Main.this, ViewAllReminders.class);
				startActivity(i);
				break;
			}
			dialog.dismiss();
		}
		
	}
	
	

}





