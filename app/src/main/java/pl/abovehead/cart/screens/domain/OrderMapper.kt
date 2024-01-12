package pl.abovehead.cart.screens.domain

import android.content.res.Resources
import pl.abovehead.R
import pl.abovehead.cart.screens.domain.FrameColor.White
import javax.inject.Inject


class OrderMapper @Inject constructor() {
    fun mapOrder(orders: List<OrderItem>, resources: Resources): String {
        val ordersStringBuilder = StringBuilder()
        for (order in orders) {
            ordersStringBuilder.append(
                "${resources.getString(R.string.title)}: ${order.title}, " +
                        "${resources.getString(R.string.size)} ${order.frameSize.string}, " +
                        "${resources.getString(R.string.color)} ${
                            translateFrameColorToString(
                                order.frameColor,
                                resources
                            )
                        }\n\n"
            )
        }

        return ordersStringBuilder.toString()
    }
}

fun translateFrameColorToString(value: FrameColor, resources: Resources): String {
    return when (value) {
        White -> resources.getString(R.string.white)
        else -> resources.getString(R.string.black)
    }
}