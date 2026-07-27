package br.com.usinasantafe.pcp.external.room

import android.content.Context
import androidx.room.*

fun provideRoomTest(appContext: Context): DatabaseRoom {
    return Room.inMemoryDatabaseBuilder(
        appContext, DatabaseRoom::class.java).allowMainThreadQueries().build()
}
