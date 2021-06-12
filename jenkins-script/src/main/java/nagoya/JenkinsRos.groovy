#!/usr/bin/env groovy
import jenkins.model.*
def map =[:]

pipeline {
	agent any

	stages {
	stage('Build') {
			steps {
			}
			node {

			}
		}

		stage('Test'){
			steps {
				echo "start test"	

			}
		}
		stage('Deploy') {
			 steps {
				
				// echo map
			 
			 }
		}
	}
}