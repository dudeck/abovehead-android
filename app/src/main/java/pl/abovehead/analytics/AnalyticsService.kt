package pl.abovehead.analytics

import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.analytics.ktx.analytics
import com.google.firebase.analytics.logEvent
import com.google.firebase.ktx.Firebase
import javax.inject.Inject

class AnalyticsService @Inject constructor() {
    private val analytics = Firebase.analytics

    fun logSubmitButtonClickedEvent() {
        analytics.logEvent(FirebaseAnalytics.Event.PURCHASE) {
            param(FirebaseAnalytics.Param.LEVEL_NAME, "Submit Button Clicked")
        }
    }

    fun logOrderButtonClickEvent() {
        analytics.logEvent(FirebaseAnalytics.Event.PURCHASE) {
            param(FirebaseAnalytics.Param.LEVEL_NAME, "Order Button Clicked")
        }
    }

    fun logAddOrderEvent(title:String) {
        analytics.logEvent(FirebaseAnalytics.Event.ADD_TO_CART) {
            param(FirebaseAnalytics.Param.ITEM_NAME, title)
        }
    }

    fun logNASAFeedsFetched() {
        analytics.logEvent("NASA_feeds_fetched", null)
    }

    fun logNASAFeedFetchError(exceptionMessage:String) {
        analytics.logEvent("NASA_feeds_fetch_error"){
            param("ExceptionMessage", exceptionMessage)
        }
    }

    fun logBlogPostDetailsAction(postId: String) {
        analytics.logEvent("BLOG_post_details"){
            param("PostId", postId)
        }
    }
}