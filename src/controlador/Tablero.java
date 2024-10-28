package controlador;

import java.util.LinkedList;
import java.util.List;
import modelo.Casillas;
import vistas.Vistas;

public class Tablero {

    Casillas[][] casillas;

    int numFilas;
    int numColumnas;
    int numMinas;
    
    private Vistas vista;

    public Tablero() {
    }

    public Tablero(int numFilas, int numColumnas, int numMinas) {

        this.numFilas = numFilas;
        this.numColumnas = numColumnas;
        this.numMinas = numMinas;
        inicializarCasillas();
    }

    public void inicializarCasillas() {

        casillas = new Casillas[this.numFilas][this.numColumnas];

        for (int i = 0; i < casillas.length; i++) {
            for (int j = 0; j < casillas[i].length; j++) {
                casillas[i][j] = new Casillas(i, j);
            }

        }
        generarMinas();
    }

    public void imprimirPistas(){
        for (int i = 0; i < casillas.length; i++) {
            for (int j = 0; j < casillas[i].length; j++) {
                System.out.println(casillas[i][j].getNumMinasAlrededor());
            }
            System.out.println("");
        }
    }
    
    public void actualizarNumeroMinasAlrededor() {
        for (int i = 0; i < casillas.length; i++) {
            for (int j = 0; j < casillas[i].length; j++) {
                if (casillas[i][j].isMina()) {   
                    List<Casillas> casillasAlrededor = obtenerCasillasAlrededor(i,j);
                    casillasAlrededor.forEach((c)->c.incrementarNumeroMinasAlrededor());
                }
            }

        }
    }

    private List<Casillas> obtenerCasillasAlrededor(int posFila, int posColumna) {
        List<Casillas> listaCasillas = new LinkedList<>();
        for (int i = 0; i < 8; i++) {
            int tmpPosColumna = posColumna;
            int tmpPosFila = posFila;
            switch (i) {
                case 0:
                    tmpPosFila--; //Arriba
                    break;
                case 1:
                    tmpPosFila--; //Arrina Derecha
                    tmpPosColumna++;
                    break;
                case 2:
                    tmpPosColumna++;
                    break; //Derecha
                case 3:
                    tmpPosColumna++;
                    tmpPosFila++;
                    break; //Derecha Abajo
                case 4:
                    tmpPosFila++;
                    break; //Abajo
                case 5:
                    tmpPosFila++;
                    tmpPosColumna--;
                    break; //Abajo Izquierda
                case 6:
                    tmpPosColumna--;
                    break; //Izquierda
                case 7:
                    tmpPosFila--;
                    tmpPosColumna--;
                    break;

            }
            if (tmpPosFila >= 0 && tmpPosFila < this.casillas.length
                    && tmpPosColumna >= 0 && tmpPosColumna < this.casillas[0].length) {
                listaCasillas.add(this.casillas[tmpPosFila][tmpPosColumna]);
            }
        }
        return listaCasillas;
    }

    private void generarMinas() {
        int minasGeneradas = 0;

        while (minasGeneradas != numMinas) {
            int posTmpFila = (int) (Math.random() * casillas.length);
            int posTmpColumna = (int) (Math.random() * casillas[0].length);
            if (!casillas[posTmpFila][posTmpColumna].isMina()) {
                casillas[posTmpFila][posTmpColumna].setMina(true);
                minasGeneradas++;
            }
        }
        actualizarNumeroMinasAlrededor();
    }

    public void imprimirTablero() {
        for (int i = 0; i < casillas.length; i++) {
            for (int j = 0; j < casillas[i].length; j++) {
                System.out.println(casillas[i][j].isMina() ? "*" : "o");
            }

        }
        System.out.println("");
    }
    
    private void cargarControles(){
        
        int posXReferencia=25;
        int posYReferencia=25;
        int anchoControl=30;
        int altoControl=30;
        
        
        
    }
    
}
