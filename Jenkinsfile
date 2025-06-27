pipeline {
    agent any
    tools {
        jdk 'jdk17'
        maven 'maven-3.9.10'
    }
    environment {
        DOCKER_HOST = "tcp://localhost:2375"
        MAVEN_OPTS = "-Dmaven.repo.local=.m2/repository"
    }

    stages {
        stage('Build & Test') {
            steps {
                bat 'mvn -B clean package'  // Suppression de -DskipTests
            }
        }

        stage('Docker') {
            steps {
                script {
                    // Ajout de logs de débogage
                    echo "Building Docker image..."
                    docker.build("melekmsallem/projet-devops:${env.BUILD_ID}")

                    echo "Starting containers..."
                    bat 'docker-compose down --remove-orphans'
                    bat "set TAG=${env.BUILD_ID} && docker-compose up -d --build"
                }
            }
        }
    }

    post {
        always {
            archiveArtifacts 'target/*.jar'
            cleanWs()
        }
        failure {
            echo "Pipeline failed - check Docker and Maven logs"
            slackSend(color: 'danger', message: "Échec du build ${env.BUILD_URL}")
        }
    }
}