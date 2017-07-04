package dataaccess;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import business.User;

public class LibraryMember implements Serializable {
	private static final long serialVersionUID = 8309080721495266420L;
	private String memberId;
	private String firstName;
	private String lastName;
	private String phone;
	
	public LibraryMember(String txtMemberId,String txtFirstName,String txtLastName, String txtPhone){
		this.memberId = txtMemberId;
		this.firstName = txtFirstName;
		this.lastName = txtLastName;
		this.phone = txtPhone;
	}
	
	public void addLibraryMember(  ) {
		List<LibraryMember> x = new ArrayList <LibraryMember> ();
		x.add(this);
		new ioStream().write(x, "LibraryMember.txt");
	}
	
	public List<LibraryMember> getMemberList(){
		List<LibraryMember> ll =  new ioStream().read("LibraryMember.txt");
		//User userInfo = null;
		for (LibraryMember l : ll) {			
				System.out.println(l);
			}
		
		return ll;
	}
	
	public String toString() {
		return "Member ID:" + memberId;
	}
	
}