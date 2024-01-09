package Controller;

import View.View;

import Model.Model;

import Model.Activo;
import org.jdom2.JDOMException;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

public class Controller implements ActionListener {


    View vista;
    Model modelo;

    public Controller() throws IOException, JDOMException {
        this.vista = new View(this);
        try {
            this.modelo = new Model();
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (JDOMException e) {
            throw new RuntimeException(e);
        }
        this.init_btns();
    }

    public void init_btns(){
        vista.getBtn_consultar().addActionListener(this);
        vista.getBtn_guardar().addActionListener(this);
        vista.getBtn_limpiar().addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        switch (e.getActionCommand()) {
            case "Guardar": {
                try {
                    guardar_instrumento();
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                } catch (JDOMException ex) {
                    throw new RuntimeException(ex);
                }
                break;
            }
            case "Borrar": {
                //eliminar_elemento(txF_Serie.getText());
                break;
            }
            case "Limpiar": {
                //limpiar_pnl_ingreso_txFields();
                break;
            }
            default:
        }
    }

    public void guardar_instrumento() throws IOException, JDOMException {
        //btn_borrar.setEnabled(false);
       // if(validar_excepciones(Integer.parseInt(txF_Maximo.getText()), Integer.parseInt(txF_Minimo.getText()), txF_Serie.getText())) {
            Activo activo = new Activo(vista.getTxf_codigo().getText(), vista.getTxf_activo().getText(), vista.getTxf_categoria().getText(),
                    Integer.valueOf(vista.getTxf_fabricacion().getText()), Double.valueOf(vista.getTxf_valor().getText()));
            modelo.save(activo);
            //limpiar_pnl_ingreso_txFields();
       // } else limpiar_pnl_ingreso_txFields();
    }
}
