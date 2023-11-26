package grape

import com.mongodb.kotlin.client.coroutine.MongoClient
import kotlinx.serialization.Serializable

@Serializable
class MongoDatabaseConfiguration(
    val url: String,
    val name: String,
    val maxHealthCheckToken: Int? = null
) {
    fun toDb() = MongoClient.create(url).getDatabase(name)

    fun toClient() = MongoClient.create(url)
}