import java.util.ArrayList;

public class User implements Observer{
	private int userId;
	private ArrayList<String> tags;
	private ArrayList<Post> userPosts;
	private static int totalUsers;
	public User() {
		totalUsers++;
		userId = totalUsers;
		tags = new ArrayList<String>();
		userPosts = new ArrayList<Post>();
	}

	@Override
	public void update(Post post) {

		System.out.println("***** Updating UserID: "+ userId+" *****");
		System.out.println(post.toString());
		System.out.println("***********************\n");
	}
	
	public int getUserId() {return userId;}
	public ArrayList<String> getTags(){return tags;}
	public ArrayList<Post> getUserPosts(){return userPosts;}

}
