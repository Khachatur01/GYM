package com.fitness.Service.Report;

import com.fitness.Model.Report.Report;
import com.fitness.Model.Report.Salary;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFormulaEvaluator;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.ss.util.RegionUtil;

import java.util.List;

public class Excel {
    private List<Report> reports;
    public Excel(List<Report> reports) {
        this.reports = reports;
    }
    public HSSFWorkbook getReportByEmployment() {
        HSSFWorkbook workbook = new HSSFWorkbook();
        HSSFSheet sheet = workbook.createSheet("Հաշվետվություն");
        HSSFCellStyle centerAlignment = workbook.createCellStyle();
        centerAlignment.setAlignment(HorizontalAlignment.CENTER);
        centerAlignment.setVerticalAlignment(VerticalAlignment.CENTER);
        centerAlignment.setBorderBottom(BorderStyle.THIN);
        centerAlignment.setBorderLeft(BorderStyle.THIN);
        centerAlignment.setBorderRight(BorderStyle.THIN);
        centerAlignment.setBorderTop(BorderStyle.THIN);

        sheet.addMergedRegion(CellRangeAddress.valueOf("A1:F1"));
        sheet.addMergedRegion(CellRangeAddress.valueOf("A2:A3"));
        sheet.addMergedRegion(CellRangeAddress.valueOf("B2:B3"));
        sheet.addMergedRegion(CellRangeAddress.valueOf("C2:C3"));
        sheet.addMergedRegion(CellRangeAddress.valueOf("D2:D3"));

        Row headerFirst = sheet.createRow(1);
        Row headerSecond = sheet.createRow(2);

        Cell employmentHeaderCell = headerFirst.createCell(0, CellType.STRING);
        employmentHeaderCell.setCellValue("Ծառայություն");

        Cell employeeHeaderCell = headerFirst.createCell(1, CellType.STRING);
        employeeHeaderCell.setCellValue("Աշխատող");

        Cell quantityHeaderCell = headerFirst.createCell(2, CellType.STRING);
        quantityHeaderCell.setCellValue("Քանակ");

        Cell priceHeaderCell = headerFirst.createCell(3, CellType.STRING);
        priceHeaderCell.setCellValue("Գումար");

        Cell totalHeaderCell = headerFirst.createCell(4, CellType.STRING);
        totalHeaderCell.setCellValue("Ընդհանուր");

        sheet.addMergedRegion(CellRangeAddress.valueOf("E2:F2"));

        Cell totalQuantityHeaderCell = headerSecond.createCell(4);
        totalQuantityHeaderCell.setCellValue("Քանակ");

        Cell totalPriceHeaderCell = headerSecond.createCell(5);
        totalPriceHeaderCell.setCellValue("Գումար");

        int rowNumber = 3;
        int nextRowNumber = rowNumber;

        for(Report report: this.reports) {
            for(Salary salary: report.getSalaries()) {
                Row nextRow = sheet.createRow(nextRowNumber++);
                Cell employeeCell = nextRow.createCell(1, CellType.STRING);
                employeeCell.setCellValue(salary.getEmployee().getFullName());

                Cell quantityCell = nextRow.createCell(2, CellType.NUMERIC);
                quantityCell.setCellValue(salary.getQuantity());

                Cell priceCell = nextRow.createCell(3, CellType.NUMERIC);
                priceCell.setCellValue(salary.getPrice());
            }

            nextRowNumber--;
            if(nextRowNumber != rowNumber) {
                sheet.addMergedRegion(new CellRangeAddress(rowNumber, nextRowNumber, 0, 0));
                sheet.addMergedRegion(new CellRangeAddress(rowNumber, nextRowNumber, 4, 4));
                sheet.addMergedRegion(new CellRangeAddress(rowNumber, nextRowNumber, 5, 5));
            }

            Cell employmentCell = sheet.getRow(rowNumber).createCell(0, CellType.STRING);
            employmentCell.setCellValue(report.getEmployment().toString());

            Cell totalQuantityCell = sheet.getRow(rowNumber).createCell(4, CellType.FORMULA);
            totalQuantityCell.setCellFormula("SUM(C" + (rowNumber + 1) + ":C" + (nextRowNumber + 1) + ")");

            Cell totalPriceCell = sheet.getRow(rowNumber).createCell(5, CellType.FORMULA);
            totalPriceCell.setCellFormula("SUM(D" + (rowNumber + 1) + ":D" + (nextRowNumber + 1) + ")");

            nextRowNumber++;
            rowNumber = nextRowNumber;
        }

        Cell totalQuantityCell = sheet.createRow(rowNumber + 1).createCell(4, CellType.FORMULA);
        totalQuantityCell.setCellFormula("SUM(E" + 4 + ":E" + (nextRowNumber) + ")");

        Cell totalPriceCell = sheet.getRow(rowNumber + 1).createCell(5, CellType.FORMULA);
        totalPriceCell.setCellFormula("SUM(F" + 4 + ":F" + (nextRowNumber) + ")");


        for(int row = 0; row < nextRowNumber; row++) {
            Row currentRow = sheet.getRow(row);
            if(currentRow == null)
                currentRow = sheet.createRow(row);
            for(int col = 0; col < 6; col++) {
                Cell currentCell = currentRow.getCell(col);
                if(currentCell == null)
                    currentCell = currentRow.createCell(col);

                currentCell.setCellStyle(centerAlignment);
            }
        }

        for(int col = 0; col < 6; col++)
            sheet.autoSizeColumn(col, true);

        HSSFFormulaEvaluator.evaluateAllFormulaCells(workbook);
        return workbook;
    }
}
