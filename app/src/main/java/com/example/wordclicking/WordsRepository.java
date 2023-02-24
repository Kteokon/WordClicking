package com.example.wordclicking;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import java.util.List;
import java.util.concurrent.ExecutionException;

public class WordsRepository {
    WordsDAO wordsDAO;
    LiveData<List<EngWordWithTranslations>> engWords;
    LiveData<List<RusWordWithTranslations>> rusWords;

    public WordsRepository(Application application) {
        MyRoomDB myRoomDB = MyRoomDB.get(application);
        this.wordsDAO = myRoomDB.wordsDAO();
        this.engWords = wordsDAO.findEngWordWithTranslations();
        this.rusWords = wordsDAO.findRusWordWithTranslations();
    }
    // region Insert
    public long insert(EngWord word) {
        InsertEngWordTask task = new InsertEngWordTask(wordsDAO);
        long wordId = -1;
        try {
            wordId = task.execute(word).get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return wordId;
    }

    public long insert(RusWord word) {
        InsertRusWordTask task = new InsertRusWordTask(wordsDAO);
        long wordId = -1;
        try {
            wordId = task.execute(word).get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return wordId;
    }

    public void insert(EngWordRusWordCrossRef word) {
        new InsertEngWordRusWordTask(wordsDAO).execute(word);
    }
    // endregion Insert
    // region Delete
    public int delete(EngWord word) {
        DeleteEngWordTask task = new DeleteEngWordTask(wordsDAO);
        int wordId = -1;
        try {
            wordId = task.execute(word).get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return wordId;
    }

    public int delete(RusWord word) {
        DeleteRusWordTask task = new DeleteRusWordTask(wordsDAO);
        int wordId = -1;
        try {
            wordId = task.execute(word).get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return wordId;
    }

    public void delete(EngWordRusWordCrossRef word) {
        new DeleteEngWordRusWordCrossRefTask(wordsDAO).execute(word);
    }
    //endregion Delete

    public LiveData<List<EngWordWithTranslations>> getAllEngWords() {
        return this.engWords;
    }

    public LiveData<List<RusWordWithTranslations>> getAllRusWords() {
        return this.rusWords;
    }

    public List<WordTuple> getWordBySpelling(String spelling) {
        FindWordBySpellingTask task = new FindWordBySpellingTask(wordsDAO);
        List<WordTuple> word = null;
        try {
            word = task.execute(spelling).get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return word;
    }

    public EngWordWithTranslations getEngWordWithTranslationsBySpelling(String spelling) {
        FindEngWordWithTranslationsBySpellingTask task = new FindEngWordWithTranslationsBySpellingTask(wordsDAO);
        EngWordWithTranslations word = null;
        try {
            word = task.execute(spelling).get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return word;
    }

    public RusWordWithTranslations getRusWordWithTranslationsBySpelling(String spelling) {
        FindRusWordWithTranslationsBySpellingTask task = new FindRusWordWithTranslationsBySpellingTask(wordsDAO);
        RusWordWithTranslations word = null;
        try {
            word = task.execute(spelling).get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return word;
    }

    // region AsyncTasks
    public static class InsertEngWordTask extends AsyncTask<EngWord, Void, Long> {
        private WordsDAO wordsDAO;

        private InsertEngWordTask(WordsDAO _wordsDAO) {
            wordsDAO = _wordsDAO;
        }

        @Override
        protected Long doInBackground(EngWord... words) {
            long wordId = wordsDAO.insert(words[0]);
            return wordId;
        }
    }

    public static class InsertEngWordRusWordTask extends AsyncTask<EngWordRusWordCrossRef, Void, Void> {
        private WordsDAO wordsDAO;

        private InsertEngWordRusWordTask(WordsDAO _wordsDAO) {
            wordsDAO = _wordsDAO;
        }

        @Override
        protected Void doInBackground(EngWordRusWordCrossRef... words) {
            wordsDAO.insert(words[0]);
            return null;
        }
    }

    public static class InsertRusWordTask extends AsyncTask<RusWord, Void, Long> {
        private WordsDAO wordsDAO;

        private InsertRusWordTask(WordsDAO _wordsDAO) {
            wordsDAO = _wordsDAO;
        }

        @Override
        protected Long doInBackground(RusWord... words) {
            long wordId = wordsDAO.insert(words[0]);
            return wordId;
        }
    }

    public static class DeleteEngWordTask extends AsyncTask<EngWord, Void, Integer> {
        private WordsDAO wordsDAO;

        private DeleteEngWordTask(WordsDAO _wordsDAO) {
            wordsDAO = _wordsDAO;
        }

        @Override
        protected Integer doInBackground(EngWord... words) {
            int wordId = wordsDAO.delete(words[0]);
            return wordId;
        }
    }

    public static class DeleteRusWordTask extends AsyncTask<RusWord, Void, Integer> {
        private WordsDAO wordsDAO;

        private DeleteRusWordTask(WordsDAO _wordsDAO) {
            wordsDAO = _wordsDAO;
        }

        @Override
        protected Integer doInBackground(RusWord... words) {
            int wordId = wordsDAO.delete(words[0]);
            return wordId;
        }
    }

    public static class DeleteEngWordRusWordCrossRefTask extends AsyncTask<EngWordRusWordCrossRef, Void, Void> {
        private WordsDAO wordsDAO;

        private DeleteEngWordRusWordCrossRefTask(WordsDAO _wordsDAO) {
            wordsDAO = _wordsDAO;
        }

        @Override
        protected Void doInBackground(EngWordRusWordCrossRef... words) {
            wordsDAO.delete(words[0]);
            return null;
        }
    }

    public static class FindWordBySpellingTask extends AsyncTask<String, Void, List<WordTuple>> {
        private WordsDAO wordsDAO;

        private FindWordBySpellingTask(WordsDAO _wordsDAO) {
            wordsDAO = _wordsDAO;
        }

        protected List<WordTuple> doInBackground(String... strings) {
            List<WordTuple> word = wordsDAO.findWordBySpelling(strings[0]);
            return word;
        }
    }

    public static class FindEngWordWithTranslationsBySpellingTask extends AsyncTask<String, Void, EngWordWithTranslations> {
        private WordsDAO wordsDAO;

        private FindEngWordWithTranslationsBySpellingTask(WordsDAO _wordsDAO) {
            wordsDAO = _wordsDAO;
        }

        @Override
        protected EngWordWithTranslations doInBackground(String... strings) {
            EngWordWithTranslations word = wordsDAO.findEngWordWithTranslationsBySpelling(strings[0]);
            return word;
        }
    }

    public static class FindRusWordWithTranslationsBySpellingTask extends AsyncTask<String, Void, RusWordWithTranslations> {
        private WordsDAO wordsDAO;

        private FindRusWordWithTranslationsBySpellingTask(WordsDAO _wordsDAO) {
            wordsDAO = _wordsDAO;
        }

        @Override
        protected RusWordWithTranslations doInBackground(String... strings) {
            RusWordWithTranslations word = wordsDAO.findRusWordWithTranslationsBySpelling(strings[0]);
            return word;
        }
    }
    //endregion
}
