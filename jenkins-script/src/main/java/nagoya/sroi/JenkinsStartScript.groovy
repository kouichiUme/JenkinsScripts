import jenkins.model.*
def map =[:]
// node {
// 	withGradle{
// 		sh 'gradle help --scan'
// 	}
// }
// fiindBuildScans()
pipeline {
	agent any
	//   triggers {
    //     cron('*/5 * * * *')
		
    // }
	environment {
				ANDROID_SDK_ROOT='/home/kouichi/Android/Sdk'
			}
	stages {
		
	stage('Build') {
			steps {
				dir('blockchain'){
					checkout([$class: 'GitSCM', branches: [[name: '*/master']], doGenerateSubmoduleConfigurations: false, extensions: [], submoduleCfg: [], userRemoteConfigs: [
							[credentialsId: '7aebbc8e-9777-437a-9290-e93f577e4da8', url: 'https://github.com/sharebase/sharecoin-web.git']
						]])
					sh 'mvn compile'

				}
				dir('android'){
					checkout([$class: 'GitSCM', branches: [[name: '*/master']], doGenerateSubmoduleConfigurations: false, extensions: [], submoduleCfg: [], userRemoteConfigs: [
							[credentialsId: '7aebbc8e-9777-437a-9290-e93f577e4da8', url: 'https://github.com/kouichiUme/AndroidNativeSample.git']
						]])
					sh '''
					export ANDROID_SDK_ROOT=/home/kouichi/Android/Sdk
					export NDK_PROJECT_PATH=.
					./gradlew build
					${ANDROID_SDK_ROOT}/cmake/3.10.2.4988404/bin/cmake
					'''
				}
				dir('fpga'){
					// checkout([$class: 'GitSCM', branches: [[name: '*/master']], doGenerateSubmoduleConfigurations: false, extensions: [], submoduleCfg: [], userRemoteConfigs: [
					// 	[credentialsId: 'github', url: 'https://github.com/kouichiUme/fpgaPractiseProject.git']
					// ]])
					git branch : 'main',
					credentialsId: 'github',
					url: 'https://github.com/kouichiUme/fpgaPractiseProject.git'
					// sh " /tools/Xilinx/Vitis_HLS/2020.2/bin/setupEnv.sh"
					sh '''
						. /tools/Xilinx/DocNav/.settings64-DocNav.sh
						. /tools/Xilinx/Vivado/2020.2/.settings64-Vivado.sh
						. /tools/Xilinx/Vitis/2020.2/.settings64-Vitis.sh
						. /tools/Xilinx/Model_Composer/2020.2/.settings64-Model_Composer.sh
						. /tools/Xilinx/Vitis_HLS/2020.2/.settings64-Vitis_HLS.sh
						xvlog HDL/blink.v 
						xelab blink
						'''
				}
			}
		}
		stage('Test'){
			steps {
				echo "start test"
				dir("android"){
					sh './gradlew test'
				}
				// junit 'reports/**/*.xml'
			}
		}
		stage('Deploy') {
			matrix {
				axes {
					axis {
						name "plathome"
						values 'linux','windows','mac'
					}
					axis{
						name "browser"
						values 'firefox',"chrome","safari","edge"
					}
				}

			stages{
				stage("deploy"){
					steps {
						echo "start deploying ${plathome} -${browser}"
						echo map.length
						// sh ". /tools/Xilinx/Vitis_HLS/2020.2/bin/setupEnv.sh"
						sh "env"
						
						// echo map
					
					}
				}
			}
			}
		}
		stage("infra"){
			agent {
				docker {image 'maven:3.8.1-adoptopenjdk-11'}
			}
			steps{
				echo "stage infra "
				sh 'java -version'
			}

		}
		stage("create docker"){
			agent any
			steps{
				echo "stage create docker  "

				}
			}
		}
	}


				node {
				def testImage = docker.build("test-image", "jenkins-script/dockerfiles") 

				}