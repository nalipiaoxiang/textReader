package application.assembly;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.util.Callback;

public class FileListView extends ListView<FileItem>
{
	// 数据源
	private ObservableList<FileItem> listData = FXCollections.observableArrayList();

	public FileListView()
	{
		// 设置数据源
		setItems(listData);

		// 设置单元格生成器 （工厂）
		setCellFactory(new Callback<ListView<FileItem>, ListCell<FileItem>>()
		{
			@Override
			public ListCell<FileItem> call(ListView<FileItem> param)
			{
				return new MyListCell();
			}
		});
	}

	public ObservableList<FileItem> data()
	{
		return listData;
	}

}
