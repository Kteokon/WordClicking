package com.example.wordclicking;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import java.util.List;

public class WordActivity extends AppCompatActivity implements ItemClickListener {
    RecyclerView wordList;

    WordsViewModel wordsViewModel;

    String type;
    List<EngWordWithTranslations> engWords;
    List<RusWordWithTranslations> rusWords;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_word);

        wordList = findViewById(R.id.wordList);
        wordList.setLayoutManager(new LinearLayoutManager(this));

        Intent intent = getIntent();
        type = intent.getStringExtra("words");

        WordListAdapter adapter = new WordListAdapter(this, type, this);
        wordList.setAdapter(adapter);

        Log.d("mytag", type);

        wordsViewModel = new ViewModelProvider(this).get(WordsViewModel.class);
        if (type.equals("eng")) {
            wordsViewModel.getEngWords().observe(this, new Observer<List<EngWordWithTranslations>>() {
                @Override
                public void onChanged(List<EngWordWithTranslations> words) {
                    engWords = words;
                    adapter.setEngWords(engWords);
                }
            });
        }
        else {
            wordsViewModel.getRusWords().observe(this, new Observer<List<RusWordWithTranslations>>() {
                @Override
                public void onChanged(List<RusWordWithTranslations> words) {
                    rusWords = words;
                    adapter.setRusWords(rusWords);
                }
            });
        }
    }

    @Override
    public void onItemClick(Word item) {
        Log.d("mytag", "Alert");
        FragmentManager manager = getSupportFragmentManager();
        MyDialogFragment myDialogFragment = new MyDialogFragment();
        myDialogFragment.setItem(item);
        myDialogFragment.show(manager, "deleteWordAlert");
    }

    public void deleteWord(Word item) {
        long engWordId, rusWordId;
        if (type.equals("eng")) {
            EngWordWithTranslations engWord = wordsViewModel.getEngWordWithTranslations(item.getSpelling());
            List<RusWord> translations = engWord.translations;
            engWordId = wordsViewModel.delete(engWord.word);
            for (int i = 0; i < translations.size(); i++) {
                RusWordWithTranslations rusWord = wordsViewModel.getRusWordWithTranslations(translations.get(i).getSpelling());
                int amountOfWords = rusWord.translations.size();
                if (amountOfWords <= 1) {
                    rusWordId = wordsViewModel.delete(rusWord.word);

                    EngWordRusWordCrossRef engWordRusWord = new EngWordRusWordCrossRef();
                    engWordRusWord.setEngWordId((int) engWordId);
                    engWordRusWord.setRusWordId((int) rusWordId);
                    wordsViewModel.delete(engWordRusWord);
                }
            }
        }
        else {
            RusWordWithTranslations rusWord = wordsViewModel.getRusWordWithTranslations(item.getSpelling());
            List<EngWord> translations = rusWord.translations;
            rusWordId = wordsViewModel.delete(rusWord.word);
            for (int i = 0; i < translations.size(); i++) {
                EngWordWithTranslations engWord = wordsViewModel.getEngWordWithTranslations(translations.get(i).getSpelling());
                int amountOfWords = engWord.translations.size();
                if (amountOfWords <= 1) {
                    engWordId = wordsViewModel.delete(engWord.word);

                    EngWordRusWordCrossRef engWordRusWord = new EngWordRusWordCrossRef();
                    engWordRusWord.setEngWordId((int) engWordId);
                    engWordRusWord.setRusWordId((int) rusWordId);
                    wordsViewModel.delete(engWordRusWord);
                }
            }
        }
    }
}