package br.com.soo.bibsys.utils;

public class Formatter {

	/**
	 * Retorna a primeira linha do bloco de referência formatada
	 *
	 * @param line primeira linha do arquivo .bib
	 * @return primeira linha do arquivo .bib formatada
	 */
	public String formatHeader(String line) {
		String[] info = line.split("\\{");
		return String.format("%s{%s", info[0].trim().toUpperCase(), info[1]);
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
		return tagLine.split("=", 2);
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
		if (tagKey.trim().length() > 13) {
			tagKey = tagKey.trim().substring(0, 13);
		}

		return String.format("  %-13s", tagKey.trim());
	}

	/**
	 * Formata o valor da tag, envolvendo-o por "{}". Também remove espaços
	 * antes e depois do valor da tag.
	 *
	 * @param tagValue valor da tag
	 * @return valor da tag formatado
	 */
	public String prepareTagValue(String tagValue) {
		tagValue = tagValue.replaceAll("[\"{}]", "");

		if (tagValue.charAt(tagValue.length() - 1) == ',') {
			tagValue = tagValue.substring(0, tagValue.length() - 1);
		}

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
	public String formatTagLine(String tagLine) {
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
	public String formatTrail(String trailLine) {
		return trailLine.trim();
	}

	/**
	 * Formata o arquivo .bib conforme especificações
	 *
	 * @param file arquivo .bib
	 * @return arquivo .bib formatado
	 */
	public String formatFile(String file) {
		String parsedFile = "";
		String[] refs = file.split("@");

		for (int i = 1; i < refs.length; i++) {
			if (refs[i].trim().equals("")) {
				continue;
			}

			refs[i] = refs[i].replace("\n\t", " ");
			refs[i] = refs[i].replace("},}", "},\n}");
			refs[i] = refs[i].replace("}, ", "},\n");
			refs[i] = refs[i].replace("\n\n", "\n");

			String[] lines = refs[i].split("\\n");

			lines[0] = "@" + formatHeader(lines[0]);

			for (int j = 1; j < lines.length - 1; j++) {
				if (!(lines[j].trim().equals("") || lines[j].trim().equals("}"))) {
					lines[j] = formatTagLine(lines[j]);
				} else {
					lines[j] = formatTrail(lines[j]);
				}
			}

			parsedFile = parsedFile + String.join("\n", lines) + "\n";

		}

		return parsedFile;
	}
}
