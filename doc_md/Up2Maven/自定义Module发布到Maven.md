## 自定义Module发布到Maven
本文只是介绍`android`将自定义的`jar`或者`aar`发布到私服的`maven`上，这里主要分为两部分，第一部分是使用`nexus`搭建自己的私服，第二部分是进行发布

### 一、使用`nexus`搭建私服（`mac`环境）
1. 安装`JDK`环境（不会的自行谷歌）
2. 下载`nexus`安装包，[下载链接](https://www.sonatype.com/download-oss-sonatype)
3. 下载完成后解压到固定目录，执行`/xxx/xxx start`启动（这里类似`mysql`启动）
4. 打开浏览器`localhost:8081`,默认账号`admain`,默认密码`admain123`

### 二、定义`module`发布`Maven`
#### 2.1配置私服
首先最基本的就是需要配置地址，账号，密码，大致说一下过程，如果项目想使用第三方库，第一步肯定是拉取到本地，此时就需要一个类似路由的东西去maven库进行相应第三方库的拉取。
 `android`新建的项目根目录都有`gradle.properties`（注）配置文件，需要在其中进行私服`maven`地址：`http://localhost:8010/nexus/content/repositories/releases/`以及私服`maven`快照地址:`http://localhost:8010/nexus/content/repositories/snapshots/`，还有就是私服账号密码。
#### 2.2发布库配置
这里需要配置的要多一点，还需要注意区分`android`和`javalib`否则会编译失败
1. 定义`maven.gradle`文件，`gradle`采用的开发语言为`groovy`这里不做详细描述，后面会有相应的demo进行讲解。
2. 在`maven.gradle`文件中编写你要发布的内容，因为是`maven`库，必须要依赖`maven`，再有就是必须要配置的`MAVEN_GROUP_ID`,`MAVEN_ARTIFACT_ID`,`MAVEN_VERSION`,`MAVEN_URL`(私服地址),`MAVEN_SNAPSHOT_URL`（私服快照地址）
3. 在库的`build.gradle`中应用`maven.gradle`
4. 执行发布命令`gradle :module名称:uploadArchives`

#### 2.3配置文件demo
详解可以看代码中的注释
```
/**
 * Author： fanyafeng
 * Data： 2020/4/16 10:43
 * Email: fanyafeng@live.cn
 * Desc: 发布到本地maven库所需maven文件
 * 发布版本命令：gradle :module名称:uploadArchives
 * uploadArchives其实就是一个gradle的task
 * 发布新版本规则为：
 * MAVEN_GROUP_ID:xxx.xxx.xxx(一般，多的话还可以继续在后面加路径名)
 * MAVEN_ARTIFACT_ID:xxx-xxx(具体的库名称)
 * MAVEN_VERSION:x.x.x(正式版) x.x.x-SNAPSHOT(快照版)
 * 版本发布必须要高于上一个版本不能重复，否则发布失败
 * 依赖的库的格式：implementation "xxx.xxx.xxx:xxx-xxx-xxx:0.1.0"
 *
 * 脚本语言为groovy
 */

apply plugin: 'maven'

def MAVEN_GROUP_ID = 'com.dmall.ui'
def MAVEN_ARTIFACT_ID = 'dialog'
//def MAVEN_VERSION = '0.6-SNAPSHOT'
def MAVEN_VERSION = '0.1.0'

task androidJavadocs(type: Javadoc) {
    source = android.sourceSets.main.java.srcDirs
    classpath += project.files(android.getBootClasspath().join(File.pathSeparator))
    failOnError = false
}

task androidJavadocsJar(type: Jar, dependsOn: androidJavadocs) {
    classifier = 'javadoc'
    from androidJavadocs.destinationDir
}

task androidSourcesJar(type: Jar) {
    classifier = 'sources'
    from android.sourceSets.main.java.srcDirs
}

artifacts {
    archives androidSourcesJar
    archives androidJavadocsJar
}

uploadArchives {
    repositories {
        mavenDeployer {
            repository(url: MAVEN_REPO_RELEASE_URL) {
                authentication(userName: NEXUS_USERNAME, password: NEXUS_PASSWORD)
            }

            snapshotRepository(url: MAVEN_REPO_SNAPSHOT_URL) {
                authentication(userName: NEXUS_USERNAME, password: NEXUS_PASSWORD)
            }

            pom.groupId = MAVEN_GROUP_ID
            pom.artifactId = MAVEN_ARTIFACT_ID
            pom.version = MAVEN_VERSION
        }
    }
}
```

注：
1. `properties`为配置文件，语法有两种一种是以`#`开头的注释，再有就是正规的`key=value`键值对，可以没有`value`但是不能没有`key`，`android`中`assets`中存放的就是这类的配置文件，配置文件读取可以进行自己解析，`android`中自带工具类解析。
2. `gradle`基本都是`groovy`语言编写，其中也可以穿插`java`，都是`jvm`语言。

