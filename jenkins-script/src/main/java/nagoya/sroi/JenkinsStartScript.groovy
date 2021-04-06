def map =[:]
pipeline {
	agent any
	stages {
	stage('Build') {
			steps {
				checkout([$class: 'GitSCM', branches: [[name: '*/master']], doGenerateSubmoduleConfigurations: false, extensions: [], submoduleCfg: [], userRemoteConfigs: [
						[credentialsId: '7aebbc8e-9777-437a-9290-e93f577e4da8', url: 'https://github.com/sharebase/sharecoin-web.git']
					]])
				sh 'mvn compile'
			}
		}
		stage('Test'){
			steps {
				echo "start test"
				// junit 'reports/**/*.xml'
			}
		}
		stage('Deploy') {
			 steps {
				echo "start deploying "
				echo map.length
				
				// echo map
			 
			 }
		}
	}
}