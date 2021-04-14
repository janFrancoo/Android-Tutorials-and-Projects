package com.janfranco.graphqlexample

import GetBooksQuery
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.apollographql.apollo.ApolloClient
import com.apollographql.apollo.coroutines.await
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import okhttp3.OkHttpClient

private const val TAG = "GRAPH_QUERY"

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        GlobalScope.launch {
            getBooks()
        }
    }

    private fun setupApollo(): ApolloClient {
        val okHttp = OkHttpClient
                .Builder()
                .build()
        return ApolloClient.builder()
                .serverUrl("http://192.168.1.35:5000/graphql")
                .okHttpClient(okHttp)
                .build()
    }

    private suspend fun getBooks() {
        coroutineScope {
            val resp = setupApollo().query(GetBooksQuery()).await()
            Log.d(TAG, "getBooks: ${resp.data?.books()}")
        }
    }

}
