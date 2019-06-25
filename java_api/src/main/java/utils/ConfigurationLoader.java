package utils;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

public class ConfigurationLoader {
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

  private ConfigurationLoader load() throws Exception{
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
