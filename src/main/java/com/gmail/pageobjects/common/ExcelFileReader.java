package com.gmail.pageobjects.common;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ExcelFileReader {

	private final static Logger LOG = LoggerFactory.getLogger(ExcelFileReader.class);
	private static final String IOEXCEPTION = "IOExeption while getting value from excel";
	private static final String GMAIL_USERS_XLSX = System.getProperty("gmail.users.xls.path");
	private static final int ADDITIONAL_USERNAME_COLUMN_NUMBER = 2;
	private static final int USER_PASSWORD_COLUMN_NUMBER = 1;
	private static final String EMAILS_SHEET_NAME = "Emails";
	private static final int EMAILS_PASSWORD_COLUMN_NUMBER = 1;
	private static final String USER_SHEET_NAME = "Users";

	private static List<Row> excelPasswords;

	public static String getGmailPassword(String userName) {
		loadDataIntoMap();
		try {
			return getRowByUsername(userName).getCell(USER_PASSWORD_COLUMN_NUMBER).getStringCellValue();
		} catch (Exception e) {
			LOG.warn(String.format("No password has found in excel sheet for the given name:%s .Error: %s", userName,
					e.getMessage()));
			return "";
		}
	}

	public static String getEmailPassword(String email) {
		return getDataFromGmailUsers(EMAILS_SHEET_NAME, email, EMAILS_PASSWORD_COLUMN_NUMBER);
	}

	private static String getDataFromGmailUsers(String sheetName, String firstColumnValue, int columnNumber) {
		for (int i = 0; i < 2; i++) {
			try {
				return getData(sheetName, firstColumnValue, columnNumber);
			} catch (Exception e) {
				LOG.error("Error reading data from excel", e);
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e1) {
					e1.printStackTrace();
				}
			}
		}
		return "";
	}

	private static synchronized String getData(String sheetName, String firstColumnValue, int columnNumber) {
		String dataFromCell = null;
		try {
			FileInputStream file = new FileInputStream(GMAIL_USERS_XLSX);
			XSSFWorkbook workbook = new XSSFWorkbook(file);
			XSSFSheet sheet = workbook.getSheet(sheetName);
			Row row = sheet.getRow(sheet.getLastRowNum());
			dataFromCell = row.getCell(columnNumber).toString();
			return dataFromCell;
		} catch (IOException e) {
			LOG.error(IOEXCEPTION, e);
		}
		return dataFromCell;

	}

	public static synchronized List<Row> getDataFromExcel() {
		List<Row> passwords = new ArrayList<Row>();
		try {
			FileInputStream file = new FileInputStream(GMAIL_USERS_XLSX);
			XSSFWorkbook workbook = new XSSFWorkbook(file);
			XSSFSheet sheet = workbook.getSheet(USER_SHEET_NAME);
			int rowsCount = sheet.getLastRowNum();
			for (int i = 1; i <= rowsCount; i++) {
				Row row = sheet.getRow(i);
				passwords.add(row);
			}
		} catch (IOException e) {
			LOG.error(IOEXCEPTION, e);
			passwords = new ArrayList<Row>();
		}
		return passwords;
	}

	private static void loadDataIntoMap() {
		for (int i = 0; i < 2; i++) {
			try {
				if (excelPasswords == null || excelPasswords.size() == 0) {
					excelPasswords = getDataFromExcel();
				}
				break;
			} catch (Exception e) {
				LOG.error("Error reading data from excel");
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e1) {
					e.printStackTrace();
				}
			}
		}
	}

	private static Row getRowByUsername(String userName) {
		for (Row row : excelPasswords) {
			if (row.getCell(0).getStringCellValue().equalsIgnoreCase(userName)
					|| getCellValue(row, ADDITIONAL_USERNAME_COLUMN_NUMBER).equalsIgnoreCase(userName)) {
				return row;
			}
		}
		throw new RuntimeException(String.format("No row is users excel for user '%s'", userName));
	}

	private static String getCellValue(Row row, int cellNum) {
		Cell cell = row.getCell(cellNum);
		return cell != null ? cell.getStringCellValue() : "";
	}
}
