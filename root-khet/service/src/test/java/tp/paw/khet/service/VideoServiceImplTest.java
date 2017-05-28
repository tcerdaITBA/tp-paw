package tp.paw.khet.service;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;
import static tp.paw.khet.model.VideoTestUtils.*;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import tp.paw.khet.model.Video;
import tp.paw.khet.persistence.VideoDao;

@RunWith(MockitoJUnitRunner.class)
public class VideoServiceImplTest {
	
	@Mock
	private VideoDao videoDaoMock;
	
	@InjectMocks
	private VideoServiceImpl videoService;
	
	@Test
	public void createVideoTest() {
		Video expected = dummyVideo(0);
		when(videoDaoMock.createVideo(expected.getVideoId(), 0)).thenReturn(expected);
		
		Video actual = videoService.createVideo(expected.getVideoId(), 0);
		
		assertEquals(expected, actual);
		verify(videoDaoMock, times(1)).createVideo(expected.getVideoId(), 0);
	}

	@Test
	public void getVideosByProductIdTest() {
		List<Video> expectedList = dummyVideoList(20, 0);
		when(videoDaoMock.getVideosByProductId(0)).thenReturn(expectedList);		
		
		List<Video> actualList = videoService.getVideosByProductId(0);
		
		assertEquals(expectedList.size(), actualList.size());
		
		for (int i = 0; i < expectedList.size(); i++)
			assertEquals(expectedList.get(0), actualList.get(0));
		
		assertTrue(videoService.getVideosByProductId(1).isEmpty());
		verify(videoDaoMock, times(2)).getVideosByProductId(anyInt());
	}
}
