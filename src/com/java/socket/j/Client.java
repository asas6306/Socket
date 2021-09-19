package com.java.socket.j;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

import javax.swing.JFrame;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class Client {
	time t = new time();
	
	JFrame frame = new JFrame("Client");
	JTextArea textArea = new JTextArea();
	JTextField inputText = new JTextField();
	Socket clientSocket;
	String host;
	int port;
	DataInputStream inputStream;
	DataOutputStream outputStream;
	
	public Client(String host, int port) {
		this.host = host;
		this.port = port;
		try {
			clientSocket = reqSocket(host, port);
			inputStream = connectInputStream();
			outputStream = connectOutputStream();
			showClientView();
			while(true) {
				String msg = receiveMsgFServer();
				textArea.append(msg + "\n");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void showClientView() {
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
					sendMsgTServer("Client " + t.regDate() + " : " + inputText.getText());
				} catch (Exception e) {
					e.printStackTrace();
				}
				textArea.append("Client " + t.regDate() + " : " + inputText.getText() + "\n");
				inputText.setText("");
			}
		});
	}
	
	public Socket reqSocket(String host, int port) throws UnknownHostException, IOException {
		clientSocket = new Socket(host, port);
		System.out.println("소켓을 서버로 요청");
		return clientSocket;
	}
	
	public DataInputStream connectInputStream() throws IOException {
		inputStream = new DataInputStream(clientSocket.getInputStream());
		System.out.println("clientInputStream");
		return inputStream;
	}
	
	public DataOutputStream connectOutputStream() throws IOException {
		outputStream = new DataOutputStream(clientSocket.getOutputStream());
		System.out.println("clientOutputStream");
		return outputStream;
	}
	
	public void sendMsgTServer(String msg) throws IOException {
		outputStream.writeUTF(msg);
		System.out.println("mTs");
	}
	
	public String receiveMsgFServer() throws IOException {
		String msg = inputStream.readUTF();
		System.out.println("mFs");
		return msg;
	}
	
	public static void main(String[] args) {
		Client client = new Client("localhost", 3000);
	}
}
