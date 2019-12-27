import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.LinkedList;

public class MessageBoardManager implements Subject{
	private LinkedHashMap<String, ArrayList<User>> map;
	private LinkedHashMap<String, ArrayList<Post>> postMap;
	private ArrayList<Post> allPosts;
	private ArrayList<LinkedList<Post>> threads;
	
	//for testing
	private ArrayList<User> notifiedUsers;
	
	public MessageBoardManager() {
		map = new LinkedHashMap<String, ArrayList<User>>();
		allPosts = new ArrayList<Post>();
		postMap = new LinkedHashMap<String, ArrayList<Post>>();
		threads = new ArrayList<LinkedList<Post>>();
		
		notifiedUsers = new ArrayList<User>();
	}

	@Override
	public void registerUserTag(String tag, User user) {
		String lowerTag = tag.toLowerCase();
		
		//adds user to tag-user map
		if(map.get(lowerTag)==null)
			map.put(lowerTag, new ArrayList<User>());
		map.get(lowerTag).add(user);
		
		user.getTags().add(lowerTag);
		System.out.println("^^^^^ Adding tag: "+tag+ " for User ID: " + user.getUserId()+" ^^^^^\n");
	}

	@Override
	public void removeUserTag(String tag, User user) {
		if(!user.getTags().contains(tag)) {
			System.out.println("error: user is not subscribed to tag "+tag);
			return;
		}
		map.get(tag).remove(user);
		user.getTags().remove(tag);
		System.out.println("^^^^^ Removing tag: "+tag+ " for User ID: " + user.getUserId()+" ^^^^^\n");

	}

	@Override
	public void notifyUsers(Post p) {
		ArrayList<String> tags = p.getTags();
		ArrayList<User> updatedUsers = new ArrayList<User>();
		for(int i=0; i<tags.size(); i++) {
			String tag = tags.get(i);
			ArrayList<User> users = map.get(tag);
			for(int j=0; j<users.size(); j++) {
				if(users.get(j)!=p.getUser()&&(!updatedUsers.contains(users.get(j)))) {
					users.get(j).update(p);
					updatedUsers.add(users.get(j));
					notifiedUsers.add(users.get(j));
				}
			}
		}

	}
	
	public void addPost(Post p) {
		if (p.getParentId()!=-1) {
			System.out.println("error: not an original post");
			return;
		}
		if(allPosts.contains(p)) {
			System.out.println("error: post already exists");
			return;
		} 

		System.out.println("+++ Adding Post to MessageBoard +++\n"+p.toString());
		System.out.println("++++++++++++++++++++++++++++\n");
		notifyUsers(p);
		
		allPosts.add(p);
		
		//adds post to tag-post map
		for(int i=0; i<p.getTags().size(); i++) {
			String tag = p.getTags().get(i).toLowerCase();
			if(postMap.get(tag)==null)
				postMap.put(tag, new ArrayList<Post>());
			postMap.get(tag).add(p);
		}
		
		LinkedList<Post> list = new LinkedList<Post>();
		list.add(p);
		threads.add(list);
	
		
	}
	
	public void addReply(Post reply) {
		if(reply.getParentId()==-1) {
			System.out.println("error: not a reply to an existing post");
		}
		if(allPosts.contains(reply)) {
			System.out.println("error: reply already exists");
			return;
		}
		Post parent = null;
		for(int i=0; i<allPosts.size(); i++) {
			if(allPosts.get(i).getPostId()==reply.getParentId()) {
				parent = allPosts.get(i);
			}
		}
		if(parent==null) {
			System.out.println("error parent "+reply.getParentId()+ " doesn't exist");
			return;
		}

		if(!parent.getTags().equals(reply.getTags())) {
			System.out.println("error: reply tags do not match parent tags\n");
			return;
		}
		System.out.println("+++ Adding Post to MessageBoard +++\n"+reply.toString());
		System.out.println("++++++++++++++++++++++++++++\n");
		notifyUsers(reply);
		
		allPosts.add(reply);
		
		parent.getReplies().add(reply);
		
		//adds post to tag-post map
		for(int i=0; i<reply.getTags().size(); i++) {
			String tag = reply.getTags().get(i).toLowerCase();
			if(postMap.get(tag)==null)
				postMap.put(tag, new ArrayList<Post>());
			postMap.get(tag).add(reply);
		}
		
		//adds post to thread organization structure
		for(int i=0; i<threads.size();i++) {
			LinkedList<Post> list = threads.get(i);
			if(list.contains(parent)){
				if(list.getLast().equals(parent)) 
					list.add(reply);
				else {
					LinkedList<Post> newThread = new LinkedList<Post>();
					for(int j=0; j<=list.indexOf(parent);j++) {
						newThread.add(list.get(j));
					}
					newThread.add(reply);
					threads.add(newThread);
					break;
				}		
			}
			
		}
/*
		for(int i=0; i<threads.size(); i++) {
			LinkedList<Post>thread = threads.get(i);
			for(int j=0; j<thread.size(); j++) {
				System.out.print("post id: "+thread.get(j).getPostId()+" re: "+thread.get(j).getParentId()+" | ");
			}
			System.out.println("***");
		}
		*/
	}
	
