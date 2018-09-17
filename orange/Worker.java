package orange;

public class Worker implements Runnable {

	private Orange currentOrange;
	private final Thread thread;
	private int orangesProvided;
	private volatile boolean timeToWork;

	public Worker() {
		orangesProvided = 0;
		thread = new Thread();
	}
	
	public void startWork() {
		thread.start();
	}
	
	private void getNewOrange() {
		currentOrange = new Orange();
		orangesProvided++;
	}
	
	private void doWork() {
		currentOrange.runProcess();
	}
	
	@Override
	public void run() {
		
	}

}
