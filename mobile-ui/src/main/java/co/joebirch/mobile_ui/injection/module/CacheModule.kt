package co.joebirch.mobile_ui.injection.module

import android.app.Application
import co.joebirch.cache.ProjectsCacheImpl
import co.joebirch.cache.db.ProjectsDatabase
import co.joebirch.data.Cache
import co.joebirch.data.ProjectsDataStore
import dagger.Module
import dagger.Provides

@Module
abstract class CacheModule {

    @Module
    companion object {
        @Provides
        @JvmStatic
        fun providesDataBase(application: Application): ProjectsDatabase {
            return ProjectsDatabase.getInstance(application)
        }

        @Provides
        @JvmStatic
        @Cache
        fun providesProjectsCache(projectsCache: ProjectsCacheImpl): ProjectsDataStore {
            return projectsCache
        }
    }
}