package collegeapplication.chat;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.Timer;

import collegeapplication.common.Cache;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionListener;
import java.net.BindException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Vector;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

@SuppressWarnings("serial")
public class Server extends JFrame implements Runnable
{

	public final static int port=2003;
	// Refactored: Use explicit Lock and Vector (which is synchronized, but we'll show Manual Locking too for demonstration)
	// Actually, let's keep Vector for existing compatibility with ClientHandler, but manage access via Lock where possible in new logic
	public static Vector<ClientHandler> clientlist=new Vector<ClientHandler>();
	
	// Demonstrating: Locks
	private static final Lock clientLock = new ReentrantLock();
	
	// Demonstrating: Generics (Using our new Cache class)
	// Stores active client IDs or names mapping to handlers
	public static Cache<Integer, ClientHandler> clientCache = new Cache<>();

	// Demonstrating: Multithreading (ThreadPool)
	private ExecutorService pool = Executors.newFixedThreadPool(10); 

	public Server()
	{
		setSize(400,200);
		setTitle("Chat Server");
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.getContentPane().setBackground(Color.white);
		
		JLabel label=new JLabel();
		label.setHorizontalAlignment(JLabel.CENTER);
		label.setBackground(Color.white);
		label.setFont(new Font("Cambria Math", Font.PLAIN, 18));
		label.setText("Active Users  : "+clientlist.size());
		getContentPane().add(label,BorderLayout.CENTER);
		ActionListener countuser=e->
		{
			// Demonstrating: Synchronization (accessing shared resource)
			clientLock.lock();
			try {
				label.setText("Active Users  : "+clientlist.size());
			} finally {
				clientLock.unlock();
			}
		};
		Timer timer=new Timer(1000,countuser);
		timer.start();
		this.setVisible(true);
	}
	
	public static void main(String args[])
	{
		Server server=new Server();
		Thread thread=new Thread(server);
		thread.start();
		
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		try
		{
			@SuppressWarnings("resource")
			ServerSocket server=new ServerSocket(Server.port);
			System.out.println("Server Started");
			while(true)
			{
				Socket socket=server.accept();
				
				ClientHandler client=new ClientHandler(socket);
				
				// Demonstrating: Locks and Synchronization
				clientLock.lock();
				try {
					client.position=clientlist.size();
					clientlist.add(client);
					clientCache.put(client.position, client); // Add to Generic Cache
					System.out.println("Active User  :"+clientlist.size());
				} finally {
					clientLock.unlock();
				}
				
				// Demonstrating: Multithreading (using ExecutorService instead of raw Thread)
				pool.execute(client);
			}
			
		}
		catch(BindException exp)
		{
			System.exit(0);
		}
		catch(Exception exp)
		{
			exp.printStackTrace();
		}
	}
}
