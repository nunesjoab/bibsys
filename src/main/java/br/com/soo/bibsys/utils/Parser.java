package br.com.soo.bibsys.utils;

public class Parser {

	public String parseHeader(String line) {
		return line;
	}

	public String[] splitTags(String tagLine) {
		return tagLine.split("=");
	}

	public String prepareTagKey(String tagKey) {
		if (tagKey.length() > 13) {
			tagKey = tagKey.substring(0, 13);
		}

		return String.format("  %-13s", tagKey.trim());
	}

	public String prepareTagValue(String tagValue) {
		tagValue = tagValue.replaceAll("[\"{},]", "");
		tagValue = String.format("{%s}", tagValue.trim());
		return tagValue;
	}

	public String parseTagLine(String tagLine) {
		String[] strings = splitTags(tagLine);

		String key = prepareTagKey(strings[0]);
		String value = prepareTagValue(strings[1]);

		return String.format("%s = %s,", key, value);
	}

	public String parseTrail(String trailLine) {
		return trailLine.trim();
	}

	public String parseFile(String file) {
		String[] split = file.split("\n");

		split[0] = parseHeader(split[0]);

		for (int i = 1; i < split.length - 1; i++) {
			split[i] = parseTagLine(split[i]);
		}

		split[split.length - 1] = parseTrail(split[split.length - 1]);

		return String.join("\n", split);
	}
}
