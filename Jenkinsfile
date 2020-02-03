node {
    def commit_id 
    def nodeImage = 'node:12'
    stage('Preparation') {
        checkout scm
        sh "git rev-parse --short HEAD > .git/commit-id"
        commit_id = readFile(".git/commit-id").trim()
    }
    stage('test') {
        def myTestContainer = docker.image(nodeImage)
        myTestContainer.pull()
        myTestContainer.inside {
            sh 'npm install --only=dev'
            sh 'npm test'
        }
    }
    stage('test with a DB') {
        def mysql = docker.image("mysql").run("-e MYSQL_ALLOW_EMPTY_PASSWORD=yes --rm")
        def myTestContainer = docker.image(nodeImage)
        myTestContainer.pull()
        myTestContainer.inside("--link ${mysql.id}:mysql") {
            sh 'npm install --only=dev'
            sh 'npm test'
        }
        mysql.stop()
    }
    stage('docker build/push') {
        docker.withRegistry('https://index.docker.io/v1', 'DockerRegistry') {
            def app = docker.build("fcomuniz/simple-node:${commit_id}", ".").push()
        }
    }
}