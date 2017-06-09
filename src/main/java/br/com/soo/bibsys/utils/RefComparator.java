package br.com.soo.bibsys.utils;

import java.util.HashMap;
import java.util.Map;

public class RefComparator {


	public Map<String, String> fileToMap(String file) {
		Map<String, String> map = new HashMap<String, String>();

		String[] split = file.split(",");

		for (int i = 1; i < split.length - 1; i++) {
			String line = split[i];

			String[] tags = line.split("=");
			String key = tags[0].trim();
			String value = tags[1].trim().replaceAll("[{},]", "");

			map.put(key, value);
		}

		return map;
	}

	public boolean compareFiles(String file1, String file12) {
		return false;
	}
}
