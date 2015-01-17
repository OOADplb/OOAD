package ooad.fudan.project3.control;

import java.sql.Date;

import edu.fudan.ss.persistence.hibernate.common.IPersistenceManager;
import ooad.fudan.project3.model.*;

public class ReadBook {
	Book book;
	//Library library;

	public ReadBook(Book b){
		this.book  = b;
	}
	
	public Reading startReading(IPersistenceManager pm){
		return Reading.create(book, pm);
	}
	
	public Reading endReading(IPersistenceManager pm, Reading r){
		r.setEnd(new Date(System.currentTimeMillis()));
		r.update(pm);
		return r;
	}
	
	public Book writeComment(IPersistenceManager pm, String commentAbstract, String uri){
		if(commentAbstract != null){
			book.createComment(commentAbstract, uri, pm);
		}
		return book;
	}
	
	public Reading writeNote(IPersistenceManager pm, Reading r, String noteContent){
		if(noteContent != null){
			r.createNote(noteContent, pm);
		}
		return r;
	}

}