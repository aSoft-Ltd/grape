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

    fun health() = options.scope.later {
        val (status, duration) = measureTimedValue {
            try {
                val col = options.db.getCollection<HealthToken>("health-check-token")
                col.insertOne(HealthToken())
                val tokens = col.find().toList()
                if (tokens.isEmpty()) return@measureTimedValue Status.UnHealthy
                if (tokens.size <= options.maxTokens) return@measureTimedValue Status.Healthy
                val token = tokens.first()
                col.deleteOne(eq("_id", token.uid))
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