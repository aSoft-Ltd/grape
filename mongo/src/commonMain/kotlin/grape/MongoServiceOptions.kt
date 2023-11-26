package grape

import com.mongodb.kotlin.client.coroutine.MongoClient
import com.mongodb.kotlin.client.coroutine.MongoDatabase
import kotlinx.coroutines.CoroutineScope

class MongoServiceOptions(
    val scope: CoroutineScope,
    val mongo: MongoClient,
    val maxHealthCheckTokens: Int
)