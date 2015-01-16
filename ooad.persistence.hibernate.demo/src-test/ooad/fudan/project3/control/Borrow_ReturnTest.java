package ooad.fudan.project3.control;

import org.junit.Test;

import ooad.fudan.project3.model.*;
import edu.fudan.ss.persistence.hibernate.common.HibernateBaseTest;

public class Borrow_ReturnTest extends HibernateBaseTest{
	
	@Test
	public void Test(){
		PaperBook pb = PaperBook.create(null, "pb", getPersistenceManager());
		assertObjectPersisted(pb);

		Friend f = Friend.create("plb", getPersistenceManager());
		
		String hql2 = "from PaperBook pb";
		String hql3 = "from BorrowRecord br2 where br2.friend.name='plb'";
		String hql4 = "from BorrowRecord br3 where br3.end is not null";
		assertEquals(1,getPersistenceManager().createQuery(hql2).list().size());
		//测试借书前的书本为可借阅状态
		assertEquals(false,((PaperBook)(getPersistenceManager().createQuery(hql2).list().get(0))).isBorrow());

		Borrow_Return bort = new Borrow_Return(pb,f);
		
		bort.borrowBook(getPersistenceManager());
		//测试书籍变为不可借阅状态
		assertEquals(true,((PaperBook)(getPersistenceManager().createQuery(hql2).list().get(0))).isBorrow());
		//测试有朋友姓名为plb的结束记录
		assertEquals(1,getPersistenceManager().createQuery(hql3).list().size());
	
		bort.returnBook(getPersistenceManager());
		//测试还书后书籍变回可借阅状态
		assertEquals(false,((PaperBook)(getPersistenceManager().createQuery(hql2).list().get(0))).isBorrow());
		//测试end时间已经加入
		assertEquals(1,getPersistenceManager().createQuery(hql4).list().size());
	}
	

}
