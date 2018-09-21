package com.shaadi.android.data.retrofitwrapper

import okhttp3.ResponseBody
import java.net.SocketException
import java.net.UnknownHostException

object ApiUtils{


    const val ERR_DEFAULT_MSG="Something went wrong! Please try again later."
    const val ERR_NO_INTERNET="No Internet Connection!"
    const val ERR_TIMEOUT="Connection Timeout"

    @JvmStatic
    fun getErrorMessage(t:Exception):String{
        if (t is UnknownHostException) {
            return ERR_NO_INTERNET
        }

        if (t is SocketException) {
            return ERR_TIMEOUT;
        }

        return ERR_DEFAULT_MSG;
    }


    @JvmStatic
    fun parseError(error: ResponseBody):String{
        return ERR_DEFAULT_MSG;
    }
}
