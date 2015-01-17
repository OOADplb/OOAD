package ooad.fudan.project3.model;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Collection;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import edu.fudan.ss.persistence.hibernate.common.BaseModelObject;
import edu.fudan.ss.persistence.hibernate.common.IPersistenceManager;

@Entity
public class Reading extends BaseModelObject {
	
	public static Reading create(Book book, IPersistenceManager pm) {
		Reading result = new Reading();
		result.setBook(book);
		result.setStart(new Date(System.currentTimeMillis()));
		pm.save(result);
		return result;
	}
	
	@ManyToOne
	private Book book;
	
	private Date start;
	
	private Date end;
	
	@OneToMany(mappedBy="reading", cascade = {CascadeType.ALL})
	Collection<Note> notes = new ArrayList<Note>();
	
	public Collection<Note> getNotes() {
		return notes;
	}
	
	public void createNote(String content, IPersistenceManager pm){
		notes.add(Note.create(this, content, pm));
	}

	public Book getBook() {
		return book;
	}

	public void setBook(Book book) {
		this.book = book;
	}

	public Date getStart() {
		return start;
	}

	public void setStart(Date start) {
		this.start = start;
	}

	public Date getEnd() {
		return end;
	}

	public void setEnd(Date end) {
		this.end = end;
	}

	public void update(IPersistenceManager pm) {
		pm.save(this);
		
	}
	
	
}
