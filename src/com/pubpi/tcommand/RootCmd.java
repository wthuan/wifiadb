package com.pubpi.tcommand;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

public final class RootCmd {
	// 执行linux命令并且输出结果
	protected static String execRootCmd(String paramString) {
		String result = "";
		try {
			Process localProcess = Runtime.getRuntime().exec("su");// 经过Root处理的android系统即有su命令
			OutputStream localOutputStream = localProcess.getOutputStream();
			DataOutputStream localDataOutputStream = new DataOutputStream(localOutputStream);
			InputStream localInputStream = localProcess.getInputStream();
			DataInputStream localDataInputStream = new DataInputStream(localInputStream);
			localDataOutputStream.writeBytes(String.valueOf(paramString) + "\n");
			localDataOutputStream.flush();
			String line = null;
			while ((line = localDataInputStream.readLine()) != null) {
				result = result + line;
			}
			localDataOutputStream.writeBytes("exit\n");
			localDataOutputStream.flush();
			localProcess.waitFor();
			return result;
		} catch (Exception localException) {
			localException.printStackTrace();
			return result;
		}
	}

	// 执行linux命令但不关注结果输出
	protected static int execRootCmdSilent(String paramString) {
		try {
			Process localProcess = Runtime.getRuntime().exec("su");
			OutputStream localObject = localProcess.getOutputStream();
			DataOutputStream localDataOutputStream = new DataOutputStream((OutputStream) localObject);
			localDataOutputStream.writeBytes(paramString + "\n");
			localDataOutputStream.flush();
			localDataOutputStream.writeBytes("exit\n");
			localDataOutputStream.flush();
			int result = -2;
			if (0 == localProcess.waitFor()) {
				result = localProcess.exitValue();
			}
			return result;
		} catch (Exception localException) {
			localException.printStackTrace();
			return -1;
		}
	}

	// 判断机器Android是否已经root，即是否获取root权限
	protected static boolean haveRoot() {
		int i = execRootCmdSilent("echo test"); // 通过执行测试命令来检测
		if (i != -1) {
			return true;
		}
		return false;
	}
}