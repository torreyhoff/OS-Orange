package orange;

public class Worker extends Thread{

	private int badOranges = 0;
	private int counter = 0;
	private static boolean orangeGoneBad = false;
	private boolean timeToWork = false;

	public Worker(String name) {
		timeToWork = true;
	}
	
	public void startWork() {
		timeToWork = true;
		start();
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
		while (currentOrange.getState() != Orange.State.Processed && timeToWork) {
			currentOrange.runProcess();
		}
		if (currentOrange.getState() != Orange.State.Processed){
			orangeGoneBad=true;
			badOranges++;
		}
	}
	
	@Override
	public void run() {
		while ( timeToWork ) {
			Orange o = new Orange();
			processOranges(o);
			if (!Plant.orangeGoneBad) {
				counter++;
			}
		}
	}
}
