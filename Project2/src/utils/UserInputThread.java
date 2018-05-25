package utils;

import java.util.Scanner;

public class UserInputThread extends Thread {
	private Scanner scanner;
	private int min;
	private int max;
	private int input;
	
	public UserInputThread(Scanner scanner, int min, int max) {
		this.scanner = scanner;
		this.min = min;
		this.max = max;
	}

	@Override
	public void run() {
		while(this.scanner.hasNext()) {
			if(this.scanner.hasNextInt()) {
				this.input = this.scanner.nextInt();
				this.scanner.nextLine(); //clear buffer
				if(this.input >= this.min && this.input <= this.max) {
					break;
				}
			}
			else {
				this.scanner.next();
			}
		}
	}
	
	public int getInput() {
		return this.input;
	}
	
}
