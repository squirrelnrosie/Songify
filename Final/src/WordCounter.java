import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.net.HttpURLConnection;

public class WordCounter {
	private String urlStr;
    private String content;
    
    public WordCounter(String urlStr){
    	this.urlStr = urlStr;
    }
    
    private String fetchContent() throws IOException{
    	String retVal = "";
		String line = null;
    	try {
    		URL url = new URL(urlStr);
    		URLConnection conn = url.openConnection();
    		conn.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/79.0.3945.88 Safari/537.36");
    		InputStream in = conn.getInputStream();
    		BufferedReader br = new BufferedReader(new InputStreamReader(in));
    		while ((line = br.readLine()) != null){
    			retVal = retVal + line + "\n";
    		}
    	}catch (Exception e) {
			System.out.println("Fail here. (WordCounter)");
		}
    	return retVal;
    }
    
    public int countKeyword(String keyword) throws IOException{
		content = fetchContent();
		
		//To do a case-insensitive search, we turn the whole content and keyword into upper-case:
		content = content.toUpperCase();
		keyword = keyword.toUpperCase();
	
		int retVal = 0;
		int fromIdx = 0;
		int found = -1;
	
		while ((found = content.indexOf(keyword, fromIdx)) != -1){
		    retVal++;
		    fromIdx = found + keyword.length();
		}
	
		return retVal;
    }
}