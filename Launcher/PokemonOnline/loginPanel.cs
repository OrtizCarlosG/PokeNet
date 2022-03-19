using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Data;
using System.Drawing;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Windows.Forms;
using PokemonOnline.Connections;
namespace PokemonOnline
{
    public partial class loginPanel : Form
    {

        
        public loginPanel()
        {
            InitializeComponent();
        }

        private void loginPanel_Load(object sender, EventArgs e)
        {
        }

        private void gunaGradientButton1_Click(object sender, EventArgs e)
        {
            ClientSend.LoginPacket(userTxt.Texts, passTxt.Texts);
        }
    }
}
