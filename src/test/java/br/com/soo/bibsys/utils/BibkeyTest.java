package br.com.soo.bibsys.utils;

import static org.junit.Assert.assertEquals;
import org.junit.Test;

import java.util.List;
import java.util.Map;

public class BibkeyTest {

    private static final String AUTHOR_ONE_NAME = "Affonso, F. J.";
    private static final String AUTHORS_TWO_NAMES = "Affonso, F. J. and Leite, G.";
    private static final String AUTHORS_THREE_NAMES = "Affonso, F. J. and Leite, G. and Nakagawa, E. Y.";

    private static final String YEAR = "2017";
    private static final String GENERATED_BIBKEY_ONE_NAME = "affonso:2017";
    private static final String GENERATED_BIBKEY_TWO_NAMES = "affonso.leite:2017";
    private static final String GENERATED_BIBKEY_THREE_NAMES = "affonso.etal:2017";

    private static final String FILE_BIB = "@ARTICLE{6740844,\nauthor={Affonso, F. J.},\n" +
            "year={2017},\n}";
    private static final String FILE_BIB2 = FILE_BIB + "\n" + FILE_BIB;
    private static final String FILE_BIB3 = FILE_BIB2 + "\n" + FILE_BIB;

    private static final String FILE_BIB4 = "@InBook{Cheng.etal:2009,\n}" + "@InProceedings{Affonso.etal:2015a,\n}" +
            "@InProceedings{Affonso.etal:2016,\n}" + "@Article{Affonso.etal:2015,\n}" + "@Misc{Bergen:2007,\n}";

    @Test
    public void splitAuthorNameTest() {
        Bibkey bibkey = new Bibkey();

        String[] s = bibkey.splitAuthorName(AUTHOR_ONE_NAME);
        assertEquals(3, s.length);
        assertEquals("Affonso", s[0]);
    }

    @Test
    public void splitAuthorsTest() {
        Bibkey bibkey = new Bibkey();

        String[] s = bibkey.splitAuthors(AUTHORS_TWO_NAMES);
        assertEquals(2, s.length);

        s = bibkey.splitAuthors(AUTHORS_THREE_NAMES);
        assertEquals(3, s.length);
    }

    @Test
    public void generateBibkeyTest() {
        Bibkey bibkey = new Bibkey();

        assertEquals(GENERATED_BIBKEY_ONE_NAME, bibkey.generateBibkey(AUTHOR_ONE_NAME, YEAR));
        assertEquals(GENERATED_BIBKEY_TWO_NAMES, bibkey.generateBibkey(AUTHORS_TWO_NAMES, YEAR));
        assertEquals(GENERATED_BIBKEY_THREE_NAMES, bibkey.generateBibkey(AUTHORS_THREE_NAMES, YEAR));
    }

    @Test
    public void editBibkeysTest() {
        Bibkey bibkey = new Bibkey();
        FileOperator comparator = new FileOperator();

        assertEquals("affonso:2017", bibkey.editBibkeys(comparator.fileToMap(FILE_BIB)).get(0).get("bibkey"));

        assertEquals("affonso:2017", bibkey.editBibkeys(comparator.fileToMap(FILE_BIB2)).get(0).get("bibkey"));
        assertEquals("affonso:2017a", bibkey.editBibkeys(comparator.fileToMap(FILE_BIB2)).get(1).get("bibkey"));

        List<Map<String, String>> list3 = bibkey.editBibkeys(comparator.fileToMap(FILE_BIB3));
        assertEquals("affonso:2017", list3.get(0).get("bibkey"));
        assertEquals("affonso:2017a", list3.get(1).get("bibkey"));
        assertEquals("affonso:2017b", list3.get(2).get("bibkey"));
    }

    @Test
    public void getBibkeysFromFileTest() {
        FileOperator comparator = new FileOperator();
        Bibkey bibkey = new Bibkey();

        List<Map<String, String>> refs = comparator.fileToMap(FILE_BIB4);
        List<String> bibkeys = bibkey.getBibkeysFromFile(refs);

        assertEquals("cheng.etal:2009", bibkeys.get(0));
        assertEquals("affonso.etal:2015a", bibkeys.get(1));
        assertEquals("affonso.etal:2016", bibkeys.get(2));
        assertEquals("affonso.etal:2015", bibkeys.get(3));
        assertEquals("bergen:2007", bibkeys.get(4));
    }

    @Test
    public void orderBibkeysTest() {
        FileOperator comparator = new FileOperator();
        Bibkey bibkey = new Bibkey();

        List<Map<String, String>> refs = comparator.fileToMap(FILE_BIB4);
        List<String> bibkeys = bibkey.getBibkeysFromFile(refs);

        bibkeys = bibkey.orderBibkeys(bibkeys);

        assertEquals("affonso.etal:2015", bibkeys.get(0));
        assertEquals("affonso.etal:2015a", bibkeys.get(1));
        assertEquals("affonso.etal:2016", bibkeys.get(2));
        assertEquals("bergen:2007", bibkeys.get(3));
        assertEquals("cheng.etal:2009", bibkeys.get(4));
    }
}
