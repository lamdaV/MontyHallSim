package main;

import java.util.Random;

/**
 * A simple simulator of the Monty Hall problem with 3 doors.
 * 
 * @author lamd. Created May 16, 2015.
 */
public class MontyHallSimulator {
	private int randomChoice;
	private int userChoice;
	private int[] options;
	private int givenWrongDoor;

	/**
	 * Constructs a Monty Hall Problem simulator with 3 doors.
	 * 
	 * @param userChoice
	 */
	public MontyHallSimulator(int userChoice) {
		this.userChoice = userChoice;
		this.options = new int[3];
		this.givenWrongDoor = -1;
		populateOptions();
	}

	/**
	 * Resets the simulator for subsequent runs.
	 * 
	 */
	public void resetSim() {
		this.givenWrongDoor = -1;
	}

	/**
	 * Sets the userChoice to the desired choice.
	 * 
	 * @param userChoice
	 */
	public void setUserChoice(int userChoice) {
		this.userChoice = userChoice;
	}

	/**
	 * Prints the options onto the console.
	 * 
	 */
	public void printDoors() {
		String[] printSelection = new String[3];

		for (int i = 0; i < this.options.length; i++) {
			if (i == this.givenWrongDoor) {
				printSelection[i] = "X";
			} else {
				printSelection[i] = Integer.toString(this.options[i]);
			}
		}

		System.out.printf("|\t%s\t|\t%s\t|\t%s\t|\n", printSelection[0],
				printSelection[1], printSelection[2]);
	}

	private void populateOptions() {
		for (int i = 1; i <= 3; i++) {
			this.options[i - 1] = i;
		}
	}

	/**
	 * Sets what the correct door should be.
	 * 
	 */
	public void setRandomChoice() {
		this.randomChoice = new Random().nextInt(3) + 1;
	}

	/**
	 * Returns one of the two incorrect choices. It invalidates it from the
	 * options array with a -1.
	 * 
	 * @return integer of an incorrect choice.
	 */
	public int getWrongDoor() {
		setRandomChoice();

		int randomWrongChoice;

		while (true) {
			randomWrongChoice = new Random().nextInt(this.options.length);

			if (this.options[randomWrongChoice] != this.randomChoice
					&& this.options[randomWrongChoice] != this.userChoice) {
				break;
			}
		}

		this.givenWrongDoor = randomWrongChoice;

		return this.options[randomWrongChoice];
	}

	/**
	 * Switches the userChoice to an unselected option that has not been given
	 * away.
	 * 
	 */
	public void switchDoor() {
		for (int i = 0; i < this.options.length; i++) {
			if (i == this.givenWrongDoor) {
				continue;
			}
			if (this.options[i] == this.userChoice) {
				continue;
			}
			this.userChoice = this.options[i];
			break;
		}
	}

	/**
	 * Checks if the user was correct.
	 * 
	 * @param isAutomate 
	 * 
	 * @return the boolean that corresponds with the user's correctness.
	 */
	public boolean checkValidity(boolean isAutomate) {
		if (this.userChoice == this.randomChoice) {
			if (!(isAutomate)) {
				System.out.println("Congrats! You Win!");
			}
			return true;
		}
		if (!(isAutomate)) {
			System.out.println("You Lose!");
		}
		return false;
	}
}
