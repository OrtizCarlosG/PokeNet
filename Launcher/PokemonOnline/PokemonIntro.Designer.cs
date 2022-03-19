
namespace PokemonOnline
{
    partial class PokemonIntro
    {
        /// <summary>
        /// Required designer variable.
        /// </summary>
        private System.ComponentModel.IContainer components = null;

        /// <summary>
        /// Clean up any resources being used.
        /// </summary>
        /// <param name="disposing">true if managed resources should be disposed; otherwise, false.</param>
        protected override void Dispose(bool disposing)
        {
            if (disposing && (components != null))
            {
                components.Dispose();
            }
            base.Dispose(disposing);
        }

        #region Windows Form Designer generated code

        /// <summary>
        /// Required method for Designer support - do not modify
        /// the contents of this method with the code editor.
        /// </summary>
        private void InitializeComponent()
        {
            this.components = new System.ComponentModel.Container();
            Guna.UI.Animation.Animation animation1 = new Guna.UI.Animation.Animation();
            System.ComponentModel.ComponentResourceManager resources = new System.ComponentModel.ComponentResourceManager(typeof(PokemonIntro));
            this.gunaGradient2Panel1 = new Guna.UI.WinForms.GunaGradient2Panel();
            this.proyectoZLabel1 = new ProyectoZ.UI.ProyectoZLabel();
            this.closeBtn = new ProyectoZ.UI.ProyectoZLabel();
            this.gunaGradientPanel1 = new Guna.UI.WinForms.GunaGradientPanel();
            this.label6 = new System.Windows.Forms.Label();
            this.totalUsers = new System.Windows.Forms.Label();
            this.label2 = new System.Windows.Forms.Label();
            this.onlineUsers = new System.Windows.Forms.Label();
            this.gunaVSeparator1 = new Guna.UI.WinForms.GunaVSeparator();
            this.gunaGradientPanel2 = new Guna.UI.WinForms.GunaGradientPanel();
            this.containerPanel = new System.Windows.Forms.Panel();
            this.uPanel = new Guna.UI.WinForms.GunaGradient2Panel();
            this.pokemonLogo = new System.Windows.Forms.PictureBox();
            this.pokeballLogo = new System.Windows.Forms.PictureBox();
            this.timer1 = new System.Windows.Forms.Timer(this.components);
            this.gunaTransition1 = new Guna.UI.WinForms.GunaTransition(this.components);
            this.gunaGradient2Panel1.SuspendLayout();
            this.gunaGradientPanel1.SuspendLayout();
            this.gunaGradientPanel2.SuspendLayout();
            this.uPanel.SuspendLayout();
            ((System.ComponentModel.ISupportInitialize)(this.pokemonLogo)).BeginInit();
            ((System.ComponentModel.ISupportInitialize)(this.pokeballLogo)).BeginInit();
            this.SuspendLayout();
            // 
            // gunaGradient2Panel1
            // 
            this.gunaGradient2Panel1.BackColor = System.Drawing.Color.Crimson;
            this.gunaGradient2Panel1.Controls.Add(this.proyectoZLabel1);
            this.gunaGradient2Panel1.Controls.Add(this.closeBtn);
            this.gunaGradient2Panel1.Controls.Add(this.gunaGradientPanel1);
            this.gunaGradient2Panel1.Controls.Add(this.gunaGradientPanel2);
            this.gunaTransition1.SetDecoration(this.gunaGradient2Panel1, Guna.UI.Animation.DecorationType.None);
            this.gunaGradient2Panel1.Dock = System.Windows.Forms.DockStyle.Fill;
            this.gunaGradient2Panel1.GradientColor1 = System.Drawing.Color.Crimson;
            this.gunaGradient2Panel1.GradientColor2 = System.Drawing.Color.Firebrick;
            this.gunaGradient2Panel1.Location = new System.Drawing.Point(0, 0);
            this.gunaGradient2Panel1.Name = "gunaGradient2Panel1";
            this.gunaGradient2Panel1.Size = new System.Drawing.Size(327, 489);
            this.gunaGradient2Panel1.TabIndex = 0;
            // 
            // proyectoZLabel1
            // 
            this.proyectoZLabel1.AutoSize = true;
            this.proyectoZLabel1.BackColor = System.Drawing.Color.Transparent;
            this.gunaTransition1.SetDecoration(this.proyectoZLabel1, Guna.UI.Animation.DecorationType.None);
            this.proyectoZLabel1.DrawingDirection = ProyectoZ.UI.ProyectoZLabel.Angles.LeftToRight;
            this.proyectoZLabel1.EnableGradient = true;
            this.proyectoZLabel1.enableOnHoverColors = true;
            this.proyectoZLabel1.EnableShadow = false;
            this.proyectoZLabel1.EndColor = System.Drawing.Color.White;
            this.proyectoZLabel1.EndColorAlpha = 255;
            this.proyectoZLabel1.Font = new System.Drawing.Font("Microsoft Sans Serif", 14.25F, System.Drawing.FontStyle.Bold, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.proyectoZLabel1.GradientDirection = System.Drawing.Drawing2D.LinearGradientMode.Horizontal;
            this.proyectoZLabel1.Location = new System.Drawing.Point(272, 0);
            this.proyectoZLabel1.Name = "proyectoZLabel1";
            this.proyectoZLabel1.OnEnableEffect = false;
            this.proyectoZLabel1.OnEnableEffectOnText = false;
            this.proyectoZLabel1.OnHoverColor = System.Drawing.Color.Maroon;
            this.proyectoZLabel1.OnHoverColor2 = System.Drawing.Color.Maroon;
            this.proyectoZLabel1.ShadowColor = System.Drawing.Color.Red;
            this.proyectoZLabel1.ShadowOffset = 1;
            this.proyectoZLabel1.Size = new System.Drawing.Size(17, 24);
            this.proyectoZLabel1.StartColor = System.Drawing.Color.White;
            this.proyectoZLabel1.StartColorAlpha = 255;
            this.proyectoZLabel1.TabIndex = 6;
            this.proyectoZLabel1.Text = "-";
            // 
            // closeBtn
            // 
            this.closeBtn.AutoSize = true;
            this.closeBtn.BackColor = System.Drawing.Color.Transparent;
            this.gunaTransition1.SetDecoration(this.closeBtn, Guna.UI.Animation.DecorationType.None);
            this.closeBtn.DrawingDirection = ProyectoZ.UI.ProyectoZLabel.Angles.LeftToRight;
            this.closeBtn.EnableGradient = true;
            this.closeBtn.enableOnHoverColors = true;
            this.closeBtn.EnableShadow = false;
            this.closeBtn.EndColor = System.Drawing.Color.White;
            this.closeBtn.EndColorAlpha = 255;
            this.closeBtn.Font = new System.Drawing.Font("Microsoft Sans Serif", 14.25F, System.Drawing.FontStyle.Bold, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.closeBtn.GradientDirection = System.Drawing.Drawing2D.LinearGradientMode.Horizontal;
            this.closeBtn.Location = new System.Drawing.Point(302, 0);
            this.closeBtn.Name = "closeBtn";
            this.closeBtn.OnEnableEffect = false;
            this.closeBtn.OnEnableEffectOnText = false;
            this.closeBtn.OnHoverColor = System.Drawing.Color.Maroon;
            this.closeBtn.OnHoverColor2 = System.Drawing.Color.Maroon;
            this.closeBtn.ShadowColor = System.Drawing.Color.Red;
            this.closeBtn.ShadowOffset = 1;
            this.closeBtn.Size = new System.Drawing.Size(25, 24);
            this.closeBtn.StartColor = System.Drawing.Color.White;
            this.closeBtn.StartColorAlpha = 255;
            this.closeBtn.TabIndex = 5;
            this.closeBtn.Text = "X";
            // 
            // gunaGradientPanel1
            // 
            this.gunaGradientPanel1.BackgroundImage = ((System.Drawing.Image)(resources.GetObject("gunaGradientPanel1.BackgroundImage")));
            this.gunaGradientPanel1.BackgroundImageLayout = System.Windows.Forms.ImageLayout.Stretch;
            this.gunaGradientPanel1.Controls.Add(this.label6);
            this.gunaGradientPanel1.Controls.Add(this.totalUsers);
            this.gunaGradientPanel1.Controls.Add(this.label2);
            this.gunaGradientPanel1.Controls.Add(this.onlineUsers);
            this.gunaGradientPanel1.Controls.Add(this.gunaVSeparator1);
            this.gunaTransition1.SetDecoration(this.gunaGradientPanel1, Guna.UI.Animation.DecorationType.None);
            this.gunaGradientPanel1.Dock = System.Windows.Forms.DockStyle.Bottom;
            this.gunaGradientPanel1.GradientColor1 = System.Drawing.Color.FromArgb(((int)(((byte)(216)))), ((int)(((byte)(22)))), ((int)(((byte)(58)))));
            this.gunaGradientPanel1.GradientColor2 = System.Drawing.Color.FromArgb(((int)(((byte)(22)))), ((int)(((byte)(22)))), ((int)(((byte)(22)))));
            this.gunaGradientPanel1.GradientColor3 = System.Drawing.Color.FromArgb(((int)(((byte)(197)))), ((int)(((byte)(28)))), ((int)(((byte)(46)))));
            this.gunaGradientPanel1.GradientColor4 = System.Drawing.Color.FromArgb(((int)(((byte)(216)))), ((int)(((byte)(22)))), ((int)(((byte)(58)))));
            this.gunaGradientPanel1.Location = new System.Drawing.Point(0, 404);
            this.gunaGradientPanel1.Name = "gunaGradientPanel1";
            this.gunaGradientPanel1.Size = new System.Drawing.Size(327, 85);
            this.gunaGradientPanel1.TabIndex = 4;
            this.gunaGradientPanel1.Text = "gunaGradientPanel1";
            // 
            // label6
            // 
            this.label6.BackColor = System.Drawing.Color.Transparent;
            this.gunaTransition1.SetDecoration(this.label6, Guna.UI.Animation.DecorationType.None);
            this.label6.ForeColor = System.Drawing.Color.White;
            this.label6.Location = new System.Drawing.Point(178, 55);
            this.label6.Name = "label6";
            this.label6.Size = new System.Drawing.Size(146, 30);
            this.label6.TabIndex = 11;
            this.label6.Text = "Usuarios registrados";
            this.label6.TextAlign = System.Drawing.ContentAlignment.MiddleCenter;
            // 
            // totalUsers
            // 
            this.totalUsers.BackColor = System.Drawing.Color.Transparent;
            this.gunaTransition1.SetDecoration(this.totalUsers, Guna.UI.Animation.DecorationType.None);
            this.totalUsers.Font = new System.Drawing.Font("Microsoft Sans Serif", 26.25F, System.Drawing.FontStyle.Bold, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.totalUsers.ForeColor = System.Drawing.Color.White;
            this.totalUsers.Location = new System.Drawing.Point(171, 11);
            this.totalUsers.Name = "totalUsers";
            this.totalUsers.Size = new System.Drawing.Size(153, 52);
            this.totalUsers.TabIndex = 10;
            this.totalUsers.Text = "1000";
            this.totalUsers.TextAlign = System.Drawing.ContentAlignment.MiddleCenter;
            // 
            // label2
            // 
            this.label2.BackColor = System.Drawing.Color.Transparent;
            this.gunaTransition1.SetDecoration(this.label2, Guna.UI.Animation.DecorationType.None);
            this.label2.ForeColor = System.Drawing.Color.Chartreuse;
            this.label2.Location = new System.Drawing.Point(3, 55);
            this.label2.Name = "label2";
            this.label2.Size = new System.Drawing.Size(146, 30);
            this.label2.TabIndex = 9;
            this.label2.Text = "Usuarios en linea";
            this.label2.TextAlign = System.Drawing.ContentAlignment.MiddleCenter;
            // 
            // onlineUsers
            // 
            this.onlineUsers.BackColor = System.Drawing.Color.Transparent;
            this.gunaTransition1.SetDecoration(this.onlineUsers, Guna.UI.Animation.DecorationType.None);
            this.onlineUsers.Font = new System.Drawing.Font("Microsoft Sans Serif", 26.25F, System.Drawing.FontStyle.Bold);
            this.onlineUsers.ForeColor = System.Drawing.Color.Chartreuse;
            this.onlineUsers.Location = new System.Drawing.Point(3, 11);
            this.onlineUsers.Name = "onlineUsers";
            this.onlineUsers.Size = new System.Drawing.Size(146, 52);
            this.onlineUsers.TabIndex = 8;
            this.onlineUsers.Text = "100";
            this.onlineUsers.TextAlign = System.Drawing.ContentAlignment.MiddleCenter;
            // 
            // gunaVSeparator1
            // 
            this.gunaVSeparator1.BackColor = System.Drawing.Color.Transparent;
            this.gunaVSeparator1.BackgroundImageLayout = System.Windows.Forms.ImageLayout.None;
            this.gunaTransition1.SetDecoration(this.gunaVSeparator1, Guna.UI.Animation.DecorationType.None);
            this.gunaVSeparator1.LineColor = System.Drawing.Color.White;
            this.gunaVSeparator1.Location = new System.Drawing.Point(155, 14);
            this.gunaVSeparator1.Name = "gunaVSeparator1";
            this.gunaVSeparator1.Size = new System.Drawing.Size(10, 58);
            this.gunaVSeparator1.TabIndex = 0;
            this.gunaVSeparator1.Thickness = 2;
            // 
            // gunaGradientPanel2
            // 
            this.gunaGradientPanel2.BackgroundImage = ((System.Drawing.Image)(resources.GetObject("gunaGradientPanel2.BackgroundImage")));
            this.gunaGradientPanel2.BackgroundImageLayout = System.Windows.Forms.ImageLayout.Stretch;
            this.gunaGradientPanel2.Controls.Add(this.containerPanel);
            this.gunaGradientPanel2.Controls.Add(this.uPanel);
            this.gunaTransition1.SetDecoration(this.gunaGradientPanel2, Guna.UI.Animation.DecorationType.None);
            this.gunaGradientPanel2.Dock = System.Windows.Forms.DockStyle.Fill;
            this.gunaGradientPanel2.GradientColor1 = System.Drawing.Color.Crimson;
            this.gunaGradientPanel2.GradientColor2 = System.Drawing.Color.Crimson;
            this.gunaGradientPanel2.GradientColor3 = System.Drawing.Color.Firebrick;
            this.gunaGradientPanel2.GradientColor4 = System.Drawing.Color.Firebrick;
            this.gunaGradientPanel2.Location = new System.Drawing.Point(0, 0);
            this.gunaGradientPanel2.Name = "gunaGradientPanel2";
            this.gunaGradientPanel2.Size = new System.Drawing.Size(327, 489);
            this.gunaGradientPanel2.TabIndex = 8;
            this.gunaGradientPanel2.Text = "gunaGradientPanel2";
            // 
            // containerPanel
            // 
            this.containerPanel.BackColor = System.Drawing.Color.Transparent;
            this.gunaTransition1.SetDecoration(this.containerPanel, Guna.UI.Animation.DecorationType.None);
            this.containerPanel.Location = new System.Drawing.Point(0, 106);
            this.containerPanel.Name = "containerPanel";
            this.containerPanel.Size = new System.Drawing.Size(327, 298);
            this.containerPanel.TabIndex = 7;
            // 
            // uPanel
            // 
            this.uPanel.BackColor = System.Drawing.Color.Transparent;
            this.uPanel.Controls.Add(this.pokemonLogo);
            this.uPanel.Controls.Add(this.pokeballLogo);
            this.gunaTransition1.SetDecoration(this.uPanel, Guna.UI.Animation.DecorationType.None);
            this.uPanel.GradientColor1 = System.Drawing.Color.Transparent;
            this.uPanel.GradientColor2 = System.Drawing.Color.Transparent;
            this.uPanel.Location = new System.Drawing.Point(0, 157);
            this.uPanel.Name = "uPanel";
            this.uPanel.Size = new System.Drawing.Size(327, 111);
            this.uPanel.TabIndex = 3;
            // 
            // pokemonLogo
            // 
            this.gunaTransition1.SetDecoration(this.pokemonLogo, Guna.UI.Animation.DecorationType.None);
            this.pokemonLogo.Image = ((System.Drawing.Image)(resources.GetObject("pokemonLogo.Image")));
            this.pokemonLogo.Location = new System.Drawing.Point(75, 3);
            this.pokemonLogo.Name = "pokemonLogo";
            this.pokemonLogo.Size = new System.Drawing.Size(222, 104);
            this.pokemonLogo.SizeMode = System.Windows.Forms.PictureBoxSizeMode.StretchImage;
            this.pokemonLogo.TabIndex = 0;
            this.pokemonLogo.TabStop = false;
            // 
            // pokeballLogo
            // 
            this.gunaTransition1.SetDecoration(this.pokeballLogo, Guna.UI.Animation.DecorationType.None);
            this.pokeballLogo.Image = global::PokemonOnline.Properties.Resources.Pokeball_style_1;
            this.pokeballLogo.Location = new System.Drawing.Point(13, 26);
            this.pokeballLogo.Name = "pokeballLogo";
            this.pokeballLogo.Size = new System.Drawing.Size(56, 56);
            this.pokeballLogo.SizeMode = System.Windows.Forms.PictureBoxSizeMode.StretchImage;
            this.pokeballLogo.TabIndex = 1;
            this.pokeballLogo.TabStop = false;
            // 
            // timer1
            // 
            this.timer1.Enabled = true;
            this.timer1.Interval = 1;
            this.timer1.Tick += new System.EventHandler(this.timer1_Tick);
            // 
            // gunaTransition1
            // 
            this.gunaTransition1.AnimationType = Guna.UI.Animation.AnimationType.HorizSlide;
            this.gunaTransition1.Cursor = null;
            animation1.AnimateOnlyDifferences = true;
            animation1.BlindCoeff = ((System.Drawing.PointF)(resources.GetObject("animation1.BlindCoeff")));
            animation1.LeafCoeff = 0F;
            animation1.MaxTime = 1F;
            animation1.MinTime = 0F;
            animation1.MosaicCoeff = ((System.Drawing.PointF)(resources.GetObject("animation1.MosaicCoeff")));
            animation1.MosaicShift = ((System.Drawing.PointF)(resources.GetObject("animation1.MosaicShift")));
            animation1.MosaicSize = 0;
            animation1.Padding = new System.Windows.Forms.Padding(0);
            animation1.RotateCoeff = 0F;
            animation1.RotateLimit = 0F;
            animation1.ScaleCoeff = ((System.Drawing.PointF)(resources.GetObject("animation1.ScaleCoeff")));
            animation1.SlideCoeff = ((System.Drawing.PointF)(resources.GetObject("animation1.SlideCoeff")));
            animation1.TimeCoeff = 0F;
            animation1.TransparencyCoeff = 0F;
            this.gunaTransition1.DefaultAnimation = animation1;
            // 
            // PokemonIntro
            // 
            this.AutoScaleDimensions = new System.Drawing.SizeF(6F, 13F);
            this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.Font;
            this.BackColor = System.Drawing.Color.Crimson;
            this.ClientSize = new System.Drawing.Size(327, 489);
            this.Controls.Add(this.gunaGradient2Panel1);
            this.gunaTransition1.SetDecoration(this, Guna.UI.Animation.DecorationType.None);
            this.FormBorderStyle = System.Windows.Forms.FormBorderStyle.None;
            this.Name = "PokemonIntro";
            this.StartPosition = System.Windows.Forms.FormStartPosition.CenterScreen;
            this.Text = "PokemonIntro";
            this.Load += new System.EventHandler(this.PokemonIntro_Load);
            this.gunaGradient2Panel1.ResumeLayout(false);
            this.gunaGradient2Panel1.PerformLayout();
            this.gunaGradientPanel1.ResumeLayout(false);
            this.gunaGradientPanel2.ResumeLayout(false);
            this.uPanel.ResumeLayout(false);
            ((System.ComponentModel.ISupportInitialize)(this.pokemonLogo)).EndInit();
            ((System.ComponentModel.ISupportInitialize)(this.pokeballLogo)).EndInit();
            this.ResumeLayout(false);

        }

        #endregion

        private Guna.UI.WinForms.GunaGradient2Panel gunaGradient2Panel1;
        private System.Windows.Forms.PictureBox pokemonLogo;
        private System.Windows.Forms.PictureBox pokeballLogo;
        private System.Windows.Forms.Timer timer1;
        private Guna.UI.WinForms.GunaGradient2Panel uPanel;
        private Guna.UI.WinForms.GunaVSeparator gunaVSeparator1;
        private Guna.UI.WinForms.GunaGradientPanel gunaGradientPanel1;
        private System.Windows.Forms.Label label6;
        private System.Windows.Forms.Label totalUsers;
        private System.Windows.Forms.Label label2;
        private System.Windows.Forms.Label onlineUsers;
        private ProyectoZ.UI.ProyectoZLabel proyectoZLabel1;
        private ProyectoZ.UI.ProyectoZLabel closeBtn;
        private System.Windows.Forms.Panel containerPanel;
        private Guna.UI.WinForms.GunaTransition gunaTransition1;
        private Guna.UI.WinForms.GunaGradientPanel gunaGradientPanel2;
    }
}