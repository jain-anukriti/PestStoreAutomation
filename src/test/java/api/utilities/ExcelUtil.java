package api.utilities;

import java.io.IOException;

import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ExcelUtil {

//	public FileInputStream fi;
//	public FileOutputStream fo;
	public XSSFWorkbook workbook;
	public XSSFSheet sheet;
	public XSSFRow row;
	public XSSFCell cell;
	public CellStyle style;
	//public String path;
	
public ExcelUtil(String path, String sheetName) {
	//fi = new FileInputStream(path);
	try {
	workbook = new XSSFWorkbook(path);
	this.sheet = workbook.getSheet(sheetName);
	}
	catch(IOException e) {
		System.out.println("Problem occured when reading excel. \n" + e.getStackTrace());
	}
}

public int getRowCount() {
//	workbook = new XSSFWorkbook(path);
//	sheet = workbook.getSheet(sheetName);
	return sheet.getPhysicalNumberOfRows();
}
	
public int getColumnCount() {
	return sheet.getRow(0).getPhysicalNumberOfCells();
}

public String getCellData(int rowNum, int columnNum) {
	DataFormatter df = new DataFormatter();
	String cellvalue = df.formatCellValue(sheet.getRow(rowNum).getCell(columnNum));
	System.out.println("Value in target cell is " + cellvalue);
	return cellvalue;
}

public void setCellData(int rowNum, int columnNum, String cellData) {
	if(sheet.getRow(rowNum) == null) {
		row = sheet.createRow(rowNum);
		cell = row.createCell(columnNum);
	}
	else {
		cell = sheet.getRow(rowNum).getCell(columnNum);
	}
	cell.setCellValue(cellData);
}


public void closeWorkbook() {
	try {
		this.workbook.close();
	} catch (IOException e) {
		System.out.println("Problem occured when closing excel. \n" + e.getStackTrace());
	}
}
	
}
