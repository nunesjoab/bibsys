package br.com.soo.bibsys.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Bibkey {
	
	public String joinFiles(String fileA, String fileB) {
		String joinedFile;
		joinedFile = fileA.concat("\n" + fileB);
		return joinedFile;
	}
	
	public String[] splitAuthorName(String authorName) {
		return authorName.replaceAll("[,\\.]", "").split(" ");
	}
	
	public String[] splitAuthors(String authors) {
		return authors.split(" and ");
	}
	
	public String generateBibkey(String authors, String year) {
		String formattedBibkey = "";
		String[] names = splitAuthors(authors);
		String[] name;
		
		if (names.length == 1) {
			name = splitAuthorName(names[0]);
			formattedBibkey += name[0].toLowerCase();
		} else if (names.length == 2) {
			name = splitAuthorName(names[0]);
			formattedBibkey += name[0].toLowerCase() + ".";
			
			name = splitAuthorName(names[1]);
			formattedBibkey += name[0].toLowerCase();
		} else {
			name = splitAuthorName(names[0]);
			formattedBibkey += name[0].toLowerCase() + ".etal";
		}
		
		formattedBibkey += ":" + year;
		
		return formattedBibkey;		
	}

	public List<Map<String, String>> editBibkeys(List<Map<String, String>> file) {
	    List<String> generatedBibkeys = new ArrayList<String>();

	    for (Map<String, String> ref : file) {
	        String bibkey = generateBibkey(ref.get("author"), ref.get("year"));
	        while (generatedBibkeys.contains(bibkey)) {
	            char lastCharacter = bibkey.charAt(bibkey.length() - 1);
	            if (Character.isDigit(lastCharacter)) {
	                bibkey = bibkey + "a";
                } else {
                    int n = (int)lastCharacter + 1;
                    lastCharacter = (char) n;
	                bibkey = bibkey.substring(0, bibkey.length() - 1) + lastCharacter;
                }
            }
            generatedBibkeys.add(bibkey);
	        ref.put("bibkey", bibkey);
        }

        return file;
    }
	
}
