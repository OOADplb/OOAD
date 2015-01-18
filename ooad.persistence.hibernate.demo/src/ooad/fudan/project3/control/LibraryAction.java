package ooad.fudan.project3.control;

import java.util.Collection;

import edu.fudan.ss.persistence.hibernate.common.IPersistenceManager;
import ooad.fudan.project3.model.*;

public class LibraryAction {
	Library library;
	
	public LibraryAction(Library library){
		this.library = library;
	}
	
	public Book addBook(String title, String type,
			IPersistenceManager pm) {
		Book temp = getBookByTitle(title);
		if(temp != null){
			System.err.println("The book already exists!");
			return temp;
		}
		
		if(type.equals("Paperbook")){
			temp = library.createPaperBook(title, pm);
		}else if(type.equals("EBook")){
			temp = library.createEBook(title, pm);
		}else{
			System.err.println("Please clarify the type of the book");
		}	

		return temp;
		
	}

	public void deleteBook(String title, IPersistenceManager pm){
		Book temp = getBookByTitle(title);
		if(temp == null){
			System.err.println("The book does not exist!");
		}
		
		library.deleteBook(temp, pm);
	}
	
	public Book getBookByTitle(String title){
		Collection<Book> books = library.getBooks();
		Object[] bookArray = books.toArray();
		if(bookArray.length == 0){
			System.err.println("There is no book in the library");
			return null;
		}
		for(int i=0;i<bookArray.length;i++){
			if(((Book)bookArray[i]).getTitle().equals(title))
				return (Book)bookArray[i];
		}
		System.err.println("There is no book with this title");
		return null;
	}

}
