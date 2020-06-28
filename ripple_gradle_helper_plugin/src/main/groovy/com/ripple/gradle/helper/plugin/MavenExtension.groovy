package com.ripple.gradle.helper.plugin;

/**
 * Author: fanyafeng
 * Data: 2020/6/23 10:11
 * Email: fanyafeng@live.cn
 * Description: maven依赖库格式
 *
 * 最普遍的依赖成为外部依赖，这些依赖存放在外部仓库
 *
 * group属性指定依赖分组
 * 在maven中就是groupId
 *
 * name属性指定依赖的名称
 * 在maven中就是artifactId
 *
 * version属性指定外部依赖的版本
 * 在maven中就是version
 *
 * maven库依赖格式：
 * implementation 'com.ripple.component:tool:0.0.4'
 * GroupId：com.ripple.component
 * ArtifactId:tool
 * Version:0.0.4
 *
 */
class MavenExtension {
    private String group
    private String artifact
    private String version

    String getGroup() {
        return group
    }

    void setGroup(String group) {
        this.group = group
    }

    String getArtifact() {
        return artifact
    }

    void setArtifact(String artifact) {
        this.artifact = artifact
    }

    String getVersion() {
        return version
    }

    void setVersion(String version) {
        this.version = version
    }
}
