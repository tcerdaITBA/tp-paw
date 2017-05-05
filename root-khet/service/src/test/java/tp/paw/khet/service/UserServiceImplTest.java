package tp.paw.khet.service;

import static org.junit.Assert.assertNull;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static tp.paw.khet.testutils.UserTestUtils.*;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import tp.paw.khet.User;
import tp.paw.khet.exception.DuplicateEmailException;
import tp.paw.khet.persistence.UserDao;

@RunWith(MockitoJUnitRunner.class)
public class UserServiceImplTest {

	@Mock
	private UserDao userDaoMock;
	
	@InjectMocks
	private UserServiceImpl userService;
	
	@Test
	public void createUserTest() throws DuplicateEmailException {
		User expected = dummyUser(0);
		byte[] picture = profilePictureFromUser(expected);
		when(userDaoMock.createUser(expected.getName(), expected.getEmail(), expected.getPassword(), picture)).thenReturn(expected).thenReturn(null);
		
		User actual = userService.createUser(expected.getName(), expected.getEmail(), expected.getPassword(), picture);
		assertEqualsUsers(expected, actual);
		
		User shouldBeNull = userService.createUser(expected.getName(), expected.getEmail(), expected.getPassword(), picture);
		assertNull(shouldBeNull);
		
		verify(userDaoMock, times(2)).createUser(expected.getName(), expected.getEmail(), expected.getPassword(), picture);
	}
	
	@Test
	public void getUserByEmailTest() {
		User expected = dummyUser(0);
		when(userDaoMock.getUserByEmail(expected.getEmail())).thenReturn(expected);
		
		User actual = userService.getUserByEmail(expected.getEmail());
		
		assertEqualsUsers(expected, actual);
		assertNull(userService.getUserByEmail("anyEmail@example.com"));
		verify(userDaoMock, times(2)).getUserByEmail(anyString());
	}
}
