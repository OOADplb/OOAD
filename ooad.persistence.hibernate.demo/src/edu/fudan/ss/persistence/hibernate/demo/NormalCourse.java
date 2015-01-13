package edu.fudan.ss.persistence.hibernate.demo;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

import edu.fudan.ss.persistence.hibernate.common.IPersistenceManager;

@Entity
@DiscriminatorValue("NORMAL")
public class NormalCourse extends Course {

	public static NormalCourse create(Teacher teacher, String name, IPersistenceManager pm) {
		NormalCourse result = new NormalCourse();
		result.setName(name);
		result.setTeacher(teacher);
		pm.save(result);
		return result;
	}

	private String classroom;

	public String getClassroom() {
		return classroom;
	}

	public void setClassroom(String classroom) {
		this.classroom = classroom;
	}
}
