node {
    // loading and running jenkins tasks
    workspace = pwd()

    project = "cloud-platforms"
    playbook= "create-vms.yml"
    task = "gcp"
    repositoryUrl = "https://github.com/joehmchiu/${project}.git"
    branch = "master"
    msg = "Create VMs in GCP"

    ok = '\u2705'
    no = '\u274C'

    stage 'Preload check and run'
    node() {
        sh "sudo ls -ltrhR ${workspace}*"
		// sh "sudo sh ./lib/run.sh"
    }

	// load "/opt/bin/jenkins-run.groovy"
}
