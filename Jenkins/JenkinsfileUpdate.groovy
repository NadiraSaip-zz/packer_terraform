node{
    stage("Pull Repo") {
        git git@github.com:NadiraSaip/packer_terraform.git
    }
}