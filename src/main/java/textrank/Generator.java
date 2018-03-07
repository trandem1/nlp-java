package textrank;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import pre.PreProcess;

/**
 * @author trandem
 *
 */
public class Generator {

	private LinkedHashMap<String, Float> strScore;
	private LinkedHashMap<String, String> lines;
	private String path;
	public Generator(String path) throws IOException {
		// TODO Auto-generated constructor stub
		this.strScore = new LinkedHashMap<String, Float>();
		this.path = path;
		insertStr();
	}

	public void insertStr() throws IOException {
		PreProcess pre = new PreProcess(this.path);
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

	// in ra % bai van
	public void print_Sum(float percent) {
		HashSet<Float> best_scores = sort_hashmap(this.strScore, percent);
		for (String sentence : this.strScore.keySet()) {
			if (best_scores.contains(this.strScore.get(sentence))) {
				for (String old_String : lines.keySet()) {
					if(lines.get(old_String).equals(sentence)) {
						System.out.println(old_String + ".");
					}
				}
			}
		}
	}

	/**
	 * lay ra so luong gia tri dang gia nhat trong bai van input la 1 map chua cau
	 * va diem cua cau input percent la phan tram vi du lay 1/2 doan van thi percent
	 * =1/2
	 * 
	 */
	public HashSet<Float> sort_hashmap(LinkedHashMap<String, Float> linkedHashMap, float percent) {
		// LinkedHashMap<String, Float> map1 = new LinkedHashMap<>();
		// map1 = linkedHashMap.entrySet().stream().sorted(Map.Entry.<String,
		// Float>comparingByValue())
		// .collect(Collectors.toMap(k -> k.getKey(), v -> v.getValue(), (k, v) -> k,
		// LinkedHashMap::new));
		// System.out.println(map1);
		List<Float> score = new ArrayList<>();
		score.addAll(linkedHashMap.values());
		Collections.sort(score);
		float nguong = percent * linkedHashMap.size();
		int dem = 0;
		HashSet<Float> result = new HashSet<>();
		for (int i = score.size() - 1; i >= 0; i--) {
			result.add(score.get(i));
			dem++;
			if (dem >= nguong) {
				break;
			}
		}
		return result;
	}

	public LinkedHashMap<String, Float> getStrScore() {
		return strScore;
	}

	public void setStrScore(LinkedHashMap<String, Float> strScore) {
		this.strScore = strScore;
	}

	public static void main(String[] args) throws IOException {
		Generator te = new Generator("tes");
		te.calcAllSentenceScores();
		te.print_Sum(0.5F);
	}

}
