package ooad.fudan.project3.control;

import ooad.fudan.project3.model.*;

import org.junit.Test;

import edu.fudan.ss.persistence.hibernate.common.HibernateBaseTest;

public class ReadBookTest extends HibernateBaseTest{
	
	@Test
	public void Test(){
		PaperBook pb = PaperBook.create(null, "pb", getPersistenceManager());
		assertObjectPersisted(pb);
		EBook eb = EBook.create(null, "eb2", getPersistenceManager());
		
		String commentAbstract = "testComment";
		String noteContent = "noteContentTest";
		String uri = "testURI";
		
		
	//Test reading paperbook
		String hql1 = "from PaperBook pb";
		String hql2 = "from Reading rd";
		
		ReadBook rb = new ReadBook(pb);
		Reading rd = rb.startReading(getPersistenceManager());
		rb.writeComment(getPersistenceManager(), commentAbstract, uri);
		rb.writeNote(getPersistenceManager(), rd, noteContent);
		rb.endReading(getPersistenceManager(), rd);
		//测试评论加入成功
		assertEquals(11,((Comment)((PaperBook)(getPersistenceManager().createQuery(hql1).list().get(0))).getComments().toArray()[0]).getAbstracts().length());
		//测试笔记加入成功
		assertEquals(15,((Note)((Reading)(getPersistenceManager().createQuery(hql2).list().get(0))).getNotes().toArray()[0]).getContent().length());
		//测试uri记录加入成功
		assertEquals(7,((Comment)((PaperBook)(getPersistenceManager().createQuery(hql1).list().get(0))).getComments().toArray()[0]).getURI().length());
		//测试阅读记录已加入
		assertEquals(1,getPersistenceManager().createQuery(hql2).list().size());
	
	//Test Reading EBook
		ReadBook rb2 = new ReadBook(eb);	
		Reading rd2 = rb2.startReading(getPersistenceManager());
		
		String hql3 = "from EBook eb";
		String hql4 = "from Reading rd2";
		
		rb2.writeComment(getPersistenceManager(), commentAbstract, uri);
		rb2.writeNote(getPersistenceManager(), rd2, noteContent);
		rb2.endReading(getPersistenceManager(), rd2);
		//测试评论加入成功
		assertEquals(11,((Comment)((EBook)(getPersistenceManager().createQuery(hql3).list().get(0))).getComments().toArray()[0]).getAbstracts().length());
		//测试笔记加入成功
		assertEquals(15,((Note)((Reading)(getPersistenceManager().createQuery(hql4).list().get(0))).getNotes().toArray()[0]).getContent().length());
		//测试uri记录加入成功
		assertEquals(7,((Comment)((EBook)(getPersistenceManager().createQuery(hql3).list().get(0))).getComments().toArray()[0]).getURI().length());
		//测试阅读记录已加入
		assertEquals(2,getPersistenceManager().createQuery(hql4).list().size());
		
	}
}
