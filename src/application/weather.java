package application;

import java.io.IOException;
import java.util.ArrayList;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class weather {
	ArrayList<String> onlyDays = new ArrayList<String>();
	ArrayList<String> degrees = new ArrayList<String>();
	ArrayList<String> images = new ArrayList<String>();
	private String grad;
	public weather(String mjesto) {
		grad=mjesto;
	}
	
	private String test() throws IOException {
		Document doc = Jsoup.connect("http://prognoza.hr/sedam.php?id=sedam&param=Hrvatska&code=14246").get();
		Element masthead = doc.select("div.sedamContents").first();
		Element table = masthead.select("table").first();
		Elements tds= table.select("td");
		Elements as= tds.select("a");
		//Element link = as.select("a").first();
		String myUrl;
		myUrl=null;
		for(int i=0;i<as.size();i++)  {
			
	        Element link=as.get(i);
	        String relHref = link.attr("href");
			String linkText = link.text();
			if(linkText.toUpperCase().equals(grad.toUpperCase())){
				return relHref;
			}
			
		System.out.println(relHref);
		
		}
		return null;
	}
public ArrayList<String> getData() throws IOException {
	String url=test();
	System.out.print(url);
	if(url!=null){
		Document doc = Jsoup.connect("http://prognoza.hr"+url).get();
		Element masthead = doc.select("div.sadrzajContents").first();
		Element table = masthead.select("table").first();
		Element table2 = table.select("table").get(3);
		Elements trs= table2.select("tr");
		int count =1;
		ArrayList<String> days = new ArrayList<String>();

		for(int i=1;i<trs.size();i++)  {
	        Element tr=trs.get(i);
				Elements tds=tr.select("td");
			for(int j=0;j<tds.size();j++) {
				if(tds.get(j).hasText()) {
					String text =tds.get(j).text();
					String[] textArr = text.split(" ");
					if(textArr.length>1) {
						if(textArr[1].equals("°C")) {
							days.add(textArr[0]);
							degrees.add(textArr[0]);
						}
						else {
							System.out.println(textArr[0]+" "+textArr[1]);
							days.add(textArr[0]+" "+textArr[1]);
							onlyDays.add(textArr[0]+" "+textArr[1]);
						}
						
						
					}
				}
				else {
					if(count%2!=0) {
						Element buddynameInput = tds.get(j).select("img[src]").first();
						String imgName=buddynameInput.attr("src");
						String[] imgArr = imgName.split("/");
						String[] imgArr2 = imgArr[1].split(".gif");
						System.out.println(imgArr2[0]);
						images.add(imgArr2[0]);
					}
					count++;
				}
					
				
				
				
				
			}
					
			
				
			
		}
		return days;
	}
	
	return null;
}
public ArrayList<String> getDays(){
	return onlyDays;
	
}
public ArrayList<String> getDegrees(){
	
	return degrees;
	
}
public ArrayList<String> getImgNames(){
	
	return images;
	
}
}
	

