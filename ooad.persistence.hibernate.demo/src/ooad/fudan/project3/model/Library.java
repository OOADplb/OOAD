package ooad.fudan.project3.model;

import java.util.ArrayList;
import java.util.Collection;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;

import edu.fudan.ss.persistence.hibernate.common.BaseModelObject;
import edu.fudan.ss.persistence.hibernate.common.IPersistenceManager;

@Entity
public class Library extends BaseModelObject {
	private static Library library = null;
	
	private Library() {}
	
	private static Library create(IPersistenceManager pm) {
		Library result = new Library();
		pm.save(result);
		return result;
	}
	
	public static Library getInstance(IPersistenceManager pm){
		if(library == null)
			return create(pm);
		return library;
	}
	
	@OneToMany(mappedBy="library", cascade = {CascadeType.ALL})
	Collection<Book> books = new ArrayList<Book>();
	
	public Collection<Book> getBooks() {
		return books;
	}
	
	public PaperBook createPaperBook(String title, IPersistenceManager pm){
		PaperBook pb = PaperBook.create(this, title, pm);
		books.add(pb);
		return pb;
	}
	
	public EBook createEBook(String title, IPersistenceManager pm){
		EBook eb = EBook.create(this, title, pm);
		books.add(eb);
		return eb;
	}
}
