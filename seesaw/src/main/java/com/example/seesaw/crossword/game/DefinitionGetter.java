package com.example.seesaw.crossword.game;

import java.util.ArrayList;
import java.util.Collections;


public class DefinitionGetter {
    public static String getDefinition(String word) {
        String[] rawData = WordsGetter.readFile("contents2.txt",1).split("\n");
        ArrayList<String>  definitions = new ArrayList<>();

        for (String line : rawData) {
            if (line.contains(word+",\t")) {
                String removeLine = line.replace(word+",", "");
                definitions.add(removeLine);
            }

        }
        Collections.shuffle(definitions);
        return definitions.get(0).trim().replace(".", "");
    }
}