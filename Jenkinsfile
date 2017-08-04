node('master') {
        checkout scm

        // modify node_name and ip address fields  //
        // ---------------------------------------//
        def node_name           = 'chefAutoMat234'
        def vm_ip               = '10.118.41.234'
        //---------------------------------------//

        def vm_template         = 'CentOsTemplate'
        def vm_resource_pool    = 'Compute/Chef_Test'
        def vm_spec             =  'CentOs_Chef'
        def vm_domain           =  'csa.local'
        def ssh_user            = 'root'
        def ssh_pwd             = 'Password1'
        def agent_description   = 'jenkins_node'
        def install_location    =  '/var/jenkins'
        def executor_count      = '1'
        def slave_label         = 'test-slave-label'
        def jenkins_user        = 'admin'
        def jenkins_pwd         = 'Password1'

        def groovy_script       =   """ 
                                   import jenkins.model.*
                                   import hudson.model.*
                                   import hudson.slaves.*
                                   import hudson.plugins.sshslaves.*
                                   Slave slave = new DumbSlave(
                                                        \"${node_name}\",
                                                        \"${agent_description}\",
                                                        \"${install_location}\",
                                                        \"${executor_count}\",
                                                        Node.Mode.NORMAL,
                                                        \"${slave_label}\",
                                                        new SSHLauncher(\"${vm_ip}\",22,\"${ssh_user}\",\"${ssh_pwd}\","","","","",""),
                                                        new RetentionStrategy.Always(),
                                                        new LinkedList())
                                    Jenkins.instance.addNode(slave)
                                    """
        dir('chef-repo'){
            
            stage('Get chef repo'){                            
                git 'https://github.com/devopsevd/chef_docker_cookbooks.git'
            }
            
            stage('Check chef connection'){                            
                if (isUnix()) {
                    sh "knife cookbook list"
                } else {
                    //bat(//) // add code if running on windows
                }

            }    

            stage('Check vCenter connection'){                            
                if (isUnix()) {
                    sh "knife vsphere template list"
                } else {
                    //bat(//) // add code if running on windows
                }

            }
                
            stage('Creat VM') {
                if (isUnix()) {
                    sh "knife vsphere vm clone ${node_name} --template ${vm_template} --start true --node-name ${node_name} --resource-pool ${vm_resource_pool} --cspec ${vm_spec} --cips '${vm_ip}/24' --cdomain ${vm_domain} --verbose"
                } else {
                    //bat(//) // add code if running on windows
                }
            }
    
            stage('Add VM as chef node') {
                timeout(time: 300, unit: 'SECONDS'){
                    waitUntil{
                        def ret = sh(script: "sshpass -p ${ssh_pwd} ssh ${ssh_user}@${vm_ip} uptime", returnStatus: true)
                        //println ret
                        return ( ret == 0 )                          
                    }
                }

                if (isUnix()) {
                    sh "knife bootstrap ${vm_ip} --ssh-user ${ssh_user} --ssh-password ${ssh_pwd} --node-name ${node_name} --sudo --verbose"                    
                    // script if bootstrap needs to pull and run the docker containers
                    //sh "knife bootstrap ${vm_ip} --ssh-user ${ssh_user} --ssh-password ${ssh_pwd} --node-name ${node_name} --sudo --verbose --run-list 'role[aosNexus]'"
                } else {
                    //bat(//) // add code if running on windows
                }
            }

            stage('Add aos recipe') {    
                if (isUnix()) {
                    sh "knife node run_list add ${node_name} 'role[aosNexus]'"
                } else {
                    //bat(//) // add code if running on windows
                }
            }
            stage('Setup aos containers') {    
                if (isUnix()) {
                    sh "knife ssh 'name:${node_name}' 'sudo chef-client' -a ipaddress --ssh-user ${ssh_user} --ssh-password ${ssh_pwd} --verbose"
                } else {
                    //bat(//) // add code if running on windows
                }
            }
        }

            stage('Add VM as Jenkins slave'){                        
                if (isUnix()) {
                    sh "curl --user '${jenkins_user}:${jenkins_pwd}' --data-urlencode  'script=${groovy_script}' -X POST http://localhost:8080/scriptText"
                } else {
                    //bat(//) // add code if running on windows
                }
            }

}

