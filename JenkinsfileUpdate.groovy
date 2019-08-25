pipeline{
    agent any
    stages{
        stage("Run Command"){
            steps{
                sh '''
                set +xe
                echo Hello
                ech Error
                sudo yum install httpd wget unzip -y
                ping -c 4 google.com
                '''
            }
        }
        stage("Download Terraform"){
            steps{
                ws("tmp/"){
                    script {
                        def exists = fileExists 'terraform_0.12.7_linux_amd64.zip'
                        if (exists) {
                            sh "unzip -o terraform_0.12.7_linux_amd64.zip"
                            sh "sudo mv terraform /bin"
                            sh "terraform version"
                        } else {
                            sh "wget https://releases.hashicorp.com/terraform/0.12.7/terraform_0.12.7_linux_amd64.zip"
                            sh "unzip -o terraform_0.12.7_linux_amd64.zip"
                            sh "sudo mv terraform /bin"
                            sh "terraform version"
                        }
                    }
                }
            }
        }
        stage("Write to a file"){
            steps{
                ws("tmp/"){
                    writeFile text: "Test", file: "TestFile"
                }
            }
        }
        stage("Download Packer"){
            steps{
                ws("tmp/"){
                    script {
                        def exists = fileExists 'packer_1.4.3_linux_amd64.zip'
                        if (exists) {
                            sh "unzip -o packer_1.4.3_linux_amd64.zip"
                            sh "sudo mv packer /sbin"
                            sh "packer version"
                        } else {
                            sh "wget https://releases.hashicorp.com/packer/1.4.3/packer_1.4.3_linux_amd64.zip"
                            sh "unzip -o packer_1.4.3_linux_amd64.zip"
                            sh "sudo mv packer /sbin"
                            sh "packer version"
                        }
                    }
                }
            }
        }
        stage("Filter AMI"){
            steps{
                script{
                //def AMI
                  //  if (REGION == "us-east-1") {
                    //    AMI = "ami-0de53d8956e8dcf80"
                //} else if (REGION == "us-east-2"){
                  //  AMI = "ami-0d8f6eb4f641ef691"
                  echo "Hello"
               
                } 
            }
        }
    }
        stage("Build Image"){
            steps{
                sh 'packer build  -var "region=${REGION}" updates/ami.json'
                echo "Hello"
            }
        }
    }
    post{
        success {
            echo "Done"
        }
        failure {
            mail to:  "testsaip1@gmail.com", subject: "job", body: "job completed"
        }
          
    }
}

