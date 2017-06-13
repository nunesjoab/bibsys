package br.com.soo.bibsys.utils;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.util.*;

public class FileOperator {

    private static final String FILE_ENCODING = "UTF-8";

    public static String convertFileToString(File file) {
        String fileString = "";

        try {
            fileString = FileUtils.readFileToString(file, FILE_ENCODING);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return fileString;
    }

    public List<Map<String, String>> fileToMap(String file) {
        List<Map<String, String>> list = new ArrayList<Map<String, String>>();

        String[] refs = file.split("@");

        for (int i = 0; i < refs.length; i++) {
            if (refs[i].trim().equals("")) {
                continue;
            }

            Map<String, String> map = new LinkedHashMap<String, String>();
            String[] lines = refs[i].split("\\n");


            for (int j = 0; j < lines.length; j++) {
                String line = lines[j];
                line = line.replaceAll("[}]", "");

                if (line.trim().equals("")) {
                    continue;
                }

                if (j == 0) {
                    String[] tags = line.split("[{]");
                    String type = tags[0];
                    type = type.toUpperCase();
                    String bibkey = tags[1];
                    bibkey = bibkey.replaceAll(",", "").toLowerCase();
                    map.put("type", type);
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

    public String mapToFile(List<Map<String, String>> refs) {
        String file = "";

        for (Map<String, String> ref : refs) {
            for (Map.Entry<String, String> entry : ref.entrySet()) {
                if (entry.getKey().equals("type")) {
                    file = file + "@" + entry.getValue() + "{";
                } else if (entry.getKey().equals("bibkey")) {
                    file = file + entry.getValue() + ",\n";
                } else {
                    file = file + entry.getKey() + "={" + entry.getValue() + "},\n";
                }
            }

            file = file + "}\n";
        }

        return file;
    }

    public String joinFiles(String fileA, String fileB) {
        String joinedFile;
        joinedFile = fileA.concat("\n" + fileB);
        return joinedFile;
    }

    public List<Map<String, String>> getDiffBetweenFiles(List<Map<String, String>> fileA, List<Map<String, String>> fileB) {
        List<Map<String, String>> diff = new ArrayList<Map<String, String>>();

        Bibkey bibkey = new Bibkey();
        List<String> bibkeysFileA = bibkey.getBibkeysFromFile(fileA);
        List<String> bibkeysFileB = bibkey.getBibkeysFromFile(fileB);

        for (int i = 0; i < bibkeysFileA.size(); i++) {
            if (!bibkeysFileB.contains(bibkeysFileA.get(i))) {
                diff.add(fileA.get(i));
            }
        }

        for (int i = 0; i < bibkeysFileB.size(); i++) {
            if (!bibkeysFileA.contains(bibkeysFileB.get(i))) {
                diff.add(fileB.get(i));
            }
        }

        return diff;
    }

    public List<Map<String, String>> orderFile(List<Map<String, String>> file) {
        file.sort(new Comparator<Map<String, String>>() {
            public int compare(Map<String, String> o1, Map<String, String> o2) {
                return o1.get("bibkey").compareTo(o2.get("bibkey"));
            }
        });

        return file;
    }

}
