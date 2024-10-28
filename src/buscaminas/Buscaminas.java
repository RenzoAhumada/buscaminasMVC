
package buscaminas;

import controlador.Tablero;


public class Buscaminas {

  
    public static void main(String[] args) {
        Tablero tablero = new Tablero(6, 6, 6);
        tablero.imprimirTablero();
        tablero.imprimirPistas();
        tablero.actualizarNumeroMinasAlrededor();
    }
    
}
