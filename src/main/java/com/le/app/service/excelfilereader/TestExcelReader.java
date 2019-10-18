package com.le.app.service.excelfilereader;


import com.le.app.model.IrregularVerb;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;

import java.io.IOException;
import java.util.ArrayList;

public class TestExcelReader {

    public static void main(String[] args) throws IOException, InvalidFormatException {
        IrregularVerbsExcelFileReader irregularVerbsExcelFileReader = new IrregularVerbsExcelFileReader();
        ArrayList<IrregularVerb> irregularVerbs;

        irregularVerbs = irregularVerbsExcelFileReader.parseExcelFile();

        System.out.println("Number of irregular verbs =  " + irregularVerbs.size());

    }
}
