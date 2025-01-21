pipeline {
  agent any

  stages {
  
    stage('Test') {
      steps {
        echo 'Testing...'
        sh '''
          docker info
          docker version
          docker compose version
          curl --version
          jq --version
        '''
      }
    }
  }
}
