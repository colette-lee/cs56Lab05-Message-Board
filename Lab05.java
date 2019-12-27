import java.util.ArrayList;

public class Lab05 {
	public static void main(String[] args) {
		MessageBoardManager messageBoard = new MessageBoardManager();
		
		User u1 = new User();
		messageBoard.registerUserTag("food", u1);
		User u2 = new User();
        messageBoard.registerUserTag("cooking", u2);
        User u3 = new User();
        messageBoard.registerUserTag("FOOD", u3);
        messageBoard.registerUserTag("cooking", u3);
        User u4 = new User();
        messageBoard.registerUserTag("sports", u4);

		ArrayList<String> tags = new ArrayList<String>();
		tags.add("cooking");
		tags.add("food");
		
		Post p1 = new Post(tags, "I like cooking", u1, -1);
        messageBoard.addPost(p1);
        
		Post p2 = new Post(tags, "I like food", u2, p1.getPostId());
        messageBoard.addReply(p2);
        
    	Post p3 = new Post(tags, "Let's make brownies", u1, p2.getPostId());
        messageBoard.addReply(p3);
        
    	Post p4 = new Post(tags, "Cookies are better", u2, p3.getPostId());
        messageBoard.addReply(p4);

    	Post p5 = new Post(tags, "I like chicken feet", u3, p2.getPostId());
        messageBoard.addReply(p5);
        
    	Post p6 = new Post(new ArrayList<String>(), "tags don't match so this shouldn't get posted", u1, p1.getPostId());
        messageBoard.addReply(p6);
        
        Post p7 = new Post(tags, "Cooking is hard", u4, p1.getPostId());
        messageBoard.addReply(p7);
        
        Post p8 = new Post(tags, "No it's not", u1, p7.getPostId());
        messageBoard.addReply(p8);
        
		ArrayList<String> tags2 = new ArrayList<String>();
		tags2.add("sports");
		
        Post p9 = new Post(tags2, "Let's play baseball", u2, -1);
        messageBoard.addPost(p9);
        
        messageBoard.displayThread(3);
        messageBoard.displayTagMessages("cooKing");
        messageBoard.displayTagMessages("adfa");
        messageBoard.displayKeywordMessages("like");
        messageBoard.displayKeywordMessages("basketball");

        messageBoard.displayUserPosts(u2);

		

	}
}
