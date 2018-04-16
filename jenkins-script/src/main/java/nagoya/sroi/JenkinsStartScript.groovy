pipeline {
	agent any
	stages {
		stage('Build') {
			steps { sh 'mvn' }
		}
		stage('Test'){
			steps {
				sh 'mvn check'
				junit 'reports/**/*.xml'
			}
		}
		stage('Deploy') {
			steps { sh 'make publish' }
		}
	}
}
