package br.com.soo.bibsys.utils;

public class Parser {

	/**
	 * Retorna a primeira linha do bloco de referência formatada
	 *
	 * @param line primeira linha do arquivo .bib
	 * @return primeira linha do arquivo .bib formatada
	 */
	public String parseHeader(String line) {
		return line;
	}

	/**
	 * Executa o split das linhas de tags a partir do caráter "=",
	 * armazenando a chave da tag na primeira posição e
	 * o valor da mesma na segunda posição do array de Strings.
	 *
	 * @param tagLine linha contendo chave/valor
	 * @return array de Strings com chave e valor divididos
	 */
	public String[] splitTags(String tagLine) {
		return tagLine.split("=");
	}

	/**
	 * Formata a chave da tag, adicionando dois espaços antes da chave
	 * e completando com espaços até chegar em 15 caracteres, além de
	 * converter para letras minúsculas.
	 *
	 * @param tagKey chave da tag
	 * @return chave da tag formatada
	 */
	public String prepareTagKey(String tagKey) {
		if (tagKey.length() > 13) {
			tagKey = tagKey.substring(0, 13);
		}

		return String.format("  %-13s", tagKey.trim()).toLowerCase();
	}

	/**
	 * Formata o valor da tag, envolvendo-o por "{}". Também remove espaços
	 * antes e depois do valor da tag.
	 *
	 * @param tagValue valor da tag
	 * @return valor da tag formatado
	 */
	public String prepareTagValue(String tagValue) {
		tagValue = tagValue.replaceAll("[\"{},]", "");
		tagValue = String.format("{%s}", tagValue.trim());
		return tagValue;
	}

	/**
	 * Retorna a linha de tag/valor formatada, adicionando um espaço antes e
	 * depois do caráter "=".
	 *
	 * @param tagLine linha de tag/valor
	 * @return linha de tag/valor formatada
	 */
	public String parseTagLine(String tagLine) {
		String[] strings = splitTags(tagLine);

		String key = prepareTagKey(strings[0]);
		String value = prepareTagValue(strings[1]);

		return String.format("%s = %s,", key, value);
	}

	/**
	 * Formata última linha da referência formatada
	 *
	 * @param trailLine última linha da referência
	 * @return última linha da referência formatada
	 */
	public String parseTrail(String trailLine) {
		return trailLine.trim();
	}

	/**
	 * Formata o arquivo .bib conforme especificações
	 *
	 * @param file arquivo .bib
	 * @return arquivo .bib formatado
	 */
	public String parseFile(String file) {
		String parsedFile = "";
		String[] refs = file.split("@");

		for (int i = 0; i < refs.length; i++) {
			if (refs[i].trim().equals("")) {
				continue;
			}

			String[] lines = refs[i].split("\n");

			lines[0] = "@" + parseHeader(lines[0]);

			for (int j = 1; j < lines.length - 1; j++) {
				lines[j] = parseTagLine(lines[j]);
			}

			lines[lines.length - 1] = parseTrail(lines[lines.length - 1]);

			if (parsedFile.trim().equals("")) {
				parsedFile = String.join("\n", lines);
			} else {
				parsedFile = parsedFile + "\n" + String.join("\n", lines);
			}
		}

		return parsedFile;
	}
}
