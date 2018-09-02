package com.melvin_joemar.perello_delacruz;

import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class Mode extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_mode);
		init();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.mode, menu);
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

	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		super.onBackPressed();
		finish();
	}

	// -------------
	private Button btn_hard, btn_easy;

	public void init() {

		AnimationDrawable ad = (AnimationDrawable) findViewById(R.id.pnl_mode)
				.getBackground();
		ad.setExitFadeDuration(5000);
		ad.setExitFadeDuration(2000);
		ad.start();

		btn_easy = (Button) findViewById(R.id.btn_easy);
		btn_hard = (Button) findViewById(R.id.btn_hard);

		btn_easy.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				easy();
			}
		});

		btn_hard.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				hard();
			}
		});
	}

	public void easy() {
		Intent start_game = new Intent(getApplicationContext(), Xoxo.class);
		start_game.putExtra("ENABLE_AI", true);
		start_game.putExtra("HARD_AI", false);
		startActivity(start_game);
		finish();
	}

	public void hard() {
		Intent start_game = new Intent(getApplicationContext(), Xoxo.class);
		start_game.putExtra("ENABLE_AI", true);
		start_game.putExtra("HARD_AI", true);
		startActivity(start_game);
		finish();
	}
}
