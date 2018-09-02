package com.melvin_joemar.perello_delacruz;

import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;

public class Main extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		init();
		event_handlers();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
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

	// declarations
	Button btn_single;
	Button btn_multi;
	Button btn_high;

	// -----------------------------------------------------------
	Animation growShrink;

	public void init() {
		// start animation
		AnimationDrawable ad = (AnimationDrawable) findViewById(R.id.pnl_home)
				.getBackground();
		ad.setExitFadeDuration(5000);
		ad.setExitFadeDuration(2000);
		ad.start();

		//
		btn_single = (Button) findViewById(R.id.btn_single);
		btn_multi = (Button) findViewById(R.id.btn_multii);
		btn_high = (Button) findViewById(R.id.btn_high);
		growShrink = AnimationUtils.loadAnimation(this, R.anim.shrink_grow);

		btn_high.startAnimation(growShrink);
		btn_single.startAnimation(growShrink);
		btn_multi.startAnimation(growShrink);
	}

	// -----------------------------------------------------------
	public void event_handlers() {
		btn_single.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				clickSingle();
			}
		});

		btn_multi.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				clickMulti();
			}
		});

		btn_high.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				clickHighScore();
			}
		});
	}

	// ----------------------- events
	public void clickHighScore() {
		Intent hs = new Intent(getApplicationContext(), HighScore.class);
		startActivity(hs);
	}

	public void clickSingle() {
		Intent start_game = new Intent(getApplicationContext(), Mode.class);
		startActivity(start_game);
	}

	public void clickMulti() {
		Intent start_game = new Intent(getApplicationContext(), Xoxo.class);
		start_game.putExtra("ENABLE_AI", false);
		startActivity(start_game);
	}

} // end of class
