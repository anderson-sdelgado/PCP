package br.com.usinasantafe.pcp.external.room

import android.content.Context
import androidx.room.*

fun provideRoomTest(appContext: Context): AppDatabaseRoom {
    return Room.inMemoryDatabaseBuilder(
        appContext, AppDatabaseRoom::class.java).allowMainThreadQueries().build()
}
