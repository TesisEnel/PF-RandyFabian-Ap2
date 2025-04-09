package edu.ucne.fluentpath.data.di

import android.content.Context
import androidx.room.Room
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import edu.ucne.fluentpath.data.local.database.AppDataBase
import edu.ucne.fluentpath.data.remote.a.GramaticaApi
import edu.ucne.fluentpath.data.remote.a.PalabrasApi
import edu.ucne.fluentpath.data.remote.a.ProfesoresApi
import edu.ucne.fluentpath.data.remote.a.VocabulariosApi
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideApplicationContext(@ApplicationContext context: Context): Context {
        return context
    }

    @Provides
    @Singleton
    fun providesMoshi(): Moshi {
        return Moshi.Builder()
            .add(KotlinJsonAdapterFactory())
            .build()
    }

    @Provides
    @Singleton
    fun providesPalabraApi(moshi: Moshi): PalabrasApi {
        return Retrofit.Builder()
            .baseUrl("http://realg4life.somee.com")
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .build()
            .create(PalabrasApi::class.java)
    }

    @Provides
    @Singleton
    fun providesVocabularioApi(moshi: Moshi): VocabulariosApi {
        return Retrofit.Builder()
            .baseUrl("http://realg4life.somee.com")
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .build()
            .create(VocabulariosApi::class.java)
    }


    @Provides
    @Singleton
    fun providesGramaticaApi(moshi: Moshi): GramaticaApi {
        return Retrofit.Builder()
            .baseUrl("http://gpapiplus.somee.com")
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .build()
            .create(GramaticaApi::class.java)
    }

    @Provides
    @Singleton
    fun providesProfesorApi(moshi: Moshi): ProfesoresApi {
        return Retrofit.Builder()
            .baseUrl("http://gpapiplus.somee.com")
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .build()
            .create(ProfesoresApi::class.java)
    }


    @Provides
    @Singleton
    fun provideAppDatabase(@ApplicationContext context: Context): AppDataBase {
        return Room.databaseBuilder(
            context,
            AppDataBase::class.java,
            "FluentPath.db"
        )
            .fallbackToDestructiveMigration()
            .build()
    }

    @Provides
    @Singleton
    fun providePalabraDao(database: AppDataBase) = database.palabraDao()

    @Provides
    @Singleton
    fun provideVocabularioDao(database: AppDataBase) = database.vocabularioDao()

    @Provides
    @Singleton
    fun provideGramaticaDao(database: AppDataBase) = database.gramaticaDao()

    @Provides
    @Singleton
    fun provideProfesorDao(database: AppDataBase) = database.profesorDao()
}