package deploy;

import com.jcraft.jsch.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

public class SSHClient {

  private static final int kSshPort = 22;
  private static int kDefaultTimeout = 15000;

  private Logger mLogger = LoggerFactory.getLogger(SSHClient.class);
  private String mAddress;
  private Session mSession;
  private Channel mSCPChannel;

  public SSHClient(String aAddress) {
    mAddress = aAddress;
  }

  public void Connect(String aUsername, String aPassword) throws Exception
  {
    JSch jsch = new JSch();
    String mKeyFilePath = "C:/secure/aws_ssh";
    if (mKeyFilePath != null)
    {
      File keyFile = new File(mKeyFilePath);
      jsch.addIdentity(keyFile.getAbsolutePath());
    }

    mLogger.info("Connecting to: " + mAddress);

    mSession = jsch.getSession(aUsername, mAddress, kSshPort);

    mSession.setConfig("StrictHostKeyChecking", "no");

    if (aPassword != null)
    {
      mSession.setPassword(aPassword);
    }

    mSession.connect(kDefaultTimeout);
  }

  public String ExecuteCommand(String aCommand) throws IOException, JSchException
  {
    System.out.println("Execute " + aCommand + " on " + mAddress);

    StringBuilder sb = new StringBuilder();

    try
    {
      ChannelExec channel = (ChannelExec) mSession.openChannel("exec");
      channel.setCommand(aCommand);
      channel.setInputStream(null);
      channel.setErrStream(System.err);

      InputStream in = channel.getInputStream();
      channel.connect();

      byte[] tmp = new byte[1024];
      while (true)
      {
        while (in.available() > 0)
        {
          int i = in.read(tmp, 0, 1024);
          if (i < 0)
            break;
          sb.append(new String(tmp, 0, i));
        }
        if (channel.isClosed())
        {
          if (in.available() > 0)
            continue;

          if (channel.getExitStatus() != 0)
          {
            sb.append("exit-status: " + channel.getExitStatus());
          }
          break;
        }
        try
        {
          Thread.sleep(1000);
        }
        catch (Exception ee)
        {
        }
      }
      channel.disconnect();

    }
    catch (Exception e)
    {
      e.printStackTrace();
    }

    mLogger.info("Execution of " + aCommand + " on " + mAddress + " returned " + sb.toString());

    return sb.toString();
  }

  public boolean ConnectSCP()
  {
    try
    {
      mSCPChannel = mSession.openChannel("sftp");
      mSCPChannel.connect();
      return true;
    }
    catch (JSchException e)
    {
      return false;
    }
  }

  public boolean UploadFile(String aSource, String aDestination)
  {
    try
    {
      if (mSCPChannel == null || !mSCPChannel.isConnected())
      {
        mSCPChannel = mSession.openChannel("sftp");
        mSCPChannel.connect();
      }

      ChannelSftp sftpChannel = (ChannelSftp) mSCPChannel;
      sftpChannel.put(aSource, aDestination, ChannelSftp.OVERWRITE);
      return true;
    }
    catch (JSchException e)
    {
      e.printStackTrace();
      return false;
    }
    catch (SftpException e1)
    {
      e1.printStackTrace();
      return false;
    }
  }

  public void Disconnect()
  {
    mSession.disconnect();
  }
}
