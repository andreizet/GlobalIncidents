from deploy.SSHConnection import SSHConnection
import platform, sys, os
from utils.ConfigurationLoader import ConfigurationLoader


ConfigurationLoader.load()
USER = ConfigurationLoader.aws_user
INSTANCE = ConfigurationLoader.aws_instance

key = ConfigurationLoader.aws_key

# Alternatively, the path can be hardcoded like in the following lines, but it is not secure at all:
# if "Windows" in platform.platform():
#     key = "C:/secure/aws_ssh"
# elif "Darwin" in platform.platform():
#     key = "/Volumes/Macintosh HD/Caphyon/aws_ssh"
# else:
#     key = "/home/caphyon/secure/aws_ssh"

connection = SSHConnection(INSTANCE, USER, '', key)
try:
    # Connect to the server
    connection.connect()
    print("Connected")

    # Get the latest updates
    response = connection.execute('cd /home/ubuntu/GlobalIncidents/; git pull --rebase')
    print('Rebase response: {}'.format(response))

    # Kill the running instance
    response = connection.execute('sudo pkill python3')
    print("Killing old: {}".format(response))

    response = connection.execute('sudo nohup python3 /home/ubuntu/GlobalIncidents/python_api/apiEndpoint.py '
                                  '/home/ubuntu/config.json > py_output.log &')
    print('Server start: {}'.format(response))

    print("Deploy done!")
except Exception as err:
    print("Couldn't deploy. The following error occurred: ")
    print(err)
finally:
    connection.close()
