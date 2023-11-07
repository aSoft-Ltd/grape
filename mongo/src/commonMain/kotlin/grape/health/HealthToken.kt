package grape.health

import java.time.LocalDateTime
import org.bson.codecs.pojo.annotations.BsonId
import org.bson.types.ObjectId

data class HealthToken(
    val time: LocalDateTime = LocalDateTime.now(),
    @BsonId val uid: ObjectId? = null
)