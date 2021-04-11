def map =[:]
// node {
// 	withGradle{
// 		sh 'gradle help --scan'
// 	}
// }
// fiindBuildScans()
pipeline {
	agent any
	stages {
	stage('Build') {
			steps {
				checkout([$class: 'GitSCM', branches: [[name: '*/master']], doGenerateSubmoduleConfigurations: false, extensions: [], submoduleCfg: [], userRemoteConfigs: [
						[credentialsId: '7aebbc8e-9777-437a-9290-e93f577e4da8', url: 'https://github.com/sharebase/sharecoin-web.git']
					]])
				sh 'mvn compile'
				checkout([$class: 'GitSCM', branches: [[name: '*/master']], doGenerateSubmoduleConfigurations: false, extensions: [], submoduleCfg: [], userRemoteConfigs: [
						[credentialsId: '7aebbc8e-9777-437a-9290-e93f577e4da8', url: 'https://github.com/kouichiUme/AndroidNativeSample.git']
					]])
				sh './gradlew build'
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
				sh ". /tools/Xilinx/Vitis_HLS/2020.2/bin/setupEnv.sh"
				sh "env"
				
				// echo map
			 
			 }
		}
	}
}