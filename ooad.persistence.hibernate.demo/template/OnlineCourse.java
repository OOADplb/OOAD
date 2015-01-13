package edu.fudan.ss.persistence.hibernate;

public class OnlineCourse extends Course {
	
	private String url;

	public OnlineCourse() {

	}

	public OnlineCourse(String name) {
		super(name);
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}
}
