package com.developersbreach.developersbreach.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
class Tags(
    val id: Int,
    val name: String
) : Parcelable