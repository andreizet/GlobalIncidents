package utils

import org.json.JSONObject
import org.slf4j.LoggerFactory
import java.io.File
import java.io.IOException

class ConfigurationLoader {
    private val mLogger = LoggerFactory.getLogger(ConfigurationLoader::class.java)

    var dbName = ""
        private set
    var dbHost = ""
        private set
    var dbUser = ""
        private set
    var dbPass = ""
        private set
    var awsKey = ""
        private set
    var awsUser = ""
        private set
    var awsInstance = ""
        private set

    @Throws(Exception::class)
    private fun load(): ConfigurationLoader? {
        val path = System.getProperty("ConfigPath")

        if (path == null) {
            mLogger.error("Invalid configuration path")
            System.exit(1)
        }

        val file = File(path)
        try {
            val content = file.readText()

            val obj = JSONObject(content)

            this.dbName = obj.getString("db_name")
            this.dbHost = obj.getString("db_host")
            this.dbUser = obj.getString("db_user")
            this.dbPass = obj.getString("db_password")
            this.awsKey = obj.getString("aws_key")
            this.awsUser = obj.getString("aws_user")
            this.awsInstance = obj.getString("aws_instance")
        }
        catch(ex: IOException){
            mLogger.error("The configuration file was not found")
            return null
        }


        return this
    }

    companion object {
        private var mInstance: ConfigurationLoader? = null

        val instance: ConfigurationLoader?
            get() {
                if (mInstance == null) {
                    try {
                        val conf = ConfigurationLoader()
                        mInstance = conf.load()
                    } catch (e: Exception) {
                        LoggerFactory.getLogger(ConfigurationLoader::class.java)
                            .error("The configuration file was not found")
                    }

                }

                return mInstance
            }
    }

}
