package com.hfad.imdblogin.model;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

@Dao
public interface DaoAccess {


    //Insert information
    @Insert
    void addUser(User user);

}
