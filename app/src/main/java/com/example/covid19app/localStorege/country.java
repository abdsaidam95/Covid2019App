package com.example.covid19app.localStorege;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "userinfo")
public class country {
    @PrimaryKey(autoGenerate = true) @ColumnInfo(name = "id")
    private int id=0;
    @ColumnInfo(name = "nameCountry")
    private String nameCountry;
    @ColumnInfo(name = "TextHealth")
    private String TextHealth;
    @ColumnInfo(name = "TextDeath")
    private String TextDeath;

    public country() {
    }

    public country(int id, String nameCountry, String textHealth, String textDeath) {
        this.id = id;
        this.nameCountry = nameCountry;
        TextHealth = textHealth;
        TextDeath = textDeath;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNameCountry() {
        return nameCountry;
    }

    public void setNameCountry(String nameCountry) {
        this.nameCountry = nameCountry;
    }

    public String getTextHealth() {
        return TextHealth;
    }

    public void setTextHealth(String textHealth) {
        TextHealth = textHealth;
    }

    public String getTextDeath() {
        return TextDeath;
    }

    public void setTextDeath(String textDeath) {
        TextDeath = textDeath;
    }
}
