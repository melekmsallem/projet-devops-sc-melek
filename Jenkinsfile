pipeline {
    agent any
    
    tools {
        jdk 'jdk17'
        maven 'maven-3.9.10'
    }
    
    environment {
        DOCKER_HOST = "tcp://localhost:2375"
        SONAR_SCANNER_OPTS = "-Xmx1024m"
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

        stage('Tests & Qualité') {
            steps {
                bat 'mvn test jacoco:report'
                junit '**/target/surefire-reports/*.xml'
                jacoco execPattern: '**/target/jacoco.exec'

                // Analyse SonarQube
                withSonarQubeEnv('SonarQube') {
                    bat 'mvn sonar:sonar -Dsonar.projectKey=adoption-project -Dsonar.projectName=Adoption_Project'
                }
            }
        }

        stage('Docker Build') {
            steps {
                script {
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
                    def dockerImage = docker.build("melekmsallem/projet-devops:${env.BUILD_ID}")
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
            cleanWs() // Nettoyage de l'espace de travail
        }
        success {
            slackSend(color: 'good', message: "Build SUCCESS: ${env.JOB_NAME} #${env.BUILD_NUMBER}")
        }
        failure {
            slackSend(color: 'danger', message: "Build FAILED: ${env.JOB_NAME} #${env.BUILD_NUMBER}")
        }
    }
}