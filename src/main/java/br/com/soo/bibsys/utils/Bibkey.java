package br.com.soo.bibsys.utils;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

public class Bibkey {

    /**
     * Divide o nome do autor e armazena cada palavra em um array de Strings
     *
     * @param authorName nome de autor
     * @return array de Strings com cada palavra do nome
     */
    public String[] splitAuthorName(String authorName) {
        return authorName.replaceAll("[,\\.]", "").split(" ");
    }


    /**
     * Divide os nomes dos autores de uma referência, armazenando
     * cada nome inteiro em um array de Strings.
     *
     * @param authors nomes dos autores
     * @return array de Strings com cada nome completo dos autores
     */
    public String[] splitAuthors(String authors) {
        return authors.split(" and ");
    }

    /**
     * Gera a bibkey conforme especificações
     *
     * @param authors valor da tag "author"
     * @param year valor da tag "year"
     * @return bibkey gerada conforme especificações
     */
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

    /**
     * Edita as bibkeys em um arquivo, passando por cada referência
     * e alterando automaticamente
     *
     * @param file lista de map com as referências do arquivo .bib
     * @return lista de map com as referências e suas bibkeys editadas
     */
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

    /**
     * Percorre a lista de map com as referências e armazena
     * as bibkeys em uma lista de Strings.
     *
     * @param file lista de map com as referências do arquivo .bib
     * @return lista de Strings contendo as bibkeys do arquivo .bib
     */
    public List<String> getBibkeysFromFile(List<Map<String, String>> file) {
        List<String> keys = new ArrayList<String>();

        for (Map<String, String> ref : file) {
            keys.add(ref.get("bibkey"));
        }

        return keys;
    }

    /**
     * Realiza a ordenação da lista de bibkeys em ordem alfabética
     *
     * @param bibkeys lista de bibkeys
     * @return lista de bibkeys ordenada
     */

    public List<String> orderBibkeys(List<String> bibkeys) {
        bibkeys.sort(new Comparator<String>() {
            public int compare(String o1, String o2) {
                return o1.compareTo(o2);
            }
        });

        return bibkeys;
    }

}
