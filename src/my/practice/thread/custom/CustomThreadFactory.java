package my.practice.thread.custom;

import java.util.concurrent.ThreadFactory;

public class CustomThreadFactory implements ThreadFactory {
	
	private static int counter = 0;

	@Override
	public Thread newThread(Runnable arg0) {
		int priority = (int)(Math.random()*10);
		if(priority <= 0) {
			priority = 10;
		}
		System.out.println("Priority " + priority);
		Thread thread = new Thread(arg0);
		thread.setPriority(priority);
		thread.setName("Custom-" + (++counter));
		return thread;
	}

}
