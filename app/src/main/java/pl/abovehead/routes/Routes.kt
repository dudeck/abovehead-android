package pl.abovehead.routes

sealed class Routes(val route : String) {
    object News : Routes("news_route")
    object Pictures : Routes("pictures_route")
    object ShoppingCart : Routes("shopping_cart_route")
}