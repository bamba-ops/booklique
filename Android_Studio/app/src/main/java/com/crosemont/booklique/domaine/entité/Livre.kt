import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import java.util.Date

@Parcelize
data class Livre(
    val isbn: String,
    val image_url: String,
    val titre: String,
    val description: String,
    val auteur: String,
    val editeur: String,
    val genre: String,
    val date_publication: Date,
    val nombre_pages: Int,
    var quantite: Int
) : Parcelable {

    fun estDisponible(): Boolean {
        return quantite > 0
    }
}
