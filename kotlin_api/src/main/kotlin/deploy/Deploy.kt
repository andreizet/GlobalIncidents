package deploy

import java.io.File

object Deploy {

    @Throws(Exception::class)
    @JvmStatic
    fun main(args: Array<String>) {
        val ssh = SSHClient("ec2-34-227-66-111.compute-1.amazonaws.com")
        ssh.Connect("ubuntu", null)
        ssh.ConnectSCP()

        val jarPath = (System.getProperty("user.dir") + File.separator + "build" + File.separator + "libs"
                + File.separator + "global-incidents-kotlin.jar")

        val response: Boolean = ssh.UploadFile(jarPath, "/home/ubuntu")
        if(!response)
        {
            println("Couldn't upload jar")
            return
        }

        val responseKill: String = ssh.ExecuteCommand("sudo pkill java")
        println("Kill response: $responseKill")

        val responseStart: String = ssh.ExecuteCommand("sudo java -jar /home/ubuntu/global-incidents-kotlin.jar > kotlin_output.log &")
        println("Start response: $responseStart")

        ssh.Disconnect()
    }
}