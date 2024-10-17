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
        stage('Compile and Deploy') {
            steps {
                // Injecting credentials into the Maven settings.xml file at runtime
                withCredentials([usernamePassword(credentialsId: 'nexus_credentials', usernameVariable: 'NEXUS_USERNAME', passwordVariable: 'NEXUS_PASSWORD')]) {
                    // Create a temporary settings.xml file
                    writeFile(file: 'settings.xml', text: """
<settings>
    <servers>
        <server>
            <id>deploymentRepo</id>
            <username>$NEXUS_USERNAME</username>
            <password>$NEXUS_PASSWORD</password>
        </server>
    </servers>
</settings>
                    ''')
                    try {
                        sh '''
                            mvn clean compile deploy \
                            -s settings.xml \
                            -DaltDeploymentRepository=deploymentRepo::default::http://192.168.50.4:8081/repository/maven-releases/ \
                            -X
                        '''
                    } catch (Exception e) {
                        echo "Maven deployment failed: ${e.getMessage()}"
                        error "Aborting build"
                    }

                }
            }
        }
        stage('SonarQube Analysis') {
            steps {
                withCredentials([string(credentialsId: 'sonar_token', variable: 'SONAR_TOKEN')]) {
                    sh '''
                        mvn sonar:sonar \
                        -Dsonar.projectKey=5SAE5-G5-Foyer \
                        -Dsonar.host.url=http://192.168.50.4:9000 \
                        -Dsonar.login=$SONAR_TOKEN
                    '''
                }
            }
        }
    }
}
