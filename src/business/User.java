package business;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import dataaccess.ioStream;

public class User  implements Serializable{
	
	private static final long serialVersionUID = 8309080721495266420L;
	private String userName;
	private String password;
	private UserRole role;
    public static String urole;
	
	public User (){
		
	}
	
	public User(String userName, String password, UserRole role) {
		this.userName = userName;
		this.password = password;
		this.role = role;
		urole = role.toString();
	}
	
	public User login(String userName, String password)  {
		
		if(userName.isEmpty() || password.isEmpty())
			return null;

		List<User> l =  new ioStream().read("User.txt");
		User userInfo = null;
		for (User u : l) {			
			if(u.getUserName().trim().equals(userName) && u.getPassword().trim().equals(password)){
				userInfo = new User(u.getUserName(),u.getPassword(), u.getRole());
				break;
			}
		}
		return userInfo;
	}
	
	public void CreateUser() {
		User user = new User("natnael", "123456", role.ADMIN);
		User user1 = new User("atul", "1234", role.LIBRARIAN);
		User user2 = new User("ashish","1234", role.BOTH);
	//	User User3 = new User("Ghana","1234", role.BOTH);
		List<User> x = new ArrayList<User>();
		x.add(user);
		x.add(user1);
		x.add(user2);
		new ioStream().write(x, "User.txt",1);
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		User other = (User) obj;
		if (password == null) {
			if (other.password != null)
				return false;
		} else if (!password.equals(other.password))
			return false;
		if (role != other.role)
			return false;
		if (userName == null) {
			if (other.userName != null)
				return false;
		} else if (!userName.equals(other.userName))
			return false;
		return true;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((password == null) ? 0 : password.hashCode());
		result = prime * result + ((role == null) ? 0 : role.hashCode());
		result = prime * result + ((userName == null) ? 0 : userName.hashCode());
		return result;
	}

	@Override
	public String toString() {
		return getUserName() + " " + getRole();
	}

	public String getUserName() {
		return userName;
	}

	public String getPassword() {
		return password;
	}

	public UserRole getRole() {
		return role;
	}
	
	public String getRole(String userName) {
		List<User> l =  new ioStream().read("User.txt");
		
		for (User u : l) {			
			if(u.getUserName().trim().equals(userName)){
				return u.getRole().toString();
			}
		}
		return "";
	}
	
}
