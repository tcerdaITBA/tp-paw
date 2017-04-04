package tp.paw.khet.service;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import tp.paw.khet.User;
import tp.paw.khet.persistence.UserDao;

public class UserServiceImplTest {

	private UserDao userDaoMock;
	private UserServiceImpl userService;
	
	@Before
	public void setUp() throws Exception {
		userDaoMock = Mockito.mock(UserDao.class);
		userService = new UserServiceImpl(userDaoMock);
	}

	@Test
	public void createUserTest() {
		User expectedUser = dummyUser(0);
		Mockito.when(userDaoMock.createUser(expectedUser.getName(), expectedUser.getMail())).thenReturn(expectedUser);
		
		User retrievedUser = userService.createUser(expectedUser.getName(), expectedUser.getMail());
		
		assertEquals(expectedUser, retrievedUser);
		assertEquals(expectedUser.getUserId(), retrievedUser.getUserId());
		assertEquals(expectedUser.getName(), retrievedUser.getName());
		assertEquals(expectedUser.getMail(), retrievedUser.getMail());
	}

	private User dummyUser(int i) {
		return new User(i, "Tomás Cerdá", "tcerda@itba.edu.ar");
	}

}
