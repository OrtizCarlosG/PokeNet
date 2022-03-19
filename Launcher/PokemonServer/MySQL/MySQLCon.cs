using System;
using System.Collections.Generic;
using System.Data;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using MySql.Data.MySqlClient;
namespace PokemonServer.MySQL
{
    class MySQLCon
    {

        public MySqlConnection con;
        public void MySQLConnect()
        {
            string connectionString = "datasource=127.0.0.1;port=3306;username=root;password=;database=pokemononline;";

           con = new MySqlConnection(connectionString);
            try
            {
                con.Open();
                con.Close();
            } catch(Exception e)
            {
            }
        }

        public void openDB()
        {
            if (con != null && con.State != ConnectionState.Open)
            {
                con.Open();
            }
        }

        public void closeDB()
        {
            con.Close();
        }
    }
}
