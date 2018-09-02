package com.melvin_joemar.perello_delacruz;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import android.content.Context;
import android.content.SharedPreferences;

public class ScoreManager {

	public ScoreManager() {

	}

	private Context global_con;

	public ScoreManager(Context con, Integer p1, Integer p2, Integer ai) {
		this.global_con = con;
		SharedPreferences sp = con.getSharedPreferences("com.tictac.highscore",
				0);
		bestOfMe(sp, p1, p2, ai);
		leaderBoard(sp, p1, p2, ai);
	}

	public void bestOfMe(SharedPreferences sp, int p1, int p2, int ai) {
		SharedPreferences.Editor editor = sp.edit();
		int prevP1 = sp.getInt("BEST_P1", 0);
		int prevP2 = sp.getInt("BEST_P2", 0);
		int prevAI = sp.getInt("BEST_AI", 0);

		if (p1 > prevP1) {
			editor.putInt("BEST_P1", p1);
		}
		if (p2 > prevP2) {
			editor.putInt("BEST_P2", p2);
		}
		if (ai > prevAI) {
			editor.putInt("BEST_AI", ai);
		}
		editor.commit();
	}

	public void leaderBoard(SharedPreferences sp, int p1, int p2, int ai) {
		ArrayList<String> previousTop = new ArrayList<String>();
		for (Integer x = 1; x <= 10; x++) {
			previousTop.add(sp.getString("TOP_" + x.toString(), "N#0"));
		}
		/*
		 * Toast.makeText(this.global_con, sp.getString("TOP_1", "N#0"),
		 * Toast.LENGTH_SHORT).show();
		 */

		// add existing
		ArrayList<FrenchFries> score_holder = new ArrayList<FrenchFries>();
		for (Integer x = 0; x < 10; x++) {
			String[] sc = previousTop.get(x).split("#");
			score_holder.add(new FrenchFries(sc[0], sc[1]));
		}
		//
		score_holder.add(new FrenchFries("P1", String.valueOf(p1)));
		score_holder.add(new FrenchFries("P2", String.valueOf(p2)));
		score_holder.add(new FrenchFries("AI", String.valueOf(ai)));

		// Toast.makeText(this.global_con, String.valueOf(score_holder.size()),
		// Toast.LENGTH_SHORT).show();

		Collections.sort(score_holder, new Comparator<FrenchFries>() {
			@Override
			public int compare(FrenchFries lhs, FrenchFries rhs) {
				// TODO Auto-generated method stub
				return lhs.getScore() < rhs.getScore() ? 1
						: lhs.getScore() == rhs.getScore() ? 0 : -1;
			}
		});

		SharedPreferences.Editor editor = sp.edit();
		for (int x = 0; x < 10; x++) {
			editor.putString("TOP_" + String.valueOf((x + 1)), score_holder
					.get(x).toString());
		}
		editor.commit();

	}

	class FrenchFries {
		private String player;
		private int score;

		FrenchFries() {

		}

		FrenchFries(String player, String score) {
			this.player = player;
			this.score = Integer.parseInt(score);
		}

		public String toString() {
			return this.player + "#" + String.valueOf(this.score);
		}

		public String getPlayer() {
			return this.player;
		}

		public int getScore() {
			return this.score;
		}
	}
}
