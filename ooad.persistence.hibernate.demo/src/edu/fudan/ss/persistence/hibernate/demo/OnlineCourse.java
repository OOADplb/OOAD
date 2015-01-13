package edu.fudan.ss.persistence.hibernate.demo;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

import edu.fudan.ss.persistence.hibernate.common.IPersistenceManager;

@Entity
@DiscriminatorValue("ONLINE")
public class OnlineCourse extends Course {
	
	public static OnlineCourse create(Teacher teacher, String name, IPersistenceManager pm) {
		OnlineCourse result = new OnlineCourse();
		result.setName(name);
		result.setTeacher(teacher);
		pm.save(result);
		return result;
	}
	private String url;

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}
}
