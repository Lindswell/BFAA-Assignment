package com.example.bfaa_submission_1

import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.loopj.android.http.AsyncHttpClient
import com.loopj.android.http.AsyncHttpResponseHandler
import cz.msebera.android.httpclient.Header
import org.json.JSONArray

class MainViewModel: ViewModel() {

    val listGitUser = MutableLiveData<ArrayList<User>>()

    fun setUserList() {
        val list = ArrayList<User>()


        val client = AsyncHttpClient()
        val url = "https://api.github.com/users"

        client.addHeader("Authorization", "token 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")
        client.addHeader("User-Agent", "request")

        client.get(url, object :AsyncHttpResponseHandler(){
            override fun onSuccess(statusCode: Int, headers: Array<Header>, responseBody: ByteArray) {

                /*Log.d(TAG, result)*/
                try {
                    val result = String(responseBody)
                    val jsonArray = JSONArray(result)

                    for (i in 0 until jsonArray.length()) {
                        val jsonObject = jsonArray.getJSONObject(i)
                        val gitUser = User()
                        gitUser.name = jsonObject.getString("login")
                        gitUser.photo = jsonObject.getString("avatar_url")

                        list.add(gitUser)
                    }
                } catch (e:Exception) {
                    Log.d("Exception", e.message.toString())
                }
                listGitUser.postValue(list)
            }

            override fun onFailure(statusCode: Int, headers: Array<Header>, responseBody: ByteArray, error: Throwable) {
                Log.d("onFailure", error.message.toString())
            }

        })
    }

    fun getUserList() : LiveData<ArrayList<User>> {
        return listGitUser
    }
}
