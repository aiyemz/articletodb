package com.dawei.utils;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.MapHandler;


import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.Map;

public class DBUtilsHelper {

    private DataSource ds = null;
    private QueryRunner runner = null;
    private DbPoolConnection dbPoolConnection = DbPoolConnection.getInstance();


    public DBUtilsHelper() {
        try {
            this.ds = dbPoolConnection.getDataSource();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        if (this.ds != null) {
            this.runner = new QueryRunner(this.ds);
        }
    }

    public DBUtilsHelper(DataSource ds) {
        this.ds = ds;
        this.runner = new QueryRunner(this.ds);
    }

    public QueryRunner getRunner() {
        return this.runner;
    }

    public void close() throws SQLException{
        dbPoolConnection.close();
    }

}