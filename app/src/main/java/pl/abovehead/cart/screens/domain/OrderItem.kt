package pl.abovehead.cart.screens.domain
enum class FrameSize { Small, Big }
enum class FrameColor { White, Black }
data class OrderItem(
    val title: String,
    val frameSize: FrameSize = FrameSize.Big,
    val frameColor: FrameColor = FrameColor.White,
    val image: String?,
    val addLogo: Boolean = true,
    val addTitle: Boolean = true,
)