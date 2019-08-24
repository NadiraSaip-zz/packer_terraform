node{
    stage("Pull Repo") {
        git git@github.com:NadiraSaip/packer_terraform.git
    }
    stage("Build AMI"){
        sh "packer build updated/updated.json"
    }
}