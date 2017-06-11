package br.com.soo.bibsys.utils;

import org.junit.Test;

import java.io.File;

import static org.junit.Assert.assertEquals;

public class FileOperatorTest {

    private static final String FILE_PATH = "/home/guilherme/Documentos/teste.bib";
    private static final String FILE = "@ARTICLE{Carnielli:1999,\nauthor = {W.A. Carnielli and J. Marcos},\n}\n"
            + "@ARTICLE{6740844,\nauthor={A. Zanella and N. Bui and A. Castellani and L. Vangelista and M. Zorzi},\n}";

    @Test
    public void convertFileToStringTest() {
        File file = new File(FILE_PATH);
        assertEquals(FILE, FileOperator.convertFileToString(file));
    }
}
