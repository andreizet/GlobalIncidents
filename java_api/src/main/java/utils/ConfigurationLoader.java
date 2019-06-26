package utils;

import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;

public class ConfigurationLoader {
  Logger mLogger = LoggerFactory.getLogger(ConfigurationLoader.class);

  private static ConfigurationLoader mInstance = null;

  private String mDBName = "";
  private String mDBHost = "";
  private String mDBUser = "";
  private String mDBPass = "";
  private String mAWSKey = "";
  private String mAWSUser = "";
  private String mAWSInstance = "";

  public static ConfigurationLoader getInstance() {
    if(mInstance == null) {
      try {
        ConfigurationLoader conf = new ConfigurationLoader();
        mInstance = conf.load();
      }
      catch(Exception e){
        e.printStackTrace();
      }
    }

    return mInstance;
  }

  private ConfigurationLoader load() {
    String path = System.getProperty("ConfigPath");

    if (path == null)
    {
      mLogger.error("Invalid configuration path");
      System.exit(1);
    }

    try {
      File file = new File(System.getProperty("ConfigPath"));
      BufferedReader br = new BufferedReader(new FileReader(file));

      String st;
      StringBuffer sb = new StringBuffer();
      while ((st = br.readLine()) != null)
        sb.append(st);

      JSONObject obj = new JSONObject(sb.toString());

      this.mDBName = obj.getString("db_name");
      this.mDBHost = obj.getString("db_host");
      this.mDBUser = obj.getString("db_user");
      this.mDBPass = obj.getString("db_password");
      this.mAWSKey = obj.getString("aws_key");
      this.mAWSUser = obj.getString("aws_user");
      this.mAWSInstance = obj.getString("aws_instance");
    }
    catch(IOException ex) {
      mLogger.error("Error reading configuration file");
      return null;
    }

    return this;
  }


  public String getDBName() {
    return this.mDBName;
  }

  public String getDBHost() {
    return this.mDBHost;
  }

  public String getDBUser() {
    return this.mDBUser;
  }

  public String getDBPass() {
    return this.mDBPass;
  }

  public String getAWSKey() {
    return this.mAWSKey;
  }

  public String getAWSUser() {
    return this.mAWSUser;
  }

  public String getAWSInstance() {
    return this.mAWSInstance;
  }

}
