package ooad.fudan.project3.control;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import edu.fudan.ss.persistence.hibernate.common.IPersistenceManager;
import ooad.fudan.project3.model.*;
import ooad.fudan.project3.database.*;

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
			return;
		}
		
		library.deleteBook(temp, pm);
	}
	
	public Book getBookByTitle(String title){
		Collection<Book> books = library.getBooks();
		if((books == null) ||(books.size() == 0)){
			System.err.println("There is no book in the library");
			return null;
		}
		Object[] bookArray = books.toArray();			
		for(int i=0;i<bookArray.length;i++){
			if(((Book)bookArray[i]).getTitle().equals(title))
				return (Book)bookArray[i];
		}
		System.err.println("There is no book with this title");
		return null;
	}

	
	
	public Friend addFriend(String name, IPersistenceManager pm){
		Friend temp = LoadUtil.getFriendByName(name, pm);
		if(temp != null){
			System.err.println("The book already exists!");
			return temp;
		}
		Friend f = Friend.create(name, pm);
		return f;
	}

	public Comment[] getCommentByBook(String title, IPersistenceManager pm){
		Book temp = getBookByTitle(title);
		if(temp == null){
			System.err.println("The book does not exist!");
			return null;
		}

		List<?> commentList = LoadUtil.getFromDB("Comment", "book", temp.getId(), pm);
		Comment[] result = new Comment[commentList.size()];
		for(int i=0;i<result.length;i++){
			result[i] = (Comment)commentList.get(i);
		}
		return result;
	}

	public Note[] getNoteByBook(String title, IPersistenceManager pm){
		Book temp = getBookByTitle(title);
			if(temp == null){
				System.err.println("The book does not exist!");
				return null;
			}
		
		
		List<?> readingList = LoadUtil.getFromDB("Reading", "book", temp.getId(), pm);
		Reading[] result = new Reading[readingList.size()];
		List<Note> noteList = new ArrayList<Note>();
		for(int i=0;i<result.length;i++){
			result[i] = (Reading)readingList.get(i);
			List<?> notesOfThisReading = LoadUtil.getFromDB("Note", "reading", result[i].getId(), pm);
			for(int j=0;j<notesOfThisReading.size();j++){
				noteList.add((Note)notesOfThisReading.get(j));
			}
		}
		Note[] ret = new Note[noteList.size()];
		for(int i=0;i<ret.length;i++){
			ret[i] = noteList.get(i);
		}
		return ret;
		
	}

	public String getStatusByTitle(String title){
		Book bk = getBookByTitle(title);
		if(bk instanceof EBook){
			return "EBook";
		}else{
			PaperBook pbbk = (PaperBook)bk;
			if(pbbk.isBorrow()){
				return "borrowed";
			}
			return "not borrowed";
		}
	}


}
