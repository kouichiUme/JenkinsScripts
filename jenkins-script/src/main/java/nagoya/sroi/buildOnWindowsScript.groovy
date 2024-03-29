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
				git branch : 'master',
					credentialsId: 'github',
					url: 'https://github.com/kouichiUme/AspNetWebApplication.git'
				bat "nuget"
				bat "nuget restore "path to *.sln" file"
				bat "donet store -manifest packages.csproj"
				bat "dotnet build WebApplication.sln"				
				bat "dotnet build --configuration Release"
				bat "dotnet publish -c Release --no-build"
				bat "msbuild "
			}
			node {
	    		powershell 'Write-Output "Hello, World!"'

			}
		}

		stage('Test'){
			steps {
				echo "start test"	
				dotnetTest 			// junit 'reports/**/*.xml'

			}
		}
		stage('Deploy') {
			 steps {
				echo "start deploying "
			 	withCredentials([usernamePassword(credentialsId: 'iis-credential', usernameVariable: 'USERNAME', passwordVariable: 'PASSWORD')])
				 {
					  bat """ "C:\\Program Files (x86)\\IIS\\Microsoft Web Deploy V3\\msdeploy.exe" -verb:sync -source:iisApp="${WORKSPACE}\\${publishedPath}" -enableRule:AppOffline -dest:iisApp="${iisApplicationName}",ComputerName="https://${targetServerIP}:8172/msdeploy.axd",UserName="$USERNAME",Password="$PASSWORD",AuthType="Basic" -allowUntrusted"""
				}
				// echo map
			 
			 }
		}
	}
}