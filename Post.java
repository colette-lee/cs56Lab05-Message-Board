import java.util.ArrayList;

public class Post {
	private ArrayList<String> tags;
	private String message;
	private User user;
	private ArrayList<Post> replies;
	private int postId;
	private int parentId;
	//private int childId;
	private static int totalPosts;
	public Post(ArrayList<String> tags, String message, User user, int parentId) {
		this.tags  = new ArrayList<>(tags);
		this.message = message;
		this.user = user;
		replies = new ArrayList<Post>();
		this.parentId = parentId;
		totalPosts++;
		postId = totalPosts;	
		user.getUserPosts().add(this);
	}
	
	public ArrayList<String> getTags(){ return tags;}
	public String getMessage() {return message;}
	public User getUser() {return user;}
	public ArrayList<Post> getReplies(){ return replies;}
	public int getPostId() {return postId;}
	public int getParentId() {return parentId;}
	
	public String toString() {
		String out = "--\nPost ID: "+postId;
		out += "\nTags: ";
		if(tags.size()>0) {
			for(int i=0; i<tags.size(); i++) {
				out+=tags.get(i).toLowerCase()+" ";
			}
		}
		out += "\nPosted by UserID: " + user.getUserId();
		if(parentId!=-1)
			out += "\nRe: to Post ID: " + parentId;
		out += "\nMessage: " + message+"\n--";
		return out;
	}
}
