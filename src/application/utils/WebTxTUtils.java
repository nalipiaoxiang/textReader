package application.utils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import application.entity.Article;
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

	public static Document getDocument(String url)throws Exception {
		String webSit = StringUtils.getWebSit(url);
		CloseableHttpClient httpClient = HttpClientBuilder.getHttpClient();
		// 请求
		HttpGet httpGet = new HttpGet(url);
		CloseableHttpResponse response = httpClient.execute(httpGet);
		HttpEntity responseEntity = response.getEntity();
		String html = EntityUtils.toString(responseEntity, "utf-8");
		Document document = Jsoup.parse(html);
		return document;
	}

	public static List<Chapter> getChapter(String url) throws Exception {
		String webSit = StringUtils.getWebSit(url);
//		CloseableHttpClient httpClient = HttpClientBuilder.getHttpClient();
//		// 请求
//		HttpGet httpGet = new HttpGet(url);
//		CloseableHttpResponse response = httpClient.execute(httpGet);
//		HttpEntity responseEntity = response.getEntity();
//		String html = EntityUtils.toString(responseEntity, "utf-8");
//		Document document = Jsoup.parse(html);
		Document document = getDocument(url);
		Elements dl = document.select("dl > *");
		int count = 0;
		List<Chapter> chapters = new ArrayList<Chapter>();
		for (Element e : dl) {
			String tagName = e.tagName();
			if (count == 2) {
				Elements a = e.select("a");
				chapters.add(new Chapter(a.text(), webSit + a.attr("href")));
			}
			if (tagName.equals("dt")) {
				count++;
			}
		}
		return chapters;
	}

	public static Article getArticle(String url) throws Exception {
		Document document = getDocument(url);
		Elements h1 = document.select("h1");
		String title = h1.text();
		Elements content = document.select("[id=content]");
		String text = content.text();
		Article build = Article.builder().title(title).content(text).build();
		return build;
	}



}
