package com.jdev;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class ExcelTest {

    private static final Map<Language, Map<String, String>> LANGUAGE_MAP = new HashMap<>();

    static {
        Map<String, String> forTranslationEn = new HashMap<>();
        forTranslationEn.put("translation_generated_datetime", "Generated date time");
        forTranslationEn.put("translation_generated_password", "Generated password");
        forTranslationEn.put("trans_gen_passw_excel_sheetname", "Generated password");


        Map<String, String> forTranslationRu = new HashMap<>();
        forTranslationRu.put("translation_generated_datetime", "Дата/время сгенирированного пароля");
        forTranslationRu.put("translation_generated_password", "Сгенирированный пароль");
        forTranslationRu.put("trans_gen_passw_excel_sheetname", "Сгенирированный пароль");

        LANGUAGE_MAP.put(Language.EN, forTranslationEn);
        LANGUAGE_MAP.put(Language.RU, forTranslationRu);
    }

    @BeforeEach
    @AfterEach
    private void beforeAndAfterEach() throws IOException {
        Files.copy(Common.getPath("excel_template_original.xlsx"),
                Common.getPath("excel_template_for_test_error.xlsx"),
                StandardCopyOption.REPLACE_EXISTING);

        Files.copy(Common.getPath("excel_template_original.xlsx"),
                Common.getPath("excel_template_for_test_success.xlsx"),
                StandardCopyOption.REPLACE_EXISTING);
    }

    @Test
    void test__error__excel_sheet_name_limit_31_symbols() {
        Workbook workbook = null;
        try {
            workbook = new XSSFWorkbook(Common.createPath("excel_template_for_test_error.xlsx"));
        } catch (IOException e) {
            System.out.println(e);
        }

        final Sheet sheet = workbook.getSheetAt(0);
        /*
        * https://excelribbon.tips.net/T013175_Using_Very_Long_Worksheet_Tab_Names.html#:~:text=Excel%2C%20however%2C%20seems%20to%20limit,into%20Excel%20at%2031%20characters.
        * Excel, however, seems to limit those names to only 30 characters, so he wonders if there is a way to exceed that limit.
The short answer is no, you cannot change the limit on worksheet name length. It is hard coded into Excel at 31 characters
        * */
        String sheetName = sheet.getSheetName();
        Assertions.assertEquals(31, sheetName.length());
        final Workbook finalWorkbook = workbook;
        final String newNameForSheet = UUID.randomUUID() + "_" + UUID.randomUUID();
        Assertions.assertEquals(36 * 2 + 1, newNameForSheet.length());
        finalWorkbook.setSheetName(0, newNameForSheet);

        try (ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream()) {
            workbook.write(byteArrayOutputStream);
            workbook.close();
            Path path = Common.getPath("excel_template_for_test_error_output.xlsx");
            Files.write(path, byteArrayOutputStream.toByteArray());
            System.out.println("File path - " + path.toFile().getAbsolutePath());

            workbook = new XSSFWorkbook(Common.createPath("excel_template_for_test_error_output.xlsx"));
            final String sheetNameAfterSave = workbook.getSheetAt(0).getSheetName();
            Assertions.assertEquals(31, sheetNameAfterSave.length());
            Assertions.assertEquals(newNameForSheet.substring(0, 31), sheetNameAfterSave);
        } catch (IOException e) {
            System.out.println(e);
        }
    }

    @ParameterizedTest
    @EnumSource(value = Language.class)
    void test__success_byLanguage(Language language) throws IOException {
        Workbook workbook = null;
        try {
            workbook = new XSSFWorkbook(Common.createPath("excel_template_for_test_success.xlsx"));
        } catch (IOException e) {
            System.out.println(e);
        }

        final Sheet sheet = workbook.getSheetAt(0);
        String sheetName = sheet.getSheetName();
        workbook.setSheetName(0, getValueByLanguage(language, sheetName));

        Row headerRow = sheet.getRow(0);
        Cell cell = headerRow.getCell(0);
        cell.setCellValue(getValueByLanguage(language, cell.getStringCellValue()));

        cell = headerRow.getCell(1);
        cell.setCellValue(getValueByLanguage(language, cell.getStringCellValue()));

        Row dataRow = sheet.createRow(1);
        dataRow.createCell(0).setCellValue(LocalDateTime.now().toString());
        dataRow.createCell(1).setCellValue(UUID.randomUUID().toString());
        try (ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream()) {
            workbook.write(byteArrayOutputStream);
            workbook.close();
            Path path = Common.getPath("excel_template_output_" + language.name() + ".xlsx");
            Files.write(path, byteArrayOutputStream.toByteArray());
            System.out.println("File path - " + path.toFile().getAbsolutePath());
        } catch (IOException e) {
            System.out.println(e);
        }

        byte[] bytesOutput = Files.readAllBytes(Common.getPath("excel_template_output_" + language.name() + ".xlsx"));
        byte[] bytesSuccessInput = Files.readAllBytes(Common.getPath("excel_template_for_test_success.xlsx"));

        Assertions.assertEquals(bytesOutput.length, bytesSuccessInput.length);
        final int length = bytesOutput.length;
        for (int i = 0; i < length; i++) {
            Assertions.assertEquals(bytesOutput[i], bytesSuccessInput[i]);
        }
//        Assertions.assertEquals(bytesOutput, bytesSuccessInput);
//        Assertions.assertTrue(bytesOutput.equals(bytesSuccessInput));
    }

    private static String getValueByLanguage(Language language, String translationCode) {
        return LANGUAGE_MAP.get(language).get(translationCode);
    }

    private enum Language {
        EN,
        RU;
    }

}
