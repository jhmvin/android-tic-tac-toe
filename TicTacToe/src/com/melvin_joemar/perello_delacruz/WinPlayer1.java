package com.melvin_joemar.perello_delacruz;

import android.app.Activity;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class WinPlayer1 extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_win_player1);
		init();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.win_player1, menu);
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

	public void onBackPressed() {
		super.onBackPressed();
		finish();
	};

	// ----------
	ImageView img_logo, img_text;
	Button btn_exit;
	TextView lbl_score;

	public void init() {
		AnimationDrawable ad = (AnimationDrawable) findViewById(R.id.pnl_result)
				.getBackground();
		ad.setExitFadeDuration(5000);
		ad.setExitFadeDuration(2000);
		ad.start();
		
		
		Drawable winp1 = getResources().getDrawable(R.drawable.winp1);
		Drawable winp2 = getResources().getDrawable(R.drawable.winp2);
		Drawable winai = getResources().getDrawable(R.drawable.winai);
		Drawable windraw = getResources().getDrawable(R.drawable.windraw);

		Drawable p1_logo = getResources().getDrawable(R.drawable.token_o);
		Drawable p2_logo = getResources().getDrawable(R.drawable.token_x);
		Drawable ai_logo = getResources().getDrawable(R.drawable.ai);
		Drawable draw_logo = getResources().getDrawable(R.drawable.draw);

		//
		img_logo = (ImageView) findViewById(R.id.img_token);
		img_text = (ImageView) findViewById(R.id.img_text);
		lbl_score = (TextView) findViewById(R.id.lbl_score);
		btn_exit = (Button) findViewById(R.id.btn_ok);
		btn_exit.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				finish();
			}
		});

		//
		String winner = getIntent().getStringExtra("winner");
		Integer score = getIntent().getIntExtra("score", 0);

		if (winner.equals("p1")) {
			img_logo.setImageDrawable(p1_logo);
			img_text.setImageDrawable(winp1);
		} else if (winner.equals("p2")) {
			img_logo.setImageDrawable(p2_logo);
			img_text.setImageDrawable(winp2);
		} else if (winner.equals("ai")) {
			img_logo.setImageDrawable(ai_logo);
			img_text.setImageDrawable(winai);
		} else if (winner.equals("draw")) {
			img_logo.setImageDrawable(draw_logo);
			img_text.setImageDrawable(windraw);
		}

		lbl_score.setText(score.toString() + " points");
	}
}
