using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Data;
using System.Drawing;
using System.Linq;
using System.Text;
using System.Threading;
using System.Threading.Tasks;
using System.Windows.Forms;
using PokemonServer.Class;
using PokemonServer.MySQL;
namespace PokemonServer
{
    public partial class PokemonServer : Form
    {

        private MySQLCon _connection;
        public PokemonServer()
        {
            InitializeComponent();
            Globals.setForm(this);
        }
        private static bool isRunning = false;
        private void PokemonServer_Load(object sender, EventArgs e)
        {
            _connection = new MySQLCon();
            _connection.MySQLConnect();
            Globals.setConnection(_connection);
            isRunning = true;
            Thread mainThread = new Thread(new ThreadStart(MainThread));
            mainThread.Start();
            Server.Start(50, 26951);
        }

        private static void MainThread()
        {
            DateTime _nextLoop = DateTime.Now;

            while (isRunning)
            {
                while (_nextLoop < DateTime.Now)
                {
                    // If the time for the next loop is in the past, aka it's time to execute another tick
                    ThreadManager.UpdateMain();

                    _nextLoop = _nextLoop.AddMilliseconds(1000f / 30f); // Calculate at what point in time the next tick should be executed

                    if (_nextLoop > DateTime.Now)
                    {
                        // If the execution time for the next tick is in the future, aka the server is NOT running behind
                        Thread.Sleep(_nextLoop - DateTime.Now); // Let the thread sleep until it's needed again.
                    }
                }
            }
        }




        /// <summary>
        /// Log Managment
        /// </summary>
        /// <param name="log"></param>
        public void addToLog(string text)
        {
            Invoke(new Action(() => richTextBox1.Text += text + "\n"));
        }

        private void richTextBox1_TextChanged(object sender, EventArgs e)
        {
            // set the current caret position to the end
            richTextBox1.SelectionStart = richTextBox1.Text.Length;
            // scroll it automatically
            richTextBox1.ScrollToCaret();
        }

        private void button1_Click(object sender, EventArgs e)
        {
            LoginManager.tryLogin("Charles", "1234");
        }
    }
}
