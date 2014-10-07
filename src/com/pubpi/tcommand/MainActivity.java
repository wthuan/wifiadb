package com.pubpi.tcommand;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

<<<<<<< HEAD
import cn.trinea.android.common.util.ShellUtils;
=======
>>>>>>> 967d73b3d2f403df2affda4a40e21765ce06af29
import android.app.Activity;
import android.content.Context;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity {

	private Handler handler;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		handler = new Handler();
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		LinearLayout parentView = (LinearLayout) findViewById(R.id.parentview);

		final Button button = (Button) findViewById(R.id.button1);

		for (String ip : getLocalIpAddress()) {
			TextView textView = new TextView(this);
			LayoutParams params = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
			textView.setLayoutParams(params);
			textView.setText(ip);
			parentView.addView(textView);
		}

		new Thread(new Runnable() {

			@Override
			public void run() {
				while (true) {
<<<<<<< HEAD

					int result = ShellUtils.execCommand("netstat -an|grep 5555|grep LISTEN", false).result;
=======
					int result = RootCmd.execRootCmdSilent("netstat -an|grep 5555|grep LISTEN");
>>>>>>> 967d73b3d2f403df2affda4a40e21765ce06af29
					if (0 == result) {
						handler.post(new Runnable() {
							public void run() {
								button.setText("停止wifi调试服务");
							}
						});
					} else {
						handler.post(new Runnable() {
							public void run() {
								button.setText("启动wifi调试服务");
							}
						});
					}
					try {
<<<<<<< HEAD
						Thread.currentThread().sleep(5000);
=======
						Thread.currentThread().sleep(600000);
>>>>>>> 967d73b3d2f403df2affda4a40e21765ce06af29
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
		}).start();

		button.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				button.setClickable(false);
<<<<<<< HEAD
				boolean isAdbWifiStarted = (0 == ShellUtils.execCommand("netstat -an|grep 5555|grep LISTEN", false).result);
				if (!isAdbWifiStarted) {

					if (0 != ShellUtils.execCommand("setprop service.adb.tcp.port 5555", true).result) {
						Toast.makeText(MainActivity.this, "设置端口失败", Toast.LENGTH_SHORT).show();
						return;
					}
					if (0 != ShellUtils.execCommand(new String[] { "stop adbd", "start adbd" }, true).result) {
=======
				boolean isAdbWifiStarted = (0 == RootCmd.execRootCmdSilent("netstat -an|grep 5555|grep LISTEN"));
				if (!isAdbWifiStarted) {

					if (0 != RootCmd.execRootCmdSilent("setprop service.adb.tcp.port 5555")) {
						Toast.makeText(MainActivity.this, "设置端口失败", Toast.LENGTH_SHORT).show();
						return;
					}
					if (0 != RootCmd.execRootCmdSilent("stop adbd;start adbd")) {
>>>>>>> 967d73b3d2f403df2affda4a40e21765ce06af29
						Toast.makeText(MainActivity.this, "启动服务失败", Toast.LENGTH_SHORT).show();
						return;
					}
					button.setText("停止wifi调试服务");
				} else {
<<<<<<< HEAD
					if (0 != ShellUtils.execCommand("stop adbd", true).result) {
=======
					if (0 != RootCmd.execRootCmdSilent("stop adbd")) {
>>>>>>> 967d73b3d2f403df2affda4a40e21765ce06af29
						Toast.makeText(MainActivity.this, "停止服务", Toast.LENGTH_SHORT).show();
						return;
					}
					button.setText("启动wifi调试服务");
				}
				button.setClickable(true);
			}
		});
	}

	public List<String> getLocalIpAddress() {
		ArrayList<String> ipList = new ArrayList<String>();
		try {
			for (Enumeration<NetworkInterface> en = NetworkInterface.getNetworkInterfaces(); en.hasMoreElements();) {
				NetworkInterface intf = en.nextElement();
				for (Enumeration<InetAddress> enumIpAddr = intf.getInetAddresses(); enumIpAddr.hasMoreElements();) {
					InetAddress inetAddress = enumIpAddr.nextElement();
					if (!inetAddress.isLoopbackAddress() && !inetAddress.isLinkLocalAddress()) {
						ipList.add(inetAddress.getHostAddress().toString());
					}
				}
			}
		} catch (SocketException ex) {
			Log.e("WifiPreference IpAddress", ex.toString());
		}
		return ipList;
	}

	public String getLocalMacAddress() {
		WifiManager wifi = (WifiManager) getSystemService(Context.WIFI_SERVICE);
		WifiInfo info = wifi.getConnectionInfo();
		return info.getMacAddress();
	}

}
