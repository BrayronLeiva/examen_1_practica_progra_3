package View;

import Controller.Controller;

import javax.swing.*;
import java.awt.*;

public class View extends JFrame {
    private JLabel lb_codigo;
    private JLabel lb_activo;
    private JLabel lb_fabricacion;
    private JLabel lb_valor;
    private JLabel lb_categoria;
    private JTextField txf_codigo;
    private JTextField txf_activo;
    private JTextField txf_fabricacion;
    private JTextField txf_valor;
    private JTextField txf_categoria;
    private JButton btn_limpiar;
    private JButton btn_consultar;
    private JTextField txf_edad;
    private JTextField textField2;
    private JTextField txf_valor_actual;
    private JButton btn_guardar;
    private JLabel lb_edad;
    private JLabel lb_depreciacion;

    private Controller controller;
    private JLabel lb_valor_actual;
    private JPanel pnl_principal;

    public View(Controller ctl){
        this.setResizable(false);
        this.setSize(800,800);
        this.setVisible(true);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setContentPane(pnl_principal);
        this.controller = ctl;

        //this.initTxtFields();
    }



    //getter and setters
    public JLabel getLb_codigo() {
        return lb_codigo;
    }

    public JLabel getLb_activo() {
        return lb_activo;
    }

    public JLabel getLb_fabricacion() {
        return lb_fabricacion;
    }

    public JLabel getLb_valor() {
        return lb_valor;
    }

    public JLabel getLb_categoria() {
        return lb_categoria;
    }

    public JTextField getTxf_codigo() {
        return txf_codigo;
    }

    public JTextField getTxf_activo() {
        return txf_activo;
    }

    public JTextField getTxf_fabricacion() {
        return txf_fabricacion;
    }

    public JTextField getTxf_valor() {
        return txf_valor;
    }

    public JTextField getTxf_categoria() {
        return txf_categoria;
    }

    public JButton getBtn_limpiar() {
        return btn_limpiar;
    }

    public JButton getBtn_consultar() {
        return btn_consultar;
    }

    public JTextField getTxf_edad() {
        return txf_edad;
    }

    public JTextField getTextField2() {
        return textField2;
    }

    public JTextField getTxf_valor_actual() {
        return txf_valor_actual;
    }

    public JButton getBtn_guardar() {
        return btn_guardar;
    }

    public JLabel getLb_edad() {
        return lb_edad;
    }

    public JLabel getLb_depreciacion() {
        return lb_depreciacion;
    }

    public JLabel getLb_valor_actual() {
        return lb_valor_actual;
    }
}
