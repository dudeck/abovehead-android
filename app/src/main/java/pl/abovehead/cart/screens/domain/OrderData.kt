package pl.abovehead.cart.screens.domain

data class OrderData(
    val name: String,
    val surname: String,
    val phoneNumber: String,
    val promoCode: String,
    val addLogo: Boolean,
    val addTitle: Boolean
)
