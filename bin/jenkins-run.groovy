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
	sh "sudo sh /opt/bin/init.sh '${project}' '${task}'"
	sh "sudo cp -rf ./${project}/* /opt/${project}/${task}/."
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
	sh "sudo sh /opt/bin/log.sh '${msg}'"
	sh "sudo sh /opt/bin/fin.sh ${project} ${task}"
}
