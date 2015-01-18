package ooad.fudan.project3.database;

import java.io.IOException;

import ooad.fudan.project3.control.*;
import ooad.fudan.project3.model.*;
import ooad.fudan.project3.runnable.*;

import org.junit.Test;

import edu.fudan.ss.persistence.hibernate.common.HibernateBaseTest;

public class TempTest extends HibernateBaseTest{
	
	@Test
	public void test(){
		MainAction ma = new MainAction(getPersistenceManager());
		try {
			ma.process();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
