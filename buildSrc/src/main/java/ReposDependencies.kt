import org.gradle.api.artifacts.dsl.RepositoryHandler

/**
 * Author: fanyafeng
 * Date: 2020/10/19 19:26
 * Email: fanyafeng@live.cn
 * Description:
 */
object ReposDependencies {
    val addRepos: (handler: RepositoryHandler) -> Unit = {
        it.google()
        it.jcenter()
        it.mavenCentral()
        it.jcenter {
            setUrl("https://jcenter.bintray.com/")
        }
        it.maven {
            setUrl("/Users/dmall/GitProject/ModuleSample/ripple_permission_plugin/repo")
        }
        it.maven {
            setUrl("https://plugins.gradle.org/m2/")
        }
        it.maven {
            setUrl("http://maven.aliyun.com/nexus/content/groups/public/")
        }
    }
}