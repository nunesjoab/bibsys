package br.com.soo.bibsys.utils;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class ParserTest {

	private static final String HEADER_LINE = "@ARTICLE{Carnielli:1999,";
	private static final String HEADER_LINE_PARSED = "@ARTICLE{Carnielli:1999,";

	private static final String TAG_LINE = "  Author = {W.A. Carnielli and J. Marcos},";
	private static final String TAG_LINE_PARSED = "  author        = {W.A. Carnielli and J. Marcos},";

	private static final String FILE = "@ARTICLE{Carnielli:1999,\nauthor = {W.A. Carnielli and J. Marcos},\n}";
	private static final String FILE_PARSED = "@ARTICLE{Carnielli:1999,\n  author        = {W.A. Carnielli and J. Marcos},\n}";

	private static final String FILE2 = "@ARTICLE{Carnielli:1999,\nauthor={W.A. Carnielli and J. Marcos},\n}\n"+
			"@ARTICLE{Carnielli:1999,\nauthor={W.A. Carnielli and J. Marcos},\nnumber={3},\n}";

	private static final String FILE2_PARSED = "@ARTICLE{Carnielli:1999,\n  author        = {W.A. Carnielli and J. Marcos},\n}\n"+
			"@ARTICLE{Carnielli:1999,\n  author        = {W.A. Carnielli and J. Marcos},\n  number        = {3},\n}";


	@Test
	public void parserHeaderTest() {
		Parser parser = new Parser();
		assertEquals(HEADER_LINE_PARSED, parser.parseHeader(HEADER_LINE_PARSED));
	}

	@Test
	public void parserSplitTagLineTest() {
		Parser parser = new Parser();

		String s[] = parser.splitTags(TAG_LINE);
		assertEquals(2, s.length);
	}

	@Test
	public void parserPrepareTagKeyTest() {
		Parser parser = new Parser();

		assertEquals("  teste        ", parser.prepareTagKey("Teste"));
		assertEquals("  teste        ", parser.prepareTagKey("  teste  "));
		assertEquals("  testecommaisd", parser.prepareTagKey("testecommaisde16caracteres"));
	}

	@Test
	public void parserPrepareTagValueTest() {
		Parser parser = new Parser();

		assertEquals("{abc123}", parser.prepareTagValue("\"abc123\""));
		assertEquals("{abc123}", parser.prepareTagValue("{abc123}"));
		assertEquals("{abc123}", parser.prepareTagValue(" {abc123}"));
		assertEquals("{abc123}", parser.prepareTagValue(" {abc123},"));
		assertEquals("{abc123}", parser.prepareTagValue(" { abc123  },"));
	}

	@Test
	public void parserTagLineTest() {
		Parser parser = new Parser();

		assertEquals(TAG_LINE_PARSED, parser.parseTagLine(TAG_LINE));
	}

	@Test
	public void parserTrailLineTest() {
		Parser parser = new Parser();

		assertEquals("}", parser.parseTrail("}"));
		assertEquals("}", parser.parseTrail(" } "));
	}

	@Test
	public void parserFileTest() {
		Parser parser = new Parser();

		assertEquals(FILE_PARSED, parser.parseFile(FILE));
		assertEquals(FILE2_PARSED, parser.parseFile(FILE2));
	}

}