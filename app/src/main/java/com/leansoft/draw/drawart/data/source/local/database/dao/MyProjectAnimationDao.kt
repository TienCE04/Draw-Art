package com.leansoft.draw.drawart.data.source.local.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.leansoft.draw.drawart.data.source.local.database.entity.FrameImageEntity
import com.leansoft.draw.drawart.data.source.local.database.entity.ProjectEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface MyProjectAnimationDao {
    @Insert
    suspend fun insert(item: ProjectEntity): Long

    @Delete
    suspend fun delete(item: ProjectEntity)

    @Update
    suspend fun update(item: ProjectEntity)

    @Query("SELECT * FROM my_project ORDER BY createdAt DESC")
    fun getAll(): Flow<List<ProjectEntity>>

    @Query("SELECT * FROM frame_images WHERE projectId = :idProject")
    fun getProjectById(idProject: Int): Flow<List<FrameImageEntity>>
    @Insert
    suspend fun insertFrame(item: FrameImageEntity)

    @Insert
    suspend fun insertFrames(items: List<FrameImageEntity>)

    @Query("UPDATE frame_images SET imagePath = :path WHERE id = :id")
    suspend fun updateImagePath(id: Int, path: String)

    @Query("DELETE FROM frame_images WHERE id = :id")
    suspend fun deleteFrameById(id: Int)

    @Query("DELETE FROM frame_images WHERE projectId = :projectId")
    suspend fun deleteFramesByProjectId(projectId: Int)

    @Query("SELECT * FROM frame_images WHERE projectId = :projectId ORDER BY id ASC")
    fun getFramesByProjectId(projectId: Int): Flow<List<FrameImageEntity>>

    @Query("SELECT * FROM frame_images WHERE id = :frameId")
    suspend fun getFrameById(frameId: Int): FrameImageEntity?
}