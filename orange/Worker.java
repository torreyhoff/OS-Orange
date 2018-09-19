package orange;

public class Worker implements Runnable {

	private final Thread thread;
	private int badOranges = 0;
	private int counter = 0;
	private static boolean orangeGoneBad = false;
	private boolean timeToWork = false;

	public Worker() {
		thread = new Thread();
//		timeToWork = true;
//		run();
	}
	
	public void startWork() {
		timeToWork = true;
		thread.start();
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
		return thread;
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
		while ( timeToWork ) {//(System.currentTimeMillis() - Plant.startTime) < Plant.PROCESSING_TIME) {
			Orange o = new Orange();
			processOranges(o);
			if (!Plant.orangeGoneBad) {
				counter++;
			}
		}
	}

}
