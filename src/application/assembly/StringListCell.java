package application.assembly;

import application.utils.StringUtils;
import javafx.scene.control.ListCell;

/**
 * 负责单元格Cell的显示
 *
 */
public class StringListCell extends ListCell<String>
{
	@Override
	public void updateItem(String string, boolean empty)
	{
		// FX框架要求必须先调用 super.updateItem()
		super.updateItem(string, empty);

		if (StringUtils.isBlank(string))
		{
			this.setText("");
		} else
		{
			this.setText(string);
		}
	}
}
