package pl.abovehead.routes

sealed class Routes(val route: String) {
    object News : Routes("news_route")
    object Pictures : Routes("pictures_route")
    object PostDetails : Routes("post_details_route")
    object ShoppingCart : Routes("shopping_cart_route")
    object Order : Routes("order_route")
    object OtherInfo : Routes("other_info_route")
}

sealed class SettingsRoutes(settingsRoute: String) : Routes(settingsRoute) {
    object PrivacyPolicy : SettingsRoutes("privacy_policy_route")
    object TermsOfUse : SettingsRoutes("terms_of_use_route")
    object About : SettingsRoutes("about_route")
}