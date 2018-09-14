package orange;

public class Plant extends Orange implements Runnable {

	private static final int NUM_THREADS = 5;
	public static final long PROCESSING_TIME = 5 * 1000;
	public static final int ORANGES_PER_BOTTLE = 4;
	public static final int WORKERS = 5;
	public static boolean orangeGoneBad = false;
	public static int counter = 0;
	public static int badOranges = 0;
	public static long startTime = System.currentTimeMillis();
	
	private final String name;
	private final Thread thread;
	 
	public static void main(String[] args) {
		// Startup a single plant - you put code here
		Plant[] plant = new Plant[NUM_THREADS];
		String nameOfOrange = "Orange/Thread ";
		for (int i = 0; i < NUM_THREADS; i++){
			plant[i]= new Plant(nameOfOrange + i); 
		}
		for (int j = 0; j < NUM_THREADS; j++) {
			try {
				System.out.println(j + " is going to die");
				plant[j].getThread().join();
				System.out.println(j + " died");
			} catch (InterruptedException ignored) {}
		}
		if (!orangeGoneBad) {
			System.out.printf("\n%d Oranges were processed\n%d Bottles were made\n%d Oranges were wasted\n", counter,
					counter / ORANGES_PER_BOTTLE, counter % ORANGES_PER_BOTTLE);
		} else {
			System.out.printf("\n%d Oranges were processed\n%d Bottles were made\n%d Oranges were wasted\n", counter,
					counter / ORANGES_PER_BOTTLE, counter % ORANGES_PER_BOTTLE +badOranges);
		}

	}

	// you write the plant functions you need
	public Plant(String name) {
		System.out.println("Creating Orange Plantation.");
		this.name = name;
		thread = new Thread(this);
		thread.start();
	}
	
	public Thread getThread() {
		return thread;
	}
	
	private void processOranges(Orange currentOrange) {
		while (currentOrange.getState() != Orange.State.Processed && (System.currentTimeMillis() - startTime) < PROCESSING_TIME) {
			currentOrange.runProcess();
		}
		if (currentOrange.getState() != Orange.State.Processed){
			orangeGoneBad=true;
			badOranges++;
		}
	}
	
	@Override
	public void run() {
		System.out.println("Starting thread " + name);
		while ((System.currentTimeMillis() - startTime) < PROCESSING_TIME) {
			Orange o = new Orange();
			processOranges(o);
			if (!orangeGoneBad) {
				counter++;
			}
		}
	}
}
