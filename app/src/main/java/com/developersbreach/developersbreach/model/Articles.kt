package com.developersbreach.developersbreach.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize


@Parcelize
data class Articles(
    val id: Int,
    val title: String
) : Parcelable


