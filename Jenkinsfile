pipeline {
  agent any

  stages {
  
    stage('Test') {
      steps {
        echo 'Testing...'
        sh '''
          sudo docker info
          sudo docker version
          sudo docker compose version
          sudo curl --version
          sudo jq --version
        '''
      }
    }
  }
}
