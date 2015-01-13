package edu.fudan.ss.persistence.hibernate.demo;

import javax.persistence.Embeddable;

@Embeddable
public class Address {

	private String postcode;
	private String addressInfo;

	public String getPostcode() {
		return postcode;
	}

	public void setPostcode(String postcode) {
		this.postcode = postcode;
	}

	public String getAddressInfo() {
		return addressInfo;
	}

	public void setAddressInfo(String addressInfo) {
		this.addressInfo = addressInfo;
	}

}
