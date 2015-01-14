package ooad.fudan.project3.model;

import org.junit.Test;

import edu.fudan.ss.persistence.hibernate.common.HibernateBaseTest;

public class BorrowRecordTest extends HibernateBaseTest {

	@Test
	public void test() {
		PaperBook paperBook = PaperBook.create(null, "pb 1", getPersistenceManager());
		Friend friend = Friend.create("f1", getPersistenceManager());
		BorrowRecord br = BorrowRecord.create(paperBook, friend, getPersistenceManager());
		assertObjectPersisted(friend);
		assertObjectPersisted(br);
		
		String hql1 = "from Friend f where f.name='f1'";
		String hql2 = "from BorrowRecord br where br.friend.name='f1'";
		//String hql3 = "from BorrowRecord br1";
		
		assertEquals(1,getPersistenceManager().createQuery(hql1).list().size());
		assertEquals(1,getPersistenceManager().createQuery(hql2).list().size());
		//assertEquals(1,getPersistenceManager().createQuery(hql3).list().size());
	}
}
