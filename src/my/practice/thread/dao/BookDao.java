package my.practice.thread.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.concurrent.Callable;

import my.practice.thread.bean.Book;

public class BookDao implements Callable<Integer>{
	private Book book;
	private Connection connection;
	
	public BookDao(final Book book, final Connection connection) {
		this.book = book;
		this.connection = connection;
	}

	@Override
	public Integer call() throws Exception {
		if(connection == null || book == null) {
			return 0;
		}
		
		PreparedStatement statement = connection.prepareStatement("INSERT INTO BOOKS VALUES(?,?,?,?,?)");
		statement.setString(1, book.getTitle());
		statement.setString(2, book.getAuthor());
		statement.setString(3, book.getGenre());
		statement.setInt(4, book.getHeight());
		statement.setString(5, book.getPublisher());
		return statement.executeUpdate();
	}}
