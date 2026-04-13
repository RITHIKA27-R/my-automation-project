package com.smartschool.utils;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ExcelUtils {

    private static final String FILE_PATH =
        "src/test/resources/testdata.xlsx";

    // ✅ EXISTING — தொடாத!
    public static List<Map<String, String>> getTestData(String sheetName) {
        List<Map<String, String>> data = new ArrayList<>();
        try (FileInputStream fis = new FileInputStream(FILE_PATH);
             Workbook workbook = new XSSFWorkbook(fis)) {

            Sheet sheet = workbook.getSheet(sheetName);
            if (sheet == null) {
                System.out.println("Sheet not found: " + sheetName);
                return data;
            }

            Row headerRow = sheet.getRow(0);
            List<String> headers = new ArrayList<>();
            for (Cell cell : headerRow) {
                headers.add(cell.getStringCellValue().trim());
            }

            for (int i = 1; i <= sheet.getLastRowNum(); i++) {
                Row row = sheet.getRow(i);
                if (row == null) continue;

                Map<String, String> rowData = new HashMap<>();
                for (int j = 0; j < headers.size(); j++) {
                    Cell cell = row.getCell(j);
                    String value = "";
                    if (cell != null) {
                        switch (cell.getCellType()) {
                            case STRING:  value = cell.getStringCellValue().trim(); break;
                            case NUMERIC: value = String.valueOf((long) cell.getNumericCellValue()); break;
                            case BOOLEAN: value = String.valueOf(cell.getBooleanCellValue()); break;
                            default:      value = "";
                        }
                    }
                    rowData.put(headers.get(j), value);
                }
                data.add(rowData);
            }

        } catch (Exception e) {
            System.out.println("Excel read error: " + e.getMessage());
        }
        return data;
    }

    // ✅ EXISTING — தொடாத!
    public static Map<String, String> getRowByTestCase(String sheetName, String testCaseName) {
        List<Map<String, String>> allData = getTestData(sheetName);
        for (Map<String, String> row : allData) {
            if (testCaseName.equals(row.get("TestCase"))) {
                return row;
            }
        }
        return new HashMap<>();
    }

    // ✅ NEW — இதை மட்டும் ADD பண்ணு!
    public static List<Map<String, String>> getAllRows(String sheetName) {
        return getTestData(sheetName); // getTestData-யே reuse பண்றோம்!
    }
}