package controlador;

import vistas.Vistas;
import modelo.Casillas;
import java.util.LinkedList;
import java.util.List;
import java.util.function.Consumer;

public class Tablero {

    Casillas[][] casillas;
    int numFilas;
    int numColumnas;
    int numMinas;

    boolean juegoTerminado;
    int numCasillasAbiertas;

    private Consumer<List<Casillas>> eventoPartidaPerdida;
    private Consumer<List<Casillas>> eventoPartidaGanada;
    Consumer<Casillas> eventoCasillaAbierta;

    private Vistas vista;

    public Tablero() {
    }

    public Tablero(int numFilas, int numColumnas, int numMinas) {
        this.numFilas = numFilas;
        this.numColumnas = numColumnas;
        this.numMinas = numMinas;
        this.inicializarCasillas();
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
                System.out.print(casillas[i][j].getNumMinasAlrededor());
            }
            System.out.println("");
        }

    }

    public void imprimirPistas() {
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
                    List<Casillas> casillasAlrededor = obtenerCasillasAlrededor(i, j);
                    casillasAlrededor.forEach((c) -> c.incrementarNumeroMinasAlrededor());
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

    List<Casillas> obtenerCasillasConMinas() {
        List<Casillas> casillasConMinas = new LinkedList<>();
        for (int i = 0; i < casillas.length; i++) {
            for (int j = 0; j < casillas[i].length; j++) {
                if (casillas[i][j].isMina()) {
                    casillasConMinas.add(casillas[i][j]);
                }
            }

        }
        return casillasConMinas;
    }

    public void seleccionarCasilla(int posFila, int posColumna) {

        eventoCasillaAbierta.accept(this.casillas[posFila][posColumna]);
        if (this.casillas[posFila][posColumna].isMina()) {
            eventoPartidaPerdida.accept(obtenerCasillasConMinas());
        } else if (this.casillas[posFila][posColumna].getNumMinasAlrededor() == 0) {
            marcarCasillaAbierta(posFila, posColumna);
            List<Casillas> casillasAlrededor = obtenerCasillasAlrededor(posFila, posColumna);
            for (Casillas casilla : casillasAlrededor) {
                if (!casilla.isAbierta()) {
                    casilla.setAbierta(true);
                    seleccionarCasilla(casilla.getPosFila(), casilla.getPosColumna());
                }
            }
        } else {
            marcarCasillaAbierta(posFila, posColumna);
        }
        if (partidaGanada()) {
            eventoPartidaGanada.accept(obtenerCasillasConMinas());
        }
    }

    public void marcarCasillaAbierta(int posFila, int posColumna) {
        if (!this.casillas[posFila][posColumna].isAbierta()) {
            numCasillasAbiertas++;
            this.casillas[posFila][posColumna].setAbierta(true);
        }

    }

    boolean partidaGanada() {
        return numCasillasAbiertas <= (numFilas * numColumnas) - numMinas;
    }

    public void setEventoPartidaPerdida(Consumer<List<Casillas>> eventoPartidaPerdida) {
        this.eventoPartidaPerdida = eventoPartidaPerdida;
    }

    public void setEventoCasillaAbierta(Consumer<Casillas> eventoCasillaAbierta) {
        this.eventoCasillaAbierta = eventoCasillaAbierta;
    }

    public void setEventoPartidaGanada(Consumer<List<Casillas>> eventoPartidaGanada) {
        this.eventoPartidaGanada = eventoPartidaGanada;
    }

}
