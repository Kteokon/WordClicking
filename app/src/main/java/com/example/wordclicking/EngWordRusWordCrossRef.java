package com.example.wordclicking;

import androidx.room.Entity;

@Entity(primaryKeys = {"engWordId", "rusWordId"})
public class EngWordRusWordCrossRef {
    private int engWordId;
    private int rusWordId;

    public int getEngWordId() {
        return this.engWordId;
    }
    public void setEngWordId(int _engWordId) {
        this.engWordId = _engWordId;
    }

    public int getRusWordId() {
        return this.rusWordId;
    }
    public void setRusWordId(int _rusWordId) {
        this.rusWordId = _rusWordId;
    }
}
