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
	public static Library create(IPersistenceManager pm) {
		Library result = new Library();
		pm.save(result);
		return result;
	}
	
	@OneToMany(mappedBy="library", cascade = {CascadeType.ALL})
	Collection<Book> books = new ArrayList<Book>();
	
	public Collection<Book> getComments() {
		return books;
	}
	
	public void createPaperBook(String title, IPersistenceManager pm){
		books.add(PaperBook.create(this, title, pm));
	}
	
	public void createEBook(String title, IPersistenceManager pm){
		books.add(EBook.create(this, title, pm));
	}
}
