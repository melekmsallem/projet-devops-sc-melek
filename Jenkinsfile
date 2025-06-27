pipeline {
    agent any
    
    tools {
        jdk 'jdk17'
        maven 'maven-3.9.10'
    }
    
    environment {
        DOCKER_HOST = "tcp://localhost:2375"
    }

    stages {
        stage('Verify Tools') {
            steps {
                bat 'java -version'
                bat 'mvn -version'
                bat 'docker --version'
            }
        }

        stage('Build') {
            steps {
                bat 'mvn clean package -DskipTests'
            }
        }

        stage('Test') {
            steps {
                // Étape 1: Exécuter les tests et générer le rapport Jacoco
                bat 'mvn test jacoco:report'

                // Étape 2: Publier les résultats JUnit
                junit '**/target/surefire-reports/*.xml'

                // Étape 3: Publier les résultats JaCoCo (seulement si le plugin est installé)
                jacoco(
                    execPattern: '**/target/jacoco.exec',
                    classPattern: '**/target/classes',
                    sourcePattern: '**/src/main/java',
                    exclusionPattern: '**/target/generated-sources/**'
                )
            }
        }

        stage('Docker Build') {
            steps {
                script {
                    if (fileExists('Dockerfile')) {
                        docker.build("melekmsallem/projet-devops:${env.BUILD_ID}")
                    } else {
                        error 'ERREUR: Dockerfile non trouvé!'
                    }
                }
            }
        }

        stage('Docker Compose') {
            steps {
                script {
                    bat 'docker-compose down'
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
    }
}