package br.com.cesar.searchonalist.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import br.com.cesar.searchonalist.R;

public class WordItemAdapter extends RecyclerView.Adapter<WordItemAdapter.ViewHolder> {

    private final Context context;
    private final List<String> words;

    public WordItemAdapter(Context context, List<String> words) {
        this.context = context;
        this.words = words;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.recycler_view_word_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final String word = words.get(position);

        holder.textViewWord.setText(word);
    }

    @Override
    public int getItemCount() {
        return words.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        TextView textViewWord;

        ViewHolder(@NonNull View itemView) {
            super(itemView);

            textViewWord = itemView.findViewById(R.id.text_view_word);
        }
    }

}
