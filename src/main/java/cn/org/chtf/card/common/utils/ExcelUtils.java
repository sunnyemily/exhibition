package cn.org.chtf.card.common.utils;

import java.io.File;
import java.io.FileOutputStream;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;

public class ExcelUtils {

	// 生成excel文件
	public static void buildExcelFile(String filename, HSSFWorkbook workbook) throws Exception {
		String filePath = "./static/excel/";
		File targetFile = new File(filePath);
		if (!targetFile.exists()) targetFile.mkdirs();
		FileOutputStream fos = new FileOutputStream(filePath + filename);
		workbook.write(fos);
		fos.flush();
		fos.close();
	}
	
}
