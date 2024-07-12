############## Falta mejorar el sistema ##############

Aciertos
1. Buen uso de las pruebas unitarias, si llegas a usar mockito, recuerda que genera un simulador de la base de datos (no registra info en la base de datos y es más eficiente)
Errores
1. Los paquetes no están bien nombrados, primero se agrega el dominio y después el nombre del paquete. Esto como buena práctica. 2. Al crear un nuevo libro me permite registrar el titulo y el autor con espacios en blanco, al agregar espacios en blanco y letras en el ISBN me manda una excepción y el sistema terminó 3. El sistema permite actualizar libros con existencia cero. 4. Al consultar el historial de miembros no muestra algo, si no existe algún registro, el sistema debe de mostrar algún mensaje para identificar si no hay información 5. Al registrar una devolución el sistema arroja una excepción 6.- No hay opción para salir del menú principal (terminar el sistema).

2. ###########################################
3. paquetes renombrados
4. se creo una verificacion en el setter de titulo y nombre para no permitir espacios individuales
5. El titulo ahora no permite numeros individuales como titulo, requiere de almenos otra palabra para tener un nuero en el titulo.
6. Se cambio el codigo para que la excepcion del ISBN se maneje en el setter y no en el metodo de main.
7. Se valido que la existencia del libro no pueda ser 0 o menos, el sistema pedira un nuevo valor y recomendara eliminar el libro en caso de que la existencia sea 0.
8. Se agrego mensaje en caso de no encontrar valores en la base de datos para historial de miembros
9. Se arreglo el error al registrar devolucion
10. Se agrego opcion para salir del menu principal
