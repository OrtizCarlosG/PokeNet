using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using PokemonServer.MySQL;

namespace PokemonServer.Class
{
    class Globals
    {
        private static PokemonServer _form;
        private static MySQLCon _connection;


        public static void setConnection(MySQLCon _con)
        {
            _connection = _con;
        }

        public static MySQLCon getConnection()
        {
            return _connection;
        }
        public static void setForm(PokemonServer form)
        {
            _form = form;
        }

        public static PokemonServer getForm()
        {
            return _form;
        }
    }
}
