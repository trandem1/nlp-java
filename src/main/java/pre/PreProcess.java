package pre;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;

/**
 * @author trandem
 *
 */
public class PreProcess {
	private String filepath;
	private LinkedHashMap<String, String> lines;

	public PreProcess(String filepath) {
		// TODO Auto-generated constructor stub
		this.filepath = filepath;
		lines = new LinkedHashMap<String, String>();
	}

	public String load_File() throws IOException {
		ArrayList<String> lines = new ArrayList<String>();
		File f = new File(this.filepath);
		BufferedReader br = new BufferedReader(new FileReader(f));
		while (br.ready()) {
			String line = br.readLine();
			lines.add(line);
		}
		String big_line = "";
		for (String line : lines) {
			big_line += line + " ";
		}
		br.close();
		return big_line;
	}

	public void repalace_String(String big_line) {
		String[] sentences = big_line.split("\\.");
		for (String sentence : sentences) {
			String pre_sen = sentence.replaceAll("[,...;\\.{}?;'']", "").toLowerCase().trim();
			this.lines.put(sentence.trim(), pre_sen);
		}
		System.out.println(this.lines);
	}

	// kich ban chinh cua class
	public void main() throws IOException {
		repalace_String(load_File());
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

	public static void main(String[] args) throws IOException {
		PreProcess pre = new PreProcess("tes");

		pre.main();
	}
}