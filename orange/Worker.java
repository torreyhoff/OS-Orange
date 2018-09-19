package orange;

public class Worker implements Runnable {

	private final Thread thread;
	private int badOranges = 0;
	private int counter = 0;

	public Worker() {
		thread = new Thread();
		run();
	}
	
	public void startWork() {
		thread.start();
	}
	
	public int getBadOranges() {
		return badOranges;
	}
	
	public int getCounter() {
		return counter;
	}
	

	public Thread getThread() {
		return thread;
	}
	
	private void processOranges(Orange currentOrange) {
		while (currentOrange.getState() != Orange.State.Processed && (System.currentTimeMillis() - Plant.startTime) < Plant.PROCESSING_TIME) {
			currentOrange.runProcess();
		}
		if (currentOrange.getState() != Orange.State.Processed){
			Plant.orangeGoneBad=true;
			badOranges++;
		}
	}
	
	@Override
	public void run() {
		while ((System.currentTimeMillis() - Plant.startTime) < Plant.PROCESSING_TIME) {
			Orange o = new Orange();
			processOranges(o);
			if (!Plant.orangeGoneBad) {
				counter++;
			}
		}
	}

}
