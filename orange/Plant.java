package orange;

import java.util.Timer;
import java.util.TimerTask;

public class Plant extends Orange implements Runnable {
	// How long do we want to run the juice processing
	public static final long PROCESSING_TIME = 5 * 1000;
	public static final int ORANGES_PER_BOTTLE = 4;
	public static final int WORKERS = 5;
	public boolean timerDone = false;
	private static int processedOrange = 0;
	private static int unProcessedOrange = 0;
	 
	public static void main(String[] args) {
		// Startup a single plant - you put code here
		Plant plant = new Plant();
		
		// plant processes Oranges until the time is up
		
		timePlant(plant);
		plant.Worker();
		
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
		if(currentOrange.getState() == Orange.State.Processed) {
			return;
		}
		else {
			currentOrange.runProcess();
		}
	}
	
	private static void timePlant(Plant plant) {
		Timer timeThoseOranges = new Timer("threadOne");
		TimerTask taskTimerForOranges = new TimerTask() {
			public void run() {
				plant.timerDone = true;
			}
		};
		timeThoseOranges.schedule(taskTimerForOranges, PROCESSING_TIME);
		
	}
	
	public void Worker() {
		Thread workerThread = new Thread(this);
		workerThread.start();
	}
	
	@Override
	public void run() {
		while(!this.timerDone) {
			this.processOranges();
			if(this.timerDone) {
				unProcessedOrange++;
				break;
			} else {
				processedOrange++;
			}
		}
	}
}
