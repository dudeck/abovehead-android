package pl.abovehead

import android.util.Log
import com.apollographql.apollo3.ApolloClient
import com.apollographql.apollo3.network.okHttpClient
import kotlinx.coroutines.delay
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response

val apolloClient = ApolloClient.Builder()
    .serverUrl("https://abovehead.pl/graphql")
    .okHttpClient(
        OkHttpClient.Builder()
            .build()
    )
    .build()
