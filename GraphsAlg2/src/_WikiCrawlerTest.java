import org.junit.Test;
import org.junit.Before;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import static org.junit.Assert.*;

/**
 * @author nkarasch
 */
public class _WikiCrawlerTest {
	private WikiCrawler w;
	private static final String CRAWL_RESULTS_FILENAME = "test/_WikiCrawlerTest_crawl_results.txt";
	
	@Before
	public void setUp() {
		w = new WikiCrawler("/wiki/Complexity_theory", 20, CRAWL_RESULTS_FILENAME);
	}
	
	@Test
	public void extractLinks() {
		String webpageFilename = "test/_WikiCrawlerTest_extractLinks_test_data.txt";
		String webpage = "";
		try {
			webpage = readFile(webpageFilename);
		} catch (IOException e) {
			fail("Couldn't open file '" + webpageFilename +
					     "'! Make sure it's in the project's test directory");
		}
		
		String expectedResultsFilename = "test/_WikiCrawlerTest_extractLinks_expected_results.txt";
		String expected = "";
		try {
			expected = readFile(expectedResultsFilename);
		} catch (IOException e) {
			fail("Couldn't open file '" + expectedResultsFilename +
					     "'! Make sure it's in the project's test directory");
		}
		
		String[] actual = w.extractLinks(webpage).toArray(new String[0]);
		String[] expectedLinks = expected.split("\n");
		assertArrayEquals(expectedLinks, actual);
	}
	
	@Test
	public void crawl() {
		String expectedFilename = "test/_WikiCrawlerTest_crawl_expected_results.txt";
		String expected = "";
		try {
			expected = readFile(expectedFilename);
		} catch (IOException e) {
			fail("Couldn't open file '" + expectedFilename +
					     "'! Make sure it's in the project's test directory");
		}
		
		w.crawl();
		
		String actual = "";
		try {
			actual = readFile(CRAWL_RESULTS_FILENAME);
		} catch (IOException e) {
			fail("Couldn't open file '" + CRAWL_RESULTS_FILENAME +
					     "'! Make sure it's in the project's test directory");
		}
		
		assertEquals(expected, actual);
	}
	
	private static String readFile(String filename) throws IOException {
		BufferedReader reader = new BufferedReader(new FileReader(filename));
		StringBuilder sb = new StringBuilder();
		String line;
		while ((line = reader.readLine()) != null) {
			sb.append(line);
			sb.append('\n');
		}
		reader.close();
		return sb.toString();
	}
	
}