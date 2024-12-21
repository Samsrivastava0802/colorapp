package com.samridhi.colorapp.di

import android.content.Context
import androidx.room.Room
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.samridhi.colorapp.data.local.db.dao.ColorDao
import com.samridhi.colorapp.data.local.db.database.AppDatabase
import com.samridhi.colorapp.data.network.repository.ColorRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    fun provideDatabase(@ApplicationContext context: Context): AppDatabase =
        Room.databaseBuilder(context, AppDatabase::class.java, "app_database").build()

    @Provides
    fun provideColorDao(database: AppDatabase): ColorDao = database.colorDao()

    @Provides
    fun provideFirebaseDatabase(): DatabaseReference =
        FirebaseDatabase.getInstance().reference

    @Provides
    fun provideColorRepository(
        colorDao: ColorDao,
        firebaseDatabase: DatabaseReference
    ): ColorRepository =
        ColorRepository(colorDao, firebaseDatabase)
}