package br.com.soo.bibsys.utils;

import org.junit.Test;

import java.util.Map;

import static org.junit.Assert.assertEquals;

public class RefComparatorTest {

	private static final String FILE1 = "@ARTICLE{Carnielli:1999,\n  author        = {W.A. Carnielli and J. Marcos},\n}";
	private static final String FILE2 = "@ARTICLE{Carnielli:1999,\n  author        = {W.A. Carnielli and J. Marcos},\n  number        = {3},\n}";

	@Test
	public void fileToMapWithOneRefTest() {
		RefComparator comparator = new RefComparator();

		Map<String, String> map = comparator.fileToMap(FILE1);

		assertEquals(1, map.size());
		assertEquals("author", map.keySet().toArray()[0]);
		assertEquals("W.A. Carnielli and J. Marcos", map.get(map.keySet().toArray()[0]));
	}

	@Test
	public void fileToMapWithTwoRefTest() {
		RefComparator comparator = new RefComparator();

		Map<String, String> map = comparator.fileToMap(FILE2);

		assertEquals(2, map.size());
	}

	public void compareTwoEqualsFileTest() {
		RefComparator comparator = new RefComparator();

		boolean filesAreEqual = comparator.compareFiles(FILE1, FILE1);
	}
}
