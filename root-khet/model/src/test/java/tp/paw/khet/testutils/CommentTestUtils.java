package tp.paw.khet.testutils;

import static org.junit.Assert.assertEquals;
import static tp.paw.khet.testutils.UserTestUtils.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import tp.paw.khet.Comment;

public final class CommentTestUtils {

	private CommentTestUtils() {
	}
	
	public static Comment dummyRootComment(int commentId) {
		return new Comment(commentId, dummyUser(commentId), "Content " + commentId, LocalDateTime.now().plusSeconds(commentId));
	}
	
	public static Comment dummyComment(int commentId, int parentId) {
		return new Comment(commentId, parentId, dummyUser(commentId), "Content " + commentId, LocalDateTime.now().plusSeconds(commentId));
	}
	
	public static List<Comment> dummyRootCommentList(int size, int initialId) {
		List<Comment> list = new ArrayList<>(size);
		
		for (int i = 0; i < size; i++)
			list.add(dummyRootComment(initialId + i));

		return list;
	}

	public static List<Comment> dummyCommentList(int size, int initialId, Integer parentId) {
		List<Comment> list = new ArrayList<>(size);
		
		for (int i = 0; i < size; i++)
			list.add(dummyComment(initialId + i, parentId));

		return list;
	}
	
	public static void assertEqualsComments(Comment expected, Comment actual) {
		assertEqualsUsers(expected.getAuthor(), actual.getAuthor());
		assertEquals(expected.getParentId(), actual.getParentId());
		assertEquals(expected.getId(), actual.getId());
		assertEquals(expected.getContent(), actual.getContent());
		assertEquals(expected.getCommentDate(), actual.getCommentDate());
		assertEquals(expected, actual);
	}	
}
