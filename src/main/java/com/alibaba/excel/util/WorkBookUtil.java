package com.alibaba.excel.util;

import java.io.IOException;

import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.alibaba.excel.support.ExcelTypeEnum;
import com.alibaba.excel.write.metadata.holder.WriteWorkbookHolder;

/**
 *
 * @author jipengfei
 */
public class WorkBookUtil {

    private static final int ROW_ACCESS_WINDOW_SIZE = 500;

    private WorkBookUtil() {}

    public static void createWorkBook(WriteWorkbookHolder writeWorkbookHolder) throws IOException {
        if (ExcelTypeEnum.XLSX.equals(writeWorkbookHolder.getExcelType())) {
            if (writeWorkbookHolder.getTempTemplateInputStream() != null) {
                XSSFWorkbook xssfWorkbook = new XSSFWorkbook(writeWorkbookHolder.getTempTemplateInputStream());
                writeWorkbookHolder.setCachedWorkbook(xssfWorkbook);
                writeWorkbookHolder.setWorkbook(new SXSSFWorkbook(xssfWorkbook, ROW_ACCESS_WINDOW_SIZE));
                return;
            }
            SXSSFWorkbook sxssWorkbook = new SXSSFWorkbook(ROW_ACCESS_WINDOW_SIZE);
            writeWorkbookHolder.setCachedWorkbook(sxssWorkbook);
            writeWorkbookHolder.setWorkbook(sxssWorkbook);
            return;
        }
        HSSFWorkbook hssfWorkbook;
        if (writeWorkbookHolder.getTempTemplateInputStream() != null) {
            hssfWorkbook = new HSSFWorkbook(new POIFSFileSystem(writeWorkbookHolder.getTempTemplateInputStream()));
        } else {
            hssfWorkbook = new HSSFWorkbook();
        }
        writeWorkbookHolder.setCachedWorkbook(hssfWorkbook);
        writeWorkbookHolder.setWorkbook(hssfWorkbook);
    }

    public static Sheet createSheet(Workbook workbook, String sheetName) {
        return workbook.createSheet(sheetName);
    }

    public static Row createRow(Sheet sheet, int rowNum) {
        return sheet.createRow(rowNum);
    }

    public static Cell createCell(Row row, int colNum) {
        return row.createCell(colNum);
    }

    public static Cell createCell(Row row, int colNum, CellStyle cellStyle) {
        Cell cell = row.createCell(colNum);
        cell.setCellStyle(cellStyle);
        return cell;
    }

    public static Cell createCell(Row row, int colNum, CellStyle cellStyle, String cellValue) {
        Cell cell = createCell(row, colNum, cellStyle);
        cell.setCellValue(cellValue);
        return cell;
    }

    public static Cell createCell(Row row, int colNum, String cellValue) {
        Cell cell = row.createCell(colNum);
        cell.setCellValue(cellValue);
        return cell;
    }
}
