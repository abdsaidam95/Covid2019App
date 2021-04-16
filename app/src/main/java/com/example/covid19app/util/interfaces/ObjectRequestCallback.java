package com.example.covid19app.util.interfaces;

public interface ObjectRequestCallback<T> {

    void onSuccess(T object);

    void onFailed();
}


