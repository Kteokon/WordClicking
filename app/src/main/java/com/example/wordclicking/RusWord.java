package com.example.wordclicking;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.Index;
import androidx.room.PrimaryKey;

@Entity(tableName = "RusWord"/*, indices = {@Index(value = {"spelling"}, unique = true)}*/)
public class RusWord extends Word {
    @PrimaryKey(autoGenerate = true)
    private long rusWordId;

    public RusWord(@NonNull String spelling) {
        super(spelling);
    }

    public long getRusWordId() {
        return this.rusWordId;
    }
    public void setRusWordId(long _rusWordId) {
        this.rusWordId = _rusWordId;
    }
}
