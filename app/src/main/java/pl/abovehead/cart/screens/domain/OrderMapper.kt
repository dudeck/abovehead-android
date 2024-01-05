package pl.abovehead.cart.screens.domain

import javax.inject.Inject


class OrderMapper @Inject constructor(){
    fun mapOrder(orders: List<OrderItem>): String{
        val filteredOrders = orders.filter {
            it.frameSize == FrameSize.Big && it.frameColor == FrameColor.White && it.addLogo && it.addTitle
        }

        val ordersStringBuilder = StringBuilder()
        for (order in filteredOrders) {
            ordersStringBuilder.append("Title: ${order.title}, Frame Size: ${order.frameSize}, Frame Color: ${order.frameColor}, Add Logo: ${order.addLogo}, Add Title: ${order.addTitle}\n")
        }

        return ordersStringBuilder.toString()
    }
}