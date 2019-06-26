package deploy;

import utils.ConfigurationLoader;

import java.io.File;

public class Deploy {

  public static void main(String[] args) throws Exception{
    SSHClient ssh = new SSHClient(ConfigurationLoader.getInstance().getAWSInstance());
    ssh.Connect(ConfigurationLoader.getInstance().getAWSUser(), null);
    ssh.ConnectSCP();

    String jarPath = System.getProperty("user.dir") + File.separator + "build" + File.separator + "libs"
                     + File.separator + "global-incidents-java.jar";

    boolean uploadResponse = ssh.UploadFile(jarPath, "/home/ubuntu");

    if(!uploadResponse)
    {
      System.out.println("Couldn't upload jar");
      return;
    }

    String killResponse = ssh.ExecuteCommand("sudo pkill java");
    System.out.println("Kill response: " + killResponse);

    String startResponse = ssh.ExecuteCommand("sudo java -jar -DConfigPath=/home/ubuntu/config.json " +
                                              "/home/ubuntu/global-incidents-java.jar > java_output.log &");
    System.out.println("Start response: " + startResponse);

    ssh.Disconnect();
  }
}
