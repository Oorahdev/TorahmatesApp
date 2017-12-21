
pipeline {


    agent any
	
	stages {
		stage('Build') {
		    steps{
                //pull tmapp and server from github
                 checkout([$class: 'GitSCM', branches: [[name: '*/master']], doGenerateSubmoduleConfigurations: false,
                                    extensions: [], submoduleCfg: [], userRemoteConfigs: [
                                    [credentialsId: 'feac2ef8-3a88-4107-945e-4f359bf1a984', url: 'https://github.com/Oorahdev/TorahmatesApp']]])
                //build tmapp apk from github

                sh 'ls'
                sh 'sudo chmod -R 777 *'
                sh 'ls "$ANDROID_HOME"/tools/bin'
                sh 'chmod +x gradlew'
                sh 'ls app/'
                sh './gradlew tasks'
                sh './gradlew clean assembleRelease'
                sh 'ls'

               }
			
		}




		stage('Deploy') {

		    steps{
		        sh 'rm app-release-*.apk'
		        sh 'ls app/'
		        //sign android apk
                step([$class: 'SignApksBuilder', apksToSign: '*.apk', archiveUnsignedApks: true,
                        keyAlias: 'tmappkey', keyStoreId: 'tmappkey', skipZipalign: true])

                sh 'ls app'
                //upload app to google play
                androidApkUpload apkFilesPattern: 'app-release-signed.apk', googleCredentialsId: 'tmappkey',
                    trackName: 'beta'

               }
		}


		
	}
}
