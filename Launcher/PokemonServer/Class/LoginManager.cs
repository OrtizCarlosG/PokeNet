using MySql.Data.MySqlClient;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace PokemonServer.Class
{
    class LoginManager
    {
        public static bool tryLogin(string username, string password)
        {

            bool _result = false;
            Globals.getConnection().openDB();
            string query = "SELECT * FROM pn_members WHERE username = @user AND password = @pass";
            MySqlCommand commandDatabase = new MySqlCommand(query, Globals.getConnection().con);
            commandDatabase.Parameters.Add("@user", MySqlDbType.VarChar).Value = username;
            commandDatabase.Parameters.Add("@pass", MySqlDbType.VarChar).Value = password;
            commandDatabase.CommandTimeout = 60;
            MySqlDataReader reader;
            try
            {
                reader = commandDatabase.ExecuteReader();
                if (reader.HasRows)
                {
                    while (reader.Read())
                    {
                        _result = true;
                        Globals.getForm().addToLog($"ID: {reader.GetString(0)} Username: {reader.GetString(1)} Logged sucessfully");
                    }
                }
                else
                {
                    _result = false;
                    Globals.getForm().addToLog($"ID: {reader.GetString(0)} Username: {reader.GetString(1)} failed to login");
                }
                Globals.getConnection().closeDB();
            }
            catch (Exception ex)
            {
                Globals.getForm().addToLog(ex.Message);
            }
            Globals.getConnection().closeDB();
            return _result;
        }
     
    }
}
