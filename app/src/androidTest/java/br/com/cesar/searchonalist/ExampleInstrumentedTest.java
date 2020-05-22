package br.com.cesar.searchonalist;

import android.content.Context;

import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.platform.app.InstrumentationRegistry;

import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

@RunWith(AndroidJUnit4.class)
public class ExampleInstrumentedTest {

    private static final List<String> words = new ArrayList<>();

    @BeforeClass
    public static void populateWords() {
        words.clear();

        Context appContext = InstrumentationRegistry.getInstrumentation().getTargetContext();
//        font: https://docs.oracle.com/javase/tutorial/collections/interfaces/examples/dictionary.txt
        final List<String> wordsInDocument = Utils.getWordsInDocument(appContext, "dictionary.txt");
        words.addAll(wordsInDocument);
    }

    @Test
    public void useAppContext() {
        Context appContext = InstrumentationRegistry.getInstrumentation().getTargetContext();

        assertEquals("br.com.cesar.searchonalist", appContext.getPackageName());
    }

    @Test
    public void testFindWordYou() {
        List<String> wordsFiltered = new ArrayList<>();
        final String wordToFilter = "you";
        for (String word : words) {
            final boolean isPartialPermutationXorTypo =
                    Utils.isPartialPermutationXorTypo(wordToFilter, word);

            if (isPartialPermutationXorTypo) wordsFiltered.add(word);
        }

        assertEquals(24, wordsFiltered.size());
    }

    @Test
    public void testFindWordGlove() {
        List<String> wordsFiltered = new ArrayList<>();
        final String wordToFilter = "glove";
        for (String word : words) {
            final boolean isPartialPermutationXorTypo =
                    Utils.isPartialPermutationXorTypo(wordToFilter, word);

            if (isPartialPermutationXorTypo) wordsFiltered.add(word);
        }

        assertEquals(8, wordsFiltered.size());
    }

}
