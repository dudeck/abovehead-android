package pl.abovehead.cart.screens.domain

import javax.inject.Inject


class OrderDataMapper @Inject constructor(){
    fun mapData(orderData: OrderData): String{
        val ordersStringBuilder = StringBuilder().apply {
            append(
                "Name: ${orderData.name}, " +
                    "Surname: ${orderData.surname}, " +
                    "email: ${orderData.email}, " +
                    "tel: ${orderData.phoneNumber}\n\n")
        }
        return ordersStringBuilder.toString()
    }
}