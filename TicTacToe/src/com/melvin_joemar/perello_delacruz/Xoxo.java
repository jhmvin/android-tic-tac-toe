package com.melvin_joemar.perello_delacruz;

import java.util.ArrayList;

import android.app.ActionBar.LayoutParams;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.Drawable;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

public class Xoxo extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_xoxo);
		init();
		event_handler();
		/*
		 * Toast.makeText(getApplicationContext(), "created",
		 * Toast.LENGTH_SHORT) .show();
		 */
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.xoxo, menu);
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

	private boolean isPaused = false;

	@Override
	protected void onPause() {
		super.onPause();
		isPaused = true;
		game_timer.cancel();
		/*
		 * Toast.makeText(getApplicationContext(), "paused", Toast.LENGTH_SHORT)
		 * .show();
		 */
	};

	@Override
	protected void onResume() {
		super.onResume();
		if (isPaused) {
			setTimer(this.milliLeft);
			/*
			 * Toast.makeText(getApplicationContext(), "resumed",
			 * Toast.LENGTH_SHORT).show();
			 */
		}

		isPaused = false;
	};

	@Override
	public void onBackPressed() {
		// super.onBackPressed();
		/*
		 * Toast.makeText(getApplicationContext(), "FUCK", Toast.LENGTH_SHORT)
		 * .show();
		 */

		alertmesample();
		// game_timer.cancel();
		// finish();
	};

	public void alertmesample() {

		android.content.DialogInterface.OnClickListener di_action = new DialogInterface.OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {
				// TODO Auto-generated method stub
				if (which == -1) {
					finish();
				}

			}
		};
		AlertDialog.Builder dlgAlert = new AlertDialog.Builder(this);
		dlgAlert.setMessage("Your game is in progress, do you really want to end it ?");
		dlgAlert.setTitle("End Game");
		dlgAlert.setNegativeButton("No", di_action);
		dlgAlert.setPositiveButton("Yes", di_action);
		// dlgAlert.setNeutralButton("Maybe", di_action);
		dlgAlert.setCancelable(true);
		dlgAlert.create().show();
	}

	// ---------------------------------------------------

	// parent of generated code
	LinearLayout pnl_board_container;
	//
	private Drawable token_x;
	private Drawable token_o;
	//
	private ArrayList<Integer> tira_x;
	private ArrayList<Integer> tira_o;
	//
	private Boolean turn_switcher;
	//
	private Integer x_score;
	private Integer o_score;

	//
	private TextView lbl_x_score;
	private TextView lbl_o_score;
	private TextView lbl_timer;
	private Button btn_pause;
	private ImageView btn_help;

	//
	private XoxoPattern patternCheck;
	private android.os.CountDownTimer game_timer;

	//
	Animation shake;

	//
	private Integer turnCounter = 27;

	private boolean ENABLE_AI = false;
	private boolean HARD_AI = false;

	// --------------------------
	public void init() {
		// load animation
		AnimationDrawable ad = (AnimationDrawable) findViewById(R.id.pnl_xoxo)
				.getBackground();
		ad.setExitFadeDuration(5000);
		ad.setExitFadeDuration(2000);
		ad.start();

		pnl_board_container = (LinearLayout) findViewById(R.id.pnl_board_container);
		lbl_x_score = (TextView) findViewById(R.id.lbl_score_x);
		lbl_o_score = (TextView) findViewById(R.id.lbl_score_o);
		lbl_x_score.setText("0");
		lbl_o_score.setText("0");
		lbl_timer = (TextView) findViewById(R.id.lbl_timer);
		btn_pause = (Button) findViewById(R.id.btn_pause);
		btn_help = (ImageView) findViewById(R.id.btn_help);

		// load images
		this.token_x = getResources().getDrawable(R.drawable.token_x);
		this.token_o = getResources().getDrawable(R.drawable.token_o);
		//
		generate_board();
		//
		tira_x = new ArrayList<Integer>();
		tira_o = new ArrayList<Integer>();
		// true =x ; false = o
		turn_switcher = false;
		//
		x_score = 0;
		o_score = 0;
		//
		patternCheck = new XoxoPattern();

		//
		setTimer(180000);

		//
		shake = AnimationUtils.loadAnimation(this, R.anim.shake);

		// SET AI
		this.ENABLE_AI = getIntent().getBooleanExtra("ENABLE_AI", false);
		this.HARD_AI = getIntent().getBooleanExtra("HARD_AI", false);
	}

	// ---------------------------------------
	public void event_handler() {
		btn_pause.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				pause();
			}
		});

		btn_help.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				help();
			}
		});
	}

	// events

	public void help() {
		Intent help = new Intent(getApplicationContext(), Help.class);
		startActivity(help);
	}

	public void pause() {
		Intent pause = new Intent(getApplicationContext(), Puase.class);
		startActivity(pause);
	}

	// AI ENGINE
	public View getView(Integer btnCount) {
		btnCount -= 1;
		int count = 0;
		for (int x = 0; x < pnl_board_container.getChildCount(); x++) {
			if (pnl_board_container.getChildAt(x) instanceof LinearLayout) {
				// layer contains 3 levels
				LinearLayout layer = (LinearLayout) pnl_board_container
						.getChildAt(x);

				for (int y = 0; y < layer.getChildCount(); y++) {
					if (layer.getChildAt(y) instanceof LinearLayout) {
						// level contains 3 buttons
						LinearLayout level = (LinearLayout) layer.getChildAt(y);
						for (int z = 0; z < level.getChildCount(); z++) {
							if (level.getChildAt(z) instanceof Button) {
								if (count == btnCount) {
									return level.getChildAt(z);
								}
								count++;
							}
						}
					}
				}
				//
			}
		}
		return null;
	}

	public void aiTokenPlacement(Integer i) {
		Integer placement = (i - 1);
		Button b = (Button) getView(i);
		xoxo_action(placement.toString(), b);
	}

	// check if the button is not taken yet
	public Boolean allowToken(Integer i) {
		Button b = (Button) getView(i);
		return b.isEnabled();
	}

	// easy
	public void aiThinker() {
		int possibleTurn = 0;
		while (true) {
			possibleTurn = aiSuggest();
			Button b = (Button) getView(possibleTurn);
			if (b.isEnabled()) {
				aiTokenPlacement(possibleTurn);
				ai_tokens.add(possibleTurn);
				break;
			} else {
				// already taken
				continue;
			}
		}
	}

	// ------------------------------ HARD MOdE
	// hard

	public void aiSunogKilay() {
		aiSunogSuggest();
	}

	private ArrayList<Integer> ai_tokens = new ArrayList<Integer>();

	// check the current tokens for patterns
	public Integer aiCompare(Integer[] pattern, ArrayList<Integer> list) {
		int suggestive = 0;
		for (int x = 0; x < pattern.length; x++) {
			if (list.contains(pattern[x])) {
				suggestive++;
			}
		}
		Log.i("INFO", pattern[0].toString() + " : " + pattern[1].toString()
				+ " : " + pattern[2].toString() + " -- " + ai_tokens.toString()
				+ " -- " + suggestive);
		return suggestive;
	}

	public void aiSunogSuggest() {
		ArrayList<Integer[]> patternList = patternCheck.getAllPattern();
		ArrayList<Integer[]> LESS_PRIORITY = new ArrayList<Integer[]>();
		ArrayList<Integer[]> HIGH_PRIORITY = new ArrayList<Integer[]>();
		for (int x = 0; x < patternList.size(); x++) {
			Integer[] pat = patternList.get(x);
			Integer comval = aiCompare(pat, ai_tokens);
			// Toast.makeText(getApplicationContext(), comval.toString(),
			// Toast.LENGTH_SHORT).show();
			if (comval == 1) {
				LESS_PRIORITY.add(pat);
			} else if (comval == 2) {
				HIGH_PRIORITY.add(pat);
			} else {

			}

		}

		if ((LESS_PRIORITY.size() + HIGH_PRIORITY.size()) == 0) {
			// there are no matching patterns
			aiThinker(); // just a random
		} else {
			Integer res = -1;
			// there is a possible match
			if (!HIGH_PRIORITY.isEmpty()) {
				// high priority pattern evaluated
				res = highPriority(HIGH_PRIORITY);
			} else {
				// low priority patterns evaluated
				res = lessPriority(LESS_PRIORITY);
			}
			//
			if (res != -1) {
				aiTokenPlacement(res);
				ai_tokens.add(res);
			} else {
				aiThinker();
			}
			//

		}
		/*
		 * Integer i = patternCheck.getAllPattern().size();
		 * Toast.makeText(getApplicationContext(), "THERE IS",
		 * Toast.LENGTH_SHORT) .show();
		 */

	}

	public int lessPriority(ArrayList<Integer[]> LESS_PRIORITY) {
		for (int x = 0; x < LESS_PRIORITY.size(); x++) {
			Integer[] less = LESS_PRIORITY.get(x);
			for (int y = 0; y < less.length; y++) {
				if (allowToken(less[y])) {
					return less[y];
				}
			}
		}
		return -1;
	}

	public int highPriority(ArrayList<Integer[]> HIGH_PRIORITY) {
		for (int x = 0; x < HIGH_PRIORITY.size(); x++) {
			Integer[] high = HIGH_PRIORITY.get(x);
			for (int y = 0; y < high.length; y++) {
				if (allowToken(high[y])) {
					return high[y];
				}
			}
		}
		return -1;
	}

	// --- END ------------------ HARD MOdE

	public void aiPlayer() {
		if (this.HARD_AI) {
			aiSunogKilay();
		} else {
			aiThinker();
		}

	}

	public Integer aiSuggest() {
		int min = 1;
		int max = 27;
		int range = (max - min) + 1;
		return (int) (Math.random() * range) + min;
	}

	// END OF AI ENGINE

	// ----------------------------------

	public void xoxo_action(String number, View v) {
		// Toast.makeText(getApplicationContext(), number,
		// Toast.LENGTH_SHORT).show();
		Button btn_clicked = (Button) v;
		int btn_dimension = (btn_clicked.getHeight() - 45);
		int temp_score = x_score + o_score;
		sfxPlace();
		btn_clicked.startAnimation(shake);

		if (turn_switcher) {
			// TIRA X
			// btn_clicked.setText("Jelyn");
			this.token_x.setBounds(0, 0, btn_dimension, btn_dimension);
			btn_clicked.setCompoundDrawables(this.token_x, null, null, null);
			btn_clicked.setEnabled(false);
			// add to tira
			tira_x.add(Integer.valueOf(number) + 1);
			// check pattern buildup
			x_score = patternCheck.checkPattern(tira_x) * 10;
			lbl_x_score.setText(String.valueOf(x_score));

			turn_switcher = false;
		} else {
			// TIRA O
			// btn_clicked.setText("Melvin");
			this.token_o.setBounds(0, 0, btn_dimension, btn_dimension);
			btn_clicked.setCompoundDrawables(this.token_o, null, null, null);
			btn_clicked.setEnabled(false);
			// add to tira
			tira_o.add(Integer.valueOf(number) + 1);
			// check pattern buildup
			o_score = patternCheck.checkPattern(tira_o) * 10;

			lbl_o_score.setText(String.valueOf(o_score));

			turn_switcher = true;

		}
		//
		if (temp_score != (x_score + o_score)) {
			sfxPattern();
		}
		//
		turnCounter--;
		if (turnCounter <= 0) {
			endGame();
			return;
		}
		if (this.ENABLE_AI) {
			if (turn_switcher) {
				/*
				 * // disable screen
				 * getWindow().setFlags(WindowManager.LayoutParams
				 * .FLAG_NOT_TOUCHABLE,
				 * WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE); // PLACE AI
				 * INITIALIZATION HERE
				 */

				// Toast.makeText(getApplicationContext(),
				// turnCounter.toString(),
				// Toast.LENGTH_SHORT).show();
				aiPlayer();
				return;
				// END OF AI PLACEMENT
			}
		}

	}

	// SFX
	public void sfxPlace() {
		MediaPlayer stapler = MediaPlayer.create(getApplicationContext(),
				R.raw.stapler);
		stapler.setOnCompletionListener(new OnCompletionListener() {

			@Override
			public void onCompletion(MediaPlayer mp) {
				// TODO Auto-generated method stub
				mp.release();
			}
		});

		try {
			if (stapler.isPlaying()) {
				stapler.stop();
				stapler.release();
				stapler = MediaPlayer.create(getApplicationContext(),
						R.raw.stapler);
			}
			stapler.start();
		} catch (Exception e) {
			Toast.makeText(getApplicationContext(), "p: " + e.toString(),
					Toast.LENGTH_SHORT).show();
		}
	}

	public void sfxPattern() {
		MediaPlayer twinkle = MediaPlayer.create(getApplicationContext(),
				R.raw.pattern);
		twinkle.setOnCompletionListener(new OnCompletionListener() {

			@Override
			public void onCompletion(MediaPlayer mp) {
				// TODO Auto-generated method stub
				mp.release();
			}
		});
		try {
			if (twinkle.isPlaying()) {
				twinkle.stop();
				twinkle.release();
				twinkle = MediaPlayer.create(getApplicationContext(),
						R.raw.pattern);
			}
			twinkle.start();
		} catch (Exception e) {
			Toast.makeText(getApplicationContext(), "u: " + e.toString(),
					Toast.LENGTH_SHORT).show();
		}
	}

	// -------------------------------------

	public void setTimer(long setTime) {
		game_timer = new CountDownTimer(setTime, 1000) {

			@Override
			public void onTick(long millisUntilFinished) {
				// TODO Auto-generated method stub
				saveTime(millisUntilFinished);
				long seconds = (long) millisUntilFinished / 1000;
				long min = (long) seconds / 60;
				long sec = (long) seconds % 60;
				displayTime(String.valueOf(min), String.valueOf(sec));
			}

			@Override
			public void onFinish() {
				// TODO Auto-generated method stub
				endGame();
			}

		};

		game_timer.start();
	}

	private long milliLeft = 0;

	public void saveTime(long mili) {
		this.milliLeft = mili;
	}

	public void endGame() {
		Intent gg = new Intent(getApplicationContext(),WinPlayer1.class);
		int ai_score = 0;
		if(this.x_score > this.o_score){
			//x wins
			
			if(this.ENABLE_AI){
				gg.putExtra("winner", "ai");
				gg.putExtra("score", this.x_score);
				//
				ai_score = this.x_score;
				this.x_score = 0;
			}else{
				gg.putExtra("winner", "p2");
				gg.putExtra("score", this.x_score);
			}
		}else if (this.x_score < this.o_score){
			//o wins
			gg.putExtra("winner", "p1");
			gg.putExtra("score", this.o_score);
		}else if (this.x_score == this.o_score){
			//draw
			gg.putExtra("winner", "draw");
			gg.putExtra("score", this.o_score);
		}
		
		new  ScoreManager(getApplication(),this.o_score,this.x_score,ai_score);
		startActivity(gg);
		finish();
	}

	public void displayTime(String min, String sec) {
		if (min.length() == 1) {
			min = "0" + min;
		}
		if (sec.length() == 1) {
			sec = "0" + sec;
		}

		lbl_timer.setText(min + ":" + sec);
	}

	// board creation

	// ----------------------------------
	public void generate_board() {
		make_buttons();
		make_board();
	}

	ArrayList<Button> xoxo_buttons = new ArrayList<Button>();
	ArrayList<LinearLayout> xoxo_layers = new ArrayList<LinearLayout>();
	Integer selected_button = 0;

	public void make_buttons() {
		for (this.selected_button = 0; this.selected_button < 27; this.selected_button++) {
			Button bt = new Button(this) {
				@Override
				protected void onMeasure(int widthMeasureSpec,
						int heightMeasureSpec) {
					super.onMeasure(widthMeasureSpec, widthMeasureSpec);
				}
			};

			// adjust button layouts
			bt.setText("");
			android.widget.TableRow.LayoutParams btn_param = new TableRow.LayoutParams(
					0, LayoutParams.WRAP_CONTENT, 1f);
			btn_param.setMargins(3, 3, 3, 3);
			bt.setLayoutParams(btn_param);
			bt.setBackground(getResources()
					.getDrawable(R.drawable.login_button));
			bt.setGravity(Gravity.CENTER);

			// add event to buttons
			XoxoClickListener btn_listener = new XoxoClickListener() {
				@Override
				public void onClick(View v) {
					xoxo_action(this.stored_value, v);
				}
			};
			btn_listener.setStoredValue(selected_button.toString());

			bt.setOnClickListener(btn_listener);
			this.xoxo_buttons.add(bt);
		}
	}

	public void make_board() {
		int xoxo_start = 0;
		int xoxo_end = 3;
		for (int board_level = 0; board_level < 3; board_level++) {
			// Create Board
			LinearLayout pnl_xoxoboard = new LinearLayout(this);
			pnl_xoxoboard.setOrientation(LinearLayout.VERTICAL);
			LayoutParams xoxo_param = new LayoutParams(
					LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
			pnl_xoxoboard.setLayoutParams(xoxo_param);

			// Create Board Layers
			for (int layer_level = 0; layer_level < 3; layer_level++) {
				// Board Layer code
				LinearLayout pnl_layer = new LinearLayout(this);
				pnl_layer.setOrientation(LinearLayout.HORIZONTAL);
				LayoutParams layer_param = new LayoutParams(
						LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
				pnl_layer.setLayoutParams(layer_param);

				pnl_layer.setWeightSum(3.0f);

				// Add Buttons to Layer
				for (int x = xoxo_start; x < xoxo_end; x++) {
					pnl_layer.addView(this.xoxo_buttons.get(x));
				}
				// Add Layer to Board
				pnl_xoxoboard.addView(pnl_layer);
				// increment button
				xoxo_start += 3;
				xoxo_end += 3;
			}

			// add header to board
			ImageView layer_head = new ImageView(this);
			if (board_level == 0) {
				layer_head.setImageResource(R.drawable.layer_1);
			} else if (board_level == 1) {
				layer_head.setImageResource(R.drawable.layer_2);
			} else if (board_level == 2) {
				layer_head.setImageResource(R.drawable.layer_3);
			}
			pnl_board_container.addView(layer_head);

			// add board to layout
			pnl_board_container.addView(pnl_xoxoboard);
		}

	}
}
