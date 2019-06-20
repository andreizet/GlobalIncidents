package utils

enum class Constants {
    API_LIMIT {
        override fun getKey(): String = "limit"
        override fun getDefault(): Any = 10
    },

    API_FILTER {
        override fun getKey(): String = "filter"
        override fun getDefault(): Any = ""
    },

    API_MIN_LAT {
        override fun getKey(): String = "min_lat"
        override fun getDefault(): Any = -1
    },

    API_MAX_LAT {
        override fun getKey(): String = "max_lat"
        override fun getDefault(): Any = -1
    },

    API_MIN_LNG {
        override fun getKey(): String = "min_lng"
        override fun getDefault(): Any = -1
    },

    API_MAX_LNG {
        override fun getKey(): String = "max_lng"
        override fun getDefault(): Any = -1
    },

    API_ID {
        override fun getKey(): String = "id"
        override fun getDefault(): Any = -1
    },

    API_TITLE {
        override fun getKey(): String = "title"
        override fun getDefault(): Any = ""
    },

    API_DESCRIPTION {
        override fun getKey(): String = "description"
        override fun getDefault(): Any = ""
    },

    API_LAT {
        override fun getKey(): String = "lat"
        override fun getDefault(): Any = ""
    },

    API_LNG {
        override fun getKey(): String = "lng"
        override fun getDefault(): Any = ""
    },

    API_PRIORITY {
        override fun getKey(): String = "priority"
        override fun getDefault(): Any = 0
    },

    API_STATUS {
        override fun getKey(): String = "status"
        override fun getDefault(): Any = 0
    };

    abstract fun getKey(): String
    abstract fun getDefault(): Any
}