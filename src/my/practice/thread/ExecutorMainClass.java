package my.practice.thread;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.StringTokenizer;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.logging.Level;
import java.util.logging.Logger;

import my.practice.thread.bean.Book;
import my.practice.thread.dao.BookDao;
import my.practice.thread.dao.DBConnection;

public class ExecutorMainClass {
	private static final File file = new File("D:\\workspace\\Inputs\\books.csv");

	private static final String COMMA = ",";

	private static final Logger LOGGER = Logger.getLogger(RunnableMainClass.class.getName());

	public static void main(String[] args) throws ClassNotFoundException, SQLException, InterruptedException, ExecutionException {
		
		ExecutorService executor = Executors.newFixedThreadPool(3);
		Connection conn = DBConnection.getConnection();

		final long startTime = Calendar.getInstance().getTimeInMillis();

		List<Future<Integer>> futureList = new ArrayList<>();

		try (FileReader fileReader = new FileReader(file);
				BufferedReader bufferedReader = new BufferedReader(fileReader);){
			String line = null;
			while((line = bufferedReader.readLine()) != null) {
				@SuppressWarnings("unchecked")
				Future<Integer> future = (Future<Integer>) executor.submit(runMethod(line, conn));
				futureList.add(future);
			}
		} catch(FileNotFoundException  fileNotFoundException) {;
		LOGGER.log(Level.SEVERE, "FileNotFoundException {}", fileNotFoundException.getStackTrace());
		} catch(IOException ioException) {
			LOGGER.log(Level.SEVERE, "IOException {}", ioException.getStackTrace());
		}finally {
			final long endTime = Calendar.getInstance().getTimeInMillis();
			LOGGER.log(Level.INFO, "Time taken ms " + (endTime - startTime) );
		}
		LOGGER.log(Level.ALL, "Main finished");
		for(Future<Integer> future : futureList) {
			LOGGER.log(Level.FINE, "Number  " + future.get());
		}
		executor.shutdown();
	}

	private static Runnable runMethod(final String line, final Connection conn) {
		Runnable runnable = () -> {
			try {
				StringTokenizer tokenizer = new StringTokenizer(line, COMMA);
				Book book = new Book();
				book.setTitle(tokenizer.nextToken());
				book.setAuthor(tokenizer.nextToken());
				book.setGenre(tokenizer.nextToken());
				book.setHeight(Integer.valueOf(tokenizer.nextToken()));
				book.setPublisher(tokenizer.nextToken());
				BookDao bookdao = new BookDao(book, conn);
				bookdao.call();
			} catch(NumberFormatException | NoSuchElementException  ex){
				//Ignore
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		};
		return runnable;
	}

}
