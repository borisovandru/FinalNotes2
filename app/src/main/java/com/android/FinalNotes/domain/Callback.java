package com.android.FinalNotes.domain;

public interface Callback<T> {

    void onSuccess(T result);
}