package br.com.cesar.searchonalist;

import android.content.Context;
import android.content.res.AssetManager;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

class Utils {

    static List<String> getWordsInDocument(Context context, String file) {
        List<String> words = new ArrayList<>();

        AssetManager assetManager = context.getAssets();
        try {
            final InputStream inputStream = assetManager.open(file);
            final InputStreamReader inputStreamReader = new InputStreamReader(inputStream, StandardCharsets.UTF_8);
            final BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                words.add(line);
            }
        } catch (IOException ignored) {
        }

        return words;
    }

    static boolean isPartialPermutationXorTypo(String firstWord, String secondWord) {
        final boolean isPartialPermutation = Utils.checkPartialPermutation(firstWord, secondWord);
        final boolean isTypo = Utils.checkTypo(firstWord, secondWord);
        return isPartialPermutation ^ isTypo;
    }

    private static boolean checkPartialPermutation(String firstWord, String secondWord) {
        if (firstWord == null || secondWord == null) return false;
        if (firstWord.charAt(0) != secondWord.charAt(0)) return false;
        if (firstWord.length() != secondWord.length()) return false;

        boolean hasSameLetters = checkEachLetter(firstWord, secondWord);
        if (!hasSameLetters) return false;

        if (firstWord.length() > 3) return checkByNumberPartialPermutation(firstWord, secondWord);

        return true;
    }

    private static boolean checkByNumberPartialPermutation(String firstWord, String secondWord) {
        int countNumberOfPermutations = 0;
        for(int i = 1; i < firstWord.length(); i++) {
            if (firstWord.charAt(i) != secondWord.charAt(i)) {
                countNumberOfPermutations++;
            }
        }
        float twoThirdsOfPermutations = (float) 2 / 3 * firstWord.length();
        return countNumberOfPermutations < twoThirdsOfPermutations;
    }

    private static boolean checkEachLetter(String firstWord, String secondWord) {
        for(Character character: firstWord.toCharArray()) {
            int occurrencesInFirstWord = occurrencesIn(firstWord, character);
            int occurrencesInSecondWord = occurrencesIn(secondWord, character);

            if (occurrencesInFirstWord != occurrencesInSecondWord) return false;
        }

        return true;
    }

    private static int occurrencesIn(String word, Character character) {
        return word.replaceAll("[^" + character + "]", "").length();
    }

    private static boolean checkTypo(String firstWord, String secondWord) {
        if (firstWord == null || secondWord == null) return false;
        if (Math.abs(firstWord.length() - secondWord.length()) > 1) return false;
        if (firstWord.equals(secondWord)) return true;

        if (checkInsertTypo(firstWord, secondWord)) return true;
        if (checkRemoveTypo(firstWord, secondWord)) return true;
        return checkReplaceTypo(firstWord, secondWord);
    }

    private static boolean checkInsertTypo(String firstWord, String secondWord) {
        if (secondWord.length() - firstWord.length() == 1) {
            return isTypoInsertOrRemoveBetween(secondWord, firstWord);
        }

        return false;
    }

    private static boolean checkRemoveTypo(String firstWord, String secondWord) {
        if (firstWord.length() - secondWord.length() == 1) {
            return isTypoInsertOrRemoveBetween(firstWord, secondWord);
        }

        return false;
    }

    private static boolean checkReplaceTypo(String firstWord, String secondWord) {
        if (firstWord.length() == secondWord.length()) {
            return isTypoReplaceBetween(firstWord, secondWord);
        }

        return false;
    }

    private static boolean isTypoInsertOrRemoveBetween(String word, String compareWord) {
        if (word.contains(compareWord)) return true;

        for (int i = 0; i < word.length(); i++) {
            final String newWordToCompare = word.substring(0, i) + word.substring(i + 1);
            if (newWordToCompare.equals(compareWord)) return true;
        }

        return false;
    }

    private static boolean isTypoReplaceBetween(String word, String compareWord) {
        for (int i = 0; i < compareWord.length(); i++) {
            final String firstWordToCompare =
                    word.substring(0, i) + word.substring(i + 1);

            final String secondWordToCompare =
                    compareWord.substring(0, i) + compareWord.substring(i + 1);

            if (firstWordToCompare.equals(secondWordToCompare)) return true;
        }

        return false;
    }

}
