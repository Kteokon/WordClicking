package com.example.wordclicking;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.room.Room;

import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.Gson;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class MainActivity extends AppCompatActivity {
    DBHelperWithLoader dbHelper;
    MyRoomDB myRoomDB;

    TextView tv, wordsTV;
    Button engWordsButton, rusWordsButton, taskButton;
    RelativeLayout rl;

    String clickedWord = "";
    List<String> translations;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        myRoomDB = Room.databaseBuilder(this, MyRoomDB.class, "words").build();

        tv = findViewById(R.id.theText);
        wordsTV = findViewById(R.id.words);
        engWordsButton = findViewById(R.id.engWordsButton);
        rusWordsButton = findViewById(R.id.rusWordsButton);
        taskButton = findViewById(R.id.taskButton);
        rl = findViewById(R.id.bottomLayout);
        String theText = "Fly me to the moon spine back";

        List<Character> symbols = Arrays.asList(new Character[]{' ', '\n', '.', '?', '!', ',', ';', ':'});

        SpannableString ss = new SpannableString(theText);

        for (int i = 0; i < theText.length(); i++) {
            if (!symbols.contains(theText.charAt(i))) {
                int endWord = 0;
                for (int j = i + 1; j < theText.length(); j++) {
                    if (symbols.contains(theText.charAt(j))) {
                        endWord = j;
                    }
                    else {
                        if (j == theText.length() - 1) {
                            endWord = theText.length();
                        }
                    }
                    if (endWord != 0) {
                        String word;
                        word = theText.substring(i, endWord);
                        ClickableSpan clickableSpan = new ClickableSpan() {
                            @Override
                            public void onClick(@NonNull View widget) {
                                rl.setVisibility(View.VISIBLE);
                                Log.d("mytag", "Click");
                                DictionaryTask task = new DictionaryTask();
                                clickedWord = word;
                                String what = "https://dictionary.yandex.net/api/v1/dicservice.json/lookup?key=dict.1.1.20230215T074722Z.df778d51e0ebdb09.260d7a33d7d4fa79d4df13fc548ce0817721cc4b&lang=en-ru&text=" + word;
                                try {
                                    translations = task.execute(what).get();
                                } catch (ExecutionException e) {
                                    e.printStackTrace();
                                } catch (InterruptedException e) {
                                    e.printStackTrace();
                                }
                            }

                            @Override
                            public void updateDrawState(@NonNull TextPaint ds) {
                                super.updateDrawState(ds);
                                ds.setColor(Color.YELLOW);
                                ds.setUnderlineText(false);
                            }
                        };
                        ss.setSpan(clickableSpan, i, endWord, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                        i = j - 1;
                        break;
                    }
                }
            }
        }
        tv.setText(ss);
        tv.setMovementMethod(LinkMovementMethod.getInstance());

        engWordsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), WordActivity.class);
                intent.putExtra("words", "eng");
                startActivity(intent);
            }
        });

        rusWordsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), WordActivity.class);
                intent.putExtra("words", "rus");
                startActivity(intent);
            }
        });

        taskButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), ClickWordsActivity.class);
                startActivity(intent);
            }
        });
    }

    class DictionaryTask extends AsyncTask<String, Void, List<String>> {

        @Override
        protected List<String> doInBackground(String... strings) {
            Dictionary dictionary = null;
            List<String> words = new ArrayList<>();
            try {
                String url = strings[0];
                URL dictionary_url = new URL(url);
                InputStream stream = (InputStream) dictionary_url.getContent();
                Gson gson = new Gson();
                dictionary = gson.fromJson(new InputStreamReader(stream), Dictionary.class);
            } catch (IOException e) {
                Log.d("mytag", e.getLocalizedMessage());
            }
            for (int i = 0; i < dictionary.def.length; i++) {
                for (int j = 0; j < dictionary.def[i].tr.length; j++) {
                    words.add(dictionary.def[i].tr[j].text);
                }
            }
            return words;
        }

        @Override
        protected void onPostExecute(List<String> words){
            super.onPostExecute(words);
            String res = "";
            boolean firstWord = true;
            for (int i = 0; i < words.size(); i++) {
                if (firstWord) {
                    res += words.get(i);
                    firstWord = false;
                }
                else {
                    res += (", " + words.get(i));
                }
            }
            wordsTV.setText(res);
        }
    }

    public void addWord(View v) {
        WordsViewModel wordsViewModel = new ViewModelProvider(this).get(WordsViewModel.class);
        boolean firstWord = false, secondWord = false;
        List<WordTuple> word = wordsViewModel.findWordBySpelling(clickedWord);
        EngWord engWord;
        long engWordId;
        if (word.size() == 0 || word.get(0) == null) {
            engWord = new EngWord(clickedWord);
            engWordId = wordsViewModel.insert(engWord);
            firstWord = true;
        }
        else {
            engWordId = word.get(0).wordId;
        }
        for (int i = 0; i < translations.size(); i++) {
            word = wordsViewModel.findWordBySpelling(translations.get(i));
            RusWord rusWord;
            long rusWordId;
            if (word.size() == 0 || word.get(0) == null) {
                rusWord = new RusWord(translations.get(i));
                rusWordId = wordsViewModel.insert(rusWord);
                secondWord = true;
            }
            else {
                rusWordId = word.get(0).wordId;
            }
            if (firstWord || secondWord) {
                EngWordRusWordCrossRef engRusWords = new EngWordRusWordCrossRef();
                engRusWords.setEngWordId((int)engWordId);
                engRusWords.setRusWordId((int)rusWordId);
                wordsViewModel.insert(engRusWords);
            }
        }
    }
}