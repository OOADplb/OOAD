package edu.fudan.ss.persistence.hibernate.demo;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.OneToMany;

import edu.fudan.ss.persistence.hibernate.common.BaseModelObject;
import edu.fudan.ss.persistence.hibernate.common.IPersistenceManager;

@Entity
public class Teacher extends BaseModelObject {

	public static Teacher create(String name, IPersistenceManager pm) {
		Teacher result = new Teacher();
		result.setName(name);
		pm.save(result);
		return result;
	}

	private String name;

	private Date birthday;

	@Embedded
	private Address address = new Address();

	private Gender gender;
	
	@OneToMany(mappedBy="teacher", cascade = {CascadeType.ALL})
	Collection<Course> courses = new ArrayList<Course>();

	public Collection<Course> getCourses() {
		return courses;
	}

	public void createNormalCourse(String name, IPersistenceManager pm){
		courses.add(NormalCourse.create(this,name, pm));
	}
	
	public void createOnlineCourse(String name, IPersistenceManager pm){
		courses.add(OnlineCourse.create(this,name, pm));
	}
	
	public Gender getGender() {
		return gender;
	}

	public void setGender(Gender gender) {
		this.gender = gender;
	}

	public Address getAddress() {
		return address;
	}

	public void setAddress(Address address) {
		this.address = address;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Date getBirthday() {
		return birthday;
	}

	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}

}
