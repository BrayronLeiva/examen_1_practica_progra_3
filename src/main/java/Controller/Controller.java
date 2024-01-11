package Controller;

import View.View;

import Model.Model;

import Model.Activo;
import org.jdom2.JDOMException;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.*;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;

public class Controller implements ActionListener {
    View vista;
    Model modelo;
    String modo_actual;
    Window_Listener w_listenr;

    public Controller() throws IOException, JDOMException {
        this.vista = new View(this);
        this.init_btns();
        this.modo_actual = "Guardar";
        w_listenr = new Window_Listener();
        this.vista.addWindowListener(w_listenr);
        try {
            this.modelo = new Model();
            System.out.println("Constructor modelo");

        } catch (IOException | JDOMException e) {
            throw new RuntimeException(e);
        }

    }

    public void init_btns(){
        vista.getBtn_consultar().addActionListener(this);
        vista.getBtn_guardar().addActionListener(this);
        vista.getBtn_limpiar().addActionListener(this);
    }

    public boolean validar_excepciones(String serie){
        try {
            if (txt_field_vacio()) {
                throw new Exception("Hay campos vacios, por favor revisar");
            }
        }catch (Exception ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage(), "Información", JOptionPane.INFORMATION_MESSAGE);
            return false;
        }
        return true;
    }


    public boolean txt_field_vacio(){
        return vista.getTxf_activo().getText().isEmpty() |  String.valueOf(vista.getCmb_categoria().getSelectedItem()).isEmpty() | vista.getTxf_codigo().getText().isEmpty()
                | vista.getTxf_valor().getText().isEmpty() | vista.getTxf_fabricacion().getText().isEmpty();
    }

    public void guardar_instrumento() throws IOException, JDOMException {

        vista.getTxf_codigo().setEnabled(true);
        if(validar_excepciones(vista.getTxf_codigo().getText())) {
            Activo activo = this.generar_activo();
            if (this.get_modo_actual().equals("Guardar")) {
                if(!modelo.save(activo)) {
                    vista.lanzar_mensaje("Este ID de activo ya existe");
                }
            } else if (this.get_modo_actual().equals("Consultar")) {
                if(!modelo.save(activo)) {
                    modelo.actualizar_activo(activo);
                }
            }
            this.limpiar_pantalla();
        }

    }

    public Activo generar_activo(){
        Activo activo = new Activo(vista.getTxf_codigo().getText(), vista.getTxf_activo().getText(), String.valueOf(vista.getCmb_categoria().getSelectedItem()),
                Integer.parseInt(vista.getTxf_fabricacion().getText()), Double.valueOf(vista.getTxf_valor().getText()));
        return activo;
    }


    public void recuperar_activo(){
        this.set_modo_actual("Consultar");
        if (!vista.getTxf_codigo().getText().isEmpty()) {
            Activo obj = modelo.seleccionar_activo_codigo(vista.getTxf_codigo().getText());
            if (obj != null) {
                int current_Year = this.obtener_fecha_actual();
                int edad = current_Year - obj.getFabricacion();
                vista.getTxf_codigo().setEnabled(false);
                vista.getTxf_activo().setText(obj.getActivo());
                vista.getTxf_fabricacion().setText(String.valueOf(obj.getFabricacion()));
                vista.getCmb_categoria().setSelectedItem(obj.getCategoria());
                vista.getTxf_valor().setText(String.valueOf(obj.getValor()));
                vista.getTxf_edad().setText(String.valueOf(edad));
                vista.getTxf_depreciacion().setText(String.valueOf(this.calcular_depreciacion(obj,edad)));
                vista.getTxf_valor_actual().setText(String.valueOf(this.calcular_valor_actual(obj, edad)));

            } else vista.lanzar_mensaje("El objeto digito no existe");

        } else vista.lanzar_mensaje("Digita un codigo antes de consultar");

    }

    public int obtener_fecha_actual(){
        LocalDate current_date = LocalDate.now();
        return current_date.getYear();
    }

    public double calcular_valor_actual(Activo obj, int edad){
        return obj.getValor() - this.calcular_depreciacion(obj, edad);
    }

    public double calcular_depreciacion(Activo obj, int edad){
        double depreciacion = 0;
        switch (String.valueOf(vista.getCmb_categoria().getSelectedItem())){
            case "Casa":{
                depreciacion = obj.getValor()*((double) edad /50);
                break;
            }
            case "Computadora":{
                depreciacion = obj.getValor()*((double) edad /5);
                break;
            }
            case "Vehiculo":{
                depreciacion = obj.getValor()*((double) edad /20);
                break;
            }
            default:{break;}
        }
        return depreciacion;

    }

    public void limpiar_pantalla(){
        this.set_modo_actual("Guardar");
        vista.limpiar_pnl_ingreso_txFields();
    }

    public void actionPerformed(ActionEvent e) {
            switch (e.getActionCommand()) {
                case "Guardar": {
                    try {
                        this.guardar_instrumento();
                    } catch (IOException | JDOMException ex) {
                        throw new RuntimeException(ex);
                    }
                    break;
                }
                case "Consultar": {
                    this.recuperar_activo();
                    break;
                }
                case "Limpiar": {
                    this.limpiar_pantalla();
                    break;
                }
                default:
            }
        }

    public String get_modo_actual(){
        return modo_actual;
    }

    public void set_modo_actual(String modo){
        this.modo_actual = modo;
    }

    //classes
    public class Window_Listener implements WindowListener {
        @Override
        public void windowClosing(WindowEvent e) {
            try {
                modelo.guardar_datos();
            } catch (IOException | JDOMException ex) {
                throw new RuntimeException(ex);
            }
        }
        @Override
        public void windowOpened(WindowEvent e) {}

        @Override
        public void windowClosed(WindowEvent e) {}

        @Override
        public void windowIconified(WindowEvent e) {}

        @Override
        public void windowDeiconified(WindowEvent e) {}

        @Override
        public void windowActivated(WindowEvent e) {}

        @Override
        public void windowDeactivated(WindowEvent e) {}
    }


}
