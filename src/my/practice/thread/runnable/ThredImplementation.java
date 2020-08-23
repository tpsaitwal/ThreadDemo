package my.practice.thread.runnable;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.NoSuchElementException;
import java.util.StringTokenizer;
import java.util.logging.Level;
import java.util.logging.Logger;

import my.practice.thread.bean.Book;

public class ThredImplementation extends Thread{

	private static final File file = new File("D:\\workspace\\Inputs\\books.csv");
	
	private static final String COMMA = ",";
	
	private static final Logger LOGGER = Logger.getLogger(ThredImplementation.class.getName());
	
	public void run() {
		try (FileReader fileReader = new FileReader(file);
				BufferedReader bufferedReader = new BufferedReader(fileReader);){
			String line = null;
			while((line = bufferedReader.readLine()) != null) {
				StringTokenizer tokenizer = new StringTokenizer(line, COMMA);
				Book book = new Book();
				book.setTitle(tokenizer.nextToken());
				book.setAuthor(tokenizer.nextToken());
				book.setGenre(tokenizer.nextToken());
				book.setHeight(Integer.valueOf(tokenizer.nextToken()));
				book.setPublisher(tokenizer.nextToken());
				LOGGER.log(Level.INFO, "Thread Name "+ Thread.currentThread().getName() + " " + book.toString());
			}
			
		}catch(FileNotFoundException  fileNotFoundException) {;
			LOGGER.log(Level.SEVERE, "FileNotFoundException {}", fileNotFoundException.getStackTrace());
		}catch(IOException ioException) {
			LOGGER.log(Level.SEVERE, "IOException {}", ioException.getStackTrace());
		}catch(NoSuchElementException  ex){
			//Ignore
		}
	}
}


