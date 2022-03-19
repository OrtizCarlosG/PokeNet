package org.pokemonium.server;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.util.Scanner;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.JSeparator;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.GnuParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;
import org.ini4j.Ini;
import org.ini4j.Ini.Section;
import org.ini4j.InvalidIniFormatException;
import org.pokemonium.server.backend.entity.Clan;
import org.pokemonium.server.connections.ActiveConnections;
import org.pokemonium.server.network.MySqlManager;

/**
 * Represents a game server.
 * Starting a server requires a parameter to be passed in, i.e. java GameServer -s low -p 500
 * Here are the different settings:
 * -low < 1.86ghz < 256MB Ram < 1mbps Up/Down Connection 75 Players
 * -medium < 2ghz 512MB - 1GB Ram 1mbps Up/Down Connection 200 Players
 * -high > 1.86ghz > 1GB Ram > 1mbps Up/Down Connection > 500 Players
 * 
 * @author shadowkanji
 * @author Nushio
 */
public class GameServer
{
	/* The revision of the game server */
	private static final int SERVER_REVISION = 0;
	public static final int MOVEMENT_THREADS = 12;
	private static boolean m_boolGui = false;
	private static String m_dbServer, m_dbName, m_dbUsername, m_dbPassword, m_serverName;
	private static GameServer m_instance;
	private static int m_maxPlayers = 500; // default 500 players
	private static ServiceManager m_serviceManager;
	private static int m_port = 7002;
	private JFrame m_gui;
	private JLabel m_pAmount, m_pHighest;
	private JButton m_start, m_stop, m_set, m_exit;
	private JPasswordField m_dbP;
	private JTextField m_dbS, m_dbN, m_dbU, m_name;
	private int m_highest;
	
	public JCheckBox m_checkServerBroadcastMessage;
	
	public static double RATE_GOLD = 1.0;
	public static double RATE_EXP_POKE = 150.0;
	public static double RATE_EXP_TRAINER = 1.0;
	public static final int AUTOSAVE_INTERVAL = 15; // Autosave interval in minutes
	public static int REVISION = getServerRevision();
	public static Clan _clanManager;

	/**
	 * Default constructor.
	 * Starts the server after checking if it runs from command line or GUI.
	 * It automatically loads settings if possible.
	 * 
	 * @param autorun True if the server should autostart, otherwise false.
	 */
	public GameServer(boolean autorun)
	{
		if(autorun)
		{
			if(m_boolGui)
				createGui();
			loadSettings();
		}
		else if(m_boolGui)
		{
			loadSettings();
			createGui();
		}
		else
		{
			ConsoleReader r = new ConsoleReader();
			System.out.println("Load Settings? Y/N");
			String answer = r.readToken();
			if(answer.contains("y") || answer.contains("Y"))
				loadSettings();
			else
				getConsoleSettings();
		}
		// removed to not start automatically
		//start();
	}

	/**
	 * Updates the player count information.
	 */
	public void updatePlayerCount()
	{
		int amount = ActiveConnections.getActiveConnections();
		if(m_boolGui)
		{
			m_pAmount.setText(amount + " players online");
			if(amount > m_highest)
			{
				m_highest = amount;
				m_pHighest.setText("Highest: " + amount);
			}
		}
		else
		{
			System.out.println(amount + " players online");
			if(amount > m_highest)
			{
				m_highest = amount;
				System.out.println("Highest: " + amount);
			}
		}
	}

	/**
	 * Returns the database host.
	 * 
	 * @return The hostname or IP address of the database to connect to.
	 */
	public static String getDatabaseHost()
	{
		return m_dbServer;
	}

	/**
	 * Returns the selected database.
	 * 
	 * @return The name of the database to connect to.
	 */
	public static String getDatabaseName()
	{
		return m_dbName;
	}

	/**
	 * Returns the database password.
	 * 
	 * @return The password for the database.
	 */
	public static String getDatabasePassword()
	{
		return m_dbPassword;
	}

	/**
	 * Returns the database username.
	 * 
	 * @return The username for the database.
	 */
	public static String getDatabaseUsername()
	{
		return m_dbUsername;
	}

	/**
	 * Returns the instance of game server.
	 * 
	 * @return An instance of the GameServer.
	 */
	public static GameServer getInstance()
	{
		return m_instance;
	}

