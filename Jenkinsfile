pipeline {
    agent any
    tools {
        jdk 'JAVA_HOME'
        maven 'M2_HOME'
    }
    stages {
        stage('GIT') {
            steps {
                git branch: 'AlaaRami_5SAE5_G5', url: 'https://github.com/Alaa-Rami/5SAE5-G5-Foyer.git'
            }
        }
        stage('Compile Stage') {
            steps {
                sh 'mvn clean compile'
            }
        }
        stage('MVN SonarQube Analysis') {
            steps {
                sh 'mvn sonar:sonar'
            }
        }
    }
}