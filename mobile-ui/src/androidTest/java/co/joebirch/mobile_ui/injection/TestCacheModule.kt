package co.joebirch.mobile_ui.injection

import android.app.Application
import co.joebirch.cache.db.ProjectsDatabase
import co.joebirch.data.Cache
import co.joebirch.data.ProjectsDataStore
import com.nhaarman.mockito_kotlin.mock
import dagger.Module
import dagger.Provides

@Module
object TestCacheModule {

    @Provides
    @JvmStatic
    fun provideDatabase(application: Application): ProjectsDatabase {
        return ProjectsDatabase.getInstance(application)
    }

    @Provides
    @JvmStatic
    @Cache
    fun provideProjectsCache(): ProjectsDataStore {
        return mock()
    }

}