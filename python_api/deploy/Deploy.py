from deploy.SSHConnection import SSHConnection
from utils.ConfigurationLoader import ConfigurationLoader
from unittest import main

ConfigurationLoader.load('/Volumes/Macintosh HD/_work/GlobalIncidents/config.json')
USER = ConfigurationLoader.aws_user
INSTANCE = ConfigurationLoader.aws_instance

key = ConfigurationLoader.aws_key

testResult = main(module='test', exit=True)

connection = SSHConnection(INSTANCE, USER, '', key)
try:
    # Connect to the server
    connection.connect()
    print("Connected")

    # Get the latest updates
    response = connection.execute('cd /home/ubuntu/GlobalIncidents/; sudo git pull --rebase')
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
