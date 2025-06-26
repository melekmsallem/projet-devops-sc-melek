pipeline {
    agent any
    
    tools {
        jdk 'JDK17'              // Doit correspondre au nom dans Jenkins
        maven 'Maven-3.9.6'      // Version configurée dans Jenkins
    }
    
    stages {
        // Étape 1: Récupération du code
        stage('Checkout Git') {
            steps {
                checkout scm
            }
        }
        
        // Étape 2: Build avec Maven
        stage('Build') {
            steps {
                bat 'mvn clean package -DskipTests'  // Windows (utilisez 'sh' pour Linux)
            }
        }
        
        // Étape 3: Exécution des tests
        stage('Test') {
            steps {
                bat 'mvn test'
                junit 'target/surefire-reports/**/*.xml'  // Rapport JUnit
            }
        }
        
        // Étape 4: Analyse SonarQube
        stage('SonarQube') {
            steps {
                withSonarQubeEnv('sonar') {  // Nom de la config Sonar dans Jenkins
                    bat 'mvn sonar:sonar -Dsonar.projectKey=projet-devops-melek'
                }
            }
        }
        
        // Étape 5: Build Docker
        stage('Build Docker') {
            steps {
                script {
                    docker.build("melekmsallem/projet-devops:${env.BUILD_ID}")
                }
            }
        }
    }
    
    // Actions post-build
    post {
        always {
            archiveArtifacts 'target/*.jar'  // Sauvegarde le .jar généré
        }
    }
}