package com.linle.init.impl;

import java.io.File;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.linle.common.util.ApplicationContextFactory;
import com.linle.common.util.SysConfig;
import com.linle.init.Initialization;
import com.linle.init.SQLExecution;

public class DBInitialization implements Initialization {
	protected final Logger logger = LoggerFactory.getLogger(getClass());

	private DataSource dataSource = null;

	public DBInitialization() {
		dataSource = (DataSource) ApplicationContextFactory.getInstance()
				.getBean("dataSource");
	}

	@Override
	public void init() throws Exception {
		logger.info("初始化数据库……");
//		clearOldTables();
		createTables();
		logger.info("数据库表初始化完成!");
	}

	private void clearOldTables() throws Exception {
		DataSource dataSource = getDataSource();
		Connection connection = null;
		Statement statement = null;
		Statement delStatement = null;
		ResultSet resultSet = null;
		try {
			connection = dataSource.getConnection();
			DatabaseMetaData metaData = connection.getMetaData();
			String databaseUrl = metaData.getURL();
			logger.debug(databaseUrl);
			if (databaseUrl.indexOf("production") > 0) {
				logger.warn("系统发现即将初始化的为生产环境……");
				logger.warn("这样做会删除所有的表和数据，不可恢复，您确定么？(请输入Y/N)");
				char i = (char) System.in.read();
				if (i != 'Y' && i != 'y') {
					System.exit(0);
				}
			}
			statement = connection.createStatement();
			delStatement = connection.createStatement();
			resultSet = statement.executeQuery("show TABLES");
			delStatement.execute("SET FOREIGN_KEY_CHECKS=0");
			while (resultSet.next()) {
				String tableName = resultSet.getString(1);
				logger.debug("删除表：" + tableName);
				try {
					delStatement.execute("drop table if exists " + tableName);
				} catch (SQLException e) {
					logger.error("删除表：" + tableName + "失败：" + e);
				}
			}
			delStatement.execute("SET FOREIGN_KEY_CHECKS=1");
		} catch (Exception e) {
			logger.error("出错了：" + e);
		} finally {
			if (null != resultSet)
				resultSet.close();
			if (null != statement)
				statement.close();
			if (null != delStatement)
				delStatement.close();
			if (null != connection)
				connection.close();
		}
	}

	private boolean createTables() throws Exception {
		Connection connection = null;
		try {
			logger.info("应用升级处理开始……");
			connection = dataSource.getConnection();
			SQLExecution sqlExecution = new SQLExecution(connection);	
			File scriptDdl = new File(
					SysConfig.DBINIT_SCRIPT_DDL);
			if (scriptDdl.isDirectory()) {
				sqlExecution.runScript(scriptDdl);
			}
		} catch (Exception e) {
			logger.error("初始化程序出错：" + e);
			return false;
		} finally {
			if (null != connection) {
				try {
					connection.close();
				} catch (SQLException e) {
					logger.error("关闭JDBC出错：" + e);
				}
			}
		}
		logger.info("应用升级处理结束……");
		return true;
	}

	private DataSource getDataSource() {
		return (DataSource) ApplicationContextFactory.getInstance().getBean(
				"dataSource");
	}
}
