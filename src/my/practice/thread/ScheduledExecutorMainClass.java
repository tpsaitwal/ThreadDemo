package my.practice.thread;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import my.practice.thread.runnable.FolderWatcher;

public class ScheduledExecutorMainClass {
	public static void main(String[] args) {
		ScheduledExecutorService schedular = Executors.newScheduledThreadPool(3);
		schedular.schedule(new FolderWatcher(), 5, TimeUnit.SECONDS);
		schedular.scheduleAtFixedRate(new FolderWatcher(), 5, 3, TimeUnit.SECONDS);
		//The fixed rate does not consider folderWatcher execution time and schedule the task again
		schedular.scheduleWithFixedDelay(new FolderWatcher(), 5, 4, TimeUnit.SECONDS);
		//The fixed delay considers the folderwatcher execution time and once previous scheduler is completed then starts the next 
		//scheduled job
	}

}
