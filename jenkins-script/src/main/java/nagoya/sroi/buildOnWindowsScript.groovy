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
				bat "dotnet build YourProjectPath\\Project.csproj --configuration Release"				
			}
		}
		stage('Test'){
			steps {
				echo "start test"				// junit 'reports/**/*.xml'

			}
		}
		stage('Deploy') {
			 steps {
				echo "start deploying "
				
				// echo map
			 
			 }
		}
	}
}