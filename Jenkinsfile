
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

               }
			
		}


		stage('Build') {
		    steps {
		        //sh './gradlew tasks'
		        sh './gradlew wrapper --gradle-version=3.3 --distribution-type=bin'
		        //sh './gradlew locate'
		        sh 'ls app/build/outputs/apk'
		        sh 'rm app/build/outputs/apk/app-*.apk'
                sh './gradlew clean assembleRelease'
                sh 'ls app/build/outputs/apk'
                sh 'ls app'


		    }
		}






		stage('Deploy') {

		    steps{
		        //sh 'rm app-release-*.apk'
		        sh 'ls'
		        //sign android apk
                step([$class: 'SignApksBuilder', apksToSign: 'app/build/outputs/apk/app-*.apk', archiveUnsignedApks: true,
                        keyAlias: 'tmappkey', keyStoreId: 'tmappkey', skipZipalign: true])

                sh 'ls'
                //upload app to google play
                androidApkUpload apkFilesPattern: 'app/build/outputs/apk/app-release-signed.apk',
                    googleCredentialsId: 'Google Play Credentials', trackName: 'beta'

               }
		}



		
	}
}
