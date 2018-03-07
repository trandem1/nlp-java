package pre;

import java.util.HashSet;

public class test {
	private HashSet<String> stopW;

	public test() {
		// TODO Auto-generated constructor stub
		stopW = new HashSet<String>();
		stopW.add("và");
		stopW.add("những");
	}

	public void remove_stopWord(String sentence) {
		String output = "";
		String[] input = sentence.split(" ");
		for (String sen : input) {
			if (this.stopW.contains(sen)) {
				continue;
			} else {
				output += sen + " ";
			}
		}
		sentence = output;
		System.out.println(output);
	}

	public static void main(String[] args) {
		String sentence ="dem và những người bạn";
		test t = new test();
		t.remove_stopWord(sentence);
		System.out.println(sentence);
	}
}
