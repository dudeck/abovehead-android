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
                        "${resources.getString(R.string.size)}: ${order.frameSize.string}, " +
                        "${resources.getString(R.string.color)}: ${
                            translateFrameColorToString(
                                order.frameColor,
                                resources
                            )
                        }, " +
                        "${resources.getString(R.string.addLogo)}: ${
                            translateBoolToString(
                                order.addLogo,
                                resources
                            )
                        }, " +
                        "${resources.getString(R.string.addTitle)}: ${
                            translateBoolToString(
                                order.addTitle,
                                resources
                            )
                        }\n\n"
            )
        }

        return ordersStringBuilder.toString()
    }

    private fun translateBoolToString(value: Boolean, resources: Resources): String {
        return if (value) {
            resources.getString(R.string.yes)
        } else {
            resources.getString(R.string.no)
        }
    }
}

fun translateFrameColorToString(value: FrameColor, resources: Resources): String {
    return when (value) {
        White -> resources.getString(R.string.white)
        else -> resources.getString(R.string.black)
    }
}