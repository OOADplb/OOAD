package ooad.fudan.project3.control;

import edu.fudan.ss.persistence.hibernate.common.IPersistenceManager;
import edu.fudan.ss.persistence.hibernate.common.PMHibernateImpl;
import ooad.fudan.project3.model.*;


public class FriendAction {
	public static Friend getFriendByName(String name){
		String hql = "from Friend f where f.name ='"+ name + "'";
		Friend friend = (Friend) PMHibernateImpl.getInstance().createQuery(hql).list().get(0);
		return friend;
	}
}
