package br.com.soo.bibsys.utils;

import org.junit.Test;

import javax.print.DocFlavor;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertEquals;

public class RefComparatorTest {

	private static final String FILE1 = "@ARTICLE{Carnielli:1999,\n  author        = {W.A. Carnielli and J. Marcos},\n}";
	private static final String FILE2 = "@ARTICLE{Carnielli:1999,\n  author        = {W.A. Carnielli and J. Marcos},\n  number        = {3},\n}";
	private static final String FILE3 = "@ARTICLE{Carnielli:1999,\n  author        = {W.A. Carnielli and J. Marcos},\n}\n"+
			"@ARTICLE{Carnielli:1999,\n  author        = {W.A. Carnielli and J. Marcos},\n  number        = {3},\n}";
	private static final String FILE4 = FILE3 + "\n" + FILE3;

	@Test
	public void fileToMapWithOneTagTest() {
		RefComparator comparator = new RefComparator();

		List<Map<String, String>> list = comparator.fileToMap(FILE1);

		assertEquals(1, list.size());
		assertEquals(2, list.get(0).size());
        assertEquals("bibkey", list.get(0).keySet().toArray()[0]);
        assertEquals("Carnielli:1999", list.get(0).get(list.get(0).keySet().toArray()[0]));
		assertEquals("author", list.get(0).keySet().toArray()[1]);
		assertEquals("W.A. Carnielli and J. Marcos", list.get(0).get(list.get(0).keySet().toArray()[1]));
	}

	@Test
	public void fileToMapWithTwoTagTest() {
		RefComparator comparator = new RefComparator();

		List<Map<String, String>> list = comparator.fileToMap(FILE2);

		assertEquals(1, list.size());
		assertEquals(3, list.get(0).size());
	}

	@Test
	public void fileToMapTwoRefs() {
		RefComparator comparator = new RefComparator();

		List<Map<String, String>> list = comparator.fileToMap(FILE3);

		assertEquals(2, list.size());
	}

    @Test
    public void fileToMapFourRefs() {
        RefComparator comparator = new RefComparator();

        List<Map<String, String>> list = comparator.fileToMap(FILE4);

        assertEquals(4, list.size());
    }

	public void compareTwoEqualsFileTest() {
		RefComparator comparator = new RefComparator();

		boolean filesAreEqual = comparator.compareFiles(FILE1, FILE1);
	}
}
