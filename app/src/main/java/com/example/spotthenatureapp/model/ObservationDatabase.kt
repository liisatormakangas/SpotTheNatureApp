package com.example.spotthenatureapp.model

import androidx.room.*

@Entity
data class ObservationEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val type: String,
    val name: String,
    val description: String,
    val scientificName: String,
    val date: String,
    val latitude: Double,
    val longitude: Double,
    val optionalLocation: String
)

@Dao
interface ObservationDao {
    @Insert
    suspend fun insertObservation(observation: ObservationEntity)

    @Query("SELECT * FROM ObservationEntity ORDER BY id DESC")
    suspend fun getAllObservations(): List<ObservationEntity>

}

@Database(entities = [ObservationEntity::class], version = 1)
abstract class ObservationDatabase : RoomDatabase() {
    abstract fun observationDao(): ObservationDao
}
