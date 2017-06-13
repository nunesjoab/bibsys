package br.com.soo.bibsys.utils;

import org.junit.Test;

import java.io.File;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertEquals;

public class FileOperatorTest {

    private static final String FILE1 = "@ARTICLE{Carnielli:1999,\n  author        = {W.A. Carnielli and J. Marcos},\n}";
    private static final String FILE2 = "@ARTICLE{Carnielli:1999,\n  author        = {W.A. Carnielli and J. Marcos},\n  number        = {3},\n}";
    private static final String FILE3 = "@ARTICLE{Carnielli:1999,\n  author        = {W.A. Carnielli and J. Marcos},\n}\n"+
            "@ARTICLE{Carnielli:1999,\n  author        = {W.A. Carnielli and J. Marcos},\n  number        = {3},\n}";
    private static final String FILE4 = FILE3 + "\n" + FILE3;

    private static final String FILE5 = "@InBook{Cheng.etal:2009,\n}\n" + "@InProceedings{Affonso.etal:2015a,\n}\n" +
            "@InProceedings{Affonso.etal:2016,\n}\n" + "@Article{Affonso.etal:2015,\n}\n" + "@Misc{Bergen:2007,\n}\n";

    private static final String FILE6 = "@ARTICLE{affonso.etal:2015,\n" +
            "title={A Reference Model as Automated Process for Software Adaptation at Runtime},\n" +
            "author={Affonso, F. J. and Carneiro, M. C. V. S. and Rodrigues, E. L. L. and Nakagawa, E. Y.},\n}\n" +
            "@INPROCEEDINGS{affonso.etal:2016,\n" +
            "title={DmS-Modeler: A Tool for Modeling Decision-making Systems for Self-adaptive Software Domain},\n" +
            "author={Affonso, F. J. and Leite, G. and Nakagawa, E. Y.},\n}\n";

    private static final String FILE7 = "@INBOOK{cheng.etal:2009,\n}\n" + "@INPROCEEDINGS{affonso.etal:2015a,\n}\n" +
            "@MISC{bergen:2007,\n}\n";

    private static final String FILE_PATH = "/home/guilherme/Documentos/teste.bib";
    private static final String FILE = "@ARTICLE{Carnielli:1999,\nauthor = {W.A. Carnielli and J. Marcos},\n}\n"
            + "@ARTICLE{6740844,\nauthor={A. Zanella and N. Bui and A. Castellani and L. Vangelista and M. Zorzi},\n}";
    @Test
    public void fileToMapWithOneTagTest() {
        FileOperator comparator = new FileOperator();

        List<Map<String, String>> list = comparator.fileToMap(FILE1);

        assertEquals(1, list.size());
        assertEquals(3, list.get(0).size());
        assertEquals("type", list.get(0).keySet().toArray()[0]);
        assertEquals("ARTICLE", list.get(0).get(list.get(0).keySet().toArray()[0]));
        assertEquals("bibkey", list.get(0).keySet().toArray()[1]);
        assertEquals("carnielli:1999", list.get(0).get(list.get(0).keySet().toArray()[1]));
        assertEquals("author", list.get(0).keySet().toArray()[2]);
        assertEquals("W.A. Carnielli and J. Marcos", list.get(0).get(list.get(0).keySet().toArray()[2]));
    }

    @Test
    public void fileToMapWithTwoTagTest() {
        FileOperator comparator = new FileOperator();

        List<Map<String, String>> list = comparator.fileToMap(FILE2);

        assertEquals(1, list.size());
        assertEquals(4, list.get(0).size());
    }

    @Test
    public void fileToMapTwoRefs() {
        FileOperator comparator = new FileOperator();

        List<Map<String, String>> list = comparator.fileToMap(FILE3);

        assertEquals(2, list.size());
    }

    @Test
    public void fileToMapFourRefs() {
        FileOperator comparator = new FileOperator();

        List<Map<String, String>> list = comparator.fileToMap(FILE4);

        assertEquals(4, list.size());
    }

    @Test
    public void orderFileTest() {
        FileOperator comparator = new FileOperator();

        List<Map<String, String>> refs = comparator.fileToMap(FILE5);
        refs = comparator.orderFile(refs);

        assertEquals("affonso.etal:2015", refs.get(0).get("bibkey"));
        assertEquals("affonso.etal:2015a", refs.get(1).get("bibkey"));
        assertEquals("affonso.etal:2016", refs.get(2).get("bibkey"));
        assertEquals("bergen:2007", refs.get(3).get("bibkey"));
        assertEquals("cheng.etal:2009", refs.get(4).get("bibkey"));
    }

    @Test
    public void mapToFileTest() {
        FileOperator comparator = new FileOperator();

        List<Map<String, String>> refs = comparator.fileToMap(FILE6);
        assertEquals(FILE6, comparator.mapToFile(refs));
    }

    @Test
    public void joinFilesTest() {
        FileOperator fileOperator = new FileOperator();
        assertEquals(FILE4, fileOperator.joinFiles(FILE3, FILE3));
    }

    @Test
    public void convertFileToStringTest() {
        File file = new File(FILE_PATH);
        assertEquals(FILE, FileOperator.convertFileToString(file));
    }

    @Test
    public void getDiffBetweenFilesTest() {
        FileOperator fileOperator = new FileOperator();

        List<Map<String, String>> fileA = fileOperator.fileToMap(FILE5);
        List<Map<String, String>> fileB = fileOperator.fileToMap(FILE6);
        List<Map<String, String>> diffs = fileOperator.getDiffBetweenFiles(fileA, fileB);

        String diffsFile = fileOperator.mapToFile(diffs);

        assertEquals(FILE7, diffsFile);
    }
}
