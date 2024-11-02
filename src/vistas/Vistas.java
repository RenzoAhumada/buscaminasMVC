package vistas;

import controlador.Tablero;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.function.Consumer;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import modelo.Casillas;

public class Vistas extends javax.swing.JFrame {

    int numFilas = 10;
    int numColumnas = 10;
    int numMinas = 20;

    JButton[][] botonesTablero;

    Tablero tablero;

    public Vistas() {
        initComponents();
        juegoNuevo();
    }

    void descargarControles() {
        if (botonesTablero != null) {
            for (int i = 0; i < botonesTablero.length; i++) {
                for (int j = 0; j < botonesTablero[i].length; j++) {
                    if (botonesTablero[i][j] != null) {
                        getContentPane().remove(botonesTablero[i][j]);
                    }
                }
            }
        }
    }

    private void juegoNuevo() {
        descargarControles();
        cargarControles();
        crearTablero();
        repaint();
    }

    public void crearTablero() {
        tablero = new Tablero(numFilas, numColumnas, numMinas);
        tablero.setEventoPartidaPerdida(new Consumer<List<Casillas>>() {
            @Override
            public void accept(List<Casillas> t) {
                for (Casillas casillaConMina : t) {
                    botonesTablero[casillaConMina.getPosFila()][casillaConMina.getPosColumna()].setText("*");
                }
            }
        });
        tablero.setEventoPartidaGanada(new Consumer<List<Casillas>>() {
            @Override
            public void accept(List<Casillas> t) {
                for (Casillas casillaConMina : t) {
                    botonesTablero[casillaConMina.getPosFila()][casillaConMina.getPosColumna()].setText(":)");
                }
            }
        });
        tablero.setEventoCasillaAbierta(new Consumer<Casillas>() {
            @Override
            public void accept(Casillas t) {
                botonesTablero[t.getPosFila()][t.getPosColumna()].setEnabled(false);
                botonesTablero[t.getPosFila()][t.getPosColumna()].setText(t.getNumMinasAlrededor() == 0 ? "" : t.getNumMinasAlrededor() + "");
            }
        });
        tablero.imprimirTablero();
    }

    public void cargarControles() {

        int posXReferencia = 25;
        int posYReferencia = 25;
        int anchoControl = 30;
        int altoControl = 30;

        botonesTablero = new JButton[numFilas][numColumnas];
        for (int i = 0; i < botonesTablero.length; i++) {
            for (int j = 0; j < botonesTablero.length; j++) {
                botonesTablero[i][j] = new JButton();
                botonesTablero[i][j].setName(i + "," + j);
                botonesTablero[i][j].setBorder(null);
                if (i == 0 && j == 0) {
                    botonesTablero[i][j].setBounds(posXReferencia, posYReferencia, anchoControl, altoControl);
                } else if (i == 0 && j != 0) {
                    botonesTablero[i][j].setBounds(botonesTablero[i][j - 1].getX() + botonesTablero[i][j - 1].getWidth(), posYReferencia, anchoControl, altoControl);
                } else {
                    botonesTablero[i][j].setBounds(
                            botonesTablero[i - 1][j].getX(), botonesTablero[i - 1][j].getY() + botonesTablero[i - 1][j].getHeight(), anchoControl, altoControl);

                }
                botonesTablero[i][j].addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        btnClick(e);
                    }

                });
                getContentPane().add(botonesTablero[i][j]);
            }
        }
       this.setSize(botonesTablero[numFilas-1][numColumnas-1].getX()+
                botonesTablero[numFilas-1][numColumnas-1].getWidth()+30,
                botonesTablero[numFilas-1][numColumnas-1].getY()+
                botonesTablero[numFilas-1][numColumnas-1].getHeight()+70);

    }

    private void btnClick(ActionEvent e) {
        JButton btn = (JButton) e.getSource();
        String[] cordenada = btn.getName().split(",");
        int posFila = Integer.parseInt(cordenada[0]);
        int posColumna = Integer.parseInt(cordenada[1]);
        ///JOptionPane.showMessageDialog(rootPane, posFila + "," + posColumna);
        tablero.seleccionarCasilla(posFila, posColumna);
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jMenuItem1 = new javax.swing.JMenuItem();
        jMenuBar1 = new javax.swing.JMenuBar();
        jMenu1 = new javax.swing.JMenu();
        numeroDeMinas = new javax.swing.JMenuItem();
        Tamaño = new javax.swing.JMenuItem();
        menuNuevoJuego = new javax.swing.JMenuItem();
        jMenu2 = new javax.swing.JMenu();

        jMenuItem1.setText("jMenuItem1");

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jMenu1.setText("Juego");
        jMenu1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenu1ActionPerformed(evt);
            }
        });

        numeroDeMinas.setText("Numero de minas");
        numeroDeMinas.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                numeroDeMinasActionPerformed(evt);
            }
        });
        jMenu1.add(numeroDeMinas);

        Tamaño.setText("Tamaño");
        Tamaño.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                TamañoActionPerformed(evt);
            }
        });
        jMenu1.add(Tamaño);

        menuNuevoJuego.setText("Nuevo Juego");
        menuNuevoJuego.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuNuevoJuegoActionPerformed(evt);
            }
        });
        jMenu1.add(menuNuevoJuego);

        jMenuBar1.add(jMenu1);

        jMenu2.setText("Edit");
        jMenuBar1.add(jMenu2);

        setJMenuBar(jMenuBar1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 400, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 279, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jMenu1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenu1ActionPerformed
        juegoNuevo();
    }//GEN-LAST:event_jMenu1ActionPerformed

    private void menuNuevoJuegoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuNuevoJuegoActionPerformed
        juegoNuevo();
// TODO add your handling code here:
    }//GEN-LAST:event_menuNuevoJuegoActionPerformed

    private void TamañoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_TamañoActionPerformed

        int num = Integer.parseInt(JOptionPane.showInputDialog("Digite el tamaño de la pantalla"));
        //while (num <= 0) {
          //  num = Integer.parseInt(JOptionPane.showInputDialog("El tamaño debe ser mayor que 0. Digite el tamaño de la pantalla"));
        //}
        this.numFilas = num;
        this.numColumnas = num;
        juegoNuevo();
    }//GEN-LAST:event_TamañoActionPerformed

    private void numeroDeMinasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_numeroDeMinasActionPerformed
        
       int num = Integer.parseInt(JOptionPane.showInputDialog("Digite la cantidad de minas"));
        //while (num <= 0) {
          //  num = Integer.parseInt(JOptionPane.showInputDialog("El tamaño debe ser mayor que 0. Digite el tamaño de la pantalla"));
        //}
        this.numMinas = num;
        juegoNuevo();
// TODO add your handling code here:
    }//GEN-LAST:event_numeroDeMinasActionPerformed

    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Vistas.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Vistas.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Vistas.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Vistas.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Vistas().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JMenuItem Tamaño;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenu jMenu2;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JMenuItem jMenuItem1;
    private javax.swing.JMenuItem menuNuevoJuego;
    private javax.swing.JMenuItem numeroDeMinas;
    // End of variables declaration//GEN-END:variables
}
