package com.example.wordclicking;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.Index;
import androidx.room.PrimaryKey;

@Entity(tableName = "EngWord"/*, indices = {@Index(value = {"spelling"}, unique = true)}*/)
public class EngWord extends Word{
    @PrimaryKey(autoGenerate = true)
    private long engWordId;

    public EngWord(@NonNull String spelling) {
        super(spelling);
    }

    public long getEngWordId() {
        return this.engWordId;
    }
    public void setEngWordId(long _engWordId) {
        this.engWordId = _engWordId;
    }
}
