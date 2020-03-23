package application.assembly;

import application.entity.Chapter;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.util.Callback;

public class ChapterListView extends ListView<Chapter> {
	// 数据源
	private ObservableList<Chapter> listData = FXCollections
			.observableArrayList();

	public ChapterListView() {
		// 设置数据源
		setItems(listData);

		// 设置单元格生成器 （工厂）
		setCellFactory(new Callback<ListView<Chapter>, ListCell<Chapter>>() {
			@Override
			public ListCell<Chapter> call(ListView<Chapter> param) {
				return new ChapterListCell();
			}
		});
	}

	public ObservableList<Chapter> data() {
		return listData;
	}

}
