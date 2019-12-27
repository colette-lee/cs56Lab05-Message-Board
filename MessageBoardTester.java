import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.LinkedList;

import org.junit.Test;

public class MessageBoardTester {


	@Test
	public void addPostTest() {
		MessageBoardManager messageBoard = new MessageBoardManager();

		User u1 = new User();
		User u2 = new User();
	    User u3 = new User();

		messageBoard.registerUserTag("memes", u1);
	    messageBoard.registerUserTag("gifs", u2);
	    messageBoard.registerUserTag("MemEs", u3);
	    messageBoard.registerUserTag("dogs", u3);


        
		ArrayList<String> tags = new ArrayList<String>();
		tags.add("memes");
		tags.add("gifs");
		
		Post p1 = new Post(tags, "blinking white guy", u1, -1);
        messageBoard.addPost(p1);
        assertEquals(u3, messageBoard.getNotifiedUsers().get(0));
        assertEquals(u2, messageBoard.getNotifiedUsers().get(1));
        assertEquals(2, messageBoard.getNotifiedUsers().size());
        
        messageBoard.clearNotifiedUsers();
        
        assertEquals(p1, messageBoard.getAllPosts().get(0));
        LinkedList<Post> list = new LinkedList<Post>();
        list.add(p1);
        assertEquals(list, messageBoard.getThreads().get(0));

	}
	
	@Test
	public void addReplyTest() {
		MessageBoardManager messageBoard = new MessageBoardManager();

		User u4 = new User();
		User u5 = new User();
	    User u6 = new User();
		messageBoard.registerUserTag("memes", u4);
        messageBoard.registerUserTag("gifs", u5);
        messageBoard.registerUserTag("MemEs", u6);
        messageBoard.registerUserTag("dogs", u6);
        
		ArrayList<String> tags = new ArrayList<String>();
		tags.add("memes");
		tags.add("gifs");
		Post p1 = new Post(tags, "blinking white guy", u4, -1);
        messageBoard.addPost(p1);
	
        messageBoard.clearNotifiedUsers();
        
        Post p1_1 = new Post(tags, "lol", u5, p1.getPostId());
        messageBoard.addReply(p1_1);
        
        assertEquals(p1_1, messageBoard.getAllPosts().get(1));
        
        assertEquals(u4, messageBoard.getNotifiedUsers().get(0));
        assertEquals(u6, messageBoard.getNotifiedUsers().get(1));
        assertEquals(2, messageBoard.getNotifiedUsers().size());
        
        messageBoard.clearNotifiedUsers();
        
        LinkedList<Post> list = new LinkedList<Post>();
        list.add(p1);
        list.add(p1_1);
        assertEquals(list, messageBoard.getThreads().get(0));


	}

}
