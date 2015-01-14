package ooad.fudan.project3.model;

import java.sql.Date;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;

import edu.fudan.ss.persistence.hibernate.common.BaseModelObject;
import edu.fudan.ss.persistence.hibernate.common.IPersistenceManager;

@Entity
public class Note extends BaseModelObject {
	
	public static Note create(Reading reading, String content, IPersistenceManager pm) {
		Note result = new Note();
		result.setContent(content);
		result.setReading(reading);
		result.setDate(new Date(System.currentTimeMillis()));
		pm.save(result);
		return result;
	}
	
	private String content;
	
	private Date date;
	
	@ManyToOne
	private Reading reading;

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public Reading getReading() {
		return reading;
	}

	public void setReading(Reading reading) {
		this.reading = reading;
	}

}
