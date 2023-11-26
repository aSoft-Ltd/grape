package grape

import com.mongodb.client.model.Filters.eq
import grape.health.HealthToken
import grape.health.HealthStatus
import koncurrent.later
import kotlin.time.measureTimedValue
import kotlinx.coroutines.flow.toList

class MongoService(private val options: MongoServiceOptions) {

    enum class Status {
        Healthy, UnHealthy
    }

    private val database by lazy {
        options.mongo.getDatabase("statuses")
    }

    private val collection by lazy {
        database.getCollection<HealthToken>("health_check_token")
    }
    fun health() = options.scope.later {
        val (status, duration) = measureTimedValue {
            try {
                collection.insertOne(HealthToken())
                val tokens = collection.find().toList()
                if (tokens.isEmpty()) return@measureTimedValue Status.UnHealthy
                if (tokens.size <= options.maxHealthCheckTokens) return@measureTimedValue Status.Healthy
                val token = tokens.first()
                collection.deleteOne(eq("_id", token.uid))
                Status.Healthy
            } catch (err: Throwable) {
                err.printStackTrace()
                Status.UnHealthy
            }
        }
        HealthStatus(
            status = status.name,
            millis = duration.inWholeMilliseconds.toString()
        )
    }
}