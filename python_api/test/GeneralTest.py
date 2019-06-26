import unittest, sys
from api.GetIncidents import GetIncidents
from utils.ConfigurationLoader import ConfigurationLoader


class TestStringMethods(unittest.TestCase):

    def test_get_incidents(self):
        ConfigurationLoader.load('/Volumes/Macintosh HD/_work/GlobalIncidents/config.json')
        incidents = GetIncidents()
        results = incidents.run()

        self.assertTrue(len(results) > 0)
        self.assertEqual(len(results), 10)


if __name__ == '__main__':
    unittest.main()