	public void displayTagMessages(String tag) {
		ArrayList<Post> posts = postMap.get(tag.toLowerCase());
		if(posts==null) {
			System.out.println("No posts exist with tag: "+tag);
			System.out.println("\n#########################\n");
			return;
		}
		System.out.println("##### Displaying posts with tag: "+tag+" #####");
		for(int i=0; i<posts.size();i++) {
			System.out.println(posts.get(i).toString());
		}
		System.out.println("#########################\n");
		
	}
	
	public void displayKeywordMessages(String keyword) {
		System.out.println("##### Displaying posts with keyword: "+keyword+" #####");
		boolean noPosts = true;
		for(int i=0; i<allPosts.size();i++) {
			String message = allPosts.get(i).getMessage().toLowerCase();
			if(message.contains(keyword.toLowerCase())) {
				System.out.println(allPosts.get(i).toString());
				noPosts = false;
			}
		}
		if(noPosts)
			System.out.println("\nNo posts found with keyword: "+keyword+"\n");
		System.out.println("#########################\n");

	}
	
	public void displayThread(int postId) {
		Post post = null;
		for(int i=0; i<allPosts.size(); i++) {
			if(allPosts.get(i).getPostId()==postId) {
				post = allPosts.get(i);
			}
		}
		if(post==null) {
			System.out.print("error: no post with ID: "+postId);
			return;
		}
		System.out.println("##### Displaying thread for PostID: "+postId+" #####");
		ArrayList<Post>printedPosts = new ArrayList<Post>();
		Post rootPost = null;
		for(int i=0; i<threads.size();i++) {
			LinkedList<Post> list = threads.get(i);
			if(list.contains(post)) {
				rootPost = list.getFirst();
				for(int j=0; j<list.size(); j++) {
					System.out.println(list.get(j).toString());
					printedPosts.add(list.get(j));
				}
				break;
			}
		}
		for(int i=0; i<threads.size();i++) {
			LinkedList<Post> list = threads.get(i);
			if(list.contains(rootPost)) {
				for(int j=0; j<list.size(); j++) {
					if(!printedPosts.contains(list.get(j))) {
						System.out.println(list.get(j).toString());
						printedPosts.add(list.get(j));
					}
					
				}
			}
		}
		System.out.println("#########################\n");

		
	}
	
	public void displayUserPosts(User user) {
		ArrayList<Post> posts = user.getUserPosts();
		if(posts.size()==0) {
			System.out.println("User ID "+user.getUserId()+" has not posted anything\n");
			System.out.println("#########################\n");
			return;
		}
		System.out.println("##### Displaying all posts for User ID: "+user.getUserId()+" #####");
		for(int i=0; i<posts.size(); i++) {
			System.out.println(posts.get(i).toString());
		}
		System.out.println("#########################\n");
	}
	
	public ArrayList<User> getNotifiedUsers(){return notifiedUsers;}
	public void clearNotifiedUsers() {notifiedUsers = new ArrayList<User>();}
	
	public LinkedHashMap<String, ArrayList<Post>> getPostMap(){return postMap;}
	public ArrayList<Post> getAllPosts(){return allPosts;}
	public ArrayList<LinkedList<Post>> getThreads(){return threads;}

}
