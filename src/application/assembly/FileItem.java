package application.assembly;

import java.io.File;

/**
 * 文件项
 * 
 */
public class FileItem
{

	public String fileName;
	public File file;
	public int type = BAD_FORMAT; // 1, 文本文件; 2,图片文件; -1, 不支持的文件类型

	// 文件类型常量
	public static final int TEXT = 1;
	public static final int IMAGE = 2;
	public static final int BAD_FORMAT = -1;

	private final String[] txtTypes =
	{ "txt", "java" };
	private final String[] imageTypes =
	{ "jpg", "jpeg", "png", "bmp" };

	public FileItem()
	{
		super();
	}

	public FileItem(File file)
	{
		this.file = file;
		// 取得文件名
		fileName = file.getName();

		// 根据文件后缀来判断文件的类型
		String suffix = getFileSuffix(fileName);
		type = BAD_FORMAT;
		if (contains(txtTypes, suffix))
			type = TEXT;
		else if (contains(imageTypes, suffix))
			type = IMAGE;
	}

	/**
	 * 判断是是否包含支持的格式
	 * 
	 * @param types  支持的种类
	 * @param suffix 文件后缀
	 * @return
	 */
	private boolean contains(String[] types, String suffix)
	{
		suffix = suffix.toLowerCase(); // 统一转成小写
		for (String s : types)
		{
			if (s.equals(suffix))
				return true;
		}
		return false;
	}

	/**
	 * 获取文件后缀
	 * 
	 * @param name 文件名
	 * @return
	 */
	private String getFileSuffix(String name)
	{
		int pos = name.lastIndexOf('.');
		if (pos > 0)
			return name.substring(pos + 1);
		return ""; // 无后缀文件
	}

}
