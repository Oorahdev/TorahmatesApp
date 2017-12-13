
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
                sh 'cd /opt'
                sh 'wget http://dl.google.com/android/android-sdk_r24.4.1-linux.tgz'
                sh 'tar zxvf android-sdk_r24.4.1-linux.tgz'
                sh 'rm android-sdk_r24.4.1-linux.tgz'

                sh 'chmod +x gradlew'

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
