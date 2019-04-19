import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Yangxiao Wang on 4/1/2017.
 */
public class WikiCrawler {
	public static final String BASE_URL = "https://en.wikipedia.org";
	private String seedUrl;
	private int max;
	private String fileName;
	
	/**
	 * @param seedUrl  A string seedUrl{relative address of the seed url (within Wiki domain)
	 * @param max      An integer max representing Maximum number pages to be crawled
	 * @param fileName A string fileName representing name of a file. The graph will be written to
	 *                 this file
	 */
	public WikiCrawler(String seedUrl, int max, String fileName) {
		this.seedUrl = seedUrl;
		this.max = max;
		this.fileName = fileName;
	}
	
	/**
	 * @param doc: gets a string (that represents contents of a .html)
	 * @return return an array list (of Strings) consisting of links from doc.
	 */
	public ArrayList<String> extractLinks(String doc) {
		ArrayList<String> re = new ArrayList<String>();
		Scanner scan = null;
		//		String wiki = "href=\"([^:#]*/wiki/[^:#]+)\"";
		String wiki = "href=\"(/wiki/[^:#]+)\"";
		Pattern pattern = Pattern.compile(wiki);
		
		scan = new Scanner(doc);
		boolean firstP = false;
		
		while (scan.hasNext()) {
			String line = scan.next();
			Matcher matcher = pattern.matcher(line);
			if ((!firstP) && (line.contains("<P>") || line.contains("<p>"))) {
				firstP = true;
			}
			
			if (firstP) {
				while (matcher.find()) {
					//						System.out.println(matcher.group(1));
					re.add(matcher.group(1));
				}
			}
		}
		scan.close();
		return re;
	}
	
	/**
	 * Wait for at least 3 seconds after every 100 requests.
	 */
	public void crawl() {
		URL url = null;
		PrintWriter pw = null;
		Queue<String> q = new LinkedList<String>();
		
		Set<String> visited = new HashSet<>();
		LinkedList<links> output = new LinkedList<links>();
		Set<String> processed = new HashSet<>();
		
		int pages = 0;
		
		q.add(seedUrl);
		visited.add(seedUrl);
		
		while (!(q.isEmpty())) {
			String current = (String) (q.poll());
			if (pages % 100 == 0) {
				//Pause for 3 seconds
				try {
					Thread.sleep(3000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			
			StringBuilder out = new StringBuilder();
			
			try {
				url = new URL(BASE_URL + current);
				InputStream is = url.openStream();
				BufferedReader br = new BufferedReader(new InputStreamReader(is));
				
				String address = "";
				String line = "";
				while ((line = br.readLine()) != null) {
					out.append(line);
				}
				br.close();
				pages++;
			} catch (IOException e) {
				e.printStackTrace();
			}
			
			ArrayList<String> extractedLinks = extractLinks(out.toString());
			HashSet<String> printed = new HashSet<>();
			printed.add(current);
			for (String nextLink : extractedLinks) {
				if (!printed.contains(nextLink)) {
					output.add(new links(current, nextLink));
					printed.add(nextLink);
				}
				if (!visited.contains(nextLink)/*&& !nextLink.equals(current)*/) {
					visited.add(nextLink);
					q.add(nextLink);
				}
			}
			processed.add(current);
			
			if (pages >= max) {
				q.clear();
				break;
			}
			
		}
		
		try {
			File file = new File(fileName);
			pw = new PrintWriter(file);
			pw.println(pages);
			int eds = 0;
			for (links s : output) {
				if (processed.contains(s.getL2())) {
					pw.println(s);
					eds++;
				}
			}
			System.out.println(eds);
			pw.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		//		File temp = new File("temp.txt");
		//		temp.delete();
	}
	
	private class links {
		String l1, l2;
		
		public links(String l1, String l2) {
			this.l1 = l1;
			this.l2 = l2;
		}
		
		public String getL1() {
			return l1;
		}
		
		public String getL2() {
			return l2;
		}
		
		@Override
		public String toString() {
			return l1 + " " + l2;
		}
	}
	
	//Output for Report
	public static void main(String[] args) {
		///wiki/Complexity theory
		WikiCrawler test = new WikiCrawler("/wiki/Computer_Science", 500, "WikiCS.txt");
		long t1 = System.currentTimeMillis();
		//		test.extractLinks("sample.txt");
		//		System.out.println("extract: " + (double)(System.currentTimeMillis() - t1) / 1000);
		t1 = System.currentTimeMillis();
		test.crawl();
		System.out.println("crawl: " + (double) (System.currentTimeMillis() - t1) / 1000);
	}
}
