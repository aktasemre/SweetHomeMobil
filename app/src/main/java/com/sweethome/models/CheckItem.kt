@Parcelize
data class CheckItem(
    val id: String,
    val title: String,
    val description: String = "",
    val photoUrl: String,
    val timestamp: Long = System.currentTimeMillis(),
    val location: String? = null
) : Parcelable {
    init {
        require(id.isNotBlank()) { "ID boş olamaz" }
        require(title.isNotBlank()) { "Başlık boş olamaz" }
        require(photoUrl.isNotBlank()) { "Fotoğraf URL'si boş olamaz" }
    }
} 