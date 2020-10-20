import org.gradle.api.artifacts.dsl.RepositoryHandler

/**
 * Author: fanyafeng
 * Date: 2020/10/19 19:32
 * Email: fanyafeng@live.cn
 * Description:
 */
object AllReposDependencies {
    val addRepos: (handler: RepositoryHandler) -> Unit = {
        it.google()
        it.jcenter()
        it.mavenCentral()
        it.maven {
            setUrl("http://maven.aliyun.com/nexus/content/groups/public/")
        }
        it.maven {
            setUrl("https://dl.bintray.com/fanyafeng/ripple")
        }
        it.maven {
            setUrl("http://maven.aliyun.com/nexus/content/groups/public/")
        }
    }
}