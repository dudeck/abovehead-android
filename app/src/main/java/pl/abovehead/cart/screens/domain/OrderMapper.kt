package pl.abovehead.cart.screens.domain

import javax.inject.Inject


class OrderMapper @Inject constructor(){
    fun mapOrder(orders: List<OrderItem>): String{
        val ordersStringBuilder = StringBuilder()
        for (order in orders) {
            ordersStringBuilder.append("Title: ${order.title}, Frame Size: ${order.frameSize}, Frame Color: ${order.frameColor}, Add Logo: ${order.addLogo}, Add Title: ${order.addTitle}\n")
        }

        return ordersStringBuilder.toString()
    }
}