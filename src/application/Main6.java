package application;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;

public class Main6 extends Application {

	HBox hBox = new HBox();
	TextField textField = new TextField();
	Button button = new Button("添加");
	TextArea textArea = new TextArea();
	String webSite = "";

	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) {
		try {
			// 頂部
			hBox.getChildren().addAll(textField, button);
			HBox.setHgrow(textField, Priority.ALWAYS);

			// boredPane(边缘布局)里嵌套HBox
			BorderPane root = new BorderPane();
			root.setTop(hBox);
			root.setCenter(textArea);

			Scene scene = new Scene(root, 400, 400);
			scene.getStylesheets().add(
					getClass().getResource("application.css").toExternalForm());
			primaryStage.setScene(scene);
			primaryStage.show();

			button.setOnAction(new EventHandler<ActionEvent>() {
				@Override
				public void handle(ActionEvent event) {
					getChapter();
				}
			});

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void getChapter() {
		webSite = textField.getText();
		
		
		
	}

}
