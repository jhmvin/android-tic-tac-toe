package com.melvin_joemar.perello_delacruz;

import java.util.ArrayList;
import java.util.Arrays;

public class XoxoPattern {

	public XoxoPattern() {
		makePattern();
	}

	private ArrayList<Integer[]> score_pattern = new ArrayList<Integer[]>();
	
	public ArrayList<Integer[]> getAllPattern(){
		return this.score_pattern;
	}

	public static Integer putToken(int placement, int layer) {
		int leverage = 9 * (layer - 1);
		return placement + leverage;
	}

	private Integer[] savePattern(int a, int b, int c) {
		return new Integer[] { a, b, c };
	}

	private Integer[] savePattern(int a, int b, int c, int val) {
		return new Integer[] { a + val, b + val, c + val };
	}

	private Integer[] layerToken(int t1, int t2, int t3, int l1, int l2, int l3) {
		return savePattern(putToken(t1, l1), putToken(t2, l2), putToken(t3, l3));
	}

	// generate 6 permutation of the tokens from 3 different layers
	private void layerPattern(int t1, int t2, int t3) {
		score_pattern.add(layerToken(t1, t2, t3, 1, 2, 3));
		score_pattern.add(layerToken(t1, t2, t3, 1, 3, 2));
		score_pattern.add(layerToken(t1, t2, t3, 2, 1, 3));
		score_pattern.add(layerToken(t1, t2, t3, 2, 3, 1));
		score_pattern.add(layerToken(t1, t2, t3, 3, 1, 2));
		score_pattern.add(layerToken(t1, t2, t3, 3, 2, 1));
	}

	private void makePattern() {
		// LAYER 1
		// horizontal
		score_pattern.add(savePattern(1, 2, 3));
		score_pattern.add(savePattern(4, 5, 6));
		score_pattern.add(savePattern(7, 8, 9));
		// vertical
		score_pattern.add(savePattern(1, 4, 7));
		score_pattern.add(savePattern(2, 5, 8));
		score_pattern.add(savePattern(3, 6, 9));
		// diagonal
		score_pattern.add(savePattern(3, 5, 7));
		score_pattern.add(savePattern(1, 5, 9));

		// LAYER 2
		// horizontal
		int leverage = 9;
		score_pattern.add(savePattern(1, 2, 3, leverage));
		score_pattern.add(savePattern(4, 5, 6, leverage));
		score_pattern.add(savePattern(7, 8, 9, leverage));
		// vertical
		score_pattern.add(savePattern(1, 4, 7, leverage));
		score_pattern.add(savePattern(2, 5, 8, leverage));
		score_pattern.add(savePattern(3, 6, 9, leverage));
		// diagonal
		score_pattern.add(savePattern(3, 5, 7, leverage));
		score_pattern.add(savePattern(1, 5, 9, leverage));

		// LAYER 3
		// horizontal
		leverage = 18;
		score_pattern.add(savePattern(1, 2, 3, leverage));
		score_pattern.add(savePattern(4, 5, 6, leverage));
		score_pattern.add(savePattern(7, 8, 9, leverage));
		// vertical
		score_pattern.add(savePattern(1, 4, 7, leverage));
		score_pattern.add(savePattern(2, 5, 8, leverage));
		score_pattern.add(savePattern(3, 6, 9, leverage));
		// diagonal
		score_pattern.add(savePattern(3, 5, 7, leverage));
		score_pattern.add(savePattern(1, 5, 9, leverage));

		// Inter Layer Pattern
		// single focus
		for (int x = 1; x <= 9; x++) {
			score_pattern.add(savePattern(putToken(x, 1), putToken(x, 2),
					putToken(x, 3)));
		}

		// 147 741 this will generate 6 patterns
		layerPattern(1, 4, 7);
		// 258 852
		layerPattern(2, 5, 8);
		// 369 963
		layerPattern(3, 6, 9);

		// horizontals 123 and 321
		layerPattern(1, 2, 3);
		// 456 654
		layerPattern(4, 5, 6);
		// 789 987
		layerPattern(7, 8, 9);

		// diagonals 159 951
		layerPattern(1, 5, 9);
		// 357 753
		layerPattern(3, 5, 7);
	}

	public Integer checkPattern(ArrayList<Integer> xoxo) {
		int score = 0;
		for (Integer[] patterns : score_pattern) {
			ArrayList<Integer> me = new ArrayList<Integer>(
					Arrays.asList(patterns));
			if (xoxo.containsAll(me)) {
				score++;
			} else {

			}
		}
		return score;
	}

}