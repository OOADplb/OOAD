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
		
		String commentAbstract = "Comment";
		String noteContent = "noteContent";
		String uri = "testURI";
		
		
	//Test reading paperbook
		String hql1 = "from Reading rd";
		
		ReadBook rb1 = new ReadBook(pb);
		Reading rd = rb1.startReading(getPersistenceManager());
		Comment c1 = rb1.writeComment(getPersistenceManager(), commentAbstract, uri);
		Note n1 = rb1.writeNote(getPersistenceManager(), rd, noteContent);
		rb1.endReading(getPersistenceManager(), rd);
		//测试评论加入成功
		assertEquals("Comment",c1.getAbstracts());
		//测试笔记加入成功
		assertEquals("noteContent",n1.getContent());
		//测试URI记录加入成功
		assertEquals("testURI",c1.getURI());
		//测试阅读记录已加入
		assertEquals(1,getPersistenceManager().createQuery(hql1).list().size());
	
	//Test Reading EBook
		ReadBook rb2 = new ReadBook(eb);	
		Reading rd2 = rb2.startReading(getPersistenceManager());
		
		String hql2 = "from Reading rd2 where rd2.end is not null";
	
		Comment c2 = rb2.writeComment(getPersistenceManager(), commentAbstract, uri);
		Note n2 = rb2.writeNote(getPersistenceManager(), rd2, noteContent);
		rb2.endReading(getPersistenceManager(), rd2);
		//测试评论加入成功
		assertEquals("Comment",c2.getAbstracts());
		//测试URI记录加入成功
		assertEquals("testURI",c2.getURI());
		//测试笔记加入成功
		assertEquals("noteContent",n2.getContent());
		//测试阅读记录已加入
		assertEquals(2,getPersistenceManager().createQuery(hql2).list().size());		
	}
}
