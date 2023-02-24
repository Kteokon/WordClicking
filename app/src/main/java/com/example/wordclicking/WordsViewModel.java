package com.example.wordclicking;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

public class WordsViewModel extends AndroidViewModel {
    private WordsRepository repository;
    private LiveData<List<EngWordWithTranslations>> engWords;
    private LiveData<List<RusWordWithTranslations>> rusWords;

    public WordsViewModel(@NonNull Application application) {
        super(application);
        this.repository = new WordsRepository(application);
        this.engWords = repository.getAllEngWords();
        this.rusWords = repository.getAllRusWords();
    }

    public long insert(EngWord word) {
        long wordId = this.repository.insert(word);
        return wordId;
    }

    public void insert(EngWordRusWordCrossRef word) {
        this.repository.insert(word);
    }

    public long insert(RusWord word) {
        long wordId = this.repository.insert(word);
        return wordId;
    }

    public long delete(EngWord word) {
        long wordId = this.repository.delete(word);
        return wordId;
    }

    public long delete(RusWord word) {
        long wordId = this.repository.delete(word);
        return wordId;
    }

    public void delete(EngWordRusWordCrossRef word) {
        this.repository.delete(word);
    }

    public LiveData<List<EngWordWithTranslations>> getEngWords() {
        return this.engWords;
    }

    public LiveData<List<RusWordWithTranslations>> getRusWords() {
        return this.rusWords;
    }

    public List<WordTuple> findWordBySpelling(String spelling) {
        return this.repository.getWordBySpelling(spelling);
    }

    public EngWordWithTranslations getEngWordWithTranslations(String spelling) {
        return this.repository.getEngWordWithTranslationsBySpelling(spelling);
    }

    public RusWordWithTranslations getRusWordWithTranslations(String spelling) {
        return this.repository.getRusWordWithTranslationsBySpelling(spelling);
    }
}
