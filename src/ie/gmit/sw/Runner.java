package ie.gmit.sw;

import java.io.File;
import java.util.Scanner;

public class Runner {
	static Parser parser = new Parser();
	static Scanner scanner = new Scanner(System.in);
	static FileWriter fileWriter;
	int nGramSize = 0;

	public static void main(String[] args) throws Exception {

		String directory = null;
		int selection = 0;
		boolean probability = true;
		int probSelection = 0;

		System.out.println(ConsoleColour.WHITE);
		System.out.println("************************************************************");
		System.out.println("*      ATU - Dept. Computer Science & Applied Physics     *");
		System.out.println("*                                                          *");
		System.out.println("*                  N-Gram Frequency Builder                *");
		System.out.println("*                                                          *");
		System.out.println("************************************************************");

		while (true) {

			System.out.println("(1) Specify Text File Directory");
			System.out.println("(2) Specify n-Gram Size");
			System.out.println("(3) Specify Output File");
			System.out.println("(4) Build n-Grams ");
			System.out.println("(5) Quit");

			// Output a menu of options and solicit text from the user
			System.out.print(ConsoleColour.BLACK_BOLD_BRIGHT);
			System.out.print("Select Option [1-5]>> ");
			// System.out.println();

			selection = scanner.nextInt();

			switch (selection) {

			case 1:
				System.out.print("Please enter the directory: ");
				directory = scanner.next();
				System.out.println(directory);
				System.out.println();
				break;

			case 2:

				System.out.print("Please enter the size of N-Gram: ");
				parser.setNgram(scanner.nextInt());
				System.out.println();
				break;

			case 3:
				System.out.print("Please enter the name of file to output: ");
				String path = scanner.next();
				if (path.contains(".csv")) {
				} else {
					path = path + ".csv";
				}
				parser.setfileOutput(new File(path));
				// System.out.println(outputFile.getPath());
				System.out.println();
				break;

			case 4:
				System.out.println();
				System.out.println("Would you like to add probability of Ngrams to your file?");
				System.out.println("(1) Add probability");
				System.out.println("(2) Don't add probability");
				System.out.print("Please enter your choice: ");
				if ((probSelection = scanner.nextInt()) == 2)
					probability = false;

				parser.parse(directory, probability);
				System.out.println();
				break;

			case 5:
				scanner.close();
				System.exit(0);
				break;

			}

		}

	}

}