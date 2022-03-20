**Codigos originales: <a href="https://bitbucket.org/Myth1c/pokemonium/src/master/">Pokemonium</a>**
# Cliente
Se utilizo el cliente de pokenet como una practica divertida para programar en java. El plan original fue tomar este mismo e intentar solucionar la mayor cantidad de bugs que se encuentren asi como tambien agregar nuevas implementaciones.
# Launcher
Se diseñó un launcher con un sistema cliente/servidor para este juego. La idea es que solamente tenga funciones simples, tales como las de registro y las de inicio de sesión. Esto se hizo porque la forma original para ejecutar el archivo .jar del cliente era a través de una consola de comandos."

# Paquetes de el servidor.
- Server ID: trae consigo nuestro id dentro del servidor del launcher.
- Login result: trae consigo una booleana (true/false) con el resultado del inicio de sesión.
- Register result: trae consigo una booleana (true/false) para saber si el registro fue o no exitoso.

# Paquetes del cliente.
- Login: enviará al servidor nuestro "id", "hwid", "usuario" y "contraseña" para iniciar sesión.
- Register: enviará al servidor todos los datos necesarios para realizar un registro.

Se planea para un futuro combinar las tecnologias del launcher y del cliente Java de Pokenet para que los usuarios no tengan que volver a iniciar sesion una vez iniciado el juego. Esto se hará utilizando una "key" genera para el usuario y su "hwid"."


## Muestra del cliente
![Cliente del launcher](https://i.imgur.com/s5wYyqR.gif)

# Servidor
Se han modificado y agregado varias funcionalidades al servidor de pokenet tales como:
- Un sistema de clanes: para que los usuarios puedan generar alianzas o grupos entre si.
- Una funcion para el nivel de admin: esto sirve para identificar el nivel de admin de un usuario en un string en lugar de numeros.
- Nivel de admin en el chat: utilizando la funcion anteriormente nombrada se ha añadido el nivel de un usuario en el chat.
- Sistema de estados de un usuario: en forma numerica dentro de la base de datos MySql se guardara si un usuario esta en linea o no, permitiendo asi saber cuantos usuarios entan conectados a traves de consultas MySql. Esto fue diseñado asi para una futura web que permita ver la cantidad de usuarios en linea o incluso poder ver este dato utilizando el launcher.

Entre otras funciones que se han añadido.

## **Es importante destacar que todo esto se encuentra bajo construccion.**

