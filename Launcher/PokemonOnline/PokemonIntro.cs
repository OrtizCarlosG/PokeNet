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
using PokemonOnline.Class;
using PokemonOnline.Connections;

namespace PokemonOnline
{
    public partial class PokemonIntro : Form
    {

        public PokemonIntro()
        {
            InitializeComponent();
            this.Opacity = 0;
        }

        private void PokemonIntro_Load(object sender, EventArgs e)
        {

            Globals.getConnection().StartConnection();
            timer1.Start();
            containerPanel.Hide();
        }

        private void timer1_Tick(object sender, EventArgs e)
        {
            if (this.Opacity < 1)
                this.Opacity += 0.01;
            else
            {
                if (uPanel.Location.Y > 0)
                    uPanel.Location = new Point(uPanel.Location.X, uPanel.Location.Y - 1);
                else if (uPanel.Location.Y <= 0)
                {
                   // containerPanel.Visible = true;
                    openFormInPanel(new loginPanel());
                    timer1.Stop();
                }
                //if (pokeballLogo.Location.Y > 26)
                //    pokeballLogo.Location = new Point(pokeballLogo.Location.X, pokeballLogo.Location.Y - 1);
            }
        }

        public void openFormInPanel(Form form)
        {
            if (containerPanel.Controls.Count > 0)
            {
                containerPanel.Controls.RemoveAt(0);
            }
            form.TopLevel = false;
            form.AutoScroll = true;
            this.containerPanel.Controls.Add(form);
            form.Anchor = ((System.Windows.Forms.AnchorStyles)((((System.Windows.Forms.AnchorStyles.Top | System.Windows.Forms.AnchorStyles.Bottom)
                    | System.Windows.Forms.AnchorStyles.Left)
                    | System.Windows.Forms.AnchorStyles.Right)));
            form.Dock = DockStyle.Fill;
            form.FormBorderStyle = FormBorderStyle.None;
            form.Show();
            gunaTransition1.Show(containerPanel);
        }

    }
}
