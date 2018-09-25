package orange;

public class Worker extends Thread{

	private int badOranges = 0;
	private int counter = 0;
	private static boolean orangeGoneBad = false;
	private boolean timeToWork = false;

	public Worker(String name) {
		timeToWork = true;
		System.out.println("worker ready to run");
	}
	
	public void startWork() {
		timeToWork = true;
		System.out.println("starting worker thread");
		run();
	}
	
	public void stopWork() {
		timeToWork = false;
	}
	
	public int getBadOranges() {
		return badOranges;
	}
	
	public int getCounter() {
		return counter;
	}
	
	public static boolean getOrangesGoneBad() {
		return orangeGoneBad;
	}

	public Thread getThread() {
		return this;
	}
	
	private void processOranges(Orange currentOrange) {
		while (currentOrange.getState() != Orange.State.Processed && timeToWork) { //(System.currentTimeMillis() - Plant.startTime) < Plant.PROCESSING_TIME) {
			currentOrange.runProcess();
		}
		if (currentOrange.getState() != Orange.State.Processed){
			orangeGoneBad=true;
			badOranges++;
		}
	}
	
	@Override
	public void run() {
		System.out.println("Worker is Working: " + currentThread().getName());
		while ( timeToWork ) {//(System.currentTimeMillis() - Plant.startTime) < Plant.PROCESSING_TIME) {
			Orange o = new Orange();
			processOranges(o);
			if (!Plant.orangeGoneBad) {
				System.out.println(counter);
				counter++;
			}
		}
	}

}
