package application.assembly;

import java.io.IOException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class Test01 {

	public static void main(String[] args) throws IOException {
		String url = "https://www.biqukan.com/54_54435";
		System.out.println("开始");
		Document document = Jsoup.connect(url).get();
		Elements listmain = document.select("[class=listmain]");
		Elements dl = listmain.select("dl");
		boolean flag = false;
		Elements chapters = new Elements();
		int count = 0;
		for (Element element : dl) {
			System.out.println(count++);
			System.out.println(element);
		}

	}

}
