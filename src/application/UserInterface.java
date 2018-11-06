package application;
import java.util.ArrayList;
import java.util.Scanner;

/**************************************************
 * 
 * Class Name : UserInterface
 * Purpose : The beginning of the program. Contains the user interactable part of the program.
 * 
 **************************************************/
public class UserInterface {

	private static ArrayList<String> inputtedSequences;

	private static Scanner input = new Scanner(System.in);

	/**
	 * Method Name : main 
	 * Purpose : Start of the program. Initiate loading in data for the algorithm.
	 * 
	 * Parameters : args - A user inputed data file to immediately run the algorithm
	 * on.
	 * 
	 * Return : Nothing
	 **/
	public static void main(String[] args) {

		inputtedSequences = new ArrayList<String>(2);
		inputtedSequences.add(" ");
		inputtedSequences.add(" ");

		commandLine();
	}

	/**
	 * Method Name : commandLine
	 * Purpose : Prints out options on screen for the user to run the program with.
	 * 
	 * Parameters : Nothing
	 * 
	 * Return : Nothing
	 **/
	public static void commandLine() {

		String selection;
		boolean flag = false;

		do {
			System.out.print("\nInputed Sequences:\n" + inputtedSequences.get(0) + "\n" + inputtedSequences.get(1)
					+ "\n\n" + "Choose the number corresponding to an option\n\n" + "1. Input DNA sequences\n"
					+ "2. Run the algorithm\n" + "3. Quit the program\n");
			selection = input.next();

			if (!parseSelection(selection, false))//// Tests to make sure user has inputed an integer value.
			{
				System.out.print("\nYou have entered an invalid choice.\n");
				flag = false;
			} else if (Integer.parseInt(selection) < 1 || Integer.parseInt(selection) > 3)
			// Tests to make sure user has inputed a correct value.
			{
				System.out.print("\nYou have entered an invalid choice.\n");
				flag = false;
			} else
				flag = true;
		} while (!flag);

		switch (Integer.parseInt(selection)) {
		case 1:
			grabSequences();
			break;
		case 2:
			runTheAlgorithm();
			break;
		case 3:
			System.exit(0);

		default:
			System.out.print("\nInvalid program selection.\n");
		}

		commandLine();
	}

	/**
	 * Method Name : parseSelection
	 * Purpose : Tests to make sure user has inputed an integer value or a valid DNA sequence.
	 * 
	 * Parameters : s - A string inputed in by the user
	 * 				sequenceFlag - A flag representing whether the selection is being tested for integer values or as a sequence
	 * 
	 * Return : Whether or not an integer value was inputed.
	 **/
	private static boolean parseSelection(String s, boolean sequenceFlag) {
		// Test for input as an integer
		if (!sequenceFlag) {
			try {
				Integer.parseInt(s);
				return true;
			} catch (NumberFormatException e) {
				return false;
			}
		}
		// Test for input as a DNA sequence
		else {
			if (s.length() > 10)
				return false;
			for (int i = 0; i < s.length(); i++)
				if (s.charAt(i) == 'A' || s.charAt(i) == 'C' || s.charAt(i) == 'G' || s.charAt(i) == 'T'
						|| s.charAt(i) == 'a' || s.charAt(i) == 'c' || s.charAt(i) == 'g' || s.charAt(i) == 't')
					;
				else
					return false;
			return true;

		}

	}

	/**
	 * Method Name : grabSequence
	 * Purpose : 	Grab DNA sequence input from the user
	 * 
	 * Parameters : Nothing
	 * 
	 * Return : Nothing
	 **/
	private static void grabSequences() {
		String selection;
		boolean flag;

		inputtedSequences = new ArrayList<String>(2);

		for (int i = 0; i < 2; i++) {
			flag = false;

			do {
				System.out.print("Please input a valid, 1-10 character sequence: ");
				selection = input.next();

				if (!parseSelection(selection, true))//// Tests to make sure user has inputed a valid DNA sequence.
				{
					System.out.print("\nYou have entered an invalid DNA sequence. HINT: A C G T only\n");
					flag = false;
				} else
					flag = true;
			} while (!flag);

			inputtedSequences.add(selection);
		}

	}

	/**
	 * Method Name : runTheAlgorithm
	 * Purpose : Starts running the NeedlemanWunsch algorithm on a loaded data set.
	 * 
	 * Parameters : Nothing
	 * 
	 * Return : Nothing
	 **/
	private static void runTheAlgorithm() {
		
		SequenceAlignment algorithm = new SequenceAlignment(inputtedSequences.get(0), inputtedSequences.get(1));
		String[] alignments = algorithm.getAlignment();
		System.out.print("\nAligned sequences:\n");
		for (int i = 0; i < alignments.length; i++)
			System.out.print(alignments[i] + "\n");
		algorithm.printScoreTable();
		System.out.print("\nEnter in any key to run again");
		input.next();
	}
}
