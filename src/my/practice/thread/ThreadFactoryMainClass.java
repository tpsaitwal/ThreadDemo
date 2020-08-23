package my.practice.thread;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import my.practice.thread.custom.CustomThreadFactory;
import my.practice.thread.runnable.LoggingProcessor;

public class ThreadFactoryMainClass {

	public static void main(String[] args) {
		ExecutorService executorService = Executors.newFixedThreadPool(5, new CustomThreadFactory()); 
		for(int i=50; i>0 ; i-- ) {
			executorService.submit(new LoggingProcessor()); 
		}
		executorService.shutdown();
	}
}
