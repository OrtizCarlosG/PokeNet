using PokemonOnline.Connections;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading;
using System.Threading.Tasks;

namespace PokemonOnline.Class
{
    class ConnectionHandler
    {

        private static bool isConnected = false;
        private static Thread mainThread;


        public void StartConnection()
        {
            Client.ConnectToServer();
            isConnected = true;
            mainThread = new Thread(new ThreadStart(MainThread));
            mainThread.Start();
        }
        private static void MainThread()
        {
            DateTime _nextLoop = DateTime.Now;

            while (isConnected)
            {
                while (_nextLoop < DateTime.Now)
                {
                    // If the time for the next loop is in the past, aka it's time to execute another tick
                    ThreadManager.UpdateMain();

                    _nextLoop = _nextLoop.AddMilliseconds(1000f / 16f); // Calculate at what point in time the next tick should be executed

                    if (_nextLoop > DateTime.Now)
                    {
                        // If the execution time for the next tick is in the future, aka the server is NOT running behind
                        Thread.Sleep(_nextLoop - DateTime.Now); // Let the thread sleep until it's needed again.
                    }
                }
            }
        }
    }
}