	/**
	 * Initializes the gameserver object.
	 * 
	 * @param autorun True if the server should autostart, otherwise false.
	 */
	public static void initGameServer(boolean autorun)
	{
		m_instance = new GameServer(autorun);
	}

	/**
	 * Returns the amount of players this server will allow.
	 * 
	 * @return The maximum amount of players simultaneously allowed.
	 */
	public static int getMaxPlayers()
	{
		return m_maxPlayers;
	}

	/**
	 * Returns the connection port for this server.
	 * 
	 * @return The port clients should connect to.
	 */
	public static int getPort()
	{
		return m_port;
	}

	/**
	 * Returns the name of this server.
	 * 
	 * @return The server's name.
	 */
	public static String getServerName()
	{
		return m_serverName;
	}

	/**
	 * Returns the service manager of the server.
	 * 
	 * @return
	 */
	public static ServiceManager getServiceManager()
	{
		return m_serviceManager;
	}

	/**
	 * The main entry point for the application.
	 * Reads the commandline arguments and starts the server.
	 * 
	 * @param args Optional commandline arguments.
	 */
	public static void main(String[] args)
	{
		/* Pipe errors to a file. */
		try
		{
			PrintStream errorPrinter = new PrintStream(new File("./errors.txt"));
			System.setErr(errorPrinter);
		}
		catch(IOException | SecurityException e)
		{
			e.printStackTrace();
		}
		/* Server settings */
		Options options = new Options();
		options.addOption("p", "players", true, "Sets the max number of players.");
		options.addOption("port", "port", true, "Sets the serverport.");
		options.addOption("ng", "nogui", false, "Starts server in headless mode.");
		options.addOption("ar", "autorun", false, "Runs without asking a single question.");
		options.addOption("h", "help", false, "Shows this menu.");
		options.addOption("rates", "serverrates", true, "Gives the file to be used for server rates config");
		if(args.length > 0)
		{
			CommandLineParser parser = new GnuParser();
			try
			{
				/* Parse the command line arguments. */
				CommandLine line = parser.parse(options, args);
				if(line.hasOption("players"))
				{
					m_maxPlayers = Integer.parseInt(line.getOptionValue("players"));
					if(m_maxPlayers < 1)
						m_maxPlayers = 500;
				}
				if(line.hasOption("port"))
					m_port = Integer.parseInt(line.getOptionValue("port"));
				if(line.hasOption("help"))
				{
					HelpFormatter formatter = new HelpFormatter();
					System.err.println("Server requires a settings parameter");
					formatter.printHelp("java GameServer [param] <args>", options);
				}
				/* Create the server gui */
				if(!line.hasOption("nogui"))
					m_boolGui = true;
				/* Load the server rates file */
				if(line.hasOption("rates"))
				{
					String rates = line.getOptionValue("rates");
					Ini ratesIni = new Ini(new FileInputStream(rates));
					Section s = ratesIni.get("RATES");
					RATE_GOLD = Double.parseDouble(s.get("GOLD"));
					RATE_EXP_POKE = Double.parseDouble(s.get("EXP_POKE"));
					RATE_EXP_TRAINER = Double.parseDouble(s.get("EXP_TRAINER"));
				}
				/* No else since it's set to default 'false'. */
				boolean autorun = line.hasOption("autorun");
				GameServer.initGameServer(autorun);
			}
			catch(ParseException pe)
			{
				/* Oops, something went wrong, automatically generate the help statement. */
				System.err.println("Parsing failed.  Reason: " + pe.getMessage());
				HelpFormatter formatter = new HelpFormatter();
				formatter.printHelp("java GameServer [param] <args>", options);
			}
			catch(InvalidIniFormatException iife)
			{
				iife.printStackTrace();
				System.err.println("Error in server rates format, using default 1.0");
			}
			catch(IOException ioe)
			{
				ioe.printStackTrace();
			}
		}
		else
		{
			/* Automatically generate the help statement. */
			HelpFormatter formatter = new HelpFormatter();
			System.err.println("Server requires a settings parameter");
			formatter.printHelp("java GameServer [param] <args>", options);
		}
	}

