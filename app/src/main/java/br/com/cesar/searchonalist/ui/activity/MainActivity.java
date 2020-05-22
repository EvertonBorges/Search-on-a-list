package br.com.cesar.searchonalist.ui.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.util.ArrayList;
import java.util.List;

import br.com.cesar.searchonalist.R;
import br.com.cesar.searchonalist.Utils;
import br.com.cesar.searchonalist.ui.adapter.WordItemAdapter;

public class MainActivity extends AppCompatActivity {

    final List<String> filteredWords = new ArrayList<>();

    List<String> words = new ArrayList<>();
    WordItemAdapter adapter = null;

    private EditText editTextWordToSearch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        words.addAll(Utils.getWordsInDocument(this, "dictionary.txt"));
        filteredWords.addAll(words);

        editTextWordToSearch = findViewById(R.id.edit_text_search_word);
        final Button buttonSearch = findViewById(R.id.button_search);
        final RecyclerView recyclerViewWords = findViewById(R.id.recycler_view_words);

        adapter = new WordItemAdapter(this, filteredWords);
        recyclerViewWords.setAdapter(adapter);

        buttonSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String wordToSearch = editTextWordToSearch.getText().toString();
                final List<String> filteredResult =
                        filterPartialPermutationXorTypo(words, wordToSearch);

                filteredWords.clear();
                filteredWords.addAll(filteredResult);

                adapter.notifyDataSetChanged();
            }
        });
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString("wordToFilter", editTextWordToSearch.getText().toString());
    }

    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        if (savedInstanceState.containsKey("wordToFilter")) {
            final String wordToFilter = savedInstanceState.getString("wordToFilter");
            final List<String> filteredResult = filterPartialPermutationXorTypo(words, wordToFilter);

            filteredWords.clear();
            filteredWords.addAll(filteredResult);

            adapter.notifyDataSetChanged();
        }
    }

    public static List<String> filterPartialPermutationXorTypo(List<String> words, String wordToFind) {
        List<String> filter = new ArrayList<>();
        for(String word: words) {
            final boolean isPartialPermutationXorTypo = Utils.isPartialPermutationXorTypo(word, wordToFind);
            if (isPartialPermutationXorTypo) filter.add(word);
        }

        return filter;
    }

}