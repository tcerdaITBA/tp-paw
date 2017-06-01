package tp.paw.khet.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import tp.paw.khet.model.Video;

public final class VideoTestUtils {

	private static final Random RANDOM = new Random();
	private static final int ID_LENGTH = 11;
	
	private VideoTestUtils() {
	}
	
	public static Video dummyVideo(int productId) {
		return new Video(randomVideoId(), productId);
	}
	
	public static List<Video> dummyVideoList(int size, int productId) {
		List<Video> videoList = new ArrayList<Video>();
		
		for (int i = 0; i < size; i++)
			videoList.add(dummyVideo(productId));
		
		return videoList;
	}
	
	private static String randomVideoId() {
		StringBuilder stringBuilder = new StringBuilder();
		
		for (int i = 0; i < ID_LENGTH; i++) {
			char randChar = (char) ('a' + RANDOM.nextInt(26));
			stringBuilder.append(RANDOM.nextBoolean() ? randChar : Character.toUpperCase(randChar));
		}
		
		return stringBuilder.toString();
	}
}
