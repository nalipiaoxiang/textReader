package application.utils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.util.EntityUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import application.entity.Chapter;

public class WebTxTUtils {

	public static List<Chapter> getChapter(String url) throws Exception {
		String ss = "https://www.dingdiann.com/ddk153653/";
		ss.substring(0, ss.indexOf("/"));
		CloseableHttpClient httpClient = HttpClientBuilder.getHttpClient();
		// 请求
		HttpGet httpGet = new HttpGet(url);
		CloseableHttpResponse response = httpClient.execute(httpGet);
		HttpEntity responseEntity = response.getEntity();
		String html = EntityUtils.toString(responseEntity, "utf-8");
		Document document = Jsoup.parse(html);
		Elements dl = document.select("dl > *");
		int count = 0;
		List<Chapter> chapters = new ArrayList<Chapter>();
		for (Element e : dl) {
			String tagName = e.tagName();
			if (count == 2) {
				Elements a = e.select("a");
				chapters.add(new Chapter(a.text(), url + a.attr("href")));
			}
			if (tagName.equals("dt")) {
				count++;
			}
		}
		return chapters;
	}

}
