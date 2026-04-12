package com.smartschool.utils;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

public class ExcelDataGenerator {
    
    public static void main(String[] args) throws IOException {
        generateTestDataExcel();
    }

    public static void generateTestDataExcel() throws IOException {
        Workbook workbook = new XSSFWorkbook();
        
        // ─── Create LoginData Sheet ──────────────────────────────────────────────
        createLoginDataSheet(workbook);
        
        // ─── Create MarksData Sheet ──────────────────────────────────────────────
        createMarksDataSheet(workbook);

        // ─── Write to File ──────────────────────────────────────────────────────
        String filePath = "src/test/resources/testdata.xlsx";
        try (FileOutputStream fileOut = new FileOutputStream(filePath)) {
            workbook.write(fileOut);
            System.out.println("✅ Excel file created successfully: " + filePath);
        }
        workbook.close();
    }

    private static void createLoginDataSheet(Workbook workbook) {
        Sheet sheet = workbook.createSheet("LoginData");

        // ─── Create Header Row ───────────────────────────────────────────────────
        Row headerRow = sheet.createRow(0);
        String[] headers = {"TestCase", "StudentID", "Password"};
        
        CellStyle headerStyle = createHeaderStyle(workbook);
        
        for (int i = 0; i < headers.length; i++) {
            Cell cell = headerRow.createCell(i);
            cell.setCellValue(headers[i]);
            cell.setCellStyle(headerStyle);
        }

        // ─── Login Test Data ─────────────────────────────────────────────────────
        List<String[]> loginData = Arrays.asList(
            new String[]{"TC01_ValidLogin", "MYST00636", "10102008"},
            new String[]{"TC02_WrongID", "wrong123", "10102008"},
            new String[]{"TC03_WrongPassword", "MYST00636", "wrongpass"},
            new String[]{"TC04_OnlyStudentID", "MYST00636", ""},
            new String[]{"TC05_OnlyPassword", "", "10102008"}
        );

        int rowNum = 1;
        for (String[] data : loginData) {
            Row row = sheet.createRow(rowNum++);
            for (int i = 0; i < data.length; i++) {
                Cell cell = row.createCell(i);
                cell.setCellValue(data[i]);
            }
        }

        // ─── Auto-size Columns ──────────────────────────────────────────────────
        for (int i = 0; i < headers.length; i++) {
            sheet.autoSizeColumn(i);
        }
    }

    private static void createMarksDataSheet(Workbook workbook) {
        Sheet sheet = workbook.createSheet("MarksData");

        // ─── Create Header Row ───────────────────────────────────────────────────
        Row headerRow = sheet.createRow(0);
        String[] headers = {"TestCase", "ExamName", "ExpectedResult"};
        
        CellStyle headerStyle = createHeaderStyle(workbook);
        
        for (int i = 0; i < headers.length; i++) {
            Cell cell = headerRow.createCell(i);
            cell.setCellValue(headers[i]);
            cell.setCellStyle(headerStyle);
        }

        // ─── Marks Test Data ─────────────────────────────────────────────────────
        List<String[]> marksData = Arrays.asList(
            new String[]{"TC01_MarksNavigation", "", "Marks Page Displayed"},
            new String[]{"TC02_HalfYearly", "Half Yearly", "No Records Found"},
            new String[]{"TC03_Term1", "Term1", "No Records Found"},
            new String[]{"TC04_AnnualExam", "Annual Exam", "No Records Found"}
        );

        int rowNum = 1;
        for (String[] data : marksData) {
            Row row = sheet.createRow(rowNum++);
            for (int i = 0; i < data.length; i++) {
                Cell cell = row.createCell(i);
                cell.setCellValue(data[i]);
            }
        }

        // ─── Auto-size Columns ──────────────────────────────────────────────────
        for (int i = 0; i < headers.length; i++) {
            sheet.autoSizeColumn(i);
        }
    }

    private static CellStyle createHeaderStyle(Workbook workbook) {
        CellStyle headerStyle = workbook.createCellStyle();
        Font headerFont = workbook.createFont();
        headerFont.setBold(true);
        headerFont.setColor(IndexedColors.WHITE.getIndex());
        headerStyle.setFont(headerFont);
        headerStyle.setFillForegroundColor(IndexedColors.BLUE.getIndex());
        headerStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        return headerStyle;
    }
}
