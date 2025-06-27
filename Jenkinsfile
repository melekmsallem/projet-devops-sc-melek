pipeline {
    agent any
    
    tools {
        jdk 'jdk17'           // Doit correspondre exactement au nom dans Jenkins
        maven 'maven-3.9.10'  // Doit correspondre exactement au nom dans Jenkins
    }
    
    environment {
        DOCKER_HOST = "tcp://localhost:2375"  // Nécessaire pour la connexion à Docker
    }
    
    stages {
        // Étape 1: Vérification des outils
        stage('Verify Tools') {
            steps {
                bat 'java -version'
                bat 'mvn -version'
                bat 'docker --version'
                bat 'docker images'  // Vérifie que Docker fonctionne
            }
        }
        
        // Étape 2: Vérification des fichiers
        stage('Verify Files') {
            steps {
                bat '''
                dir /b   # Liste tous les fichiers
                type Dockerfile  # Affiche le contenu du Dockerfile
                '''
            }
        }
        
        // Étape 3: Build Maven
        stage('Build') {
            steps {
                bat 'mvn clean package -DskipTests'
            }
        }
        
        // Étape 4: Tests
        stage('Test') {
            steps {
                bat 'mvn test'
                junit 'target/surefire-reports/**/*.xml'  // Publie les résultats
            }
        }
        
        // Étape 5: Construction Docker
        stage('Docker Build') {
            steps {
                script {
                    // Vérifie explicitement l'existence du Dockerfile
                    if (!fileExists('Dockerfile')) {
                        error 'Dockerfile non trouvé!'
                    }
                    
                    // Construction avec log verbeux
                    docker.build("melekmsallem/projet-devops:${env.BUILD_ID}", "--no-cache --progress=plain .")
                }
            }
        }
    }
    
    post {
        always {
            archiveArtifacts 'target/*.jar'  // Sauvegarde le .jar
            cleanWs()  // Nettoie l'espace de travail
        }
        failure {
            echo 'Pipeline échoué. Voir les logs pour détails.'
            // Envoyer une notification si nécessaire
        }
        success {
            echo 'Pipeline réussi!'
        }
    }
}