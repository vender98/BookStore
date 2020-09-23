package com.vender98.bookstore.dto

import com.google.gson.annotations.SerializedName

enum class ResponseStatus {

    @SerializedName(value = "success")
    SUCCESS,

    @SerializedName(value = "error")
    ERROR

}
