package ooad.fudan.project3.model;

import java.util.ArrayList;
import java.util.Collection;

import javax.persistence.CascadeType;
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
	
	public void createComment(String abstracts, IPersistenceManager pm){
		comments.add(Comment.create(this, abstracts, pm));
	}
	
	@OneToMany(mappedBy="book", cascade = {CascadeType.ALL})
	Collection<Reading> readings = new ArrayList<Reading>();
	
	public Collection<Reading> getReadings() {
		return readings;
	}
	
	public void createReading(IPersistenceManager pm){
		readings.add(Reading.create(this, pm));
	}
	
	public Reading getLatestReading(){
		return ((ArrayList<Reading>)readings).get(readings.size() - 1);
	}
	
}
