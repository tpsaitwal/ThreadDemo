package my.practice.thread.runnable;

import java.util.Calendar;
import java.util.logging.Logger;

public class LoggingProcessor implements Runnable{

	private static final Logger LOGGER = Logger.getLogger(LoggingProcessor.class.getName());
	
	@Override
	public void run() {
		LOGGER.info(Thread.currentThread().getName() + " " + Calendar.getInstance().getTime());
	}

}
