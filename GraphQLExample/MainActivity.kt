package com.janfranco.graphqlexample

import GetBooksQuery
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.apollographql.apollo.ApolloCall
import com.apollographql.apollo.ApolloClient
import com.apollographql.apollo.api.Response
import com.apollographql.apollo.exception.ApolloException
import okhttp3.OkHttpClient

private const val TAG = "GRAPH_QUERY"

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setupApollo()
                .query(GetBooksQuery
                        .builder()
                        .build())
                .enqueue(object : ApolloCall.Callback<GetBooksQuery.Data>() {
                    override fun onResponse(response: Response<GetBooksQuery.Data>) {
                        val books = response.data?.books()
                        books?.let { bookList ->
                            bookList.forEach { book ->
                                Log.d(TAG, "onResponse: ${book.name()}")
                            }
                        }
                    }

                    override fun onFailure(e: ApolloException) {
                        Log.d(TAG, "onFailure: ${e.localizedMessage}")
                    }
                })
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

}
