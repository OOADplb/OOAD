package ooad.fudan.project3.model;

import junit.framework.Assert;

import org.hibernate.Query;
import org.junit.Test;

import edu.fudan.ss.persistence.hibernate.common.HibernateBaseTest;
import edu.fudan.ss.persistence.hibernate.demo.Teacher;

public class ReadingTest extends HibernateBaseTest {

	@Test
	public void createTeacherTest() {
		Reading r = Reading.create(null, getPersistenceManager());
		Assert.assertNotNull(r.getId());
	}
	
	@Test
	public void findTeacherByHQL(){
		Reading r = Reading.create(null, getPersistenceManager());
		String hql = "from Reading r";
		Query query = getPersistenceManager().createQuery(hql);
		Assert.assertEquals(1,query.list().size());
	}

}
