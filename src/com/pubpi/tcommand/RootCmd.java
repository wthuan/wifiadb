package com.pubpi.tcommand;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

public final class RootCmd {
	// ִ��linux�����������
	protected static String execRootCmd(String paramString) {
		String result = "";
		try {
			Process localProcess = Runtime.getRuntime().exec("su");// ����Root�����androidϵͳ����su����
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

	// ִ��linux�������ע������
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

	// �жϻ���Android�Ƿ��Ѿ�root�����Ƿ��ȡrootȨ��
	protected static boolean haveRoot() {
		int i = execRootCmdSilent("echo test"); // ͨ��ִ�в������������
		if (i != -1) {
			return true;
		}
		return false;
	}
}