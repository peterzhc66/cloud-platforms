node {
    project = "cloud-platforms"
    playbook= "start-vms.yml"
    task = "gcp"
    repositoryUrl = "https://github.com/peterzhc66/${project}.git"
    branch = "master"
    msg = "Start VMs in GCP"

    stage 'Git Update'
    node() {
        git url: repositoryUrl, credentialsId: "gcp-2019", branch: branch
        sh "ls -ltrhR"
    }

    stage 'Preload check and run'
    node() {
	sh "sudo sh ./lib/run.sh"
    }
	
    load "/opt/bin/jenkins-run.groovy"
}
