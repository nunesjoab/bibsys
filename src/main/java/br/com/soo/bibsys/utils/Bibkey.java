package br.com.soo.bibsys.utils;

public class Bibkey {
	
	public String joinFiles(String fileA, String fileB) {
		String joinedFile;
		joinedFile = fileA.concat("\n" + fileB);
		return joinedFile;
	}

	public String formatBibkey(String bibkey) {
		return "@" + bibkey.toUpperCase();
	}
	
	public String[] splitAuthorName(String authorName) {
		return authorName.split(" ");
	}
	
	public String[] splitAuthors(String authors) {
		return authors.split(" e ");
	}
	
	public String generateBibkey(String bibkey, String authors, String year) {
		String formattedBibkey = formatBibkey(bibkey) + "{";
		String[] names = splitAuthors(authors);
		String[] name;
		
		if (names.length == 1) {
			name = splitAuthorName(names[0]);
			formattedBibkey += name[name.length - 1].toLowerCase();
		} else if (names.length == 2) {
			name = splitAuthorName(names[0]);
			formattedBibkey += name[name.length - 1].toLowerCase() + ".";
			
			name = splitAuthorName(names[1]);
			formattedBibkey += name[name.length - 1].toLowerCase();
		} else {
			name = splitAuthorName(names[0]);
			formattedBibkey += name[name.length - 1].toLowerCase() + ".etal";
		}
		
		formattedBibkey += ":" + year;
		
		return formattedBibkey;		
	}
	
}
