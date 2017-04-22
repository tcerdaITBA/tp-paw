package tp.paw.khet.service;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;
import static tp.paw.khet.testutils.CommentTestUtils.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import tp.paw.khet.Comment;
import tp.paw.khet.persistence.CommentDao;
import tp.paw.khet.structures.ParentNode;

@RunWith(MockitoJUnitRunner.class)
public class CommentServiceImplTest {
		
	@Mock
	private CommentDao commentDaoMock;
	
	@InjectMocks
	private CommentServiceImpl commentServiceImpl;

	@Test
	public void createComment() {
		Comment expected = dummyComment(0, 0, 0);
		when(commentDaoMock.createComment(eq(expected.getContent()), any(LocalDateTime.class), eq(0), eq(0), eq(0))).thenReturn(expected);
		
		Comment actual = commentServiceImpl.createComment(expected.getContent(), 0, 0, 0);
		
		assertEqualsComments(expected, actual);
		verify(commentDaoMock, times(1)).createComment(eq(expected.getContent()), any(LocalDateTime.class), eq(0), eq(0), eq(0));		
	}
	
	@Test
	public void createParentComment() {
		Comment expected = dummyParentComment(0, 0);
		when(commentDaoMock.createParentComment(eq(expected.getContent()), any(LocalDateTime.class), eq(0), eq(0))).thenReturn(expected);
		
		Comment actual = commentServiceImpl.createParentComment(expected.getContent(), 0, 0);
		
		assertEqualsComments(expected, actual);
		verify(commentDaoMock, times(1)).createParentComment(eq(expected.getContent()), any(LocalDateTime.class), eq(0), eq(0));
	}
	
	@Test
	public void getCommentsByProductId() {
		List<List<Comment>> commentBlocks = buildBlockComments();
		List<Comment> comments = new ArrayList<>(200);
		
		for (List<Comment> list : commentBlocks)
			comments.addAll(list);
		
		when(commentDaoMock.getCommentsByProductId(anyInt())).thenReturn(comments);
		
		List<ParentNode<Comment>> commentsList = commentServiceImpl.getCommentsByProductId(0);
		assertEquals(60, commentsList.size());
		
		for (ParentNode<Comment> parentNode : commentsList) {
			if (!parentNode.getChildren().isEmpty())
				assertValidChildCommentList(parentNode);
		}
	}

	public List<List<Comment>> buildBlockComments() {
		List<List<Comment>> list = new ArrayList<>(10);
		
		for (int i = 0; i < 3; i++)
			list.add(dummyParentCommentList(20, i * 20, i));

		for (int i = 3; i < 10; i++)
			list.add(dummyCommentList(20, i * 20, i, i));
		
		return list;
	}
	
	private void assertValidChildCommentList(ParentNode<Comment> parentNode) {
		Comment parentComment = parentNode.getParent();		
		
		Comment prev = null;
		for	(Comment child : parentNode.getChildren()) {
			if (prev != null)
				assertTrue(child.getCommentDate().compareTo(prev.getCommentDate()) > 0);
			
			assertEquals(child.getParentId(), parentComment.getId());
			prev = child;
		}
	}
}
