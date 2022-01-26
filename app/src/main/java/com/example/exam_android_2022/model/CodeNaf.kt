package com.example.exam_android_2022.model

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import java.util.*

@Entity
    (tableName = "code_NAF_APE")
data class CodeNaf(

    @PrimaryKey var CodeNAFAPE:String,
    var Description:String?,
    var Descriptionsection:String?,
    var Section:Int?,

)
