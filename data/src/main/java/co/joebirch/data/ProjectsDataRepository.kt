package co.joebirch.data

import co.joebirch.data.mapper.ProjectMapper
import co.joebirch.domain.model.Project
import co.joebirch.domain.repository.ProjectsRepository
import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.functions.BiFunction
import javax.inject.Inject
import javax.inject.Qualifier

class ProjectsDataRepository @Inject constructor(
        private val mapper: ProjectMapper,
        @Cache private val cache: ProjectsDataStore,
        @Remote private val remote: ProjectsDataStore)
    : ProjectsRepository {

    override fun getProjects(): Observable<List<Project>> {
        return Observable.zip(cache.areProjectsCached().toObservable(),
                cache.isProjectsCacheExpired().toObservable(),
                BiFunction<Boolean, Boolean, Pair<Boolean, Boolean>> { areCached, isExpired ->
                    Pair(areCached, isExpired)
                })
                .flatMap {
                    if (it.first && !it.second) {
                        cache.getProjects().toObservable().distinctUntilChanged()
                    } else {
                        remote.getProjects().toObservable().distinctUntilChanged()
                    }
                }
                .flatMap { projects ->
                    cache.saveProjects(projects).andThen(Observable.just(projects))
                }
                .map {
                    it.map {
                        mapper.mapFromEntity(it)
                    }
                }
    }

    override fun bookmarkProject(projectId: String): Completable {
        return cache.setProjectAsBookmarked(projectId)
    }

    override fun unbookmarkProject(projectId: String): Completable {
        return cache.setProjectAsNotBookmarked(projectId)
    }

    override fun getBookmarkedProjects(): Observable<List<Project>> {
        return cache.getBookmarkedProjects().toObservable()
                .map { it.map { mapper.mapFromEntity(it) } }
    }

}

@Qualifier
@Retention(AnnotationRetention.RUNTIME)
annotation class Remote


@Qualifier
@Retention(AnnotationRetention.RUNTIME)
annotation class Cache