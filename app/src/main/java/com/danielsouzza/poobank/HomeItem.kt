package com.danielsouzza.poobank

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes

data class HomeItem(
    val id: Int,
    @DrawableRes val drawableId: Int,
    @StringRes val textStringId: Int
)

data class TrfItem(
    val id: Int,
    val textStringId: String
)

data class KeysItem(
    val textTypeKeyId: String,
    val textKeyId: String
)