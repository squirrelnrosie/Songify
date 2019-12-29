import java.io.BufferedReader;

import java.io.IOException;

import java.io.InputStream;

import java.io.InputStreamReader;

import java.net.URL;

import java.net.URLConnection;
import java.util.ArrayList;
import java.util.HashMap;



import org.jsoup.Jsoup;

import org.jsoup.nodes.Document;

import org.jsoup.nodes.Element;

import org.jsoup.select.Elements;



public class GoogleQuery 

{
	public ArrayList<String> ListForTitles = new ArrayList<String>();
	
	public ArrayList<String> ListForResultsUrl = new ArrayList<String>();
	
	public String searchKeyword;

	public String url;

	public String content;

	public GoogleQuery(String searchKeyword)

	{

		this.searchKeyword = searchKeyword;

		this.url = "http://www.google.com/search?q="+searchKeyword+"&oe=utf8&num=13";

	}

	

	private String fetchContent() throws IOException

	{
		String retVal = "";

		URL u = new URL(url);

		URLConnection conn = u.openConnection();

		conn.setRequestProperty("User-agent", "Chrome/7.0.517.44");

		InputStream in = conn.getInputStream();

		InputStreamReader inReader = new InputStreamReader(in,"utf-8");

		BufferedReader bufReader = new BufferedReader(inReader);
		String line = null;

		while((line=bufReader.readLine())!=null)
		{
			retVal += line;

		}
		return retVal;
	}
	public HashMap<String, String> query() throws IOException

	{

		if(content==null)

		{

			content= fetchContent();

		}

		HashMap<String, String> retVal = new HashMap<String, String>();
		
		Document doc = Jsoup.parse(content);
		// System.out.println(doc.text());
		Elements lis = doc.select("div");
		lis = lis.select(".ZINbbc");
		System.out.println(lis.size());
		
		
		for(Element li : lis)
		{
			try 

			{
			
				String url = li.select("a").get(0).attr("href");
				int http = url.indexOf("http");
				int sa = url.indexOf("&sa");
				url = url.substring(http, sa);
				if (url.indexOf("youtube.com")==-1) {
					ListForResultsUrl.add(url);
					System.out.println(url);
				}
				
				String title = li.select(".BNeawe").get(0).text();
				if (title.indexOf("YouTube")==-1) {
					if (ListForTitles.size()==ListForResultsUrl.size()-1) {
						ListForTitles.add(title);
						System.out.println(title);
					}
				}
				
				
//				System.out.println(li.select("a").get(0).attr("href"));

				//				for(int i = 0 ; i < block.size(); i++)
//					System.out.println(block.get(i).text());
				
//				System.out.println(block.get(1).text());
//				System.out.println(block.get(2).text());
				
//				String title = block.get(1).text();
//				String citeUrl = block.get(2).text();
				
//				System.out.println(title+" "+citeUrl);

//				retVal.put(title, citeUrl);

				

			} catch (IndexOutOfBoundsException e) {

				

			}
			if (ListForTitles.size()!=ListForResultsUrl.size()) {
				ListForTitles.remove(0);
			}
			

		}
		return retVal;

	}
	
	public ArrayList<String> getTitleList(){
		return ListForTitles;
	}
	
	public ArrayList<String> getUrlList(){
		return ListForResultsUrl;
	}
}