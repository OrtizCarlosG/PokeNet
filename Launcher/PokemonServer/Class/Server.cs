using System;
using System.Collections.Generic;
using System.Text;
using System.Net;
using System.Net.Sockets;

namespace PokemonServer.Class
{
    class Server
    {
        public static int MaxPlayers { get; private set; }
        public static int Port { get; private set; }
        public static Dictionary<int, Client> clients = new Dictionary<int, Client>();
        public delegate void PacketHandler(int _fromClient, Packet _packet);
        public static Dictionary<int, PacketHandler> packetHandlers;

        private static TcpListener tcpListener;

        /// <summary>Starts the server.</summary>
        /// <param name="_maxPlayers">The maximum players that can be connected simultaneously.</param>
        /// <param name="_port">The port to start the server on.</param>
        public static void Start(int _maxPlayers, int _port)
        {
            MaxPlayers = _maxPlayers;
            Port = _port;

            Globals.getForm().addToLog("Starting server...");
            InitializeServerData();

            tcpListener = new TcpListener(IPAddress.Any, Port);
            tcpListener.Start();
            tcpListener.BeginAcceptTcpClient(TCPConnectCallback, null);

            Globals.getForm().addToLog($"Server started on port {Port}.");
        }
        public static string RemovePort(string endPoint)
        {
            var splitList = endPoint.Split(':');
            if (splitList.Length > 2)
            {
                endPoint = IPAddress.Parse(endPoint).ToString();
            }
            else if (splitList.Length == 2)
            {
                endPoint = splitList[0];
            }

            return endPoint;
        }
        /// <summary>Handles new TCP connections.</summary>
        private static void TCPConnectCallback(IAsyncResult _result)
        {
            TcpClient _client = tcpListener.EndAcceptTcpClient(_result);
            tcpListener.BeginAcceptTcpClient(TCPConnectCallback, null);
            Globals.getForm().addToLog($"Incoming connection from {_client.Client.RemoteEndPoint}...");
            //Console.WriteLine($"Incoming connection from {_client.Client.RemoteEndPoint}...");

            for (int i = 1; i <= MaxPlayers; i++)
            {
                if (clients[i].tcp.socket == null)
                {
                    clients[i].tcp.Connect(_client);
                    return;
                }
            }

            Globals.getForm().addToLog($"{_client.Client.RemoteEndPoint} failed to connect: Server full!");
        }
   
        /// <summary>Initializes all necessary server data.</summary>
        private static void InitializeServerData()
        {
            for (int i = 1; i <= MaxPlayers; i++)
            {
                clients.Add(i, new Client(i));
            }

            packetHandlers = new Dictionary<int, PacketHandler>()
            {
                { (int)ClientPackets.login, ServerHandle.LoginRecived },
                { (int)ClientPackets.register, ServerHandle.RegisterRecived },
                { (int)ClientPackets.startAcc, ServerHandle.AccountStartRecived },
                { (int)ClientPackets.profileImage, ServerHandle.returnProfileImage },
                { (int)ClientPackets.changeImage, ServerHandle.ChangeProfileImage }
            };
            Console.WriteLine("Initialized packets.");
        }
    }
}