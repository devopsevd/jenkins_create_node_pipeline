import jenkins.model.*
import hudson.model.*
import hudson.slaves.*
import hudson.plugins.sshslaves.*

Slave slave = new DumbSlave(
                    "test-script",
                    "test node",
                    "/var/jenkins",
                    "1",
                    Node.Mode.NORMAL,
                    "test-slave-label",
                    //new SSHLauncher("10.118.41.231",22,"root","Password1","","","","",""),
                    new JNLPLauncher(),
                    new RetentionStrategy.Always(),
                    new LinkedList())

Jenkins.instance.addNode(slave)
