import java.awt.List;
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
	
	public ArrayList chineselist = new ArrayList();

	public GoogleQuery(String searchKeyword)

	{

		this.searchKeyword = searchKeyword;

		this.url = "http://www.google.com/search?q="+searchKeyword+"&oe=utf8&num=20";

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
		//System.out.println(lis.size());
		
		
		for(Element li : lis)
		{
			try 

			{
			
				String url = li.select("a").get(0).attr("href");
				int http = url.indexOf("http");
				int sa = url.indexOf("&sa");
				url = url.substring(http, sa);
				//lrcbox
				if (url.indexOf("youtube.com")==-1 && url.indexOf(".pdf")==-1 && url.indexOf(".doc")==-1 && url.indexOf(".txt")==-1 && url.indexOf("weibo")==-1 && url.indexOf("apple.com")==-1 && url.indexOf("wikipedia")==-1 && url.indexOf("facebook")==-1 && url.indexOf("books.google")==-1 && url.indexOf("twitter")==-1 && url.indexOf("reddit.com")==-1 && url.indexOf(".aspx")==-1 && url.indexOf("novel")==-1 && url.indexOf("pinterest")==-1) {
					ListForResultsUrl.add(url);
				//	System.out.println("url added");
				//	System.out.println(url);
				}
				
				String title = li.select(".BNeawe").get(0).text();
			//	if (title.indexOf("YouTube")==-1 && url.indexOf("PDF")==-1 && url.indexOf("pdf")==-1 && url.indexOf("DOC")==-1 && url.indexOf("doc")==-1 && url.indexOf("txt")==-1) {
					if (ListForTitles.size()==ListForResultsUrl.size()-1) {
						ListForTitles.add(title);
				//		System.out.println("title added");
				//		System.out.println(title);
					}
			//	}
				if (title.indexOf("相關")!=-1 || title.indexOf("圖片")!=-1 || title.indexOf("Song Directory")!=-1 || title.indexOf("相关")!=-1) {
					ListForTitles.remove(ListForTitles.size()-1);
					ListForResultsUrl.remove(ListForResultsUrl.size()-1);
				}

//				System.out.println(li.select("a").get(0).attr("href"));

				//				for(int i = 0 ; i < block.size(); i++)
//					System.out.println(block.get(i).text());
				
//				System.out.println(block.get(1).text());
//				System.out.println(block.get(2).text());
				
//				String title = block.get(1).text();
//				String citeUrl = block.get(2).text();
				
//				System.out.println(title+" "+citeUrl);
				if (ListForTitles.size() == (retVal.size()+1) && ListForResultsUrl.size() == (retVal.size()+1)) {
					retVal.put(title, url);
					//System.out.println("retval add");
				}
				

			} catch (IndexOutOfBoundsException e) {


			}
			if (ListForTitles.size()!=ListForResultsUrl.size()) {
				System.out.println(ListForTitles.size());
				System.out.println(ListForResultsUrl.size());
			}
			

		}
		
		for (int a = 0; a<ListForResultsUrl.size(); a++) {
			System.out.println(ListForTitles.get(a));
		//	System.out.println(ListForResultsUrl.get(a));
		}
		System.out.println(retVal.size());
		
		return retVal;

	}
	
	public ArrayList<String> getTitleList(){
		return ListForTitles;
	}
	
	public ArrayList<String> getUrlList(){
		return ListForResultsUrl;
	}
	
	public void removeTitle(int index) {
		ListForTitles.remove(index);
		
	}
	
	public void removeUrl(int index) {
		ListForResultsUrl.remove(index);
	}
	
	//相關搜尋結果
	public String search(){
		String list="" ;
		if (content.contains("相關搜尋")) {
			int search = content.indexOf("相關搜尋");
			String newcontent =content.substring(search);
			
			while(newcontent.contains("div class=\"BNeawe deIvCb AP7Wnd")) 
			{
				int s= newcontent.indexOf("div class=\"BNeawe deIvCb AP7Wnd");
				String a = newcontent.substring(s+33);
				
				int chn =a.indexOf("<");
				String chinese =a.substring(0,chn);
				//System.out.println(chinese);
				list=list+chinese+"  ,";	
				//chineselist.add(chinese);
				newcontent =a.substring(chn);
				
			}
			//System.out.println("相關搜尋結果："+chineselist);
			return "你可能有興趣 ："+list;
		}
		
		else {
			//System.out.println("沒有相關搜尋");
			return "沒有相關搜尋結果";
		}
		//return "你可能有興趣 ："+list;
	}
}