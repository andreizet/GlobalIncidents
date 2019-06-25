package deploy

import com.jcraft.jsch.*
import org.slf4j.LoggerFactory
import utils.ConfigurationLoader
import java.io.File
import java.io.IOException


class SSHClient(private val mAddress: String?) {

    private val mLogger = LoggerFactory.getLogger(SSHClient::class.java)
    private var mSession: Session? = null
    private var mSCPChannel: Channel? = null

    @Throws(Exception::class)
    fun Connect(aUsername: String?, aPassword: String?) {
        val jsch = JSch()
        val mKeyFilePath = ConfigurationLoader.instance?.awsKey
        if (mKeyFilePath != null) {
            val keyFile = File(mKeyFilePath)
            jsch.addIdentity(keyFile.absolutePath)
        }

        mLogger.info("Connecting to: $mAddress")

        mSession = jsch.getSession(aUsername, mAddress, kSshPort)

        mSession!!.setConfig("StrictHostKeyChecking", "no")

        if (aPassword != null) {
            mSession!!.setPassword(aPassword)
        }

        mSession!!.connect(kDefaultTimeout)
    }

    @Throws(IOException::class, JSchException::class)
    fun ExecuteCommand(aCommand: String): String {
        println("Execute $aCommand on $mAddress")

        val sb = StringBuilder()

        try {
            val channel = mSession!!.openChannel("exec") as ChannelExec
            channel.setCommand(aCommand)
            channel.inputStream = null
            channel.setErrStream(System.err)

            val `in` = channel.inputStream
            channel.connect()

            val tmp = ByteArray(1024)
            while (true) {
                while (`in`.available() > 0) {
                    val i = `in`.read(tmp, 0, 1024)
                    if (i < 0)
                        break
                    sb.append(String(tmp, 0, i))
                }
                if (channel.isClosed) {
                    if (`in`.available() > 0)
                        continue

                    if (channel.exitStatus != 0) {
                        sb.append("exit-status: " + channel.exitStatus)
                    }
                    break
                }
                try {
                    Thread.sleep(1000)
                } catch (ee: Exception) {
                }

            }
            channel.disconnect()

        } catch (e: Exception) {
            e.printStackTrace()
        }

        mLogger.info("Execution of $aCommand on $mAddress returned $sb")

        return sb.toString()
    }

    fun ConnectSCP(): Boolean {
        try {
            mSCPChannel = mSession!!.openChannel("sftp")
            mSCPChannel!!.connect()
            return true
        } catch (e: JSchException) {
            return false
        }

    }

    fun UploadFile(aSource: String, aDestination: String): Boolean {
        try {
            if (mSCPChannel == null || !mSCPChannel!!.isConnected) {
                mSCPChannel = mSession!!.openChannel("sftp")
                mSCPChannel!!.connect()
            }

            val sftpChannel = mSCPChannel as ChannelSftp?
            sftpChannel!!.put(aSource, aDestination, ChannelSftp.OVERWRITE)
            return true
        } catch (e: JSchException) {
            e.printStackTrace()
            return false
        } catch (e1: SftpException) {
            e1.printStackTrace()
            return false
        }

    }

    fun Disconnect() {
        mSession!!.disconnect()
    }

    companion object {

        private val kSshPort = 22
        private val kDefaultTimeout = 15000
    }
}
