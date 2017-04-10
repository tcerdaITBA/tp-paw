package tp.paw.khet.service;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;
import static tp.paw.khet.testutils.UserTestUtils.*;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import tp.paw.khet.User;
import tp.paw.khet.persistence.UserDao;

@RunWith(MockitoJUnitRunner.class)
public class UserServiceImplTest {

	@Mock
	private UserDao userDaoMock;
	
	@InjectMocks
	private UserServiceImpl userService;

	@Test
	public void createUserTest() {
		User expected = dummyUser(0);
		when(userDaoMock.createUser(expected.getName(), expected.getMail())).thenReturn(expected).thenReturn(null);
		
		User actual = userService.createUser(expected.getName(), expected.getMail());
		assertEqualsUsers(expected, actual);
		
		User shouldBeNull = userService.createUser(expected.getName(), expected.getMail());
		assertNull(shouldBeNull);
		
		verify(userDaoMock, times(2)).createUser(expected.getName(), expected.getMail());
	}
	
	@Test
	public void getUserByEmailTest() {
		User expected = dummyUser(0);
		when(userDaoMock.getUserByEmail(expected.getMail())).thenReturn(expected);
		
		User actual = userService.getUserByEmail(expected.getMail());
		
		assertEqualsUsers(expected, actual);
		assertNull(userService.getUserByEmail("anyEmail@example.com"));
		verify(userDaoMock, times(2)).getUserByEmail(anyString());
	}
	
	@Test
	public void createUserOrRetrieveIfExistsAndUserExistsTest() {
		User alreadyCreated = dummyUser(0);
		when(userDaoMock.createUser(anyString(), eq(alreadyCreated.getMail()))).thenReturn(null);
		when(userDaoMock.getUserByEmail(alreadyCreated.getMail())).thenReturn(alreadyCreated);
		
		User actual = userService.createUserOrRetrieveIfExists("Miguel", alreadyCreated.getMail());
		
		assertEqualsUsers(alreadyCreated, actual);
		
		verify(userDaoMock, times(1)).createUser(anyString(), eq(alreadyCreated.getMail()));
		verify(userDaoMock, atLeastOnce()).getUserByEmail(alreadyCreated.getMail());
	}
	
	@Test
	public void createUserOrRetrieveIfExistsAndUserNotExistsTest() {
		User expected = dummyUser(0);
		when(userDaoMock.createUser(expected.getName(), expected.getMail())).thenReturn(expected);
		
		User actual = userService.createUserOrRetrieveIfExists(expected.getName(), expected.getMail());
		
		assertEqualsUsers(expected, actual);
		
		verify(userDaoMock, times(1)).createUser(anyString(), eq(expected.getMail()));
		verify(userDaoMock, atMost(1)).getUserByEmail(anyString());
	}
}
