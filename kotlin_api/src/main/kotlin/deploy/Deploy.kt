package deploy

import org.slf4j.LoggerFactory
import utils.ConfigurationLoader
import java.io.File

object Deploy {

    private val mLogger = LoggerFactory.getLogger(Deploy::class.java)

    @Throws(Exception::class)
    @JvmStatic
    fun main(args: Array<String>) {
        val ssh = SSHClient(ConfigurationLoader.instance?.awsInstance)
        ssh.Connect(ConfigurationLoader.instance?.awsUser, null)
        ssh.ConnectSCP()

        val jarPath = (System.getProperty("user.dir") + File.separator + "build" + File.separator + "libs"
                + File.separator + "global-incidents-kotlin.jar")

        val response: Boolean = ssh.UploadFile(jarPath, "/home/ubuntu")
        if(!response)
        {
            mLogger.error("Couldn't upload jar")
            return
        }

        val responseKill: String = ssh.ExecuteCommand("for pid in $(ps -aux | grep global-incidents-kotlin | awk '{print $2}'); do pkill \$pid; done")
        mLogger.info("Kill response: $responseKill")

        val responseStart: String = ssh.ExecuteCommand("sudo java -jar -DConfigPath=/home/ubuntu/config.json " +
                                                        "/home/ubuntu/global-incidents-kotlin.jar > kotlin_output.log &")
        mLogger.info("Start response: $responseStart")

        ssh.Disconnect()
    }
}