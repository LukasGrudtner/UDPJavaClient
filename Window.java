import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

public class Window extends JFrame implements ActionListener {
	
	private JButton sendButton;
	private JTextField textField;

	public Window() {
		
		createWindow();
		createSendButton();
	}
	
	public void actionPerformed(ActionEvent evt) {
		
		try {
			DatagramSocket client = new DatagramSocket(); // make the socket for udp
            DatagramPacket sendPacket,receivePacket; // packet one for send and one for receive
            InetAddress IPAddress = InetAddress.getByName("150.162.63.156"); // se the ip of the arduino ethernet or wifi
            int yourport = 8888;
            byte[] sendData = new byte[1024]; 
            byte[] receiveData = new byte[1024];
            
            if (evt.getSource() == sendButton) {
            	String data = JOptionPane.showInputDialog("Mensagem a ser enviada:");
            	if (data.isEmpty())
            		data = "emptyString";
            	sendData = data.getBytes();
            	sendPacket = new DatagramPacket(sendData, sendData.length, IPAddress, yourport);
            	client.send(sendPacket);
            	
            	System.out.println("Enviado:\n" + data);
            	
            	receivePacket = new DatagramPacket(receiveData, receiveData.length);
            	client.receive(receivePacket);
            	
            	String modifiedSentence = new String(receivePacket.getData());
            	System.out.println("Recebido:\n" + modifiedSentence + "\n");
            	
            }
		} catch (SocketException ex) {
            ex.printStackTrace();
        } catch (UnknownHostException ex) {
            ex.printStackTrace();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
		
	}
	
	private void createSendButton() {
		sendButton = new JButton("Send");
		sendButton.setLocation(100, 100);
		sendButton.setSize(100, 40);
		sendButton.setVisible(true);
		sendButton.addActionListener(this);
		getContentPane().add(sendButton);
	}
	
	private void createWindow() {
		setSize(300, 300);
		setVisible(true);
		setResizable(false);
		setLayout(null);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
	}
	
}
