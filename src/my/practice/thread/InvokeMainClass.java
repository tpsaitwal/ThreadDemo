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

public class InvokeMainClass {
	private static final File file = new File("D:\\workspace\\Inputs\\books.csv");

	private static final String COMMA = ",";

	private static final Logger LOGGER = Logger.getLogger(RunnableMainClass.class.getName());

	public static void main(String[] args) throws ClassNotFoundException, SQLException, InterruptedException, ExecutionException {

		ExecutorService executor = Executors.newFixedThreadPool(3);
		Connection conn = DBConnection.getConnection();

		final long startTime = Calendar.getInstance().getTimeInMillis();

		List<BookDao> callableList = new ArrayList<>();

		try (FileReader fileReader = new FileReader(file);
				BufferedReader bufferedReader = new BufferedReader(fileReader);){
			String line = null;
			while((line = bufferedReader.readLine()) != null) {
				callableList.add(runMethod(line, conn));
			}

			List<Future<Integer>> futureList = executor.invokeAll(callableList);
			for(Future<Integer> future : futureList) {
				LOGGER.log(Level.FINE, "Result of invokeAll  " + future.get());
			}

			//LOGGER.log(Level.INFO, "Result of invokeAny " + executor.invokeAny(callableList));

			executor.shutdown();
			LOGGER.log(Level.ALL, "Main finished");

		} catch(FileNotFoundException  fileNotFoundException) {;
		LOGGER.log(Level.SEVERE, "FileNotFoundException {}", fileNotFoundException.getStackTrace());
		} catch(IOException ioException) {
			LOGGER.log(Level.SEVERE, "IOException {}", ioException.getStackTrace());
		} catch(NullPointerException ne){
			//
		} finally {
			final long endTime = Calendar.getInstance().getTimeInMillis();
			LOGGER.log(Level.INFO, "Time taken ms " + (endTime - startTime) );
		} 
	}

	private static BookDao runMethod(final String line, final Connection conn) {
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
			LOGGER.log(Level.INFO, bookdao.toString());
			return bookdao;
		} catch(NumberFormatException | NoSuchElementException  ex){
			//Ignore
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
}
