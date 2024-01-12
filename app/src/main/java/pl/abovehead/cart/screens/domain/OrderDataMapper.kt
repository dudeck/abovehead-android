package pl.abovehead.cart.screens.domain

import android.content.res.Resources
import pl.abovehead.R
import javax.inject.Inject


class OrderDataMapper @Inject constructor() {
    fun mapData(orderData: OrderData, resources: Resources): String {
        val ordersStringBuilder = StringBuilder().apply {
            append(
                "${resources.getString(R.string.name)}: ${orderData.name}, " +
                        "${resources.getString(R.string.surname)}: ${orderData.surname}, " +
                        "${resources.getString(R.string.email)}: ${orderData.email}, " +
                        "${resources.getString(R.string.phone)}: ${orderData.phoneNumber}, " +
                        "${resources.getString(R.string.addLogo)}: ${
                            translateBoolToString(
                                orderData.addLogo,
                                resources
                            )
                        }, " +
                        "${resources.getString(R.string.addTitle)}: ${
                            translateBoolToString(
                                orderData.addTitle,
                                resources
                            )
                        }, " +
                        "${resources.getString(R.string.promo_code)}: ${orderData.promoCode}\n\n"
            )
        }
        return ordersStringBuilder.toString()
    }
}

private fun translateBoolToString(value: Boolean, resources: Resources): String {
    return if (value) {
        resources.getString(R.string.yes)
    } else {
        resources.getString(R.string.no)
    }
}