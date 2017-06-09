package br.com.soo.bibsys.utils;

import static org.junit.Assert.assertEquals;
import org.junit.Test;

public class BibkeyTest {
	
	private static final String FILE_A = "@ARTICLE{Carnielli:1999,\nauthor = {W.A. Carnielli and J. Marcos},\n}";
        private static final String FILE_B = "@ARTICLE{6740844,\nauthor={A. Zanella and N. Bui and A. Castellani and L. Vangelista and M. Zorzi},\n}";
	private static final String FILE_JOINED = "@ARTICLE{Carnielli:1999,\nauthor = {W.A. Carnielli and J. Marcos},\n}\n"
		+ "@ARTICLE{6740844,\nauthor={A. Zanella and N. Bui and A. Castellani and L. Vangelista and M. Zorzi},\n}";
	
	private static final String BIBKEY = "article";
	private static final String BIBKEY_FORMATED = "@ARTICLE";
		
	private static final String AUTHOR_ONE_NAME = "Fulano da Silva";
	private static final String AUTHORS_TWO_NAMES = "Fulano da Silva e Ciclano da Rocha";
	private static final String AUTHORS_THREE_NAMES = "Fulano da Silva e Ciclano da Rocha e Beltrano da Costa";
	
	private static final String YEAR = "2017";
	private static final String GENERATED_BIBKEY_ONE_NAME = "@ARTICLE{silva:2017";
	private static final String GENERATED_BIBKEY_TWO_NAMES = "@ARTICLE{silva.rocha:2017";
	private static final String GENERATED_BIBKEY_THREE_NAMES = "@ARTICLE{silva.etal:2017";
        
        @Test
	public void joinFilesTest() {
		Bibkey bibkey = new Bibkey();
		assertEquals(FILE_JOINED, bibkey.joinFiles(FILE_A, FILE_B));
	}
	
	@Test
	public void formatBibkeyTest() {
		Bibkey bibkey = new Bibkey();
		assertEquals(BIBKEY_FORMATED, bibkey.formatBibkey(BIBKEY));
	}
	
	@Test
	public void splitAuthorNameTest() {
		Bibkey bibkey = new Bibkey();
		
		String[] s = bibkey.splitAuthorName(AUTHOR_ONE_NAME);
		assertEquals(3, s.length);
	}
	
	@Test
	public void splitAuthors() {
		Bibkey bibkey = new Bibkey();
		
		String[] s = bibkey.splitAuthors(AUTHORS_TWO_NAMES);
		assertEquals(2, s.length);
		
		s = bibkey.splitAuthors(AUTHORS_THREE_NAMES);
		assertEquals(3, s.length);
	}
	
	@Test
	public void generateBibkeyTest() {
		Bibkey bibkey = new Bibkey();
		
		assertEquals(GENERATED_BIBKEY_ONE_NAME, bibkey.generateBibkey(BIBKEY, AUTHOR_ONE_NAME, YEAR));
		assertEquals(GENERATED_BIBKEY_TWO_NAMES, bibkey.generateBibkey(BIBKEY, AUTHORS_TWO_NAMES, YEAR));
		assertEquals(GENERATED_BIBKEY_THREE_NAMES, bibkey.generateBibkey(BIBKEY, AUTHORS_THREE_NAMES, YEAR));
	}
}
