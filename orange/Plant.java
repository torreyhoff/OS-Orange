package orange;

import java.util.Timer;
import java.util.TimerTask;

public class Plant extends Orange{
	// How long do we want to run the juice processing
	public static final long PROCESSING_TIME = 5 * 1000;
	public static final int ORANGES_PER_BOTTLE = 4;
	public boolean timerDone;
	 
	public static void main(String[] args) {
		// Startup a single plant - you put code here
		Plant plant = new Plant();
		
		// plant processes Oranges until the time is up
		int processedOrange = 0;
		int unProcessedOrange = 0;
		
		Timer timeThoseOranges = new Timer("threadOne");
		TimerTask taskTimerForOranges = new TimerTask() {
			public void run() {
				plant.timerDone = true;
			}
		};
		timeThoseOranges.schedule(taskTimerForOranges, PROCESSING_TIME);
		
		
		while(!plant.timerDone) {
			plant.processOranges();
			if(plant.timerDone) {
				unProcessedOrange++;
				break;
			} else {
				processedOrange++;
			}
		}
		
		// Summarize the results at the end
		// print total oranges started, processed, number of bottles finished and oranges
		// wasted (not enough to fill a bottle)
		System.out.println("Oranges Started: "+(processedOrange+unProcessedOrange));
		System.out.println("Oranges Processed: "+processedOrange);
		System.out.println("Bottles Finished: "+processedOrange/ORANGES_PER_BOTTLE);
		System.out.println("Oranges Wasted: "+(processedOrange%ORANGES_PER_BOTTLE+unProcessedOrange));		
		System.exit(0);

	}

	// you write the plant functions you need
	public Plant() {
		System.out.println("Creating Orange Plantation.");
		this.timerDone = false;
	}
	
	private void processOranges() {
		Orange currentOrange = new Orange();
		while(true) {
			try {
				currentOrange.runProcess();
			} catch(IllegalStateException e) {
				break;
			}
		}
	}

}
