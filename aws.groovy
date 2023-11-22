stage('Deploy') {
    steps {
        script {
            def elasticBeanstalk = [
                region: 'us-east-1',
                credentialsId: 'your-aws-credentials-id',
                applicationName: 'your-application-name',
                environmentName: 'your-environment-name',
                bucket: 'your-s3-bucket',
                key: 'your-application-key.zip'
            ]

            withAWS(credentials: elasticBeanstalk.credentialsId, region: elasticBeanstalk.region) {
                sh "aws elasticbeanstalk create-application-version --application-name ${elasticBeanstalk.applicationName} --version-label ${BUILD_NUMBER} --source-bundle S3Bucket=${elasticBeanstalk.bucket},S3Key=${elasticBeanstalk.key}"
                sh "aws elasticbeanstalk update-environment --environment-name ${elasticBeanstalk.environmentName} --version-label ${BUILD_NUMBER}"
            }
        }
    }
}
