package orange;

public class Plant implements Runnable {

	public static final int NUM_THREADS = 5;
	public static final long PROCESSING_TIME = 1 * 1000;
	public static final int ORANGES_PER_BOTTLE = 4;
	public static final int WORKERS = 5;
	public String name = "";
	public static boolean orangeGoneBad = false;
	public static int totalCounter = 0;
	public static int totalBadOranges = 0;
	public static int workersCounter = 0;
	public static int workersBadOranges = 0;
	public static long startTime = System.currentTimeMillis();
	
	private Worker[] workers = new Worker[WORKERS];
	
	public static void main(String[] args) {
		
		Plant[] plant = new Plant[NUM_THREADS];
		String nameOfOrange = "Plant ";
		for (int i = 0; i < NUM_THREADS; i++){
			plant[i]= new Plant(nameOfOrange + i);
		}
		for (Plant p : plant) {
			p.startPlant();
		}
		
		delay(PROCESSING_TIME, "Plant Malfunction");
		
		for (Plant p : plant) {
			workersCounter = 0;
			workersBadOranges = 0;
			System.out.println("Stopping Plant");
			p.stopPlant();
			System.out.println(p.name + " produced " + workersCounter + " Oranges. And wasted " + workersBadOranges + " Oranges");
			totalCounter += workersCounter;
			totalBadOranges += workersBadOranges;
		}
		
		if (!Worker.getOrangesGoneBad()) {
			System.out.printf("\n%d Oranges were processed\n%d Bottles were made\n%d Oranges were wasted\n", totalCounter,
					totalCounter / ORANGES_PER_BOTTLE, totalCounter % ORANGES_PER_BOTTLE);
		} else {
			System.out.printf("\n%d Oranges were processed\n%d Bottles were made\n%d Oranges were wasted\n", totalCounter,
					totalCounter / ORANGES_PER_BOTTLE, totalCounter % ORANGES_PER_BOTTLE + totalBadOranges);
		}

	}

	public Plant(String name) {
		this.name = name;
		System.out.println("Creating Orange Plantation " + name);
		for (int i = 0; i < WORKERS; i++){
			workers[i]= new Worker("Worker " + i);
		}
	}
	
	public void startPlant() {
		run();
	}
	
	public void stopPlant() {
		for (Worker w : workers) {
			w.stopWork();
			try {
				//System.out.println("Worker is going home");
				workersCounter += w.getCounter();
				workersBadOranges += w.getBadOranges();
				w.getThread().join();
				//System.out.println("Worker left");
			} catch (InterruptedException ignored) {}
		}
	}
	
	public Plant getThread() {
		return this;
	}

	private static void delay(long time, String errMsg) {
		long sleepTime = Math.max(1, time);
		try {
			Thread.sleep(sleepTime);
		} catch (InterruptedException e) {
			System.err.println(errMsg);
		}
	}
	
	@Override
	public void run() {
		System.out.println("Starting Workers");
		for (Worker w : workers) {
			w.startWork();
		}
	}
}
