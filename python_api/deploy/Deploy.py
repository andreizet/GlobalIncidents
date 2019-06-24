from deploy.SSHConnection import SSHConnection
import platform, sys, os


USER = "ubuntu"
INSTANCE = "ec2-34-227-66-111.compute-1.amazonaws.com"

if "Windows" in platform.platform():
    key = "C:/secure/aws_ssh"
else:
    key = "/home/caphyon/secure/aws_ssh"

connection = SSHConnection(INSTANCE, USER, '', key)
connection.connect()
response = connection.execute('ls -al')
print(response)
