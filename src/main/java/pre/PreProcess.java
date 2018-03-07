package pre;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedHashMap;

/**
 * @author trandem
 *
 */
public class PreProcess {
	private String filepath;
	private LinkedHashMap<String, String> lines;
	private HashSet<String> stopW;

	public PreProcess(String filepath) {
		// TODO Auto-generated constructor stub
		this.filepath = filepath;
		this.lines = new LinkedHashMap<String, String>();
		this.stopW = new HashSet<String>();
	}

	// doc 1 van ban tra lai 1 string
	public String load_File() throws IOException {
		ArrayList<String> lines = new ArrayList<String>();
		File f = new File(this.filepath);
		BufferedReader br = new BufferedReader(new FileReader(f));
		while (br.ready()) {
			String line = br.readLine();
			lines.add(line);
//			System.out.println(line);
		}
		String big_line = "";
		for (String line : lines) {
			big_line += line + " ";
		}
		br.close();
//		System.out.println(big_line);
		return big_line;
	}

	// lay ra cac tu dung trong tieng viet cac tu khong co y nghia phan chia
	public void get_StopWord() throws IOException {
		String path = "stopword";
		File f = new File(path);
		BufferedReader br = new BufferedReader(new FileReader(f));
		while (br.ready()) {
			this.stopW.add(br.readLine());
		}
		br.close();
	}

	/**
	 * loai bo tu dung ra khoi 1 cau input 1 string -> output la cau da loai bo tu
	 * dung
	 * 
	 */
	public String remove_stopWord(String sentence) {
		String output = "";
		String[] input = sentence.split(" ");
		for (String sen : input) {
			if (this.stopW.contains(sen)) {
				continue;
			} else {
				output += sen + " ";
			}
		}
		return output.trim();
	}

	public void repalace_String(String big_line) {
		String[] sentences = big_line.split("\\. ");
		for (String sentence : sentences) {
			if(sentence.length() ==0) {
				continue;
			}
			String pre_sen = sentence.replaceAll("[,...;\\.{}?;'']", "").toLowerCase().trim();
			this.lines.put(sentence.trim(), remove_stopWord(pre_sen));
		}

	}

	// kich ban chinh cua class
	// load file -> repalace_string -> remove stop
	// output ta se nhan duoc 1 linked hashset moi
	public void main() throws IOException {
		repalace_String(load_File());
//		for (String key : this.lines.keySet()) {
//			System.out.println(lines.get(key));
//		}
	}

	public String getFilepath() {
		return filepath;
	}

	public void setFilepath(String filepath) {
		this.filepath = filepath;
	}

	public LinkedHashMap<String, String> getLines() {
		return lines;
	}

	public void setLines(LinkedHashMap<String, String> lines) {
		this.lines = lines;
	}

	public HashSet<String> getStopW() {
		return stopW;
	}

	public void setStopW(HashSet<String> stopW) {
		this.stopW = stopW;
	}

	public static void main(String[] args) throws IOException {
		PreProcess pre = new PreProcess("tes");
		pre.main();
	}
}