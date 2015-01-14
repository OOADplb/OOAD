package ooad.fudan.project3.model;

import java.util.ArrayList;
import java.util.Collection;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;

import edu.fudan.ss.persistence.hibernate.common.BaseModelObject;
import edu.fudan.ss.persistence.hibernate.common.IPersistenceManager;

@Entity
public class Friend extends BaseModelObject {

	public static Friend create(String name, IPersistenceManager pm) {
		Friend result = new Friend();
		result.setName(name);
		pm.save(result);
		return result;
	}
	
	private String name;
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@OneToMany(mappedBy="friend", cascade = {CascadeType.ALL})
	Collection<BorrowRecord> records = new ArrayList<BorrowRecord>();
	
	public Collection<BorrowRecord> getRecords() {
		return records;
	}
	
	public void addRecord(BorrowRecord record){
		records.add(record);
	}
}
