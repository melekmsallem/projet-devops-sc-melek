pipeline {
    agent any
    
    tools {
        jdk 'jdk17'          // Exactement comme dans Jenkins (minuscules)
        maven 'maven-3.9.10' // Exactement comme dans Jenkins (minuscules)
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
                bat 'mvn test'
                junit 'target/surefire-reports/**/*.xml'
            }
        }
        
        stage('Docker Build') {
            steps {
                script {
                    docker.build("melekmsallem/projet-devops:${env.BUILD_ID}")
                }
            }
        }
    }
    
    post {
        always {
            archiveArtifacts 'target/*.jar'
        }
    }
}