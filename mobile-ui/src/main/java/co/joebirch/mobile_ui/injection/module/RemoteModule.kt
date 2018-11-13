package co.joebirch.mobile_ui.injection.module

import co.joebirch.data.ProjectsDataStore
import co.joebirch.data.Remote
import co.joebirch.mobile_ui.BuildConfig
import co.joebirch.remote.ProjectsRemoteImpl
import co.joebirch.remote.service.GithubTrendingService
import co.joebirch.remote.service.GithubTrendingServiceFactory
import dagger.Module
import dagger.Provides
import javax.inject.Qualifier

@Module
abstract class RemoteModule {

    @Module
    companion object {
        @Provides
        @JvmStatic
        fun provideGithubService(): GithubTrendingService {
            return GithubTrendingServiceFactory.makeGithubTrendingService(BuildConfig.DEBUG)
        }

        @Provides
        @JvmStatic
        @Remote
        fun providesProjectsRemote(projectsRemote: ProjectsRemoteImpl): ProjectsDataStore {
            return projectsRemote
        }
    }
}
