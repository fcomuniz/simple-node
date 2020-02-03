job('NodeJS example') {
    scm {
        git('git://github.com/fcomuniz/simple-node.git') { node -> 
            node / gitConfigName('DSL User')
            node / gitConfigEmail('jenkins-dsl@daemonquant.com')
        }
    }
    triggers {
        scm('H/5 * * * *')
    }
    wrappers {
        nodejs('nodejs')
    }
    steps {
        shell('npm install')
    }
}