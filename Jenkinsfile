pipeline {
    agent any

    tools {
        maven 'Maven'
        
    }

    stages {
        stage('Checkout') {
            steps {
                git branch: 'master',
                    url: 'https://github.com/sindhud205/MyMavenSeleniumApp05.git'
            }
        }

        stage('Build') {
            steps {
                sh 'mvn clean package'
            }
        }

        stage('Test') {
            steps {
                sh 'mvn test'
            }
        }

        stage('Run Application') {
            steps {
                sh 'java -Dheadless=true -jar target/MyMavenSeleniumApp05-1.0-SNAPSHOT-jar-with-dependencies.jar'
            }
        }
    }

    post {
        success {
            echo 'Build and execution successful!'
        }
        failure {
            echo 'Build failed!'
        }
    }
}
