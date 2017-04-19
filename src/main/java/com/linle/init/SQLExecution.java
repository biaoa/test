package com.linle.init;

import java.io.File;
import java.io.FileFilter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.Reader;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Date;

import org.apache.ibatis.jdbc.ScriptRunner;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.linle.common.util.SysConfig;


public class SQLExecution {
	public static final Logger _logger = LoggerFactory.getLogger(SQLExecution.class);
	private final static Logger logger = LoggerFactory
			.getLogger(SQLExecution.class);
	
	private final static String CREATE_SCRIPT = "create table if not exists db_maintain_script" +
			"(" +
			"	file_name varchar(128) not null COMMENT '文件名'," +
			"	version varchar(32) COMMENT '版本'," +
			"	file_last_modified_at bigint COMMENT '最后修改时间'," +
			"	checksum varchar(64) COMMENT '文件摘要'," +
			"	executed_at datetime COMMENT '执行时间'," +
			"	succeeded int(1) COMMENT '执行结果'" +
			")ENGINE = InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT = '数据库脚本执行记录';";
	
	private Connection connection = null;
	
	public SQLExecution(Connection connection) throws SQLException {
		this.connection = connection;
		createTable(connection);
	}

	public boolean runScript(File directory) {
		File[] sqlFiles = getSqlFiles(directory);
		sort(sqlFiles);
		for (File sqlFile : sqlFiles) {
			if (sqlFile.isFile()) {
				Script script = getScript(sqlFile);
				if (!isRun(connection, script))
					continue;
				logger.info("开始运行脚本：" + sqlFile.getPath());
				if (runner(connection, sqlFile)) {
					script.setSucceeded(1);
				} else {
					script.setSucceeded(0);
				}
				saveRunLog(connection, script);
			} else if (sqlFile.isDirectory()) {
				runScript(sqlFile);
			}
		}
		return true;
	}	

	private void saveRunLog(Connection connection, Script script) {
		String sql = "insert into db_maintain_script values (?,?,?,?,now(),?)";
		PreparedStatement statement = null;
		try {
			connection.setAutoCommit(false);
			statement = connection.prepareStatement(sql);
			int index = 1;
			statement.setString(index++, script.getFileName());
			statement.setString(index++, script.getVirsion());
			statement.setLong(index++, script.getFileLastModifiedAt());
			statement.setString(index++, script.getChecksum());
			statement.setInt(index++, script.getSucceeded());
			statement.executeUpdate();
			connection.commit();
			isRun(connection, script);
		} catch (Exception e) {
			logger.error("保存执行记录出错：" + e);
		} finally {
			try {
				if (null != statement)
					statement.close();
			} catch (Exception e) {
				e.printStackTrace(); _logger.error("出错了", e);
			}
		}
	}
	
	private boolean isRun(Connection connection, Script script) {
		String sql = "select 1 from db_maintain_script where file_name =? and checksum =?";
		PreparedStatement statement = null;
		ResultSet resultSet = null;
		boolean result = true;
		try {
			statement = connection.prepareStatement(sql);
			statement.setString(1, script.getFileName());
			statement.setString(2, script.getChecksum());
			resultSet = statement.executeQuery();

			if (resultSet.next())
				result = false;
		} catch (SQLException e) {
			logger.error("查询出错：" + e);
		} finally {
			try {
				if (null != resultSet)
					resultSet.close();
				if (null != statement)
					statement.close();
			} catch (Exception e) {
				e.printStackTrace();
				_logger.error("出错了", e);
			}
		}
		return result;
	}	

	private Script getScript(File sqlFile) {
		Script script = new Script();
		script.setFileName(sqlFile.getName());
		script.setVirsion(sqlFile.getName().split("_")[0]);
		script.setFileLastModifiedAt(sqlFile.lastModified());
		script.setExecutedAt(new Date());
		script.setChecksum(getChecksum(sqlFile));
		return script;
	}

	private String getChecksum(File sqlFile) {
		try {
			MessageDigest digest = MessageDigest.getInstance("MD5");
			byte buffer[] = new byte[1024];
			int len;
			FileInputStream in = new FileInputStream(sqlFile);
			while ((len = in.read(buffer, 0, 1024)) != -1) {
				digest.update(buffer, 0, len);
			}
			in.close();
			BigInteger bigInt = new BigInteger(1, digest.digest());
			return bigInt.toString(16);
		} catch (NoSuchAlgorithmException e) {
			logger.error("获取MD5失败:" + e);
		} catch (FileNotFoundException e) {
			logger.error("文件不存在:" + e);
		} catch (IOException e) {
			logger.error("读文件失败:" + e);
		}
		return null;
	}
	private File[] getSqlFiles(File directory) {
		return directory.listFiles(new FileFilter() {
			@Override
			public boolean accept(File pathname) {
				if (pathname.isDirectory()
						|| (pathname.getName().indexOf("_") > 0 && pathname
								.getName().toLowerCase().endsWith(".sql"))) {
					return true;
				}
				return false;
			}
		});
	}
	private void sort(File[] sqlFiles) {
		Arrays.sort(sqlFiles, new Comparator<File>() {
			@Override
			public int compare(File o1, File o2) {
				if (o1.isDirectory() && o2.isFile())
					return 1;
				if (o1.isFile() && o2.isDirectory())
					return -1;
				return o1.getName().compareTo(o2.getName());
			}
		});
	}

	private boolean runner(Connection connection, File scriptFile) {
		try {
			ScriptRunner runner = new ScriptRunner(connection);
			String logPath = SysConfig.DBINIT_LOGS;
			Date date = new Date();
			SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
			logPath += "/" + formatter.format(date);
			File path = new File(logPath);
			if (!path.exists()) {
				path.mkdirs();
			}
			formatter = new SimpleDateFormat("yyyy-MM-dd_HH_mm_ss");
			File errorLogFile = new File(logPath + "/error-"
					+ scriptFile.getName() + "-" + formatter.format(date)
					+ ".log");
			File infoLogFile = new File(logPath + "/info-"
					+ scriptFile.getName() + "-" + formatter.format(date)
					+ ".log");
			PrintWriter pwError = new PrintWriter(errorLogFile);
			PrintWriter pwInfo = new PrintWriter(infoLogFile);
			runner.setErrorLogWriter(pwError);
			runner.setLogWriter(pwInfo);
			Reader reader = new FileReader(scriptFile);
			runner.runScript(reader);
			pwError.close();
			pwInfo.close();
			if (errorLogFile.exists() && errorLogFile.length() > 0) {
				logger.error("sql文件" + scriptFile.getPath() + "执行出错了!");
				logger.error("请检查日志文件：" + errorLogFile.getPath());
				return false;
			} else {
				logger.debug("sql文件" + scriptFile.getPath() + "执行成功了!");
				errorLogFile.deleteOnExit();
			}
		} catch (Exception e) {
			logger.error("出错了：" + e);
			return false;
		}
		return true;
	}
	
	private void createTable(Connection connection) throws SQLException{
		Statement statement = connection.createStatement();
		statement.execute(CREATE_SCRIPT);
		statement.close();
	}
}
