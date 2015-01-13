package edu.fudan.ss.persistence.hibernate.demo;

import org.junit.Test;
import org.springframework.test.annotation.Rollback;

import edu.fudan.ss.persistence.hibernate.common.HibernateBaseTest;

public class CourseTest extends HibernateBaseTest {

	@Test
	public void createCourseTest(){
		OnlineCourse onlineCourse = OnlineCourse.create(null, "online course 1", getPersistenceManager());
		NormalCourse normalCourse = NormalCourse.create(null,"normal course 1", getPersistenceManager());
		assertObjectPersisted(normalCourse);
		assertObjectPersisted(onlineCourse);
		
		String hql1 = "from Course c";
		String hql2 = "from NormalCourse nc";
		
		assertEquals(2,getPersistenceManager().createQuery(hql1).list().size());
		assertEquals(1,getPersistenceManager().createQuery(hql2).list().size());
	}
}
