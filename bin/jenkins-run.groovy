import hudson.model.*
import hudson.EnvVars
import groovy.json.JsonSlurperClassic
import groovy.json.JsonBuilder
import groovy.json.JsonOutput
import java.net.URL

// loading and running jenkins tasks 
workspace = pwd()

stage 'Confirm Task'
node() {
    timeout(time: 30, unit: 'SECONDS') {
        input "${msg}?"
    }
}

stage 'Init Working Env'
node() {
        sh "sudo /opt/bin/init.sh ${project} ${task}"
}

stage 'Check List'
node() {
	echo "\u2705 Check Workspace: ${workspace}/"
        sh "ls -ltrh /tmp/env/"
	echo "\u2705 Check Ansible Availability"
	sh 'which ansible'
	echo "\u2705 Check Ansible Version"
        sh 'ansible --version'
	echo "\u274C Something's wrong..."
	echo '$&@*&%#)(*#@(*_)*&%#*^@&$)*'
}

stage 'SCM Update'
node() {
        git url: repositoryUrl, branch: branch
        sh "sudo cp -rf ./${task}/* /opt/${project}/${task}/."
}

stage 'Run Playbook Tasks'
node() {
    withEnv(["PATH+ANSIBLE=${tool 'ansible-2.4.2.0'}"]) {
        ansiblePlaybook (
            playbook: "/opt/${project}/${task}/${script}",
            sudo: true
        )
    }
}

stage "Tasks Finalized"
node() {
        sh "sudo /opt/bin/log.sh '${msg}'"
        sh "sudo /opt/bin/fin.sh ${project} ${task}"        
}

