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
		when(userDaoMock.createUser(expected.getName(), expected.getEmail())).thenReturn(expected).thenReturn(null);
		
		User actual = userService.createUser(expected.getName(), expected.getEmail());
		assertEqualsUsers(expected, actual);
		
		User shouldBeNull = userService.createUser(expected.getName(), expected.getEmail());
		assertNull(shouldBeNull);
		
		verify(userDaoMock, times(2)).createUser(expected.getName(), expected.getEmail());
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
	
	@Test
	public void createUserOrRetrieveIfExistsAndUserExistsTest() {
		User alreadyCreated = dummyUser(0);
		when(userDaoMock.createUser(anyString(), eq(alreadyCreated.getEmail()))).thenReturn(null);
		when(userDaoMock.getUserByEmail(alreadyCreated.getEmail())).thenReturn(alreadyCreated);
		
		User actual = userService.createUserOrRetrieveIfExists("Miguel", alreadyCreated.getEmail());
		
		assertEqualsUsers(alreadyCreated, actual);
		
		verify(userDaoMock, times(1)).createUser(anyString(), eq(alreadyCreated.getEmail()));
		verify(userDaoMock, atLeastOnce()).getUserByEmail(alreadyCreated.getEmail());
	}
	
	@Test
	public void createUserOrRetrieveIfExistsAndUserNotExistsTest() {
		User expected = dummyUser(0);
		when(userDaoMock.createUser(expected.getName(), expected.getEmail())).thenReturn(expected);
		
		User actual = userService.createUserOrRetrieveIfExists(expected.getName(), expected.getEmail());
		
		assertEqualsUsers(expected, actual);
		
		verify(userDaoMock, times(1)).createUser(anyString(), eq(expected.getEmail()));
		verify(userDaoMock, atMost(1)).getUserByEmail(anyString());
	}
}
