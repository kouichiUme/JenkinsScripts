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
				bat "nuget restore "path to *.sln" file"
				bat "dotnet build YourProjectPath\\Project.csproj --configuration Release"				
				bat "msbuild "
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