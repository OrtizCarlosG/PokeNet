
namespace PokemonOnline
{
    partial class loginPanel
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
            System.ComponentModel.ComponentResourceManager resources = new System.ComponentModel.ComponentResourceManager(typeof(loginPanel));
            this.gunaGradient2Panel1 = new Guna.UI.WinForms.GunaGradient2Panel();
            this.gunaGradientButton1 = new Guna.UI.WinForms.GunaGradientButton();
            this.passTxt = new ProyectoZ.UI.ProyectoZTextBox();
            this.userTxt = new ProyectoZ.UI.ProyectoZTextBox();
            this.gunaGradientPanel1 = new Guna.UI.WinForms.GunaGradientPanel();
            this.proyectoZCheckBox1 = new ProyectoZ.UI.ProyectoZCheckBox();
            this.proyectoZLabel2 = new ProyectoZ.UI.ProyectoZLabel();
            this.proyectoZLabel1 = new ProyectoZ.UI.ProyectoZLabel();
            this.proyectoZLabel3 = new ProyectoZ.UI.ProyectoZLabel();
            this.gunaGradient2Panel1.SuspendLayout();
            this.gunaGradientPanel1.SuspendLayout();
            this.SuspendLayout();
            // 
            // gunaGradient2Panel1
            // 
            this.gunaGradient2Panel1.BackColor = System.Drawing.Color.Transparent;
            this.gunaGradient2Panel1.Controls.Add(this.gunaGradientButton1);
            this.gunaGradient2Panel1.Controls.Add(this.passTxt);
            this.gunaGradient2Panel1.Controls.Add(this.userTxt);
            this.gunaGradient2Panel1.Controls.Add(this.gunaGradientPanel1);
            this.gunaGradient2Panel1.Dock = System.Windows.Forms.DockStyle.Fill;
            this.gunaGradient2Panel1.GradientColor1 = System.Drawing.Color.Crimson;
            this.gunaGradient2Panel1.GradientColor2 = System.Drawing.Color.Firebrick;
            this.gunaGradient2Panel1.Location = new System.Drawing.Point(0, 0);
            this.gunaGradient2Panel1.Name = "gunaGradient2Panel1";
            this.gunaGradient2Panel1.Size = new System.Drawing.Size(327, 309);
            this.gunaGradient2Panel1.TabIndex = 0;
            // 
            // gunaGradientButton1
            // 
            this.gunaGradientButton1.AnimationHoverSpeed = 0.07F;
            this.gunaGradientButton1.AnimationSpeed = 0.03F;
            this.gunaGradientButton1.BaseColor1 = System.Drawing.Color.SlateBlue;
            this.gunaGradientButton1.BaseColor2 = System.Drawing.Color.Fuchsia;
            this.gunaGradientButton1.BorderColor = System.Drawing.Color.Black;
            this.gunaGradientButton1.DialogResult = System.Windows.Forms.DialogResult.None;
            this.gunaGradientButton1.FocusedColor = System.Drawing.Color.Empty;
            this.gunaGradientButton1.Font = new System.Drawing.Font("Segoe UI", 9F);
            this.gunaGradientButton1.ForeColor = System.Drawing.Color.White;
            this.gunaGradientButton1.Image = ((System.Drawing.Image)(resources.GetObject("gunaGradientButton1.Image")));
            this.gunaGradientButton1.ImageSize = new System.Drawing.Size(20, 20);
            this.gunaGradientButton1.Location = new System.Drawing.Point(74, 255);
            this.gunaGradientButton1.Name = "gunaGradientButton1";
            this.gunaGradientButton1.OnHoverBaseColor1 = System.Drawing.Color.FromArgb(((int)(((byte)(155)))), ((int)(((byte)(145)))), ((int)(((byte)(221)))));
            this.gunaGradientButton1.OnHoverBaseColor2 = System.Drawing.Color.FromArgb(((int)(((byte)(255)))), ((int)(((byte)(85)))), ((int)(((byte)(255)))));
            this.gunaGradientButton1.OnHoverBorderColor = System.Drawing.Color.Black;
            this.gunaGradientButton1.OnHoverForeColor = System.Drawing.Color.White;
            this.gunaGradientButton1.OnHoverImage = null;
            this.gunaGradientButton1.OnPressedColor = System.Drawing.Color.Black;
            this.gunaGradientButton1.Size = new System.Drawing.Size(160, 42);
            this.gunaGradientButton1.TabIndex = 5;
            this.gunaGradientButton1.Text = "Inciar sesion";
            this.gunaGradientButton1.Click += new System.EventHandler(this.gunaGradientButton1_Click);
            // 
            // passTxt
            // 
            this.passTxt.BackColor = System.Drawing.Color.Crimson;
            this.passTxt.BorderColor = System.Drawing.Color.Transparent;
            this.passTxt.BorderFocusColor = System.Drawing.Color.Maroon;
            this.passTxt.BorderRadius = 5;
            this.passTxt.BorderSize = 2;
            this.passTxt.Font = new System.Drawing.Font("Microsoft Sans Serif", 9.5F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.passTxt.ForeColor = System.Drawing.Color.FromArgb(((int)(((byte)(64)))), ((int)(((byte)(64)))), ((int)(((byte)(64)))));
            this.passTxt.Location = new System.Drawing.Point(17, 120);
            this.passTxt.Margin = new System.Windows.Forms.Padding(4);
            this.passTxt.MaxTextLenght = 32767;
            this.passTxt.Multiline = false;
            this.passTxt.Name = "passTxt";
            this.passTxt.Padding = new System.Windows.Forms.Padding(10, 7, 10, 7);
            this.passTxt.PasswordChar = true;
            this.passTxt.PlaceholderColor = System.Drawing.Color.DarkGray;
            this.passTxt.PlaceholderText = "Contraseña";
            this.passTxt.Size = new System.Drawing.Size(281, 31);
            this.passTxt.TabIndex = 3;
            this.passTxt.Texts = "";
            this.passTxt.UnderlinedStyle = true;
            // 
            // userTxt
            // 
            this.userTxt.BackColor = System.Drawing.Color.Crimson;
            this.userTxt.BorderColor = System.Drawing.Color.Transparent;
            this.userTxt.BorderFocusColor = System.Drawing.Color.Maroon;
            this.userTxt.BorderRadius = 5;
            this.userTxt.BorderSize = 2;
            this.userTxt.Font = new System.Drawing.Font("Microsoft Sans Serif", 9.5F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.userTxt.ForeColor = System.Drawing.Color.FromArgb(((int)(((byte)(64)))), ((int)(((byte)(64)))), ((int)(((byte)(64)))));
            this.userTxt.Location = new System.Drawing.Point(17, 50);
            this.userTxt.Margin = new System.Windows.Forms.Padding(4);
            this.userTxt.MaxTextLenght = 32767;
            this.userTxt.Multiline = false;
            this.userTxt.Name = "userTxt";
            this.userTxt.Padding = new System.Windows.Forms.Padding(10, 7, 10, 7);
            this.userTxt.PasswordChar = false;
            this.userTxt.PlaceholderColor = System.Drawing.Color.DarkGray;
            this.userTxt.PlaceholderText = "Escribe aqui tu usuario";
            this.userTxt.Size = new System.Drawing.Size(281, 31);
            this.userTxt.TabIndex = 1;
            this.userTxt.Texts = "";
            this.userTxt.UnderlinedStyle = true;
            // 
            // gunaGradientPanel1
            // 
            this.gunaGradientPanel1.BackgroundImage = ((System.Drawing.Image)(resources.GetObject("gunaGradientPanel1.BackgroundImage")));
            this.gunaGradientPanel1.BackgroundImageLayout = System.Windows.Forms.ImageLayout.Stretch;
            this.gunaGradientPanel1.Controls.Add(this.proyectoZLabel3);
            this.gunaGradientPanel1.Controls.Add(this.proyectoZCheckBox1);
            this.gunaGradientPanel1.Controls.Add(this.proyectoZLabel2);
            this.gunaGradientPanel1.Controls.Add(this.proyectoZLabel1);
            this.gunaGradientPanel1.Dock = System.Windows.Forms.DockStyle.Fill;
            this.gunaGradientPanel1.GradientColor1 = System.Drawing.Color.Crimson;
            this.gunaGradientPanel1.GradientColor2 = System.Drawing.Color.Crimson;
            this.gunaGradientPanel1.GradientColor3 = System.Drawing.Color.Firebrick;
            this.gunaGradientPanel1.GradientColor4 = System.Drawing.Color.Firebrick;
            this.gunaGradientPanel1.Location = new System.Drawing.Point(0, 0);
            this.gunaGradientPanel1.Name = "gunaGradientPanel1";
            this.gunaGradientPanel1.Size = new System.Drawing.Size(327, 309);
            this.gunaGradientPanel1.TabIndex = 6;
            this.gunaGradientPanel1.Text = "gunaGradientPanel1";
            // 
            // proyectoZCheckBox1
            // 
            this.proyectoZCheckBox1._Text = "Inciar sesion automaticamente";
            this.proyectoZCheckBox1.BackColor = System.Drawing.Color.Crimson;
            this.proyectoZCheckBox1.CheckboxCheckColor = System.Drawing.Color.White;
            this.proyectoZCheckBox1.CheckboxColor = System.Drawing.Color.Maroon;
            this.proyectoZCheckBox1.CheckboxHoverColor = System.Drawing.Color.Firebrick;
            this.proyectoZCheckBox1.CheckboxStyle = ProyectoZ.UI.ProyectoZCheckBox.Style.iOS;
            this.proyectoZCheckBox1.Checked = false;
            this.proyectoZCheckBox1.ForeColor = System.Drawing.Color.White;
            this.proyectoZCheckBox1.Location = new System.Drawing.Point(17, 158);
            this.proyectoZCheckBox1.Name = "proyectoZCheckBox1";
            this.proyectoZCheckBox1.Size = new System.Drawing.Size(207, 20);
            this.proyectoZCheckBox1.TabIndex = 4;
            this.proyectoZCheckBox1.Text = "Inciar sesion automaticamente";
            this.proyectoZCheckBox1.TickThickness = 2;
            // 
            // proyectoZLabel2
            // 
            this.proyectoZLabel2.AutoSize = true;
            this.proyectoZLabel2.DrawingDirection = ProyectoZ.UI.ProyectoZLabel.Angles.LeftToRight;
            this.proyectoZLabel2.EnableGradient = true;
            this.proyectoZLabel2.enableOnHoverColors = false;
            this.proyectoZLabel2.EnableShadow = false;
            this.proyectoZLabel2.EndColor = System.Drawing.Color.Maroon;
            this.proyectoZLabel2.EndColorAlpha = 255;
            this.proyectoZLabel2.Font = new System.Drawing.Font("Microsoft Sans Serif", 15.75F, System.Drawing.FontStyle.Bold, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.proyectoZLabel2.GradientDirection = System.Drawing.Drawing2D.LinearGradientMode.Horizontal;
            this.proyectoZLabel2.Location = new System.Drawing.Point(12, 91);
            this.proyectoZLabel2.Name = "proyectoZLabel2";
            this.proyectoZLabel2.OnEnableEffect = false;
            this.proyectoZLabel2.OnEnableEffectOnText = false;
            this.proyectoZLabel2.OnHoverColor = System.Drawing.Color.White;
            this.proyectoZLabel2.OnHoverColor2 = System.Drawing.Color.Red;
            this.proyectoZLabel2.ShadowColor = System.Drawing.Color.LightGray;
            this.proyectoZLabel2.ShadowOffset = 1;
            this.proyectoZLabel2.Size = new System.Drawing.Size(140, 25);
            this.proyectoZLabel2.StartColor = System.Drawing.Color.FromArgb(((int)(((byte)(150)))), ((int)(((byte)(0)))), ((int)(((byte)(0)))));
            this.proyectoZLabel2.StartColorAlpha = 255;
            this.proyectoZLabel2.TabIndex = 2;
            this.proyectoZLabel2.Text = "Contraseña:";
            // 
            // proyectoZLabel1
            // 
            this.proyectoZLabel1.AutoSize = true;
            this.proyectoZLabel1.DrawingDirection = ProyectoZ.UI.ProyectoZLabel.Angles.LeftToRight;
            this.proyectoZLabel1.EnableGradient = true;
            this.proyectoZLabel1.enableOnHoverColors = false;
            this.proyectoZLabel1.EnableShadow = false;
            this.proyectoZLabel1.EndColor = System.Drawing.Color.Maroon;
            this.proyectoZLabel1.EndColorAlpha = 255;
            this.proyectoZLabel1.Font = new System.Drawing.Font("Microsoft Sans Serif", 15.75F, System.Drawing.FontStyle.Bold, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.proyectoZLabel1.GradientDirection = System.Drawing.Drawing2D.LinearGradientMode.Horizontal;
            this.proyectoZLabel1.Location = new System.Drawing.Point(12, 21);
            this.proyectoZLabel1.Name = "proyectoZLabel1";
            this.proyectoZLabel1.OnEnableEffect = false;
            this.proyectoZLabel1.OnEnableEffectOnText = false;
            this.proyectoZLabel1.OnHoverColor = System.Drawing.Color.White;
            this.proyectoZLabel1.OnHoverColor2 = System.Drawing.Color.Red;
            this.proyectoZLabel1.ShadowColor = System.Drawing.Color.LightGray;
            this.proyectoZLabel1.ShadowOffset = 1;
            this.proyectoZLabel1.Size = new System.Drawing.Size(100, 25);
            this.proyectoZLabel1.StartColor = System.Drawing.Color.FromArgb(((int)(((byte)(150)))), ((int)(((byte)(0)))), ((int)(((byte)(0)))));
            this.proyectoZLabel1.StartColorAlpha = 255;
            this.proyectoZLabel1.TabIndex = 0;
            this.proyectoZLabel1.Text = "Usuario:";
            // 
            // proyectoZLabel3
            // 
            this.proyectoZLabel3.AutoSize = true;
            this.proyectoZLabel3.DrawingDirection = ProyectoZ.UI.ProyectoZLabel.Angles.LeftToRight;
            this.proyectoZLabel3.EnableGradient = true;
            this.proyectoZLabel3.enableOnHoverColors = true;
            this.proyectoZLabel3.EnableShadow = false;
            this.proyectoZLabel3.EndColor = System.Drawing.Color.White;
            this.proyectoZLabel3.EndColorAlpha = 255;
            this.proyectoZLabel3.Font = new System.Drawing.Font("Microsoft Sans Serif", 11.25F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.proyectoZLabel3.GradientDirection = System.Drawing.Drawing2D.LinearGradientMode.Horizontal;
            this.proyectoZLabel3.Location = new System.Drawing.Point(14, 191);
            this.proyectoZLabel3.Name = "proyectoZLabel3";
            this.proyectoZLabel3.OnEnableEffect = false;
            this.proyectoZLabel3.OnEnableEffectOnText = false;
            this.proyectoZLabel3.OnHoverColor = System.Drawing.Color.Red;
            this.proyectoZLabel3.OnHoverColor2 = System.Drawing.Color.Red;
            this.proyectoZLabel3.ShadowColor = System.Drawing.Color.WhiteSmoke;
            this.proyectoZLabel3.ShadowOffset = 1;
            this.proyectoZLabel3.Size = new System.Drawing.Size(274, 18);
            this.proyectoZLabel3.StartColor = System.Drawing.Color.White;
            this.proyectoZLabel3.StartColorAlpha = 255;
            this.proyectoZLabel3.TabIndex = 7;
            this.proyectoZLabel3.Text = "¿No tienes cuenta aun? Registrate aca   ";
            // 
            // loginPanel
            // 
            this.AutoScaleDimensions = new System.Drawing.SizeF(6F, 13F);
            this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.Font;
            this.ClientSize = new System.Drawing.Size(327, 309);
            this.Controls.Add(this.gunaGradient2Panel1);
            this.FormBorderStyle = System.Windows.Forms.FormBorderStyle.None;
            this.Name = "loginPanel";
            this.Text = "loginPanel";
            this.Load += new System.EventHandler(this.loginPanel_Load);
            this.gunaGradient2Panel1.ResumeLayout(false);
            this.gunaGradientPanel1.ResumeLayout(false);
            this.gunaGradientPanel1.PerformLayout();
            this.ResumeLayout(false);

        }

        #endregion

        private Guna.UI.WinForms.GunaGradient2Panel gunaGradient2Panel1;
        private ProyectoZ.UI.ProyectoZLabel proyectoZLabel1;
        private ProyectoZ.UI.ProyectoZTextBox userTxt;
        private ProyectoZ.UI.ProyectoZTextBox passTxt;
        private ProyectoZ.UI.ProyectoZLabel proyectoZLabel2;
        private Guna.UI.WinForms.GunaGradientButton gunaGradientButton1;
        private Guna.UI.WinForms.GunaGradientPanel gunaGradientPanel1;
        private ProyectoZ.UI.ProyectoZCheckBox proyectoZCheckBox1;
        private ProyectoZ.UI.ProyectoZLabel proyectoZLabel3;
    }
}