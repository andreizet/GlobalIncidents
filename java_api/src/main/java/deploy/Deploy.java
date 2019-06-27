package deploy;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import utils.ConfigurationLoader;

import java.io.File;

public class Deploy {
  public static Logger mLogger = LoggerFactory.getLogger(Deploy.class);

  public static void main(String[] args) throws Exception{
    SSHClient ssh = new SSHClient(ConfigurationLoader.getInstance().getAWSInstance());
    ssh.Connect(ConfigurationLoader.getInstance().getAWSUser(), null);
    ssh.ConnectSCP();

    String jarPath = System.getProperty("user.dir") + File.separator + "build" + File.separator + "libs"
                     + File.separator + "global-incidents-java.jar";

    boolean uploadResponse = ssh.UploadFile(jarPath, "/home/ubuntu");

    if(!uploadResponse)
    {
      mLogger.info("Couldn't upload jar");
      return;
    }

    String killResponse = ssh.ExecuteCommand("for pid in $(ps -aux | grep global-incidents-kotlin | awk '{print $2}'); do pkill $pid; done");
    mLogger.info("Kill response: " + killResponse);

    String startResponse = ssh.ExecuteCommand("sudo java -jar -DConfigPath=/home/ubuntu/config.json " +
                                              "/home/ubuntu/global-incidents-java.jar > java_output.log &");
    mLogger.info("Start response: " + startResponse);

    ssh.Disconnect();
  }
}
