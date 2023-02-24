package com.example.wordclicking;

import androidx.room.Embedded;
import androidx.room.Junction;
import androidx.room.Relation;

import java.util.List;

public class RusWordWithTranslations {
    @Embedded
    public RusWord word;
    @Relation(
            parentColumn = "rusWordId",
            entityColumn = "engWordId",
            associateBy = @Junction(EngWordRusWordCrossRef.class)
    )
    public List<EngWord> translations;
}
