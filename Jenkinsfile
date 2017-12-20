
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
                sh 'wget http://dl.google.com/android/android-sdk_r24.4.1-linux.tgz'
                sh 'tar zxvf android-sdk_r24.4.1-linux.tgz'
                sh 'rm android-sdk_r24.4.1-linux.tgz'
                sh 'sudo chmod -R 777 *'
                sh 'ls "$ANDROID_HOME"/tools/bin'
                sh 'echo "y" |"$ANDROID_HOME"/tools/bin/sdkmanager --update'

                sh 'chmod +x gradlew'

                sh './gradlew tasks'
                sh 'ls app/'
                sh './gradlew assembleRelease'
                sh 'ls app/'
               }
			
		}




		stage('Deploy') {

		    steps{
		        sh 'ls "/var/lib/jenkins/workspace/TorahMates App/android-sdk-linux/build-tools/25.0.0"'
		        //zipalign apk
		        //sh '$ zipalign -f -v 4 app/*.apk app-release.apk'
		        //sh '"$ANDROID_HOME"/build-tools/25.0.0/zipalign -v 4 app/*.apk app-release.apk'

                //sign android apk
                step([$class: 'SignApksBuilder', apksToSign: 'app/*.apk', archiveUnsignedApks: true,
                        keyAlias: 'tmappkey', keyStoreId: 'tmappkey', skipZipalign: true])
                sh 'ls /app'
                //upload app to google play
                androidApkUpload apkFilesPattern: '/app/app-release.apk', googleCredentialsId: 'Google Play Credentials',
                    trackName: 'beta'

               }
		}

		
	}
}
