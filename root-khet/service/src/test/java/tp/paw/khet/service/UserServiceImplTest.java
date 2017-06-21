package tp.paw.khet.service;

import static org.mockito.Matchers.anyString;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static tp.paw.khet.model.UserTestUtils.*;
import static org.junit.Assert.*;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import tp.paw.khet.exception.DuplicateEmailException;
import tp.paw.khet.model.User;
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
		when(userDaoMock.createUser(expected.getName(), expected.getEmail(), expected.getPassword(), picture))
				.thenReturn(expected).thenReturn(null);

		User actual = userService.createUser(expected.getName(), expected.getEmail(), expected.getPassword(), picture);
		assertEqualsUsers(expected, actual);

		User shouldBeNull = userService.createUser(expected.getName(), expected.getEmail(), expected.getPassword(),
				picture);
		assertNull(shouldBeNull);

		verify(userDaoMock, times(2)).createUser(expected.getName(), expected.getEmail(), expected.getPassword(),
				picture);
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
	public void getUserByIdTest() {
		User expected = dummyUser(0);
		when(userDaoMock.getUserById(0)).thenReturn(expected);

		User actual = userService.getUserById(0);

		assertEqualsUsers(expected, actual);
		assertNull(userService.getUserById(2));
		verify(userDaoMock, times(2)).getUserById(anyInt());
	}

	@Test
	public void getProfilePictureByUserIdTest() {
		User dummyUser = dummyUser(0);
		byte[] expected = profilePictureFromUser(dummyUser);
		when(userDaoMock.getProfilePictureByUserId(0)).thenReturn(expected);

		byte[] actual = userService.getProfilePictureByUserId(0);

		assertArrayEquals(expected, actual);
		assertNull(userService.getProfilePictureByUserId(1));
		verify(userDaoMock, times(2)).getProfilePictureByUserId(anyInt());
	}

	@Test
	public void changePasswordTest() {
		User dummyUser = dummyUser(0);
		String expectedPassword = "sucutrule";
		User expected = new User(dummyUser.getName(), dummyUser.getEmail(), expectedPassword,
				profilePictureFromEmail(dummyUser.getEmail()));
		when(userDaoMock.changePassword(0, expectedPassword)).thenReturn(expected);

		User actual = userService.changePassword(0, expectedPassword);

		assertEqualsUsers(expected, actual);
		assertEquals(expectedPassword, actual.getPassword());
		verify(userDaoMock, times(1)).changePassword(0, expectedPassword);
	}
}
