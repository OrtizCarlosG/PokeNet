using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Data;
using System.Diagnostics;
using System.Drawing;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Windows.Forms;
namespace PokemonOnline
{
    public partial class MainForm : Form
    {
        public MainForm()
        {
            InitializeComponent();
        }

        private void button1_Click(object sender, EventArgs e)
        {
            Process myprocess = new Process();
            myprocess.StartInfo.UseShellExecute = false;
            myprocess.StartInfo.CreateNoWindow = true;
            myprocess.StartInfo.FileName = "java";
            myprocess.StartInfo.Arguments = "-jar -Djava.library.path=lib/native PokemonClient.jar";
            myprocess.Start();
            //Merge("D:\\Pokemon Project\\Other pokemons\\POKEMONGREEN SERVER + CLIENT\\Client\\PokemonClient.jar");

        }
        private void Merge(string strPath)
        {
            var processInfo = new ProcessStartInfo("C:\\Program Files\\Java\\jdk1.6.0_24\\binjava.exe", "-jar app.jar")
            {
                CreateNoWindow = true,
                UseShellExecute = false
            };

            processInfo.WorkingDirectory = strPath; // this is where your jar file is.
            Process proc;

            if ((proc = Process.Start(processInfo)) == null)
            {
                throw new InvalidOperationException("??");
            }

            proc.WaitForExit();
            int exitCode = proc.ExitCode;
            proc.Close();
        }

        private void MainForm_Load(object sender, EventArgs e)
        {
            
        }
    }
}
