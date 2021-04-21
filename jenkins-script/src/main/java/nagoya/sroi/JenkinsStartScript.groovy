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
				sh '''
				export ANDROID_SDK_ROOT=/home/kouichi/Android/Sdk
				export NDK_PROJECT_PATH=.
				 ./gradlew build
				${ANDROID_SDK_ROOT}/cmake/3.10.2.4988404/bin/cmake
				'''
				dir('fpga'){
					// checkout([$class: 'GitSCM', branches: [[name: '*/master']], doGenerateSubmoduleConfigurations: false, extensions: [], submoduleCfg: [], userRemoteConfigs: [
					// 	[credentialsId: 'github', url: 'https://github.com/kouichiUme/fpgaPractiseProject.git']
					// ]])
					git branch : 'master',
					credentialsId: 'github',
					url: 'https://github.com/kouichiUme/fpgaPractiseProject.git'
					// sh " /tools/Xilinx/Vitis_HLS/2020.2/bin/setupEnv.sh"
					sh '''
						. /tools/Xilinx/DocNav/.settings64-DocNav.sh
						. /tools/Xilinx/Vivado/2020.2/.settings64-Vivado.shs
						. /tools/Xilinx/Vitis/2020.2/.settings64-Vitis.sh
						. /tools/Xilinx/Model_Composer/2020.2/.settings64-Model_Composer.sh
						. /tools/Xilinx/Vitis_HLS/2020.2/.settings64-Vitis_HLS.sh
						xvlog HDL/blink.v 
						'''
				}
			}
		}
		stage('Test'){
			steps {
				echo "start test"
				sh 'ANDROID_SDK_ROOT=/home/kouichi/Android/Sdk ./gradlew test'
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