package com.java.socket.j;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import javax.swing.JFrame;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import com.java.socket.util.Util;

public class Server {
	Util util = new Util();
	
	JFrame frame = new JFrame("Server");
	JTextArea textArea = new JTextArea();
	JTextField inputText = new JTextField();
	ServerSocket server;
	int port;
	Socket serverSocket;
	DataInputStream inputStream;
	DataOutputStream outputStream;
	
	public Server(int port) {
		this.port = port;
		try {
			serverSocket = makeServer(port);
			inputStream = connectInputStream();
			outputStream = connectOutputStream();
			showServerView();
			while(true) {
				String msg = receiveMsgFClient();
				textArea.append(msg + "\n");
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void showServerView() {
		textArea.setEditable(false);
		frame.add("Center", textArea);
		frame.add("South", inputText);
		frame.setSize(300, 500);
		frame.setVisible(true);
		eventHandler();
	}
	
	public void eventHandler() {
		inputText.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				try {
					sendMsgTClient("Server " + util.getRegDate() + " : " + inputText.getText());
				} catch (Exception e) {
					e.printStackTrace();
				}
				textArea.append("Server " + util.getRegDate() + " : " + inputText.getText() + "\n");
				inputText.setText("");
			}
		});
	}
	
	public Socket makeServer(int port) throws IOException {
		server = new ServerSocket(port);
		System.out.println("연결 대기중");
		serverSocket = server.accept();
		System.out.println("소켓을 받음");
		return serverSocket;
	}
	
	public DataInputStream connectInputStream() throws IOException {
		inputStream = new DataInputStream(serverSocket.getInputStream());
		return inputStream;
	}
	
	public DataOutputStream connectOutputStream() throws IOException {
		outputStream = new DataOutputStream(serverSocket.getOutputStream());
		return outputStream;
	}
	
	public void sendMsgTClient(String msg) throws IOException {
		outputStream.writeUTF(msg);
	}
	
	public String receiveMsgFClient() throws IOException {
		String msg = inputStream.readUTF();
		return msg;
	}
	
	public static void main(String[] args) {
		Server server = new Server(3000);
	}
}
