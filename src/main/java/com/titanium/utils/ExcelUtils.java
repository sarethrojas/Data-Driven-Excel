package com.titanium.utils;


import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

public class ExcelUtils {
    //XSSF para .xlsx y HSSF para .xls
    private static XSSFWorkbook excelWBook;
    private static XSSFSheet excelWSheet;
    private static Cell cell;

    public static Object[][] getTableArray(String FilePath, String SheetName) throws IOException {
        String [][] tabArray = null;

        try {
            FileInputStream excelFile = new FileInputStream(FilePath);
            excelWBook = new XSSFWorkbook(excelFile);
            excelWSheet = excelWBook.getSheet(SheetName);
            int startRow, startCol, ci, cj;
            int totalRows = excelWSheet.getLastRowNum();
            int totalCol = excelWSheet.getRow(0).getLastCellNum();

            tabArray = new String[totalRows][totalCol];
            ci = 0;

            for(startRow = 1;startRow<=totalRows;startRow++,ci++){
                cj= 0;

                for (startCol = 0; startCol<totalCol; startCol++,cj++){
                    tabArray[ci][cj]= getCellDataDDT(startRow, startCol);
                }
            }
        }catch (FileNotFoundException fne){
            System.err.println("Class: ExcelUtils | Method: getTableArray | Exception desc: "+ fne.getMessage());

        }catch (IOException ioe){
            System.err.println("Class: ExcelUtils | Method: getTableArray | Exception desc: "+ ioe.getMessage());
        }
        return(tabArray);
    }

    public static String getCellDataDDT(int RowNum, int ColNum){
        String data ="";
        try {
            cell = excelWSheet.getRow(RowNum).getCell(ColNum);
            if (cell.getCellTypeEnum() == CellType.STRING){
                data = cell.getStringCellValue();
            }else if (cell.getCellTypeEnum()== CellType.NUMERIC) {
                data = String.valueOf(cell.getNumericCellValue());
            }
        }catch (Exception ex){
            System.err.println("Class: ExcelUtils | Method: getTableArray | Exception desc: "+ ex.getMessage());
        }
        return data;
    }

}
