package my.practice.thread.runnable;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.logging.Level;
import java.util.logging.Logger;

public class FolderWatcher implements Runnable {

	File folder = new File("C:\\Windows\\Temp\\");
	
	@Override
	public void run() {
		try {
			File [] files = folder.listFiles();
			for(File file : files) {
				if((System.currentTimeMillis() - file.lastModified()) > 5*60*10000) {
					Logger.getLogger(FolderWatcher.class.getName()).log(Level.INFO, Thread.currentThread().getName() + " File to delete " + file.getName());
					Files.delete(Paths.get(file.getAbsolutePath()));
				}
			}
		} catch (Exception e) {
			
		}
	}

}
