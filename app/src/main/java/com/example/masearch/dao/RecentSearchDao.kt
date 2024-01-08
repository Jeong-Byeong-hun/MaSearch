package com.example.masearch.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.masearch.api.vo.LikeCharacterVo
import com.example.masearch.api.vo.RecentSearchVO

@Dao
interface RecentSearchDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertRecentName(recentSearchVO: RecentSearchVO)

    @Query("SELECT * FROM recent_table ORDER BY id DESC")
    fun getAllListName(): LiveData<List<RecentSearchVO>>

    @Query("DELETE FROM recent_table WHERE nickName = :nickName")
    suspend fun deleteRecentNameData(nickName: String)
}