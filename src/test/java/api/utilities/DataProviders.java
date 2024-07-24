package api.utilities;

import org.testng.annotations.DataProvider;

public class DataProviders {
	
	@DataProvider(name = "allData")
	public String[][] getAllData() {
		ExcelUtil userData = new ExcelUtil(System.getProperty("user.dir") + "//testData//UserData.xlsx", "Users");
		int rowCount = userData.getRowCount();
		System.out.println("Row count is " + rowCount);
		int colCount = userData.getColumnCount();
		System.out.println("Column count is " + colCount);
		String[][] users = new String[rowCount-1][colCount];
		for(int i = 1; i < rowCount; i++) {
			for(int j = 0; j < colCount; j++) {
				users[i-1][j] = userData.getCellData(i, j);
			}
		}
		userData.closeWorkbook();
		return users;
	}
	
	@DataProvider(name = "usernames")
	public String[] getUsernames() {
		ExcelUtil userData = new ExcelUtil(System.getProperty("user.dir") + "//testData//UserData.xlsx", "Users");
		int rowCount = userData.getRowCount();
		String[] users = new String[rowCount-1];
		for(int i = 1; i < rowCount; i++) {
				users[i-1] = userData.getCellData(i, 1);
		}
		userData.closeWorkbook();
		return users;
	} 
}
