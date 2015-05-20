package main;

import java.util.Scanner;

/**
 * The main class.
 * 
 * @author lamd. Created May 16, 2015.
 */
public class MontyHallMain {
	/**
	 * Simulates the Monty Hall problem.
	 * 
	 * @param args
	 */
	@SuppressWarnings({ "boxing", "null" })
	public static void main(String[] args) {
		// Variables.
		String userInput = null;
		int wrongDoor;
		boolean isCorrect;
		double win = 0;
		double lose = 0;
		double probability;
		int userChoice;
		boolean isAutomate = false;
		int automateCounter = 0;
		int automateCounterLimit = 0;
		long start = 0;
		long end = 0;

		// Setup.
		Scanner sc = new Scanner(System.in);
		MontyHallSimulator sim = new MontyHallSimulator(0);
		
		
		// Checks for command line arguments.
		try {
			if (args[0] != null && args[1] != null) {
				if (args[0].equals("-a")) {
					isAutomate = true;
					try {
						userInput = args[1];
						automateCounterLimit = Integer.parseInt(userInput);
						start = System.currentTimeMillis();
					} catch (NumberFormatException e) {
						System.out.println("Please enter a number next time!");
						sc.close();
						return;
					}
				}
			}
		} catch (ArrayIndexOutOfBoundsException e) {
			// Do nothing. Allow users to manually enter choices.
		}

		while (true) {
			if (isAutomate) {
				if (automateCounter >= automateCounterLimit) {
					break;
				}
				automateCounter++;
			}

			// Print the options.
			if (!(isAutomate)) {
				sim.printDoors();
			}

			// Sets a random choice.
			sim.setRandomChoice();

			if (!(isAutomate)) {
				System.out
						.println("Enter a number between 1 and 3 as choices. Enter 'q' to quit.");
			}

			if (isAutomate) {
				userInput = "1";
			} else {
				userInput = sc.next();
				System.out.println();
			}

			// Quit.
			if (userInput.equals("q")) {
				break;
			}

			// Catches erroneous entries.
			try {
				userChoice = Integer.parseInt(userInput);
				if (userChoice <= 0 || userChoice > 3) {
					System.out.println("Please enter a number between 1 - 3.");
					continue;
				}
				sim.setUserChoice(Integer.parseInt(userInput));
			} catch (NumberFormatException e) {
				System.out
						.println("Please enter a number between 1 - 3 or enter 'q' to quit!\n");
				continue;
			}

			// Give the user a hint by getting one of the two incorrect options.
			wrongDoor = sim.getWrongDoor();

			if (!(isAutomate)) {
				System.out.printf(
						"One of the incorrect choices was door %d.\n",
						wrongDoor);
				sim.printDoors();

				// Ask if the user would like to switch his choice.
				System.out.println("Do you wish to change your choice? (y/n)");
			}

			// Ensures 'y' or 'n' is typed.
			while (true) {
				if (isAutomate) {
					userInput = "y";
				} else {
					userInput = sc.next();
					System.out.println();
				}

				if (userInput.equals("y")) {
					sim.switchDoor();
					break;
				}

				if (userInput.equals("n")) {
					break;
				}

				System.out.println("Please enter 'y' for yes and 'n' for no.");
			}

			// Checks if the user has correctly decided.
			isCorrect = sim.checkValidity(isAutomate);

			// Increment the results accordingly.
			if (isCorrect) {
				win++;
			} else {
				lose++;
			}

			// Reset the simulator.
			sim.resetSim();
		}

		// Calculate the probability.
		probability = win / (win + lose);

		// Print the results to console.
		System.out.println("\n~~~~~~~~~~~~~Results~~~~~~~~~~~~~~~~~");
		System.out.printf("\n Win:\t\t%.2f", win);
		System.out.printf("\n Lose:\t\t%.2f", lose);
		System.out.printf("\n Probability:\t%.2f", probability);
		
		// Print the time it took to process.
		if (isAutomate) {
			end = System.currentTimeMillis();
			System.out.printf("\n\n Time: \t\t%d s\n", (end - start) / 1000);
		}
		// Close the Scanner.
		sc.close();

	}

}
