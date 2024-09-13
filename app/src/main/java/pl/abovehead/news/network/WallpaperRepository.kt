package pl.abovehead.news.network

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Log
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.OkHttpClient
import okhttp3.Request
import org.xmlpull.v1.XmlPullParser
import org.xmlpull.v1.XmlPullParserFactory
import pl.abovehead.common.Repository
import pl.abovehead.news.domain.RssItem
import java.io.IOException
import java.io.InputStream
import javax.inject.Inject

class WallpaperRepository @Inject constructor(
    private val okHttpClient: OkHttpClient,
) : Repository<Bitmap?> {
    override suspend fun fetch(url: String): Bitmap? {
       return withContext(Dispatchers.IO) {
            val request = Request.Builder().url(url).build()

            try {
                val response = okHttpClient.newCall(request).execute()
                if (response.isSuccessful) {
                    val inputStream = response.body?.byteStream()
                    if (inputStream != null) {
                        val bitmap = BitmapFactory.decodeStream(inputStream)
                        return@withContext bitmap
                    }
                }
            } catch (e: Exception) {
                Log.e("Wallpaper repository", "fetch: error:${e.message}", )// Handle exceptions (e.g., log error or show a message)
            }

           return@withContext null
        }
    }
}