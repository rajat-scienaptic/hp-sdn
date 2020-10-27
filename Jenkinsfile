node {
    stage('Checkout') {
        git 'https://github.com/rajat-scienaptic/hp-sdn.git'
    }

    stage('Build') {
        sh 'mvn -B -V -U -e clean package'
    }

    stage('Test') {
     steps {
        echo 'Testing...'
     }
   }

   stage('Deploy') {
     steps {
       echo 'Deploying...'
     }
   }
}
