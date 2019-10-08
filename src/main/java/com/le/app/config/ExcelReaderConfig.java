package com.le.app.config;

import com.le.app.service.ExcelFileReader.IrregularVerbsExcelFileReader;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ExcelReaderConfig {
    @Bean(name = "excelReader")
    public IrregularVerbsExcelFileReader getExcelReader() {
        return new IrregularVerbsExcelFileReader();
    }
}