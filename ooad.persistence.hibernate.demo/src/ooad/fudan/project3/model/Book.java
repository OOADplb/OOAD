package ooad.fudan.project3.model;

import java.util.ArrayList;
import java.util.Collection;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import edu.fudan.ss.persistence.hibernate.common.BaseModelObject;
import edu.fudan.ss.persistence.hibernate.common.IPersistenceManager;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "BOOKTYPE")

public class Book extends BaseModelObject {
	@Column(unique = true)
	private String title;
	
	@ManyToOne
	private Library library;

	public Library getLibrary() {
		return library;
	}

	public void setLibrary(Library library) {
		this.library = library;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}
	
	@OneToMany(mappedBy="book", cascade = {CascadeType.ALL})
	Collection<Comment> comments = new ArrayList<Comment>();
	
	public Collection<Comment> getComments() {
		return comments;
	}
	
	public Comment createComment(String abstracts, String uri, IPersistenceManager pm){
		Comment c = Comment.create(this, abstracts, uri, pm);
		comments.add(c);
		return c;
	}
	
	@OneToMany(mappedBy="book", cascade = {CascadeType.ALL})
	Collection<Reading> readings = new ArrayList<Reading>();
	
	public Collection<Reading> getReadings() {
		return readings;
	}
	
	public Reading createReading(IPersistenceManager pm){
		Reading r = Reading.create(this, pm);
		readings.add(r);
		return r;
	}
	
	public Reading getLatestReading(){
		return (Reading)readings.toArray()[readings.toArray().length - 1];
	}

	public void update(IPersistenceManager pm) {
		pm.save(this);
		
	}
	
}
