package application;

import java.io.File;
import java.io.IOException;
import java.util.List;

import application.entity.Article;
import javafx.scene.control.*;
import lombok.SneakyThrows;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.util.EntityUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import application.assembly.FileItem;
import application.assembly.FileListView;
import application.assembly.ChapterListView;
import application.assembly.MyImagePane;
import application.entity.Chapter;
import application.utils.TextFileUtils;
import application.utils.WebTxTUtils;
import javafx.application.Application;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.stage.Stage;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;

//https://www.biqukan.com/54_54435
public class Main extends Application {
	// 顶部
	HBox hBox = new HBox();
	TextField textField = new TextField("");
	Button button = new Button("获取");
	// 左侧列表
	ChapterListView chapterList = new ChapterListView();
	// 右侧tab
//	TabPane tabPane = new TabPane();
	TextArea textArea = new TextArea("双击左侧章节");

	public static void main(String[] args) {

		launch(args);
	}

	@Override
	public void start(Stage primaryStage) {
		try {
			// 初始化数据
			// initFileList();

			// 布局
			BorderPane root = new BorderPane();
			HBox.setHgrow(textField, Priority.ALWAYS);
			hBox.getChildren().addAll(textField, button);

			root.setTop(hBox);
			root.setLeft(chapterList);
			root.setCenter(textArea);

			Scene scene = new Scene(root, 800, 400);
			scene.getStylesheets().add(
					getClass().getResource("application.css").toExternalForm());
			primaryStage.setScene(scene);
			primaryStage.show();

			button.setOnAction(new EventHandler<ActionEvent>() {
				@Override
				public void handle(ActionEvent event) {
					String url = textField.getText();
					if (!url.contains("https://www.dingdiann.com")){
						Alert alert = new Alert(Alert.AlertType.INFORMATION);
//						alert.titleProperty().set("信息");
						alert.headerTextProperty().set("不支持该小说网站");
						alert.showAndWait();
						return;
					}
					try {
						List<Chapter> chapters = getChapter(url);
						showChapterList(chapters);
						System.out.println(chapters);
					} catch (Exception e) {
						e.printStackTrace();
					}

				}
			});
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	private void showChapterList(List<Chapter> chapters ){
		for (Chapter chapter : chapters) {
			chapterList.data().add(chapter);
		}
		chapterList.setOnMouseClicked(new EventHandler<MouseEvent>() {
			@SneakyThrows
			@Override
			public void handle(MouseEvent event) {
				//双击
				if (event.getClickCount() == 2) {
					showArticle(event);
				}
			}
		});
	}

	private void showArticle(MouseEvent event) throws Exception {
		 int index = chapterList.getSelectionModel().getSelectedIndex();
		Chapter chapter = chapterList.data().get(index);
		Article article = WebTxTUtils.getArticle(chapter.getUrl());
		textArea.setText(article.getTitle()+"\n");
		textArea.setWrapText(true);
		String content = article.getContent();
		textArea.appendText(content);
//		String hang = "";
//		for (int i = 0; i < content.length(); i++) {
//			hang+= content.charAt(i);
//			if (i%10==0){
//				textArea.appendText(hang+"\n");
//				hang="";
//			}
//		}
//		textArea.appendText(hang);

	}


	// protected void onFileListDbclicked() {
	// int index = fileList.getSelectionModel().getSelectedIndex();
	// FileItem item = fileList.data().get(index);
	// try {
	// openFile(item);
	// } catch (Exception e) {
	// e.printStackTrace();
	// }
	//
	// }

//	private void openFile(FileItem item) throws Exception {
//		// 查看选项卡是否已经打开
//		Tab tab = findTab(item);
//		if (tab != null) {
//			int tabIndex = tabPane.getTabs().indexOf(tab);
//			tabPane.getSelectionModel().select(tabIndex);
//			return;
//		}
//
//		// 打开一个新选项卡
//		Node contentView = null;
//		if (item.type == FileItem.TEXT) // text file
//		{
//			// 注意: 这里演示的文件是GBK的
//			String text = TextFileUtils.read(item.file, "GBK");
//			TextArea t = new TextArea();
//			t.setText(text);
//			contentView = t;
//		} else if (item.type == FileItem.IMAGE) {
//			Image image = new Image(item.file.toURI().toString());
//			MyImagePane t = new MyImagePane();
//			t.showImage(image);
//			contentView = t;
//		}
//
//		// 添加一个TAB页
//		tab = new Tab();
//		tab.setText(item.fileName);
//		tab.setContent(contentView);
//		tabPane.getTabs().add(tab);
//		int tabIndex = tabPane.getTabs().indexOf(tab);
//		tabPane.getSelectionModel().select(tabIndex);
//
//	}

//	private Tab findTab(FileItem item) {
//		ObservableList<Tab> tabs = tabPane.getTabs();
//		for (int i = 0; i < tabs.size(); i++) {
//			Tab t = tabs.get(i);
//			if (t.getText().equals(item.fileName)) {
//				return t;
//			}
//		}
//		return null;
//	}

	private List<Chapter> getChapter(String url) throws Exception {
		return WebTxTUtils.getChapter(url);
	}

}
