package co.joebirch.remote

import co.joebirch.data.model.ProjectEntity
import co.joebirch.data.ProjectsDataStore
import co.joebirch.remote.mapper.ProjectsResponseModelMapper
import co.joebirch.remote.service.GithubTrendingService
import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Single
import javax.inject.Inject

class ProjectsRemoteImpl @Inject constructor(
        private val service: GithubTrendingService,
        private val mapper: ProjectsResponseModelMapper)
    : ProjectsDataStore {

    override fun getProjects(): Flowable<List<ProjectEntity>> {
        return service.searchRepositories("language:kotlin", "stars", "desc")
                .map {
                    it.items.map { mapper.mapFromModel(it) }
                }
    }

    override fun saveProjects(projects: List<ProjectEntity>): Completable {
        throw UnsupportedOperationException("Saving projects isn't supported here...")
    }

    override fun clearProjects(): Completable {
        throw UnsupportedOperationException("Clearing projects isn't supported here...")
    }

    override fun getBookmarkedProjects(): Flowable<List<ProjectEntity>> {
        throw UnsupportedOperationException("Getting bookmarked projects isn't supported here...")
    }

    override fun setProjectAsBookmarked(projectId: String): Completable {
        throw UnsupportedOperationException("Setting bookmarks isn't supported here...")
    }

    override fun setProjectAsNotBookmarked(projectId: String): Completable {
        throw UnsupportedOperationException("Setting bookmarks isn't supported here...")
    }

    override fun areProjectsCached(): Single<Boolean> {
        throw UnsupportedOperationException("Are projects cached isn't supported here...")
    }

    override fun setLastCacheTime(lastCache: Long): Completable {
        throw UnsupportedOperationException("Setting last cache time isn't supported here...")
    }

    override fun isProjectsCacheExpired(): Single<Boolean> {
        throw UnsupportedOperationException("Is projects cache expired isn't supported here...")
    }
}