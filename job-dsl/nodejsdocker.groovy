job('NodeJS Docker example'){
    scm{
        git('git://github.com/fcomuniz/simple-node.git') { node ->
            node / gitConfigName('DSL User')
            node / gitConfigEmail('jenkins-dsl@daemonquant.com')
        }
        triggers {
            scm('H/5 * * * *')
        }
        wrappers {
            nodejs('nodejs')
        }
        steps {
            dockerBuildAndPublish {
                repositoryName('fcomuniz/sample-node')
                tag('${GIT_REVISION,length=9}')
                registryCredentials('DockerRegistry')
                forcePull(false)
                forceTag(false)
                createFingerprints(false)
                skipDecorate()
            }
        }
    }
}