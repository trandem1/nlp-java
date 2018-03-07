package textrank;

import java.awt.List;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.Set;

import pre.PreProcess;

/**
 * @author trandem
 *
 */
public class Generator {

	private LinkedHashMap<String, Float> strScore;
	private LinkedHashMap<String, String> lines;

	public Generator() throws IOException {
		// TODO Auto-generated constructor stub
		this.strScore = new LinkedHashMap<String, Float>();
		insertStr();
	}

	public void insertStr() throws IOException {
		PreProcess pre = new PreProcess("tes");
		pre.main();
		this.lines = pre.getLines();
		for (String key : lines.keySet()) {
			this.strScore.put(lines.get(key), 0F);
		}
	}

	public float calIntersec(String s1, String s2) {
		if (s1.equals(s2)) {
			return 0;
		}
		HashSet<String> x = new HashSet<String>(Arrays.asList(s1.split(" ")));
		HashSet<String> y = new HashSet<String>(Arrays.asList(s2.split(" ")));
		int sizex = x.size();
		int sizey = y.size();
		x.retainAll(y);
		int giao = x.size();
		if (sizex + sizey == 0) {
			return 0;
		}
		return (float) 2F * giao / (sizex + sizey);
	}

	public void calcAllSentenceScores() {
		ArrayList<String> mySenten = new ArrayList<String>();
		for (String key : strScore.keySet()) {
			mySenten.add(key);
		}
		for (int i = 0; i < mySenten.size(); i++) {
			float diem = 0;
			for (int j = 0; j < mySenten.size(); j++) {
				if (i == j) {
					continue;
				} else {
					diem += calIntersec(mySenten.get(i), mySenten.get(j));
				}
			}
			strScore.put(mySenten.get(i), diem);
		}
	}

	public void print_Sum() {
		for (String x : strScore.keySet()) {
			for (String key : lines.keySet()) {
				if (lines.get(key).equals(x)) {
					System.out.println(key + "\t" + strScore.get(x));
				}
			}
		}
	}

	public static void main(String[] args) throws IOException {
		Generator te = new Generator();
		te.calcAllSentenceScores();
		te.print_Sum();
	}
}
