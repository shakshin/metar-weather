package com.shakshin.metar;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.TextView;
import android.widget.Toast;

public class SettingsActivity extends Activity {
	
	public class IntervalListener implements OnSeekBarChangeListener {
		public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
			if (progress < 10) {
				seekBar.setProgress(10);
				return;
			}
			TextView intView = (TextView) findViewById(R.id.intervalView);
			intView.setText(""+progress);
		}
		public void onStartTrackingTouch(SeekBar seekBar) {}
		public void onStopTrackingTouch(SeekBar seekBar) {}
		
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_settings);
		
		SeekBar intBar = (SeekBar) findViewById(R.id.intervalBar);
		intBar.setOnSeekBarChangeListener(new IntervalListener());
		
		SharedPreferences settings = getSharedPreferences("com.shakshin.metar", 0);
		
		intBar.setProgress(settings.getInt("update_interval", 30));
		
		EditText station = (EditText) findViewById(R.id.stationField);
		station.setText(settings.getString("station_name", ""));
		
		CheckBox autostart = (CheckBox) findViewById(R.id.autostartCheck);
		autostart.setChecked(settings.getBoolean("autostart", false));		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.activity_settings, menu);
		return true;
	}
	
	public void applySettings(View view) {
		SharedPreferences settings = getSharedPreferences("com.shakshin.metar", 0);
		SharedPreferences.Editor editor = settings.edit();
		
		EditText station = (EditText) findViewById(R.id.stationField);
		SeekBar intBar = (SeekBar) findViewById(R.id.intervalBar);
		CheckBox autostart = (CheckBox) findViewById(R.id.autostartCheck);
		
		editor.putString("station_name", station.getText().toString().toUpperCase());
		editor.putInt("update_interval", intBar.getProgress());
		editor.putBoolean("autostart", autostart.isChecked());
		
		editor.commit();
		
		stopService(new Intent(this, UpdateService.class));
		startService(new Intent(this, UpdateService.class));
	}
	
	public void startServ(View view) {
		startService(new Intent(this, UpdateService.class));
	}
	
	public void stopServ(View view) {
		stopService(new Intent(this, UpdateService.class));
	}
	
}
