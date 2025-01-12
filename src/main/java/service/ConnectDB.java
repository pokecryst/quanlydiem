package service;

import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Properties;

public class ConnectDB {
	public static String getUrl() {
		var url = "";
		try (
				var file = new FileInputStream("db.properties");
				) {
			var prop = new Properties();
			prop.load(file);
			url = prop.getProperty("url")+
					prop.getProperty("server")+":"+
					prop.getProperty("port")+";databaseName="+
					prop.getProperty("databaseName")+";user="+
					prop.getProperty("user")+";password="+
					prop.getProperty("password")+";encrypt="+
					prop.getProperty("encrypt")+";trustServerCertificate="+
					prop.getProperty("trustServerCertificate")+";";
		}catch(Exception e) {
			e.printStackTrace();
		}
		return url;
	}

	public static Connection getCon() {
		Connection conn = null;
		try {
			conn = DriverManager.getConnection(getUrl());
		}catch(Exception e) {
			e.printStackTrace();
			conn = null;
		}
		return conn;
	}

}
