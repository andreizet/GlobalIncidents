import paramiko


class SSHConnection:
    ssh = None
    host = None
    user = None
    password = ""
    key = None

    def __init__(self, aHost, aUser, aPassword, aKey):
        self.ssh = paramiko.SSHClient()
        self.ssh.set_missing_host_key_policy(paramiko.AutoAddPolicy())
        self.host = aHost
        self.user = aUser
        self.password = aPassword
        self.key = aKey

    def connect(self):
        self.ssh.connect(self.host, username=self.user, password=self.password, key_filename=self.key)

    def execute(self, command):
        stdin, stdout, stderr = self.ssh.exec_command(command)
        to_return = stdout.readlines()
        print(to_return)
        print(stderr.readlines())
        return to_return

    def close(self):
        self.ssh.close()
