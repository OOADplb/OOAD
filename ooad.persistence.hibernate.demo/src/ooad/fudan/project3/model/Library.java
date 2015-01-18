package ooad.fudan.project3.model;

import java.util.ArrayList;
import java.util.Collection;
//import java.util.List;


import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;






import org.hamcrest.core.IsInstanceOf;

import com.sun.org.apache.xml.internal.security.Init;

import ooad.fudan.project3.database.LoadUtil;
//import ooad.fudan.project3.database.LoadUtil;
import edu.fudan.ss.persistence.hibernate.common.BaseModelObject;
import edu.fudan.ss.persistence.hibernate.common.IPersistenceManager;

@Entity
public class Library extends BaseModelObject {
	private static Library library = null;
	
	private Library(){}
	
	
	private static Library create(IPersistenceManager pm) {
		Library result = new Library();
		pm.save(result);
		return result;
	}
	
	public static Library getInstance(IPersistenceManager pm){
		if(library == null){
			List<Library> ll = (List<Library>) LoadUtil.getLibraryFromDB(pm);
			if(ll.size() != 0){
				library = ll.get(0);
				return library;
			}
			return create(pm);
		}
		return library;
//		return create(pm);
	}
	
	public Library init(IPersistenceManager pm){
		books = (Collection<Book>) LoadUtil.getFromDB("Book", "library", this.getId(), pm);
		Object[] bookArray = books.toArray();
		for(int i = 0; i < bookArray.length; i++){
			((Book)bookArray[i]).init(pm);
		}
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

	public void deleteBook(Book temp, IPersistenceManager pm) {
		books.remove(temp);
		pm.delete(temp);
	}
}
