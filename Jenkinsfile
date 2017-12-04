pipeline {
	
	stages {
		stage('Build') {
		    steps{
                //pull tmapp and server from github
                checkout([$class: 'GitSCM', branches: [[name: '*/master']], doGenerateSubmoduleConfigurations: false,
                    extensions: [], submoduleCfg: [], userRemoteConfigs: [[credentialsId: 'feac2ef8-3a88-4107-945e-4f359bf1a984',
                    url: 'https://github.com/Oorahdev/TorahMatesApp-FCM_XMPP_Server'],
                    [credentialsId: 'feac2ef8-3a88-4107-945e-4f359bf1a984', url: 'https://github.com/Oorahdev/TorahmatesApp']]])
                //build tmapp apk from github
               }
			
		}
		stage('Test') {
			//run tests
			
		}
		stage('Deploy') {
		    steps{
                //sign android apk
                step([$class: 'SignApksBuilder',
                    apksToSign: '/app/*-unsigned.apk', keyAlias: 'tmappkey', keyStoreId: 'tmappkey'])
                //upload app to google play
                androidApkUpload apkFilesPattern: '/app/app-release.apk', googleCredentialsId: 'Google Play Credentials',
                    trackName: 'beta'
                //run server on aws
               }
		}
		
	}
}