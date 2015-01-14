package ooad.fudan.project3.model;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

import edu.fudan.ss.persistence.hibernate.common.IPersistenceManager;


@Entity
@DiscriminatorValue("ELECTRONIC")
public class EBook extends Book {
	
	public static EBook create(Library library, String title, IPersistenceManager pm) {
		EBook result = new EBook();
		result.setLibrary(library);
		result.setTitle(title);
		pm.save(result);
		return result;
	}
	
	private String uri;

	public String getUri() {
		return uri;
	}

	public void setUri(String uri) {
		this.uri = uri;
	}
		
}
