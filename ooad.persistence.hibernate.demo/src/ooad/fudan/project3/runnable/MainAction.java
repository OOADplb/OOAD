package ooad.fudan.project3.runnable;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import org.junit.runner.RunWith;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import ooad.fudan.project3.model.*;
import ooad.fudan.project3.control.*;
import edu.fudan.ss.persistence.hibernate.common.*;


//@ContextConfiguration(locations = { "classpath:HibernateApplicationContext-aop.xml" })
public class MainAction implements ApplicationContextAware{
	Library library = Library.getInstance(getPersistenceManager());
	protected static ApplicationContext appContext;
	final String EXIT = "exit";
	final String SPLIT_REGEX = 	"_";
	final String COMMAND_ADD = "addbook";
	final String COMMAND_READ = "readbook";
	final String COMMAND_LEND = "lendbook";
	final String COMMAND_RETURN = "returnbook";
		
	public void doCommand(String command) throws IOException{
								
		
			if(command != null){
				String[] para = command.split(SPLIT_REGEX);
				
				//command to add book: addbook_title_type (1 for paperbook and else for ebook)
				if(para[0].equals(COMMAND_ADD) && para.length == 3){
					doAddCommand(para);		
				}
				
				//command to read book: readbook_title
				else if(para[0].equals(COMMAND_READ) && para.length == 2){
					doReadCommand(para);
				}
				
				//command to lend book: lendbook_friend_title
				else if(para[0].equals(COMMAND_LEND) && para.length == 3){
					doLendCommand(para);
				}
				
				//command to return book: returnbook_friend_title
				else if(para[0].equals(COMMAND_RETURN) && para.length == 3){
					doReturnCommand(para);
				}
				
				//command to exit: exit
				else if(para[0].equals(EXIT)){
					
				}				
				
				//Other commands are illegal
				else{
					System.err.println("Illegal command!");
				}
			}
			System.out.println("You command has been processed!");
		}		

	public void doAddCommand(String[] para){
		String title = para[1];
		String type = (para[2].equals("1"))?"Paperbook":"EBook";
		if(type.equals("Paperbook")){
			PaperBook pb = PaperBook.create(library, title, getPersistenceManager());
			LibraryAction la = new LibraryAction(library);
			la.addBook(pb, getPersistenceManager());
		}else if(type.equals("EBook")){
			EBook eb = EBook.create(library, title, getPersistenceManager());
			LibraryAction la = new LibraryAction(library);
			la.addBook(eb, getPersistenceManager());
		}		
	}
	
	public void doReadCommand(String[] para) throws IOException{
		String title = para[1];
		LibraryAction la = new LibraryAction(library);
		Book bookGet = la.getBookByTitle(title);
		
		ReadBook rb = new ReadBook(bookGet);
		Reading rd = rb.startReading(getPersistenceManager());
		
		//Write notes
		while(true){
			System.out.print("write your notes here(exit to skip):");
			BufferedReader noteReader = new BufferedReader(new InputStreamReader(System.in));
			String note = noteReader.readLine();
			if(note.equals(EXIT)){
				break;
			}else{
				rb.writeNote(getPersistenceManager(), rd, note);
			}
		}
		
		//Write comments
		while(true){
			System.out.print("write your comments here(exit to skip):");
			BufferedReader commentReader = new BufferedReader(new InputStreamReader(System.in));
			String comment = commentReader.readLine();
			if(comment.equals(EXIT)){
				break;					
			}else{
				System.out.print("write the uri of this comment:");
				BufferedReader uriReader = new BufferedReader(new InputStreamReader(System.in));
				String uri = uriReader.readLine();
				rb.writeComment(getPersistenceManager(), comment, uri);
			}
		}
	
		rb.endReading(getPersistenceManager(), rd);
	}
	
	public void doLendCommand(String[] para){
		String friendName = para[1];
		Friend friend = FriendAction.getFriendByName(friendName);
		String title = para[2];
		LibraryAction la = new LibraryAction(library);
		Book bookGet = la.getBookByTitle(title);
		if(bookGet instanceof PaperBook){
			PaperBook pb = (PaperBook)bookGet;
			Borrow_Return br = new Borrow_Return(pb, friend);
			br.borrowBook(getPersistenceManager());
		}else{
			System.err.println("This is an EBook!");
		}
	}
	
	public void doReturnCommand(String[] para){
		String friendName = para[1];
		Friend friend = FriendAction.getFriendByName(friendName);
		String title = para[2];
		LibraryAction la = new LibraryAction(library);
		Book bookGet = la.getBookByTitle(title);
		if(bookGet instanceof PaperBook){
			PaperBook pb = (PaperBook)bookGet;
			Borrow_Return br = new Borrow_Return(pb, friend);
			br.returnBook(getPersistenceManager());
		}else{
			System.err.println("This is an EBook!");
		}
	}
	
	public void process() throws IOException{
				
		while(true){
			System.out.print("Command>");
			BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
			String command = br.readLine();
			
			if(command.equals(EXIT)){
				br.close();
				break;
			}else{
				doCommand(command);
			}
		}
	}
	
	public static void main(String args[]){
		appContext = new ClassPathXmlApplicationContext("HibernateApplicationContext-aop.xml");
		MainAction ma = new MainAction();
		try {
			ma.process();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}


	public void setApplicationContext(ApplicationContext context)
			throws BeansException {
		appContext = context;

	}

	public IPersistenceManager getPersistenceManager() {
		return (IPersistenceManager) appContext.getBean("persistenceManager");
	}
	
}
