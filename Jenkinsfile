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
        
        stage('List Files') {
            steps {
                bat '''
                echo Liste des fichiers :
                dir
                '''
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
                    // Vérification explicite du Dockerfile
                    def dockerfileExists = fileExists('Dockerfile')
                    echo "Dockerfile existe: ${dockerfileExists}"
                    
                    if (dockerfileExists) {
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
            // Construit l'image si pas déjà faite
            def dockerImage = docker.build("melekmsallem/projet-devops:${env.BUILD_ID}")
            
            // Lance les containers
            bat '''
            docker-compose down
            set TAG=%BUILD_ID% && docker-compose up -d --build
            '''
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