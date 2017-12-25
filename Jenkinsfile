
pipeline {


    agent any
	
	stages {
		stage('SCM') {
		    steps{
                //pull tmapp and server from github
                 checkout([$class: 'GitSCM', branches: [[name: '*/master']], doGenerateSubmoduleConfigurations: false,
                                    extensions: [], submoduleCfg: [], userRemoteConfigs: [
                                    [credentialsId: 'feac2ef8-3a88-4107-945e-4f359bf1a984', url: 'https://github.com/Oorahdev/TorahmatesApp']]])
                //build tmapp apk from github

                sh 'ls'
                sh 'sudo chmod -R 777 *'
                sh 'chmod +x gradlew'
                sh 'ls app'
                //sh 'rm app-release*.apk'

               }
			
		}


		stage('Build') {
		    steps {
		        sh './gradlew tasks'
		        //sh './gradlew locate'
		        sh 'ls'
                sh './gradlew clean assembleRelease'
                sh 'ls'
                sh 'ls app'

		        gradle {
		            useWrapper true
		            tasks 'clean assembleRelease'
		        }
		    }
		}






		stage('Deploy') {

		    steps{
		        //sh 'rm app-release-*.apk'
		        sh 'ls'
		        //sign android apk
                step([$class: 'SignApksBuilder', apksToSign: '*.apk', archiveUnsignedApks: true,
                        keyAlias: 'tmappkey', keyStoreId: 'tmappkey', skipZipalign: true])

                sh 'ls'
                //upload app to google play
                androidApkUpload apkFilesPattern: 'app-release-signed.apk',
                    googleCredentialsId: 'Google Play Credentials', trackName: 'beta'

               }
		}


		
	}
}
