package utils;

public enum Constants {
  API_LIMIT {
    @Override
    public String getKey() {
      return "limit";
    }

    @Override
    public Integer getDefault() {
      return 10;
    }
  },

  API_FILTER {
    @Override
    public String getKey() {
      return "filter";
    }

    @Override
    public String getDefault() {
      return null;
    }
  },

  API_MIN_LAT {
    @Override
    public String getKey() {
      return "min_lat";
    }

    @Override
    public Double getDefault() {
      return -1d;
    }
  },

  API_MAX_LAT {
    @Override
    public String getKey() {
      return "max_lat";
    }

    @Override
    public Double getDefault() {
      return -1d;
    }
  },

  API_MIN_LNG {
    @Override
    public String getKey() {
      return "min_lng";
    }

    @Override
    public Double getDefault() {
      return -1d;
    }
  },

  API_MAX_LNG {
    @Override
    public String getKey() {
      return "max_lat";
    }

    @Override
    public Double getDefault() {
      return -1d;
    }
  },

  API_ID {
    @Override
    public String getKey() {
      return "id";
    }

    @Override
    public Integer getDefault() {
      return -1;
    }
  },

  API_TITLE {
    @Override
    public String getKey() {
      return "title";
    }

    @Override
    public String getDefault() {
      return null;
    }
  },

  API_DESCRIPTION {
    @Override
    public String getKey() {
      return "description";
    }

    @Override
    public String getDefault() {
      return null;
    }
  },

  API_LAT {
    @Override
    public String getKey() {
      return "lat";
    }

    @Override
    public Double getDefault() {
      return -1d;
    }
  },

  API_LNG {
    @Override
    public String getKey() {
      return "lng";
    }

    @Override
    public Double getDefault() {
      return -1d;
    }
  },

  API_PRIORITY {
    @Override
    public String getKey() {
      return "priority";
    }

    @Override
    public Integer getDefault() {
      return 0;
    }
  },

  API_STATUS {
    @Override
    public String getKey() {
      return "status";
    }

    @Override
    public Integer getDefault() {
      return 0;
    }
  };

  public abstract String getKey();

  public abstract Object getDefault();
}
