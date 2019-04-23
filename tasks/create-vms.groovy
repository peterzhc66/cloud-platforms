node {
    // loading and running jenkins tasks
    workspace = pwd()

    project = "gcp"
    playbook= "create-vms.yml"
    task = ""
    repositoryUrl = "https://github.com/joehmchiu/${project}.git"
    branch = "master"
    msg = "Create VMs in GCP"

    ok = '\u2705'
    no = '\u274C'

    stage 'Git Update'
    node() {
        git url: repositoryUrl, credentialsId: "gcp-2019", branch: branch
        sh "ls -ltrhR"
    }

    stage 'Init Working Env'
    node() {
        sh "sudo [ -d /opt/bin ] || mkdir /opt/bin"
        sh "sudo cp -rf ./bin/* /opt/bin/."
        sh "sudo chmod 755 /opt/bin/*"
        sh "sudo /opt/bin/init.sh ${project} ${task}"
        sh "sudo cp -rf ./${project}/* /opt/${project}/${task}/."
        sh "sudo /opt/bin/init.sh '${project}' '${task}'"
    }

    stage 'Check List'
    node() {
        echo "${ok} Check Workspace: ${workspace}/"
        sh "ls -ltrh /tmp/env/"
        echo "${ok} Check Ansible Availability"
        sh 'which ansible'
        echo "${ok} Check Ansible Version"
        sh 'ansible --version'
        echo "${no} Something's wrong..."
        echo '$&@*&%#)(*#@(*_)*&%#*^@&$)*'
    }

    stage 'Power On'
    node() {
        sh "cd './${project}/${task}';sudo ansible-playbook -vvv ${playbook}"
        // sh "cd './${project}/${task}';sudo ansible-playbook -vvv --tags=\"preload,poweron\" ${playbook}"
    }

    stage "Tasks Finalized"
    node() {
        sh "sudo /opt/bin/log.sh '${msg}'"
        sh "sudo /opt/bin/fin.sh ${project} ${task}"
    }

}
