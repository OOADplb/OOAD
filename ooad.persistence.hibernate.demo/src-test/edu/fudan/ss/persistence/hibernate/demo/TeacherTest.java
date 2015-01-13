package edu.fudan.ss.persistence.hibernate.demo;

import junit.framework.Assert;

import org.hibernate.Query;
import org.junit.Test;
import org.springframework.test.annotation.Rollback;

import edu.fudan.ss.persistence.hibernate.common.HibernateBaseTest;

public class TeacherTest extends HibernateBaseTest {

	@Test
	public void createTeacherTest() {
		Teacher t = Teacher.create("teacher1", getPersistenceManager());
		Assert.assertNotNull(t.getId());
	}
	
	@Test
	public void findTeacherByHQL(){
		Teacher t = Teacher.create("teacher1", getPersistenceManager());
		String hql = "from Teacher t where t.name like 't%'";
		Query query = getPersistenceManager().createQuery(hql);
		Assert.assertEquals(1,query.list().size());
	}

}
