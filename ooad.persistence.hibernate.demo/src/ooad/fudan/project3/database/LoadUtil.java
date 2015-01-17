package ooad.fudan.project3.database;

import java.util.List;

import edu.fudan.ss.persistence.hibernate.common.IPersistenceManager;

public class LoadUtil {
	
	public static List<?> getFromDB(String hql, IPersistenceManager pm){
		return pm.createQuery(hql).list();
	}
}
