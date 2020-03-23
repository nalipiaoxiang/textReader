package application.test;

import org.apache.http.HeaderIterator;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.ParseException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.ssl.SSLContextBuilder;
import org.apache.http.ssl.TrustStrategy;
import org.apache.http.util.EntityUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.List;

public class TestHttpClient {

	private static SSLConnectionSocketFactory sslsf = null;
	private static PoolingHttpClientConnectionManager cm = null;
	private static SSLContextBuilder builder = null;
	private static final String HTTP = "http";
	private static final String HTTPS = "https";
	static {
		try {
			builder = new SSLContextBuilder();
			// 全部信任 不做身份鉴定
			builder.loadTrustMaterial(null, new TrustStrategy() {
				@Override
				public boolean isTrusted(X509Certificate[] x509Certificates,
						String s) throws CertificateException {
					return true;
				}
			});
			sslsf = new SSLConnectionSocketFactory(builder.build(),
					new String[]{"SSLv2Hello", "SSLv3", "TLSv1", "TLSv1.2"},
					null, NoopHostnameVerifier.INSTANCE);
			Registry<ConnectionSocketFactory> registry = RegistryBuilder
					.<ConnectionSocketFactory>create()
					.register(HTTP, new PlainConnectionSocketFactory())
					.register(HTTPS, sslsf).build();
			cm = new PoolingHttpClientConnectionManager(registry);
			cm.setMaxTotal(200);// max connection
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) throws Exception {
		// httpclient客户端，类似于一个浏览器，可以由这个客户端执行http请求
		// CloseableHttpClient httpClient = HttpClients.createDefault();
		CloseableHttpClient httpClient = getHttpClient();
		// 请求
		HttpGet httpGet = new HttpGet("https://www.dingdiann.com/ddk153653/");
//		httpGet.setHeader("User-Agent",
//				"Mozilla/5.0 (Windows NT 6.1; Win64; x64; rv:50.0) Gecko/20100101 Firefox/50.0");
//		// 响应
		CloseableHttpResponse response = null;
		try {
			// execute()执行成功会返回HttpResponse响应
			response = httpClient.execute(httpGet);
			// 响应体
			HttpEntity responseEntity = response.getEntity();
			System.out.println("响应状态：" + response.getStatusLine());
			// gzip,deflate,compress
			System.out
					.println("响应体编码方式：" + responseEntity.getContentEncoding());
			// 响应类型如text/html charset也有可能在ContentType中
			System.out.println("响应体类型：" + responseEntity.getContentType());
			String html = EntityUtils.toString(responseEntity, "utf-8");
			Document document = Jsoup.parse(html);
			Elements dl = document.select("dl > *");
			int count = 0;
			Elements elements = new Elements();
			for (Element element : dl) {
				String tagName = element.tagName();
				if (count == 2) {
					elements.add(element);
				}
				if (tagName.equals("dt")) {
					count++;
				}
			}
			
			System.out.println(elements);
			System.out.println("完毕");
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (response != null) {
					// 关闭连接，则此次连接被丢弃
					response.close();
				}
				httpClient.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

	}
	public static CloseableHttpClient getHttpClient() throws Exception {
		CloseableHttpClient httpClient = HttpClients.custom()
				.setSSLSocketFactory(sslsf).setConnectionManager(cm)
				.setConnectionManagerShared(true).build();
		return httpClient;
	}

}
