package com.le.app.util.excelfilereader;

import com.le.app.model.IrregularVerb;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.*;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class IrregularVerbsExcelFileReader {
    public static final String FILE_PATH = "./InitialData.xlsx"; //Todo redo this file should be upload from frontent

    public ArrayList<IrregularVerb> parseExcelFile() throws IOException, InvalidFormatException {

        // Creating a Workbook from an Excel file (.xls or .xlsx)
        Workbook workbook = WorkbookFactory.create(new File(FILE_PATH));

        // Retrieving the number of sheets in the Workbook
        System.out.println("Workbook has " + workbook.getNumberOfSheets() + " Sheets : ");

        /*
           =============================================================
           Iterating over all the sheets in the workbook (Multiple ways)
           =============================================================
        */

        System.out.println("Retrieving Sheets name using Java 8 forEach with lambda");
        workbook.forEach(sheet -> {
            System.out.println("=> " + sheet.getSheetName());
        });

        /*
           ==================================================================
           Iterating over all the rows and columns in a Sheet (Multiple ways)
           ==================================================================
        */

        //Getting the Sheet at index zero
        Sheet sheet = workbook.getSheet("IrregularVerbs");

        // Create a DataFormatter to format and get each cell's value as String
        DataFormatter dataFormatter = new DataFormatter();


        // Iterating over the rows and columns
        System.out.println("\n\nIterating over Rows and Columns using for-each loop\n");

        ArrayList<IrregularVerb> irregularVerbs = new ArrayList<>();

        System.out.println("\n\nIterating over Rows and Columns using Java 8 forEach with lambda\n");
        sheet.forEach(row -> {
            ArrayList<String> irregularVerbsProperties = new ArrayList<>();
            row.forEach(cell -> {
                String cellValue = dataFormatter.formatCellValue(cell);
                System.out.print(cellValue + "\t");
                irregularVerbsProperties.add(cellValue);
            });
            irregularVerbs.add(createIrregularVerb(irregularVerbsProperties));
            System.out.println();
        });

        // Closing the workbook
        workbook.close();
        // remove the header of the table
        irregularVerbs.remove(0);
        return irregularVerbs;
    }

    private IrregularVerb createIrregularVerb(ArrayList<String> irregularVerbsProperties) {
        IrregularVerb irregularVerb = new IrregularVerb();

        irregularVerb.setInfinitive(irregularVerbsProperties.get(0));
        irregularVerb.setTranscriptionInfinitive(irregularVerbsProperties.get(1));
        irregularVerb.setPastTense(irregularVerbsProperties.get(2));
        irregularVerb.setTranscriptionPastTense(irregularVerbsProperties.get(3));
        irregularVerb.setPastParticiple(irregularVerbsProperties.get(4));
        irregularVerb.setTranscriptionPastParticiple(irregularVerbsProperties.get(5));
        irregularVerb.setTranslation(irregularVerbsProperties.get(6));

        return irregularVerb;
    }

}

