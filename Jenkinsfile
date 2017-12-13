
pipeline {


    agent any
	
	stages {
		stage('Build') {
		    steps{
                //pull tmapp and server from github
                checkout([$class: 'GitSCM', branches: [[name: '*/master']], doGenerateSubmoduleConfigurations: false,
                    extensions: [], submoduleCfg: [], userRemoteConfigs: [
                    [credentialsId: 'feac2ef8-3a88-4107-945e-4f359bf1a984', url: 'https://github.com/Oorahdev/TorahmatesApp']]])
                    //,[credentialsId: 'feac2ef8-3a88-4107-945e-4f359bf1a984',
                     //                     url: 'https://github.com/Oorahdev/TorahMatesApp-FCM_XMPP_Server']
                //build tmapp apk from github
                sh 'chmod +x gradlew'
                sh 'chmod -R 777 *'
                //install android sdk
                sh 'wget https://dl.google.com/android/repository/sdk-tools-linux-3859397.zip'
                sh 'ls'
                sh 'tar zxvf /opt/android-sdk'
                sh 'export ANDROID_HOME="/opt/android-sdk-linux"'
                sh 'export PATH="$ANDROID_HOME/tools:$ANDROID_HOME/platform-tools:$PATH"'

                sh 'android update sdk --no-ui'

                sh './gradlew tasks'
                //sh './gradlew assembleRelease'

               }
			
		}




		stage('Deploy') {

		    steps{
                //sign android apk
                step([$class: 'SignApksBuilder', apksToSign: 'app/*.apk', archiveUnsignedApks: true,
                        keyAlias: 'tmappkey', keyStoreId: 'tmappkey'])
                //upload app to google play
                androidApkUpload apkFilesPattern: '/app/app-release.apk', googleCredentialsId: 'Google Play Credentials',
                    trackName: 'beta'
               }
		}
		
	}
}
