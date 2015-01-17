package ooad.fudan.project3.control;

import java.util.Collection;

import edu.fudan.ss.persistence.hibernate.common.IPersistenceManager;
import ooad.fudan.project3.model.*;

public class LibraryAction {
	Library library;
	
	public LibraryAction(Library library){
		this.library = library;
	}
	
	public Library addBook(Book b, IPersistenceManager pm){
		if(b instanceof PaperBook){
			library.createPaperBook(b.getTitle(), pm);
		}else if(b instanceof EBook){
			library.createEBook(b.getTitle(), pm);
		}else{
			System.err.println("Please clarify the type of the book");
		}
		return library;
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
