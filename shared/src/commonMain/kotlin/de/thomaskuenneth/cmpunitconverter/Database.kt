package de.thomaskuenneth.cmpunitconverter

import androidx.room.*
import androidx.sqlite.driver.bundled.BundledSQLiteDriver
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.flow.Flow

// Learn more at https://developer.android.com/kotlin/multiplatform/room

@Database(entities = [HistoryEntity::class], version = 1)
@ConstructedBy(AppDatabaseConstructor::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun getHistoryDao(): HistoryDao
}

@Suppress("NO_ACTUAL_FOR_EXPECT")
expect object AppDatabaseConstructor : RoomDatabaseConstructor<AppDatabase> {
    override fun initialize(): AppDatabase
}

fun getRoomDatabase(
    builder: RoomDatabase.Builder<AppDatabase>
): AppDatabase {
    return builder
        .setDriver(BundledSQLiteDriver())
        .setQueryCoroutineContext(Dispatchers.IO)
        .build()
}

@Dao
interface HistoryDao {
    @Insert
    suspend fun insert(item: HistoryEntity)

    @Query("SELECT * FROM HistoryEntity")
    fun getAllAsFlow(): Flow<List<HistoryEntity>>

    @Query("DELETE FROM HistoryEntity")
    suspend fun deleteAll()
}

@Entity
data class HistoryEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val sourceUnit: UnitsAndScales,
    val sourceValue: Float,
    val destinationUnit: UnitsAndScales,
    val destinationValue: Float,
    val timestamp: Long
)
