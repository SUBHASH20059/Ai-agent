package com.example.data

import androidx.room.*
import kotlinx.coroutines.flow.Flow

/**
 * Represents a prebuilt or user-created automation tool designed in Python.
 */
@Entity(tableName = "automation_tools")
data class AutomationTool(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val name: String,
    val description: String,
    val pythonScript: String,
    val category: String, // e.g., "System", "Media", "Security", "Web Scraping"
    val sampleTrigger: String,
    val isSystemTool: Boolean = true
)

/**
 * Represents a record of a voice or text command compiled and simulated in Aetheris.
 */
@Entity(tableName = "command_logs")
data class CommandLog(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val prompt: String,
    val generatedScript: String,
    val timestamp: Long = System.currentTimeMillis(),
    val status: String, // "SUCCESS", "PENDING", "FAILED"
    val durationMs: Long = 0,
    val outputLog: String
)

@Dao
interface AutomationDao {
    @Query("SELECT * FROM automation_tools ORDER BY id ASC")
    fun getAllTools(): Flow<List<AutomationTool>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTool(tool: AutomationTool)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTools(tools: List<AutomationTool>)

    @Query("DELETE FROM automation_tools WHERE id = :id")
    suspend fun deleteToolById(id: Int)

    @Query("SELECT * FROM command_logs ORDER BY timestamp DESC")
    fun getAllLogs(): Flow<List<CommandLog>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertLog(log: CommandLog): Long

    @Query("DELETE FROM command_logs")
    suspend fun clearLogs()
}
