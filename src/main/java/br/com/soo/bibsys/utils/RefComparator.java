package br.com.soo.bibsys.utils;

import java.util.*;

public class RefComparator {


	public List<Map<String, String>> fileToMap(String file) {
		List<Map<String, String>> list = new ArrayList<Map<String, String>>();

		String[] refs = file.split("@");

		for (int i = 0; i < refs.length; i++) {
		    if (refs[i].trim().equals("")) {
                continue;
            }

		    Map<String, String> map = new HashMap<String, String>();
		    String[] lines = refs[i].split("\\n");


		    for (int j = 0; j < lines.length; j++) {
                String line = lines[j];
                line = line.replaceAll("[}]", "");

                if (line.trim().equals("")) {
                    continue;
                }

                if (j == 0) {
                    String[] tags = line.split("[{]");
                    String bibkey = tags[1];
                    bibkey = bibkey.replaceAll(",", "");
                    map.put("bibkey", bibkey);
                } else {

                    String[] tags = line.split("=");
                    String key = tags[0].trim();
                    String value = tags[1].trim().replaceAll("[{}]", "");
                    value = value.substring(0, value.length() - 1);

                    map.put(key, value);
                }
		    }
    		list.add(map);
        }


		return list;
	}

	public boolean compareFiles(String file1, String file12) {
		return false;
	}

	public List<String> getBibkeysFromFile(List<Map<String, String>> file) {
        List<String> keys = new ArrayList<String>();

        for (Map<String, String> ref : file) {
            keys.add(ref.get("bibkey"));
        }

        return keys;
    }
}
