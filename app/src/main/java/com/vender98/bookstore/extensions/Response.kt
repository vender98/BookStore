package com.vender98.bookstore.extensions

import com.vender98.bookstore.dto.Response
import com.vender98.bookstore.dto.ResponseStatus
import java.io.IOException

fun <T> Response<T>.unpack(): T = if (this.status == ResponseStatus.SUCCESS) {
    this.data!!
} else {
    throw IOException(this.reason)
}
