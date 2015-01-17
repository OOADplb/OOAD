package ooad.fudan.project3.database;

import java.util.List;

import org.hibernate.Query;

import ooad.fudan.project3.model.Library;
import edu.fudan.ss.persistence.hibernate.common.IPersistenceManager;

public class LoadUtil {
	
	public static List<?> getFromDB(String className, String attrName, int id, IPersistenceManager pm){
		String hql = "from "+ className +" temp where temp."+attrName+".id = "+id;
		return pm.createQuery(hql).list();
	}

	public static List<?> getLibraryFromDB(
			IPersistenceManager pm) {
		String hql = "from Library l";
		Query q = pm.createQuery(hql);
		List<?> l = q.list();
		return l;
	}
}
