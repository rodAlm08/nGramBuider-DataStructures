package ie.gmit.sw;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.PriorityQueue;
import java.util.Queue;

public class Parser {

	int nGramSize = 0;
	public File fileOutput;
	long totalSum = 0L;
	long ngramProduced = 0L;
	int size;
	double percentage = 0.0;
	int numProcessFiles = 0;
	DecimalFormat formatter = new DecimalFormat("#,###.00"); // Formats the time output
	double processTime;

	private Map<String, Long> nGramMap = new HashMap<>();
	private FileWriter writeFile;

	public Parser() {
	}

	public void setNgram(int nGramSize) {
		this.nGramSize = nGramSize;
	}

	public void setfileOutput(File file) {
		fileOutput = file;
	}

	public long getHashMapSize() {
		return totalSum;
	}

	public void parse(String dir, boolean probability) throws Exception {

		File directory = new File(dir);

		// System.out.println("Directory : " + directory.getPath());
		File[] files = directory.listFiles();

		
		for (int i = 0; i < files.length; i++) {
			process(dir + "/" + files[i].getName());
			numProcessFiles++;
			// System.out.println(dir + "/" + files[i].getName());
		}

		// sorting with priority queue
		Queue<Entry<String, Long>> sorting = new PriorityQueue<Entry<String, Long>>(nGramMap.size(),
				(a, b) -> b.getValue().compareTo(a.getValue()));

		sorting.addAll(nGramMap.entrySet());

		List<Entry<String, Long>> list = new ArrayList<Entry<String, Long>>();

		// O(n log(n))
		while (sorting.peek() != null) {

			Entry<String, Long> y = sorting.poll();
			list.add(y);
		}

		writeFile = new FileWriter(fileOutput, list, totalSum, probability, size);
		writeFile.output(probability);
		System.out.println();
		System.out
				.println(" _________________________________________________________________________________________");
		System.out
				.println("                                                                                          ");
		System.out
				.println("|**************************************** Summary ****************************************|");
		System.out
				.println("|File saved successful!                                                                   |");
		System.out.println("|File output >>> " + fileOutput.getAbsolutePath() + "         |");
		System.out.println("|Total of " + nGramSize + " Grams " + "produced >>> " + totalSum
				+ "                                                   |");
		System.out.println("|Total of " + numProcessFiles
				+ " Files Processed!                                                             |");
		System.out.println("|Time taken >>>" + formatter.format(processTime)
				+ "  ms.                                                                   |");
		System.out
				.println("|_________________________________________________________________________________________|");
		System.out.println();
	}

	public void process(String files) throws Exception {
		nGramMap.clear();// clear the map
		double start = System.currentTimeMillis(); // Start the clock
		totalSum = 0L;
		ngramProduced = 0L;

		// show the user which file is processing
		System.out.println();
		System.out.println("Processing file: " + files);

		try (BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(files)))) {
			String line = null;
			String last = "";

			// O(n) -> n number of files
			while ((line = br.readLine()) != null) {

				String words = line.toLowerCase().trim().replaceAll("[^a-zA-Z]", "");

				if (!last.equals("")) {

					last = last.substring(last.length() - (nGramSize - 1), last.length());

				}

				words = last + words;
				char[] wd = words.toCharArray();

				// O(n): worst case scenario to loop over all characters in the file
				for (int i = 0; i < wd.length - nGramSize + 1; i++) {
					String gram = String.copyValueOf(wd, i, nGramSize);

					// O(1) put into a hashMap
					if (nGramMap.containsKey(gram)) {
						nGramMap.put(gram, nGramMap.get(gram) + 1L);

					} else {
						nGramMap.put(gram, 1L);
					}
					// get the size to calculate probability
					totalSum += 1L;
					ngramProduced = totalSum;
					size = words.length();

				}
				processTime = ((System.currentTimeMillis() - start) / 1000); // Stop the clock
				last = words;
			}

			// progress bar
			System.out.print(ConsoleColour.YELLOW);
			int meterSize = 100;
			for (int i = 0; i < meterSize; i++) {
				ProgressMeter.printProgress(i + 1, meterSize);
				Thread.sleep(10);
			}

			br.close();
		} catch (Exception e) {
			throw new Exception("[ERROR] Encountered a problem reading the File. " + e.getMessage());
		}

	}

}
