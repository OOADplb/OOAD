package ooad.fudan.project3.model;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;

import edu.fudan.ss.persistence.hibernate.common.BaseModelObject;
import edu.fudan.ss.persistence.hibernate.common.IPersistenceManager;

@Entity
public class Comment extends BaseModelObject {
	public static Comment create(Book book, String abstracts, String uri, IPersistenceManager pm) {
		Comment result = new Comment();
		result.setAbstracts(abstracts);
		result.setBook(book);
		result.setURI(uri); //对于杂志上收集的书评,uri为null
		pm.save(result);
		return result;
	}
	
	private String abstracts;
	
	@ManyToOne
	private Book book;
	
	private String URI;

	public String getAbstracts() {
		return abstracts;
	}

	public void setAbstracts(String abstracts) {
		this.abstracts = abstracts;
	}
	
	public Book getBook() {
		return book;
	}

	public void setBook(Book book) {
		this.book = book;
	}

	public String getURI() {
		return URI;
	}

	public void setURI(String uRI) {
		URI = uRI;
	}

}
