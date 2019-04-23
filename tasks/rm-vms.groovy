node {
    // loading and running jenkins tasks
    workspace = pwd()

    project = "cloud-platforms"
    playbook= "rm-vms.yml"
    task = "gcp"
    repositoryUrl = "https://github.com/joehmchiu/${project}.git"
    branch = "master"
    msg = "Remove VMs in GCP"

    ok = '\u2705'
    no = '\u274C'

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