	/**
	 * Gets the SVN revision for the server.
	 * 
	 * @return Returns the number in the rev.txt file, otherwise 0.
	 */
	private static int getServerRevision()
	{
		int rev = SERVER_REVISION;
		String path = System.getProperty("res.path");
		if(path == null || path.equalsIgnoreCase("NULL"))
			path = "./";
		File file = new File(path + "/res/rev.txt");
		try(Scanner sc = new Scanner(file))
		{
			rev = Integer.parseInt(sc.nextLine());
		}
		catch(FileNotFoundException fnfe)
		{
			fnfe.printStackTrace();
		}
		return rev;
	}

	/**
	 * Starts the GameServer.
	 * This function fills the GUI fields (if any), checks the database connection and starts the servicemanager.
	 * Once the servicemanager is started, the server will start booting up.
	 **/
	public void start()
	{
		if(m_boolGui)
		{
			m_dbServer = m_dbS.getText();
			m_dbName = m_dbN.getText();
			m_dbUsername = m_dbU.getText();
			m_dbPassword = new String(m_dbP.getPassword());
			m_serverName = m_name.getText();

			m_checkServerBroadcastMessage.setEnabled(true);
			m_start.setEnabled(false);
			m_stop.setEnabled(true);
		}
		MySqlManager.getInstance();
		m_serviceManager = new ServiceManager();
		m_serviceManager.start();
		_clanManager = new Clan();
	}

	/**
	 * Stops the GameServer.
	 * This function stops the servicemanager and waits for the processes to terminate.
	 * Finally, the database connection is closed and the application shutdown.
	 **/
	public void stop()
	{
		m_serviceManager.stop();
		if(m_boolGui)
		{
			m_checkServerBroadcastMessage.setEnabled(true);
			m_start.setEnabled(true);
			m_stop.setEnabled(false);
		}
		else
		{
			try
			{
				Thread.sleep(10 * 1000);
				System.out.println("Exiting server...");
				MySqlManager.getInstance().close();
				System.exit(0);
			}
			catch(InterruptedException ie)
			{
				ie.printStackTrace();
			}
		}
	}

