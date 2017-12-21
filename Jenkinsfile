
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
                //sh 'echo "y" |"$ANDROID_HOME"/tools/bin/sdkmanager --update'

                sh 'chmod +x gradlew'
                sh 'ls app/'
                //sh './gradlew -P versCode=$BUILD_NUMBER clean'
                //sh 'echo -PversCode'
                sh './gradlew tasks'
                sh './gradlew clean assembleRelease'
                sh 'ls app/'

               }
			
		}




		stage('Deploy') {

		    steps{
		        //sh 'ls "/var/lib/jenkins/workspace/TorahMates App/android-sdk-linux/build-tools/25.0.0"'
		        //sh 'rm app/app-release-*.apk'
		        sh 'ls app/'
		        sh 'ls "$ANDROID_HOME/keystore/"'
                //sign android apk
                step([$class: 'SignApksBuilder', apksToSign: '**/*.apk', archiveUnsignedApks: true,
                        keyAlias: 'tmappkey', keyStoreId: 'tmappkey', skipZipalign: true])

                sh 'ls app'
                //upload app to google play
                androidApkUpload apkFilesPattern: 'app/*-release-signed.apk', googleCredentialsId: 'TorahmatesApp',
                    trackName: 'beta'

               }
		}


		
	}
}
