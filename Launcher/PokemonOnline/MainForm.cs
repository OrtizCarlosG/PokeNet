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
    }
}
