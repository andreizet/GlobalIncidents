import sys, json


class ConfigurationLoader:
    db_name = db_host = db_user = db_password = aws_key = aws_user = aws_instance = None

    @staticmethod
    def load(file = None):
        try:
            if file is None:
                file = sys.argv[1]
            json_file = open(file, "r")
            json_content = json.loads(json_file.read())
            ConfigurationLoader.db_name = json_content['db_name']
            ConfigurationLoader.db_host = json_content['db_host']
            ConfigurationLoader.db_user = json_content['db_user']
            ConfigurationLoader.db_password = json_content['db_password']
            ConfigurationLoader.aws_key = json_content['aws_key']
            ConfigurationLoader.aws_user = json_content['aws_user']
            ConfigurationLoader.aws_instance = json_content['aws_instance']
            json_file.close()
        except Exception as err:
            print(err)
            print("Couldn't load the configuration file")
            sys.exit(1)