	/**
	 * Creates the gui-version of the server.
	 */
	private void createGui()
	{
		m_gui = new JFrame();
		m_gui.setTitle("Pokemonium Server");
		m_gui.setSize(400, 350);
		m_gui.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		m_gui.getContentPane().setLayout(null);
		m_gui.setResizable(false);
		m_gui.setLocation(32, 32);
		/* Set line on middle */
		JSeparator m_lSeparator = new JSeparator(JSeparator.VERTICAL);
		m_lSeparator.setSize(10, 350);
		m_lSeparator.setLocation(150, 0);
		m_gui.getContentPane().add(m_lSeparator);
		/* Set up the buttons */
		m_pAmount = new JLabel("0 players online");
		m_pAmount.setSize(160, 16);
		m_pAmount.setLocation(4, 4);
		m_pAmount.setVisible(true);
		m_gui.getContentPane().add(m_pAmount);

		m_pHighest = new JLabel("[No record]");
		m_pHighest.setSize(160, 16);
		m_pHighest.setLocation(4, 24);
		m_pHighest.setVisible(true);
		m_gui.getContentPane().add(m_pHighest);
		
		m_checkServerBroadcastMessage = new JCheckBox("Focus System Messages on clients");
		m_checkServerBroadcastMessage.setSize(230,  24);
		m_checkServerBroadcastMessage.setLocation(160, 48);
		m_gui.getContentPane().add(m_checkServerBroadcastMessage);
		
		m_start = new JButton("Start Server");
		m_start.setSize(128, 24);
		m_start.setLocation(4, 48);
		m_start.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent arg0)
			{
				start();
			}
		});
		m_gui.getContentPane().add(m_start);
		m_stop = new JButton("Stop Server");
		m_stop.setSize(128, 24);
		m_stop.setLocation(4, 74);
		m_stop.setEnabled(false);
		m_stop.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent arg0)
			{
				stop();
			}
		});
		m_gui.getContentPane().add(m_stop);
		m_set = new JButton("Save Settings");
		m_set.setSize(128, 24);
		m_set.setLocation(4, 100);
		m_set.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent arg0)
			{
				saveSettings();
			}
		});
		m_gui.getContentPane().add(m_set);
		m_exit = new JButton("Quit");
		m_exit.setSize(128, 24);
		m_exit.setLocation(4, 290);
		m_exit.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent arg0)
			{
				exit();
			}
		});
		m_gui.getContentPane().add(m_exit);
		/* Settings text boxes */
		m_dbS = new JTextField();
		m_dbS.setSize(128, 24);
		m_dbS.setText("127.0.0.1");
		m_dbS.setLocation(4, 128);
		m_gui.getContentPane().add(m_dbS);
		
		m_dbN = new JTextField();
		m_dbN.setSize(128, 24);
		m_dbN.setText("pokemononline");
		m_dbN.setLocation(4, 160);
		m_gui.getContentPane().add(m_dbN);

		m_dbU = new JTextField();
		m_dbU.setSize(128, 24);
		m_dbU.setText("sa");
		m_dbU.setLocation(4, 192);
		m_gui.getContentPane().add(m_dbU);

		m_dbP = new JPasswordField();
		m_dbP.setSize(128, 24);
		m_dbP.setText("");
		m_dbP.setLocation(4, 224);
		m_gui.getContentPane().add(m_dbP);

		m_name = new JTextField();
		m_name.setSize(128, 24);
		m_name.setText("Your Server Name");
		m_name.setLocation(4, 260);
		m_gui.getContentPane().add(m_name);
		/* Load pre-existing settings if any */
		File f = new File("res/settings.txt");
		if(f.exists())
		{
			try(Scanner s = new Scanner(f))
			{
				m_dbS.setText(s.nextLine());
				m_dbN.setText(s.nextLine());
				m_dbU.setText(s.nextLine());
				m_dbP.setText(s.nextLine());
				m_name.setText(s.nextLine());
			}
			catch(IOException ioe)
			{
				ioe.printStackTrace();
			}
		}
		m_gui.setVisible(true);
	}

	/** Exits the game server application. */
	private void exit()
	{
		if(m_boolGui && m_stop.isEnabled())
			JOptionPane.showMessageDialog(null, "You must stop the server before exiting.");
		else
		{
			MySqlManager.getInstance().close();
			System.exit(0);
		}
	}

	/**
	 * Asks for Database User/Pass, then asks to save
	 * NOTE: It doesnt save the database password.
	 **/
	private void getConsoleSettings()
	{
		ConsoleReader r = new ConsoleReader();
		System.out.println("Please enter the required information.");
		System.out.println("Database Server: ");
		m_dbServer = r.readToken();
		System.out.println("Database Name:");
		m_dbName = r.readToken();
		System.out.println("Database Username:");
		m_dbUsername = r.readToken();
		System.out.println("Database Password:");
		m_dbPassword = r.readToken();
		System.out.println("This server's IP or hostname:");
		m_serverName = r.readToken();
		System.out.println("Save info? (Y/N)");
		String answer = r.readToken();
		if(answer.contains("y") || answer.contains("Y"))
			saveSettings();
		System.out.println();
		System.err.println("WARNING: When using -nogui, the server should only be shut down using a master client");
	}

	/**
	 * Load pre-existing settings if any are available.
	 * NOTE: It loads the database password if available.
	 **/
	private void loadSettings()
	{
		File settings = new File("res/settings.txt");
		if(settings.exists())
		{
			try(Scanner s = new Scanner(settings))
			{
				m_dbServer = s.nextLine();
				m_dbName = s.nextLine();
				m_dbUsername = s.nextLine();
				m_dbPassword = s.nextLine();
				m_serverName = s.nextLine();
			}
			catch(IOException ioe)
			{
				ioe.printStackTrace();
			}
		}
	}

	/**
	 * Writes server settings to a file.
	 * NOTE: It never stores the database password.
	 **/
	private void saveSettings()
	{
		/* Store globally */
		if(m_boolGui)
		{
			m_dbServer = m_dbS.getText();
			m_dbName = m_dbN.getText();
			m_dbUsername = m_dbU.getText();
			m_dbPassword = new String(m_dbP.getPassword());
			m_serverName = m_name.getText();
		}
		/* Write settings to file */
		File settings = new File("res/settings.txt");
		if(settings.exists())
			settings.delete();
		try(PrintWriter settingsWriter = new PrintWriter(settings))
		{
			settingsWriter.println(m_dbServer);
			settingsWriter.println(m_dbName);
			settingsWriter.println(m_dbUsername);
			settingsWriter.println(m_serverName);
			settingsWriter.println(" ");
		}
		catch(IOException ioe)
		{
			ioe.printStackTrace();
		}
	}
}
