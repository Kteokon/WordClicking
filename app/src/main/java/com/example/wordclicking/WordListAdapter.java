package com.example.wordclicking;

import android.app.Application;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class WordListAdapter extends RecyclerView.Adapter<WordListAdapter.CustomViewHolder> {
    String type;
    private List<EngWordWithTranslations> engWords = new ArrayList<>();
    private List<RusWordWithTranslations> rusWords = new ArrayList<>();
    LayoutInflater inflater;
    ItemClickListener listener;

    public WordListAdapter(Context context, String _type, ItemClickListener _listener) {
        this.inflater = LayoutInflater.from(context);
        this.type = _type;
        this.listener = _listener;
    }

    @NonNull
    @Override
    public CustomViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.word_item, parent, false);
        return new CustomViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CustomViewHolder holder, int position) {
        Word word;
        String translations = "";
        if (type.equals("eng")) {
            EngWordWithTranslations wordWithTranslations = engWords.get(position);
            List<RusWord> trs = wordWithTranslations.translations;
            for (int i = 0; i < trs.size(); i++) {
                if (i == 0) {
                    translations += trs.get(i).getSpelling();
                }
                else {
                    translations += (", " + trs.get(i).getSpelling());
                }
            }
            word = wordWithTranslations.word;
        }
        else {
            RusWordWithTranslations wordWithTranslations = rusWords.get(position);
            List<EngWord> trs = wordWithTranslations.translations;
            for (int i = 0; i < trs.size(); i++) {
                if (i == 0) {
                    translations += trs.get(i).getSpelling();
                }
                else {
                    translations += (", " + trs.get(i).getSpelling());
                }
            }
            word = wordWithTranslations.word;
        }
        holder.wordSpellingTV.setText(word.getSpelling());
        holder.wordTranslationTV.setText(translations);
        holder.deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onItemClick(word);
            }
        });
    }

    public void setEngWords(List<EngWordWithTranslations> words) {
        Log.d("mytag", "set eng words " + words.size());
        engWords = words;
        notifyDataSetChanged();
    }

    public void setRusWords(List<RusWordWithTranslations> words) {
        Log.d("mytag", "set rus words");
        rusWords = words;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        if (type.equals("eng")) {
            return engWords.size();
        }
        else {
            return rusWords.size();
        }
    }

    public Word getWordAt(int position) {
        if (type.equals("eng")) {
            return engWords.get(position).word;
        }
        else {
            return rusWords.get(position).word;
        }
    }

    public static class CustomViewHolder extends RecyclerView.ViewHolder {
        private TextView wordSpellingTV, wordTranslationTV;
        Button deleteButton;
        ConstraintLayout cl;

        public CustomViewHolder(View view) {
            super(view);

            wordSpellingTV = view.findViewById(R.id.wordSpelling);
            wordTranslationTV = view.findViewById(R.id.wordTranslation);
            deleteButton = view.findViewById(R.id.deleteButton);
            cl = view.findViewById(R.id.recycle_view_layout);
        }
    }
}
