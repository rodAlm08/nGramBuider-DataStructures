package ie.gmit.sw;

import java.io.File;
import java.util.List;
import java.util.Map.Entry;

public class FileWriter {

	private File fileWr;
	double totalSum;
	long hashSize;
	boolean prob = true;
	int size;

	public FileWriter(File file, List<Entry<String, Long>> frequency, double totalSum, boolean prob, int size) {
		this.fileWr = file;
		this.frequency = frequency;
		this.totalSum = totalSum;
		this.prob = prob;
		this.size = size;

	}

	private List<Entry<String, Long>> frequency;
	//O(n)
	public void output(boolean prob) throws Exception {

		java.io.FileWriter fw = new java.io.FileWriter(fileWr);

		// System.out.println(totalSum +
		// "*********************************************");

		for (int i = 0; i < frequency.size(); i++) {

			double percentage = ((double) frequency.get(i).getValue() / (double) totalSum);

			if (prob == true) {
				fw.write(frequency.get(i).getKey() + "," + frequency.get(i).getValue() + ","
						+ String.format("%.7f", percentage) + "\n");
			} else {
				fw.write(frequency.get(i).getKey() + "," + frequency.get(i).getValue() + "\n");

			}

		}

		fw.flush();
		fw.close();

	}
}
