package edu.fudan.ss.persistence.hibernate;

import edu.fudan.ss.persistence.hibernate.common.BaseModelObject;

public abstract class Course extends BaseModelObject {

	private String name;
	private Integer totalStudents;

	public Course() {

	}

	public Course(String name) {
		super();
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getTotalStudents() {
		return totalStudents;
	}

	public void setTotalStudents(Integer totalStudents) {
		this.totalStudents = totalStudents;
	}

}
