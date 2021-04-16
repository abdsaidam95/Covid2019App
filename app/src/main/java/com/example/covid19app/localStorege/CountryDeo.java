package com.example.covid19app.localStorege;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;
@Dao
public interface CountryDeo {
    @Query("SELECT * FROM userinfo ORDER BY id ASC")
    public List<country>getCountry();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public void insertCountry(country country);
    @Query("DELETE From userinfo")
    public void deleteAllWords();



}
