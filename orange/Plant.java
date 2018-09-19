package orange;

public class Plant extends Orange implements Runnable {

	public static final int NUM_THREADS = 5;
	public static final long PROCESSING_TIME = 5 * 1000;
	public static final int ORANGES_PER_BOTTLE = 4;
	public static final int WORKERS = 5;
	public static boolean orangeGoneBad = false;
	public static int totalCounter = 0;
	public static int totalBadOranges = 0;
	public static long startTime = System.currentTimeMillis();
	
	private Thread thread;
	private Worker[] workers = new Worker[WORKERS];
	
	public static void main(String[] args) {
		
		Plant[] plant = new Plant[NUM_THREADS];
		String nameOfOrange = "Plant ";
		for (int i = 0; i < NUM_THREADS; i++){
			plant[i]= new Plant(nameOfOrange + i); 
			plant[i].startPlant();
		}
		
		delay(PROCESSING_TIME, "Plant Malfunction");
		
		for (Plant p : plant) {
			p.stopPlant();
		}
//		for (int j = 0; j < NUM_THREADS; j++) {
//			try {
//				System.out.println("Plant " + j + " is going to close");
//				plant[j].getThread().join();
//				System.out.println(j + " closed");
//			} catch (InterruptedException ignored) {}
//		}
		
		if (!Worker.getOrangesGoneBad()) {
			System.out.printf("\n%d Oranges were processed\n%d Bottles were made\n%d Oranges were wasted\n", totalCounter,
					totalCounter / ORANGES_PER_BOTTLE, totalCounter % ORANGES_PER_BOTTLE);
		} else {
			System.out.printf("\n%d Oranges were processed\n%d Bottles were made\n%d Oranges were wasted\n", totalCounter,
					totalCounter / ORANGES_PER_BOTTLE, totalCounter % ORANGES_PER_BOTTLE + totalBadOranges);
		}

	}

	public Plant(String name) {
		System.out.println("Creating Orange Plantation " + name);
		for (int i = 0; i < WORKERS; i++){
			workers[i]= new Worker(); 
		}
		thread = new Thread();
		//run();
	}
	
	
	public void startPlant() {
		thread.start();
	}
	
	public void stopPlant() {
		for (Worker w : workers) {
			w.stopWork();
		}
	}
	
	public Thread getThread() {
		return thread;
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
		for (Worker w : workers) {
			w.startWork();
		}
		for (int j = 0; j < NUM_THREADS; j++) {
			try {
				System.out.println("Worker " + j + " is going home");
				System.out.println(workers[j].getCounter());
				totalCounter += workers[j].getCounter();
				totalBadOranges += workers[j].getBadOranges();
				workers[j].getThread().join();
				System.out.println(j + " left");
			} catch (InterruptedException ignored) {}
		}
	}
}
