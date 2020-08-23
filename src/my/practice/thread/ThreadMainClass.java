package my.practice.thread;

import my.practice.thread.runnable.ThredImplementation;

public class ThreadMainClass {
	
	public static void main(String[] args) {
		Thread threadImpl1 = new ThredImplementation();
		Thread threadImpl2 = new ThredImplementation();
		Thread threadImpl3 = new ThredImplementation();
		threadImpl1.start();
		threadImpl2.start();
		threadImpl3.start();
	}

}
