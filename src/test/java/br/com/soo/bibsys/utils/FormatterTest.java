package br.com.soo.bibsys.utils;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class FormatterTest {

	private static final String HEADER_LINE = "@ARTICLE{Carnielli:1999,";
	private static final String HEADER_LINE_PARSED = "@ARTICLE{Carnielli:1999,";

	private static final String TAG_LINE = "  Author = {W.A. Carnielli and =J. Mar=cos},";
	private static final String TAG_LINE_PARSED = "  Author        = {W.A. Carnielli and J. Marcos},";

	private static final String FILE = "@ARTICLE{Carnielli:1999,\nauthor = {W.A. Carnielli and J. Marcos},\n}";
	private static final String FILE_PARSED = "@ARTICLE{Carnielli:1999,\n  author        = {W.A. Carnielli and J. Marcos},\n}\n";

	private static final String FILE2 = "@ARTICLE{Carnielli:1999,\nauthor={W.A. Carnielli and J. Marcos},\n}\n"+
			"@ARTICLE{Carnielli:1999,\nauthor={W.A. Carnielli and J. Marcos},\nnumber={3},\n}";

	private static final String FILE2_PARSED = "@ARTICLE{Carnielli:1999,\n  author        = {W.A. Carnielli and J. Marcos},\n}\n"+
			"@ARTICLE{Carnielli:1999,\n  author        = {W.A. Carnielli and J. Marcos},\n  number        = {3},\n}\n";


	@Test
	public void parserHeaderTest() {
		Formatter formatter = new Formatter();
		assertEquals(HEADER_LINE_PARSED, formatter.formatHeader(HEADER_LINE_PARSED));
	}

	@Test
	public void parserSplitTagLineTest() {
		Formatter formatter = new Formatter();

		String s[] = formatter.splitTags(TAG_LINE);
		assertEquals(2, s.length);
	}

	@Test
	public void parserPrepareTagKeyTest() {
		Formatter formatter = new Formatter();

		assertEquals("  teste        ", formatter.prepareTagKey("teste"));
		assertEquals("  teste        ", formatter.prepareTagKey("  teste  "));
		assertEquals("  testecommaisd", formatter.prepareTagKey("testecommaisde16caracteres"));
	}

	@Test
	public void parserPrepareTagValueTest() {
		Formatter formatter = new Formatter();

		assertEquals("{abc123}", formatter.prepareTagValue("\"abc123\""));
		assertEquals("{abc123}", formatter.prepareTagValue("{abc123}"));
		assertEquals("{abc123}", formatter.prepareTagValue(" {abc123}"));
		assertEquals("{abc123}", formatter.prepareTagValue(" {abc123},"));
		assertEquals("{abc123}", formatter.prepareTagValue(" { abc123  },"));
	}

	@Test
	public void parserTagLineTest() {
		Formatter formatter = new Formatter();

		assertEquals(TAG_LINE_PARSED, formatter.formatTagLine(TAG_LINE));
	}

	@Test
	public void parserTrailLineTest() {
		Formatter formatter = new Formatter();

		assertEquals("}", formatter.formatTrail("}"));
		assertEquals("}", formatter.formatTrail(" } "));
	}

	@Test
	public void parserFileTest() {
		Formatter formatter = new Formatter();

		assertEquals(FILE_PARSED, formatter.formatFile(FILE));
		assertEquals(FILE2_PARSED, formatter.formatFile(FILE2));
	}

}