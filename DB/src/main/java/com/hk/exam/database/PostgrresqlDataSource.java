package com.hk.exam.database;

import javax.sql.DataSource;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

import org.postgresql.ds.PGPoolingDataSource;
import org.postgresql.ds.PGSimpleDataSource;

public class PostgrresqlDataSource {
    public DataSource getDataSource(){
        PGSimpleDataSource ds = new PGSimpleDataSource();
        Properties props = new Properties();
        /*try (FileInputStream fis = new FileInputStream("postgresql.properties")){
            props.load(fis);
            ds.setUrl(props.getProperty("url"));
            ds.setUrl(props.getProperty("user"));
            ds.setUrl(props.getProperty("pw"));
        } catch (IOException e) {
            e.printStackTrace();
        }*/
        ds.setUrl("jdbc:postgresql://localhost:5432/java_exam_db");
        ds.setUser("postgres");
        ds.setPassword("201197mtj");
        return ds;
    }
}
