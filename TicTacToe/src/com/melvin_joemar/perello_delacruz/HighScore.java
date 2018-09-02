package com.melvin_joemar.perello_delacruz;

import android.app.Activity;
import android.content.SharedPreferences;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

public class HighScore extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_high_score);

		init();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.high_score, menu);
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

	// ---------------

	TextView lbl_o_bom;
	TextView lbl_x_bom;
	TextView lbl_ai_bom;

	// scores
	TextView hs_1;
	TextView hs_2;
	TextView hs_3;
	TextView hs_4;
	TextView hs_5;
	TextView hs_6;
	TextView hs_7;
	TextView hs_8;
	TextView hs_9;
	TextView hs_10;

	// images
	ImageView t_1;
	ImageView t_2;
	ImageView t_3;
	ImageView t_4;
	ImageView t_5;
	ImageView t_6;
	ImageView t_7;
	ImageView t_8;
	ImageView t_9;
	ImageView t_10;

	//
	Drawable logo_x;
	Drawable logo_o;
	Drawable logo_ai;
	Drawable logo_none;

	public void init() {
		AnimationDrawable ad = (AnimationDrawable) findViewById(
				R.id.pnl_highscore).getBackground();
		ad.setExitFadeDuration(5000);
		ad.setExitFadeDuration(2000);
		ad.start();

		// get
		lbl_o_bom = (TextView) findViewById(R.id.player_o_bom);
		lbl_x_bom = (TextView) findViewById(R.id.player_x_bom);
		lbl_ai_bom = (TextView) findViewById(R.id.player_ai_bom);

		lbl_o_bom.setText("1");
		lbl_x_bom.setText("2");
		lbl_ai_bom.setText("3");

		//
		hs_1 = (TextView) findViewById(R.id.hs_score_1);
		hs_2 = (TextView) findViewById(R.id.hs_score_2);
		hs_3 = (TextView) findViewById(R.id.hs_score_3);
		hs_4 = (TextView) findViewById(R.id.hs_score_4);
		hs_5 = (TextView) findViewById(R.id.hs_score_5);
		hs_6 = (TextView) findViewById(R.id.hs_score_6);
		hs_7 = (TextView) findViewById(R.id.hs_score_7);
		hs_8 = (TextView) findViewById(R.id.hs_score_8);
		hs_9 = (TextView) findViewById(R.id.hs_score_9);
		hs_10 = (TextView) findViewById(R.id.hs_score_10);

		hs_1.setText("1");
		hs_2.setText("2");
		hs_3.setText("3");
		hs_4.setText("4");
		hs_5.setText("5");
		hs_6.setText("6");
		hs_7.setText("7");
		hs_8.setText("8");
		hs_9.setText("9");
		hs_10.setText("10");

		//

		t_1 = (ImageView) findViewById(R.id.hs_token_1);
		t_2 = (ImageView) findViewById(R.id.hs_token_2);
		t_3 = (ImageView) findViewById(R.id.hs_token_3);
		t_4 = (ImageView) findViewById(R.id.hs_token_4);
		t_5 = (ImageView) findViewById(R.id.hs_token_5);
		t_6 = (ImageView) findViewById(R.id.hs_token_6);
		t_7 = (ImageView) findViewById(R.id.hs_token_7);
		t_8 = (ImageView) findViewById(R.id.hs_token_8);
		t_9 = (ImageView) findViewById(R.id.hs_token_9);
		t_10 = (ImageView) findViewById(R.id.hs_token_10);

		//
		logo_ai = getResources().getDrawable(R.drawable.ai);
		logo_o = getResources().getDrawable(R.drawable.token_o);
		logo_x = getResources().getDrawable(R.drawable.token_x);
		logo_none = getResources().getDrawable(R.drawable.none);

		t_1.setImageDrawable(logo_ai);
		t_2.setImageDrawable(logo_none);

		// get highscores
		SharedPreferences sp = this.getSharedPreferences(
				"com.tictac.highscore", 0);
		lbl_o_bom.setText(String.valueOf(sp.getInt("BEST_P1", 0)));
		lbl_x_bom.setText(String.valueOf(sp.getInt("BEST_P2", 0)));
		lbl_ai_bom.setText(String.valueOf(sp.getInt("BEST_AI", 0)));

		//
		String[] sc;
		sc = sp.getString("TOP_1", "N#0").split("#");
		setDetails(sc, t_1, hs_1);
		sc = sp.getString("TOP_2", "N#0").split("#");
		setDetails(sc, t_2, hs_2);
		sc = sp.getString("TOP_3", "N#0").split("#");
		setDetails(sc, t_3, hs_3);
		sc = sp.getString("TOP_4", "N#0").split("#");
		setDetails(sc, t_4, hs_4);
		sc = sp.getString("TOP_5", "N#0").split("#");
		setDetails(sc, t_5, hs_5);
		sc = sp.getString("TOP_6", "N#0").split("#");
		setDetails(sc, t_6, hs_6);
		sc = sp.getString("TOP_7", "N#0").split("#");
		setDetails(sc, t_7, hs_7);
		sc = sp.getString("TOP_8", "N#0").split("#");
		setDetails(sc, t_8, hs_8);
		sc = sp.getString("TOP_9", "N#0").split("#");
		setDetails(sc, t_9, hs_9);
		sc = sp.getString("TOP_10", "N#0").split("#");
		setDetails(sc, t_10, hs_10);
	}
	
	public void setDetails(String[] sc,ImageView t_1,TextView hs_1){
		if (sc[0].equals("P1")) {
			t_1.setImageDrawable(logo_o);
		} else if (sc[0].equals("P2")) {
			t_1.setImageDrawable(logo_x);
		} else if (sc[0].equals("AI")) {
			t_1.setImageDrawable(logo_ai);
		} else if (sc[0].equals("N")) {
			t_1.setImageDrawable(logo_none);
		}
		hs_1.setText(String.valueOf(sc[1]));
	}
}
