##############
Falta mejorar el sistema
##############

<h1>Aciertos</h1>
1. Buen uso de las pruebas unitarias, si llegas a usar mockito, recuerda que genera un simulador de la base de datos (no registra info en la base de datos y es más eficiente)

<h1>Errores</h1>
1. Los paquetes no están bien nombrados, primero se agrega el dominio y después el nombre del paquete. Esto como buena práctica.
2. Al crear un nuevo libro me permite registrar el titulo y el autor con espacios en blanco, al agregar espacios en blanco y letras en el ISBN me manda una excepción y el sistema terminó
3. El sistema permite actualizar libros con existencia cero.
4. Al consultar el historial de miembros no muestra algo, si no existe algún registro, el sistema debe de mostrar algún mensaje para identificar si no hay información
5. Al registrar una devolución el sistema arroja una excepción
6.- No hay opción para salir del menú principal (terminar el sistema).
