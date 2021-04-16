package com.example.covid19app.util.interfaces;

import java.util.List;

public interface ListRequestCallback<T> {

    void onSuccess(List<T> objects);

    void onFailed();
}

