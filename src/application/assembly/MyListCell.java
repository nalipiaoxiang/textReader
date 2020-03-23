package application.assembly;

import javafx.scene.control.ListCell;

/**
 * 负责单元格Cell的显示
 *
 */
public class MyListCell extends ListCell<FileItem>
{
	@Override
	public void updateItem(FileItem item, boolean empty)
	{
		// FX框架要求必须先调用 super.updateItem()
		super.updateItem(item, empty);

		// 自己的代码
		if (item == null)
		{
			this.setText("");
		} else
		{
			this.setText(item.fileName);
		}
	}
}
