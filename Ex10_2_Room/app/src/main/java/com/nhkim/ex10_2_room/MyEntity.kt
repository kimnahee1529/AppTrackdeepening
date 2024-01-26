package com.nhkim.ex10_2_room

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "student_table")    // 테이블 이름을 student_table로 지정함
data class Student (
    @PrimaryKey
    @ColumnInfo(name = "student_id")
    val id: Int,
    val name: String
)