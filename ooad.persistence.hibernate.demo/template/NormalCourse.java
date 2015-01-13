package edu.fudan.ss.persistence.hibernate;

public class NormalCourse extends Course {
	private String classRoom;

	public NormalCourse() {

	}

	public NormalCourse(String name) {
		super(name);
	}

	public String getClassroom() {
		return classRoom;
	}

	public void setClassroom(String classRoom) {
		this.classRoom = classRoom;
	}

}
