package com.example.wordclicking;

import androidx.room.Embedded;
import androidx.room.Junction;
import androidx.room.Relation;

import java.util.List;

public class EngWordWithTranslations {
    @Embedded
    public EngWord word;
    @Relation(
            parentColumn = "engWordId",
            entityColumn = "rusWordId",
            associateBy = @Junction(EngWordRusWordCrossRef.class)
    )
    public List<RusWord> translations;
}
