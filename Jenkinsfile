node('master') {
        checkout scm

        // modify node_name and ip address fields  //
        // ---------------------------------------//
        def node_name           = 'chefAutoMat231'
        def vm_ip               = '10.118.41.231'
        def agent_name          = 'chefAutoMat231'
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

        def groovy_script       =   """ 
                                   import jenkins.model.*
                                   import hudson.model.*
                                   import hudson.slaves.*
                                   import hudson.plugins.sshslaves.*
                                   Slave slave = new DumbSlave(
                                                        \"${agent_name}\",
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
                    //bat(/knife cookbook list/)
                }

            }    

            // stage('Check vCenter connection'){
                            
            //     if (isUnix()) {
            //         sh "knife vsphere template list"
            //     } else {
            //         //bat(/knife vsphere template list/)
            //     }

            // }   

                
            // stage('Creat VM') {

            //     if (isUnix()) {
            //         sh "knife vsphere vm clone ${node_name} --template ${vm_template} --start true --node-name ${node_name} --resource-pool ${vm_resource_pool} --cspec ${vm_spec} --cips '${vm_ip}/24' --cdomain ${vm_domain} --verbose"
            //     } else {
            //         //bat(/knife vsphere vm clone "${node_name}" --template "${vm_template}" --start true --node-name "${node_name}" --resource-pool "${vm_resource_pool}" --cspec "${vm_spec}" --cips "${vm_ip}" --cdomain "${vm_domain}" --verbose/)
            //     }
            // }
    
            // stage('Add VM as chef node') {
            //     timeout(time: 300, unit: 'SECONDS'){
            //         waitUntil{
            //             def ret = sh(script: "sshpass -p ${ssh_pwd} ssh ${ssh_user}@${vm_ip} uptime", returnStatus: true)
            //             //println ret
            //             return ( ret == 0 )  
                        
            //         }
            //     }

            //     if (isUnix()) {
            //         sh "knife bootstrap ${vm_ip} --ssh-user ${ssh_user} --ssh-password ${ssh_pwd} --node-name ${node_name} --sudo --verbose"
            //     } else {
            //         //bat(/knife bootstrap 10.118.41.247 --ssh-user root --ssh-password Password1 --node-name chefAutoMat247 --sudo --verbose/)
            //     }
            // }

            // stage('Add aos recipe') {    
            //     if (isUnix()) {
            //         sh "knife node run_list add ${node_name} 'role[aosNexus]'"
            //     } else {
            //         //bat(/knife node run_list add chefAutoMat241 'role[dockerinstall]'/)
            //     }
            // }
            // stage('Setup aos containers') {
    
            //     if (isUnix()) {
            //         sh "knife ssh 'name:${node_name}' 'sudo chef-client' -a ipaddress --ssh-user ${ssh_user} --ssh-password ${ssh_pwd} --verbose"
            //     } else {
            //         //bat(/knife ssh 'name:chefAutoMat241' 'sudo chef-client' -a ipaddress --ssh-user root --ssh-password Password1 --verbose/)
            //     }
            // }
    }

            stage('Create slave entry'){

                 //test                           
                    sh "curl --data-urlencode  'script=${groovy_script}' -X POST http://admin:7be803fbaa37ef9ab9455a981c1e19b6@localhost:8080/scriptText"

            }

}

