package pl.abovehead.common

import android.content.Context
import androidx.lifecycle.ViewModel
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdSize
import com.google.android.gms.ads.AdView
import dagger.hilt.android.lifecycle.HiltViewModel
import java.lang.ref.WeakReference
import javax.inject.Inject

@HiltViewModel
class AdViewModel @Inject constructor() : ViewModel() {
    private var adViewReference: WeakReference<AdView>? = null

    fun getAdView(context: Context): AdView {
        val adView = adViewReference?.get()
        if (adView == null) {
            val newAdView = AdView(context)
            newAdView.adUnitId = "ca-app-pub-9991692736822332/5681716521"
            newAdView.setAdSize(AdSize.BANNER)
            val adRequest = AdRequest.Builder().build()
            newAdView.loadAd(adRequest)
            adViewReference = WeakReference(newAdView)
            return newAdView
        }
        return adView
    }
}