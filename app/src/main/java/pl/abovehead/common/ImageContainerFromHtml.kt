package pl.abovehead.common

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.text.Html
import android.widget.TextView
import coil.Coil
import coil.ImageLoader
import coil.request.ImageRequest

open class CoilImageGetter(
    private val textView: TextView,
    private val imageLoader: ImageLoader = Coil.imageLoader(textView.context),
    private val sourceModifier: ((source: String) -> String)? = null,
    private val widthScreen: Int = 320
) : Html.ImageGetter {

    override fun getDrawable(source: String): Drawable {
        val finalSource = sourceModifier?.invoke(source) ?: source
        val targetBitmap = Bitmap.createBitmap(widthScreen, widthScreen, Bitmap.Config.ARGB_8888)
        val drawablePlaceholder = DrawablePlaceHolder(textView.context, targetBitmap)
        imageLoader.enqueue(ImageRequest.Builder(textView.context).data(finalSource).apply {
            target { drawable ->
                drawablePlaceholder.updateDrawable(drawable, widthScreen)
                // invalidating the drawable doesn't seem to be enough...
                textView.text = textView.text
            }
        }.build())
        // Since this loads async, we return a "blank" drawable, which we update
        // later
        return drawablePlaceholder
    }

    private class DrawablePlaceHolder(context: Context, bitmap: Bitmap) :
        BitmapDrawable(context.getResources(), bitmap) {

        private var drawable: Drawable? = null

        override fun draw(canvas: Canvas) {
            drawable?.draw(canvas)
        }

        fun updateDrawable(drawable: Drawable, widthScreen: Int) {
            this.drawable = drawable
            var height = drawable.intrinsicHeight
            val aspectRatio: Double = drawable.intrinsicWidth.toDouble() / widthScreen
            height = (height / aspectRatio).toInt()
            drawable.setBounds(0, 0, widthScreen, height)
            setBounds(0, 0, widthScreen, height)
        }
    }
}