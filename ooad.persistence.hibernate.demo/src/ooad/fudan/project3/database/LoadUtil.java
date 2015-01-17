package ooad.fudan.project3.database;

import java.util.List;

import edu.fudan.ss.persistence.hibernate.common.IPersistenceManager;

public class LoadUtil {
	
	public static List<?> getFromDB(String className, String attrName, int id, IPersistenceManager pm){
		String hql = "from "+ className +" temp where temp."+attrName+".id = "+id;
		return pm.createQuery(hql).list();
	}
}
