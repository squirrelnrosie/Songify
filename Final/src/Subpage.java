import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;

public class Subpage {
	private String urlStr;
    private String content;
    private ArrayList<String> subpage = new ArrayList<String>();
    private String fetchContent(String urlStr) throws IOException{
    	String retVal = "";
		String line = null;
    		URL url = new URL(urlStr);
    		URLConnection conn = url.openConnection();
    		conn.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/79.0.3945.88 Safari/537.36");
    		InputStream in = conn.getInputStream();
    		BufferedReader br = new BufferedReader(new InputStreamReader(in));
    		while ((line = br.readLine()) != null){
    			retVal = retVal + line + "\n";
    		}
    	
    	return retVal;
    }
 
    public ArrayList<String> fetchSubpage(String urlStr) throws IOException{
    	try{
    		subpage.clear();
    		content = fetchContent(urlStr);
    		int retVal = 0;
    		boolean boo = false;
    		int index;
    		String href = "href=\"";
    	
    		while(!boo) {
    			index = content.indexOf(href);
    			if (index!=-1) {
    				content = content.substring(index+href.length());
    				int end = content.indexOf("\"");
    				String sub = content.substring(0, end);
    				if (sub.indexOf("http")!=-1) {
    				if (sub.indexOf("png")==-1 && sub.indexOf("xml")==-1 && sub.indexOf("css")==-1 && sub.indexOf("js")==-1 && sub.indexOf("album")==-1 && sub.indexOf("ico")==-1 && sub.indexOf("wordpress")==-1) {
    					subpage.add(sub);
    				}
    				}
    			}
    			else {
    				boo = true;
    			}
    		}
    	/**
    	for (String sub:subpage) {
   			System.out.println(sub);
  		}
    	 */
    	}catch (Exception e) {
			System.out.println("Page is down.");
		}
    	System.out.println("Number of subpages: " + subpage.size());
		return subpage; // return an arraylist storing url of subpages
    }
    
    public int getSubpageSize() {
    	return subpage.size();
    }
    
}