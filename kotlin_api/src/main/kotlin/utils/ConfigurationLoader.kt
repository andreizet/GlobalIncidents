package utils

import org.json.JSONObject
import java.io.File
import java.io.IOException

class ConfigurationLoader {

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
        val file = File(System.getProperty("ConfigPath"))
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
            ex.printStackTrace()
            println("The configuration file was not found")
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
                        e.printStackTrace()
                    }

                }

                return mInstance
            }
    }

}
