pipeline {
    agent any
    tools {
        jdk 'jdk17'
        maven 'maven-3.9.10'
    }
    environment {
        DOCKER_HOST = "tcp://localhost:2375"
        MAVEN_OPTS = "-Dmaven.repo.local=.m2/repository"  # Cache local
    }

    stages {
        stage('Build & Test') {
            parallel {
                stage('Build') {
                    steps { bat 'mvn -B clean package -DskipTests' }
                }
                stage('Test') {
                    steps {
                        bat 'mvn -B test jacoco:report'
                        junit '**/target/surefire-reports/*.xml'
                        jacoco(
                            execPattern: 'target/jacoco.exec',
                            classPattern: 'target/classes',
                            sourcePattern: 'src/main/java'
                        )
                    }
                }
            }
        }

        stage('Docker') {
            steps {
                script {
                    docker.build("melekmsallem/projet-devops:${env.BUILD_ID}")
                    bat 'docker-compose down && docker-compose up -d --build'
                }
            }
        }
    }
    post {
        always {
            archiveArtifacts 'target/*.jar'
            cleanWs()
        }
    }
}