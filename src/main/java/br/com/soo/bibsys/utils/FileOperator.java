package br.com.soo.bibsys.utils;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;

public class FileOperator {

    private static final String FILE_ENCODING = "UTF-8";

    public static String convertFileToString(File file) {
        String fileString = "";

        try {
            fileString = FileUtils.readFileToString(file, FILE_ENCODING);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return fileString;
    }

    public static void writeStringIntoFile(String file) {

    }
}
