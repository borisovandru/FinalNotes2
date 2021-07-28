package com.android.FinalNotes.domain

import android.os.Parcel
import android.os.Parcelable
import android.os.Parcelable.Creator
import java.util.*

open class Note : Parcelable {
    val id: String?
    val title: String?
    val url: String?
    val date: Date

    constructor(id: String?, title: String?, url: String?, date: Date) {
        this.id = id
        this.title = title
        this.url = url
        this.date = date
    }

    protected constructor(`in`: Parcel) {
        id = `in`.readString()
        title = `in`.readString()
        url = `in`.readString()
        date = Date(`in`.readLong())
    }

    override fun describeContents(): Int {
        return 0
    }

    override fun writeToParcel(dest: Parcel, flags: Int) {
        dest.writeString(id)
        dest.writeString(title)
        dest.writeString(url)
        dest.writeLong(date.time)
    }

    companion object {
        @JvmField val CREATOR = object : Creator<Note?> {
            override fun createFromParcel(`in`: Parcel): Note {
                return Note(`in`)
            }

            override fun newArray(size: Int): Array<Note?> {
                return arrayOfNulls(size)
            }
        }
    }
}