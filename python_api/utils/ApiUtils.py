class ApiUtils:

    @staticmethod
    def get_param(a_args, a_param):
        if a_args.get(a_param['key']) is None:
            return a_param['default']
        else:
            return a_args.get(a_param['key'])

    # @staticmethod
    # def get_query_cond(a_param, a_default, a_col_name, search):
    #     value = ApiUtils.get_param(a_param, a_default)
    #     if a_param is "limit":
    #         return " limit " + value
    #
    #     if value is not None:
    #         if search is False:
    #             return " and " + a_col_name + "=" + value
    #         else:
    #             return " and " + a_col_name + " like '%" + value + "%'"
    #     else:
    #         return search
