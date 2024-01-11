package pl.abovehead.cart.screens.domain

enum class FrameSize(val string: String) { Small("21cm x 30 cm"), Big("30cm x 40cm") }
enum class FrameColor { White, Black }
data class OrderItem(
    val title: String,
    val frameSize: FrameSize = FrameSize.Big,
    val frameColor: FrameColor = FrameColor.White,
    val image: String?,
    val addLogo: Boolean = true,
    val addTitle: Boolean = true,
)