pipeline {
	agent any
	stages {
		stage('Build') {
			steps {
				checkout([$class: 'GitSCM', branches: [[name: '*/master']], doGenerateSubmoduleConfigurations: false, extensions: [], submoduleCfg: [], userRemoteConfigs: [
						[credentialsId: '7aebbc8e-9777-437a-9290-e93f577e4da8', url: 'https://github.com/sharebase/sharecoin-web.git']
					]])
				sh 'mvn'
			}
		}
		stage('Test'){
			steps {
				sh 'mvn check'
				junit 'reports/**/*.xml'
			}
		}
		stage('Deploy') { steps {
			 echo "start deploying "
			
			 sh 'make publish' 
			 
			 } }


	}
}
6