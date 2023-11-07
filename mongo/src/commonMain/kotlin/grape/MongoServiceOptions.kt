package grape

import com.mongodb.kotlin.client.coroutine.MongoDatabase
import kotlinx.coroutines.CoroutineScope

class MongoServiceOptions(
    val scope: CoroutineScope,
    val db: MongoDatabase,
    val maxTokens: Int
)