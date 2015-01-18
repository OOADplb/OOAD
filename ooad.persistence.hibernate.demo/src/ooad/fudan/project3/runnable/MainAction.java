package ooad.fudan.project3.runnable;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import ooad.fudan.project3.model.*;
import ooad.fudan.project3.control.*;
import ooad.fudan.project3.database.LoadUtil;
import edu.fudan.ss.persistence.hibernate.common.*;


//@ContextConfiguration(locations = { "classpath:HibernateApplicationContext-aop.xml" })
public class MainAction implements ApplicationContextAware{
	private IPersistenceManager pm;
	Library library;

	protected static ApplicationContext appContext;
	final String EXIT = "exit";
	final String SPLIT_REGEX = 	"_";
	final String COMMAND_ADD = "addbook";
	final String COMMAND_READ = "readbook";
	final String COMMAND_LEND = "lendbook";
	final String COMMAND_RETURN = "returnbook";
	final String COMMAND_DELETE = "deletebook";
	final String COMMAND_STATUS = "bookstatus";
	final String COMMAND_HISTORY = "gethistory";
	final String COMMAND_NOTE = "getnote";
	final String COMMAND_COMMENT = "getcomment";
	
	public MainAction() {
	}
	
	public MainAction(IPersistenceManager pm) {
		super();
		this.pm = pm;
		library = Library.getInstance(pm);
	}

	public void doCommand(String command) throws IOException{
								
			boolean isIllegal = false;
			if(command != null){
				String[] para = command.split(SPLIT_REGEX);
				
				//command to add book: addbook_title_type (1 for paperbook and else for ebook)
				if(para[0].equals(COMMAND_ADD) && para.length == 3){
					doAddCommand(para);		
				}
				
				//Command to delete book: deletebook_title
				else if(para[0].equals(COMMAND_DELETE)){
					doDeleteCommand(para);
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
				
				//command to see status: bookstatus_title
				else if(para[0].equals(COMMAND_STATUS) && para.length == 2){
					doStatusCommand(para);
				}
				
				//command to check history: gethistory_friend
				else if(para[0].equals(COMMAND_HISTORY) && para.length == 2){
					doHistoryCommand(para);
				}
				
				//command to get notes: getnote_title
				else if(para[0].equals(COMMAND_NOTE) && para.length == 2){
					doNoteCommand(para);
				}
				
				//command to get comments: getcomment_title
				else if(para[0].equals(COMMAND_COMMENT) && para.length == 2){
					doCommentCommand(para);
				}
				
				//command to exit: exit
				else if(para[0].equals(EXIT)){
					
				}	
				
				//Other commands are illegal
				else{
					isIllegal = true;
					System.err.println("Illegal command!");
				}
			}
			if(!isIllegal)
				System.out.println("Your command has been processed!");
		}		

	private void doDeleteCommand(String[] para) {
		String title = para[1];
		LibraryAction la = new LibraryAction(library);
		la.deleteBook(title, pm);
	}

	public void doAddCommand(String[] para){
		String title = para[1];
		String type = (para[2].equals("1"))?"Paperbook":"EBook";
		LibraryAction la = new LibraryAction(library);
		la.addBook(title, type, pm);	
	}
	
	public void doReadCommand(String[] para) throws IOException{
		String title = para[1];
		LibraryAction la = new LibraryAction(library);
		Book bookGet = la.getBookByTitle(title);
		
		if(bookGet == null){
			System.err.println("No such book!");
			return;
		}
		
		ReadBook rb = new ReadBook(bookGet);
		Reading rd = rb.startReading(pm);
		
		//Write notes
		while(true){
			System.out.print("write your notes here(exit to skip):");
			BufferedReader noteReader = new BufferedReader(new InputStreamReader(System.in));
			String note = noteReader.readLine();
			if(note.equals(EXIT)){
				break;
			}else{
				rb.writeNote(pm, rd, note);
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
				rb.writeComment(pm, comment, uri);
			}
		}
	
		rb.endReading(pm, rd);
	}
	
	public void doLendCommand(String[] para){
		String friendName = para[1];
		LibraryAction la = new LibraryAction(library);
		Friend friend = LoadUtil.getFriendByName(friendName, pm);		
		if(friend == null){
			friend = Friend.create(friendName, pm);
		}
		String title = para[2];
		Book bookGet = la.getBookByTitle(title);
		if(bookGet instanceof PaperBook){
			PaperBook pb = (PaperBook)bookGet;
			Borrow_Return br = new Borrow_Return(pb, friend);
			br.borrowBook(pm);
		}else{
			System.err.println("This is an EBook!");
		}
	}
	
	public void doReturnCommand(String[] para){
		String friendName = para[1];
		LibraryAction la = new LibraryAction(library);
		Friend friend = LoadUtil.getFriendByName(friendName, pm);
		if(friend == null){
			System.err.println("No such a friend");
			return;
		}
		String title = para[2];
		Book bookGet = la.getBookByTitle(title);
		if(bookGet instanceof PaperBook){
			PaperBook pb = (PaperBook)bookGet;
			Borrow_Return br = new Borrow_Return(pb, friend);
			br.returnBook(pm);
		}else{
			System.err.println("This is an EBook!");
		}
	}
	
	public void doStatusCommand(String[] para){
		String title = para[1];
		LibraryAction la = new LibraryAction(library);
		System.out.println(title + " " + la.getStatusByTitle(title));
	}
	
	public void doHistoryCommand(String[] para){
		String name = para[1];
		BorrowRecord[] br = Borrow_Return.getHistoryByName(name, pm);
		if(br == null || br.length == 0){
			System.out.println("no borrow record");
			return;
		}
		for(int i=0;i<br.length;i++){
			BorrowRecord record = br[i];
			System.out.println("start:" + record.getStart() + 
					" end:" + record.getEnd() + " book_Title:" + 
					record.getPaperBook().getTitle());
		}
	}
	
	public void doNoteCommand(String[]  para){
		String title = para[1];
		LibraryAction la = new LibraryAction(library);
		Note[] n = la.getNoteByBook(title, pm);
		if(n == null || n.length == 0){
			System.out.println("No notes");
			return;
		}else{
			for(int i=0;i<n.length;i++){
				System.out.println("Date: " + n[i].getDate() + " "
						+ "Content:" + n[i].getContent());
			}
		}
	}
	
	public void doCommentCommand(String[]  para){
		String title = para[1];
		LibraryAction la = new LibraryAction(library);
		Comment[] c = la.getCommentByBook(title, pm);
		if(c == null || c.length == 0){
			System.out.println("No comments");
			return;
		}else{
			for(int i=0;i<c.length;i++){
				System.out.println("Book: " + c[i].getBook().getTitle() 
						+ " Abstract: " + c[i].getAbstracts()
						+ " uri: " + c[i].getURI());
			}
		}
	}
	
	public void process() throws IOException{
		//library.init(pm);		
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
	
}
