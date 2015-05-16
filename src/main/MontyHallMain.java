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
		boolean isAutomate;
		int automateCounter = 0;
		int automateCounterLimit = 0;

		// Setup.
		Scanner sc = new Scanner(System.in);
		MontyHallSimulator sim = new MontyHallSimulator(0);

		System.out.println("Automate n trials with switch? (y/n)");
		// Ensures 'y' or 'n' is typed.
		while (true) {
			userInput = sc.next();
			System.out.println();

			if (userInput.equals("y")) {
				isAutomate = true;
				System.out.println("Enter limit: ");
				try {
					userInput = sc.next();
					automateCounterLimit = Integer.parseInt(userInput);
				} catch (NumberFormatException e) {
					System.out.println("Please enter a number next time!");
					return;
				}
				break;
			}

			if (userInput.equals("n")) {
				isAutomate = false;
				break;
			}

			System.out.println("Please enter 'y' for yes and 'n' for no.");
		}

		while (true) {
			if (isAutomate) {
				if (automateCounter >= automateCounterLimit) {
					break;
				} 
				automateCounter++;
			}
			
			// Print the options.
			sim.printDoors();

			// Sets a random choice.
			sim.setRandomChoice();

			System.out
					.println("Enter a number between 1 and 3 as choices. Enter 'q' to quit.");
			if (isAutomate) {
				userInput = "1";
			} else {
				userInput = sc.next();
			}
			System.out.println();

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
			System.out.printf("One of the incorrect choices was door %d.\n",
					wrongDoor);
			sim.printDoors();

			// Ask if the user would like to switch his choice.
			System.out.println("Do you wish to change your choice? (y/n)");
			
			// Ensures 'y' or 'n' is typed.
			while (true) {
				if (isAutomate) {
					userInput = "y";
				} else {
					userInput = sc.next();
				}
				
				System.out.println();
				
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
			isCorrect = sim.checkValidity();

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
		System.out.println("~~~~~~~Results~~~~~~~");
		System.out.printf(" Win:\t\t%.2f", win);
		System.out.printf("\n Lose:\t\t%.2f", lose);
		System.out.printf("\n Probability:\t%.2f", probability);

		// Close the Scanner.
		sc.close();

	}

}
