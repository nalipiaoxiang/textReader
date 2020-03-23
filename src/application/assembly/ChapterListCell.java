package application.assembly;

import java.util.Objects;

import application.entity.Chapter;
import application.utils.StringUtils;
import javafx.scene.control.ListCell;

/**
 * 负责单元格Cell的显示
 *
 */
public class ChapterListCell extends ListCell<Chapter> {
	@Override
	public void updateItem(Chapter chapter, boolean empty) {
		// FX框架要求必须先调用 super.updateItem()
		super.updateItem(chapter, empty);

		if (Objects.isNull(chapter)) {
			this.setText("");
		} else {
			this.setText(chapter.getName());
		}
	}
}
