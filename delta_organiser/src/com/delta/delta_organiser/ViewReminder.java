package com.delta.delta_organiser;

import java.util.ArrayList;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

public class ViewReminder extends Activity implements OnItemClickListener {
	TextView tvDate, tvTitle, tvTitleContent, tvDesc, tvDescContent, tvTime,
			tvTimeContent, tvsingle_rem;
	String data, date, time, totalTimes;
	String[] allrem, TimeList;
	Dialog dialog;
	int listLength;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.view_reminder);
		Bundle bundle = getIntent().getExtras();
		date = bundle.getString("key").toString();
		tvDate = (TextView) findViewById(R.id.tvdisplayDate);
		tvTitleContent = (TextView) findViewById(R.id.tvtitleContent);
		tvDescContent = (TextView) findViewById(R.id.tvDescContent);
		tvTitle = (TextView) findViewById(R.id.tvdisplayTitle);
		tvDesc = (TextView) findViewById(R.id.tvdisplayDesc);
		tvTime = (TextView) findViewById(R.id.tvTime);
		tvTimeContent = (TextView) findViewById(R.id.tvTimeContent);
		tvsingle_rem = (TextView) findViewById(R.id.tvSingle_rem);
		SQLReminders allrem = new SQLReminders(this);
		allrem.open();
		try {
			totalTimes = allrem.getTimes(date);
			TimeList = totalTimes.split("::");
			if (totalTimes != null) {
				ArrayList<String> remList = new ArrayList<String>();
				for (int i = 0; i < TimeList.length; i++)
					remList.add(TimeList[i]);
				listLength = remList.size();
				dialog = new Dialog(this);
				dialog.setContentView(R.layout.reminder_list);
				dialog.setTitle("At What Time?");
				ListView listView = (ListView) dialog
						.findViewById(R.id.listRem);

				ArrayAdapter<String> adp = new ArrayAdapter<String>(this,
						R.layout.single_rem, R.id.tvSingle_rem, remList);
				listView.setAdapter(adp);
				dialog.show();
				listView.setOnItemClickListener(this);
			}
		} catch (Exception e) {
			tvDate.setText(date);
			Dialog d = new Dialog(this);
			d.setTitle("OOPS!");
			TextView tv = new TextView(this);
			tv.setTextColor(getResources().getColor(R.color.sky));
			tv.setText("No Reminders Found!");
			d.setContentView(tv);
			d.show();
		}

		allrem.close();		
	}

	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		// TODO Auto-generated method stub

		String time = TimeList[arg2];
		SQLReminders viewData = new SQLReminders(this);
		viewData.open();
		try {
			data = viewData.getData(date, time);
			String[] details = data.split(";");
			tvDate.setText(date);
			tvTimeContent.setText(details[0]);
			tvTitleContent.setText(details[1]);
			tvDescContent.setText(details[2]);
		} catch (Exception e) {
			tvDate.setText(date);
			Dialog d = new Dialog(this);
			d.setTitle("OOPS!");
			TextView tv = new TextView(this);
			tv.setTextColor(getResources().getColor(R.color.sky));
			tv.setText("No Reminders Found!");
			d.setContentView(tv);
			d.show();
		}
		viewData.close();
		dialog.dismiss();

	}
}
