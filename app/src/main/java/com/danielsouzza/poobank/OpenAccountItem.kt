package com.danielsouzza.poobank

import androidx.annotation.StringRes

data class OpenAccountItem(
    val id: Int,
    @StringRes val hintStringId: Int,
    @StringRes val textStringId: Int,
)
