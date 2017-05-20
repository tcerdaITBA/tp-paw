package tp.paw.khet.testutils;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import tp.paw.khet.User;

public final class UserTestUtils {

	private UserTestUtils() {
	}
	
	public static User dummyUser(int id) {
		return new User(id, "Tomas Cerda " + id, "tcerda" + id + "@itba.edu.ar", "secreto" + id);
	}
	
	public static List<User> dummyUserList(int size, int initialId) {
		List<User> userList = new ArrayList<User>(size);
	
		for (int i = 0; i < size; i++)
			userList.add(dummyUser(initialId + i));
		
		return userList;
	}
	
	public static void assertEqualsUsers(User expected, User actual) {
		assertEquals(expected, actual);
		assertEquals(expected.getUserId(), actual.getUserId());
		assertEquals(expected.getName(), actual.getName());
		assertEquals(expected.getEmail(), actual.getEmail());
	}
	
	public static byte[] profilePictureFromUser(User dummyUser) {
		return dummyUser.getEmail().getBytes();
	}
}
