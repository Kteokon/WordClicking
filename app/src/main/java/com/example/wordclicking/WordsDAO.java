package com.example.wordclicking;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Transaction;
import androidx.room.Update;

import java.util.List;

@Dao
public interface WordsDAO {
    @Insert
    long insert(EngWord engWord);
    @Insert
    long insert(RusWord rusWord);
    @Insert
    void insert(EngWordRusWordCrossRef... engWordRusWordCrossRefs);

    @Update
    void update(EngWord... engWords);
    @Update
    void update(RusWord... rusWords);

    @Delete
    int delete(EngWord... engWords);
    @Delete
    int delete(RusWord... rusWords);
    @Delete
    int delete(EngWordRusWordCrossRef... engWordRusWordCrossRefs);

    @Query("SELECT * FROM EngWord")
    LiveData<List<EngWord>> findAllEngWords();
    @Query("SELECT * FROM RusWord")
    LiveData<List<RusWord>> findAllRusWords();
    @Query("SELECT spelling, engWordId as wordId FROM EngWord where spelling=:spelling union SELECT spelling, rusWordId as wordId FROM RusWord where spelling=:spelling")
    List<WordTuple> findWordBySpelling(String spelling);
    @Transaction
    @Query("SELECT * FROM EngWord")
    LiveData<List<EngWordWithTranslations>> findEngWordWithTranslations();
    @Transaction
    @Query("SELECT * FROM RusWord")
    LiveData<List<RusWordWithTranslations>> findRusWordWithTranslations();

    @Transaction
    @Query("SELECT * FROM EngWord where spelling=:spelling")
    EngWordWithTranslations findEngWordWithTranslationsBySpelling(String spelling);
    @Transaction
    @Query("SELECT * FROM RusWord where spelling=:spelling")
    RusWordWithTranslations findRusWordWithTranslationsBySpelling(String spelling);
    @Query("SELECT spelling, engWordId as wordId FROM EngWord where spelling=:spelling union SELECT spelling, rusWordId as wordId FROM RusWord where spelling=:spelling")
    List<WordTuple> findWordWithTranslationsBySpelling(String spelling);
}
