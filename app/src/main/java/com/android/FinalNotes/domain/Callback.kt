package com.android.FinalNotes.domain

interface Callback<T> {
    fun onSuccess(result: T)
}