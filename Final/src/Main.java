import java.awt.List;
import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

import javax.swing.plaf.basic.BasicTreeUI.TreeCancelEditingAction;

public class Main {
	public static ArrayList<WebNode> ListForSearchResults = new ArrayList<WebNode>();
	
	public static void main(String[] args) {
		
		Scanner in = new Scanner(System.in);
		String teString = in.nextLine();
		String[] userInput = teString.split(" ");
		in.close();
		String input = "";
		for (String userinput:userInput) {
			input += userinput + "+";
		}
		// System.out.println(input);
			
		
		
		try {
			ArrayList<Keyword> keywords = new ArrayList<Keyword>();
			keywords.add(new Keyword("lyrics", 50));
			keywords.add(new Keyword("song", 5));
			keywords.add(new Keyword("composer", 3));
			keywords.add(new Keyword("album", 4));
			keywords.add(new Keyword("track", 4));
			keywords.add(new Keyword("artist", 4));
			keywords.add(new Keyword("vocal", 4));
			keywords.add(new Keyword("producer", 4));
			keywords.add(new Keyword("chorus", 50));
			keywords.add(new Keyword("verse", 4));
			keywords.add(new Keyword("written", 4));
			//keywords.add(new Keyword("genius", 100));
			keywords.add(new Keyword("Apple Music", -1000));
			keywords.add(new Keyword("Apple_mobile", -1000));
			keywords.add(new Keyword("App Store", -1000));
			keywords.add(new Keyword("wikipedia", -1000));
			keywords.add(new Keyword("facebook", -500));
			keywords.add(new Keyword("維基", -1000));
			keywords.add(new Keyword("歌詞", 5));
			keywords.add(new Keyword("歌曲", 5));
			keywords.add(new Keyword("作曲", 3));
			keywords.add(new Keyword("作詞", 3));
			keywords.add(new Keyword("專輯", 4));
			keywords.add(new Keyword("歌手", 4));
			keywords.add(new Keyword("魔鏡", 200));
			keywords.add(new Keyword("熱門歌曲", 4));
			keywords.add(new Keyword("所有專輯", 4));
			keywords.add(new Keyword(teString, 1000));
			
			//GoogleQuery query = new GoogleQuery(input + "site:genius.com+OR+site:mojim.com+OR+site:azlyrics.com");

			GoogleQuery query = new GoogleQuery(input + "lyrics+genius+OR+魔鏡");
			System.out.println(query.query());
			
			System.out.println("Size of results: " + query.getUrlList().size()); // search results
			System.out.println();
			Subpage subpage = new Subpage();
			//System.out.println(subpage.fetchSubpage(query.getUrlList().get(0)));
			
			
			for (int m = 0; m<query.getUrlList().size()-1; m++) {
			//for (int m = 0; m<10; m++) {
				WebPage rootPage = new WebPage(query.getUrlList().get(m), query.getTitleList().get(m));		
				WebTree tree = new WebTree(rootPage);
				ListForSearchResults.add(tree.root);
				ArrayList<String> ListForSubpage = subpage.fetchSubpage(query.getUrlList().get(m)); // list storing subpages for each result
				//System.out.println(query.getUrlList().get(10));
				//System.out.println(ListForSubpage.size());
				// int SubpageSize = subpage.getSubpageSize();
				if (ListForSubpage.size()==0) {
					System.out.println("No subpages");
				}
				else if (ListForSubpage.size()<2) {
					for (String sub : ListForSubpage) {
						tree.root.addChild(new WebNode(new WebPage(sub, "HI")));
					}
				}
				else {
					for (int s = 0; s<2; s++) {
						tree.root.addChild(new WebNode(new WebPage(ListForSubpage.get(s), "HI")));
					}
					System.out.println("Adding subpages is done.");
				}
				
				//for (int z = 0; z<2; z++) {
				//	System.out.println(tree.root.children.get(z).webPage.url);
				//}
				try {
					tree.setPostOrderScore(keywords);
				}catch (Exception e) {
					System.out.println("error: ");
					e.printStackTrace();
				}	
				System.out.println("Grading is done.");
				System.out.println("Score:" + tree.root.nodeScore);
				System.out.println();
			}
			sort();
			Collections.reverse(ListForSearchResults); // finish sorting
			
			for(int x = 0; x<ListForSearchResults.size(); x++) {
				System.out.print(ListForSearchResults.get(x).nodeScore + "\t");
				System.out.println(ListForSearchResults.get(x).webPage.name);
			}
			
			//相關搜尋結果
			query.search();
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	
		/**
		try{
			Subpage subpage = new Subpage();
			subpage.fetchSubpage("https://genius.com/Baek-yerin-lovelovelove-lyrics");
		}catch (IOException e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		*/	
		
	}

	public static void sort(){
		quickSort(0, ListForSearchResults.size()-1);
		System.out.println("Done");
	}
	
	
	public static void quickSort(int leftbound, int rightbound){
		//implement quickSort algorithm
		if (leftbound < rightbound) {
			double pivot = ListForSearchResults.get(rightbound).nodeScore; // rightbound -> pivot
			int count = leftbound-1; // how many keyword's count is smaller than that of pivot
			for (int m=leftbound; m<rightbound; m++) {
				if (ListForSearchResults.get(m).nodeScore <= pivot) {
					count++;
					swap(count, m);
				}
			}
			swap(count+1, rightbound); // put the pivot next to keyword whose count is less than its count
			quickSort(leftbound, count); // left
			quickSort(count+1, rightbound); // right
		}
	}
	
	
	public static void swap(int aIndex, int bIndex){
		WebNode temp = ListForSearchResults.get(aIndex);
		ListForSearchResults.set(aIndex, ListForSearchResults.get(bIndex));
		ListForSearchResults.set(bIndex, temp);
	}
	
}