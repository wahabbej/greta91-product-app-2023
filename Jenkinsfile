pipeline {
  agent any
  stages {
    stage('build') {
      steps {
        bat './mvnw -B -DskipTests clean package'
      }
    }

  }
}