package Model;


import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.output.Format;
import org.jdom2.output.XMLOutputter;
import org.jdom2.input.SAXBuilder;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.xml.XMLConstants;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.util.List;

public class Model {
    private Document doc;
    SAXBuilder sax;
    File file;
    private Activo current;
    private List_Activos lista;
    private static final String FILENAME = "src\\data.xml";
    public Model() throws IOException, JDOMException {
        this.lista = new List_Activos();
        this.init_xml_file();
        this.cargar_datos();
        //doc.setRootElement(new Element("Activos"));
    }

    public boolean activo_existente(String serie){
        Activo activo = seleccionar_activo_codigo(serie);
        return activo != null;
    }

    public boolean save(Activo obj) throws IOException, JDOMException {
        current = seleccionar_activo_codigo(obj.getCodigo());
        //System.out.println("Ingreso save model");
        if (!this.activo_existente(obj.getCodigo())) {
            lista.agregar(obj);
            //System.out.printf(obj.toString());
        }
        else return false; //actualizar_activo(obj);
        return true;
    }



    public void init_xml_file() throws IOException, JDOMException {
            this.sax = new SAXBuilder();
            file = new File(FILENAME);
            try {
                sax.setProperty(XMLConstants.ACCESS_EXTERNAL_DTD, "");
                sax.setProperty(XMLConstants.ACCESS_EXTERNAL_SCHEMA, "");

                if (file.exists() && file.length() != 0){
                doc = sax.build(file);
                }
                else {
                    doc = new Document(new Element("Activos"));
                }

            } catch (JDOMException | IOException e) {
                throw new RuntimeException(e);
            }
        // doc.setRootElement(new Element("Activos"));
        }
        public Activo seleccionar_activo_codigo(String serie){
            return lista.obtener(serie);
        }

        public void actualizar_activo(Activo obj){
            current.setActivo(obj.getActivo());
            current.setCategoria(obj.getActivo());
            current.setFabricacion(obj.getFabricacion());
            current.setValor(obj.getValor());
        }

    public void write_activo(Activo obj, OutputStream output) throws JDOMException, IOException{


        Element activo = new Element("Activo");
        //doc.getRootElement().addContent(activo);

        Element codigo = new Element("Codigo");
        codigo.setText(obj.getCodigo());
        activo.addContent(codigo);

        Element nombre = new Element("Nombre");
        nombre.setText(obj.getActivo());
        activo.addContent(nombre);

        Element fabricacion = new Element("Fabricacion");
        fabricacion.setText(String.valueOf(obj.getFabricacion()));
        activo.addContent(fabricacion);

        Element valor = new Element("Valor");
        valor.setText(String.valueOf(obj.getValor()));
        activo.addContent(valor);

        Element categoria = new Element("Categoria");
        categoria.setText(obj.getCategoria());
        activo.addContent(categoria);

        doc.getRootElement().addContent(activo);

        XMLOutputter xmlOutputter = new XMLOutputter();

        xmlOutputter.setFormat(Format.getPrettyFormat());
        xmlOutputter.output(doc, output);

        try(FileWriter fileWriter =
                    new FileWriter(FILENAME)){
            xmlOutputter.output(doc, fileWriter);
        }


    }

    public void actualizar_activo_xml(Activo obj, OutputStream output) throws IOException {
        Element rootNode = doc.getRootElement();

        List<Element> lista_activos = rootNode.getChildren("Activo");

        for (Element element : lista_activos){
            System.out.println(lista_activos.size());
            String id = element.getChild("Codigo").getText();
            System.out.println("Este es el id en analisis "+ id);
            if(id.equals(obj.getCodigo())){
                System.out.println("Este es el id repetido" + obj.getCodigo());
                System.out.println("Valor de nombre a cambiat "+ obj.getActivo());
                element.getChild("Nombre").setText(obj.getActivo());
                element.getChild("Fabricacion").setText(String.valueOf(obj.getFabricacion()));
                element.getChild("Valor").setText(String.valueOf(obj.getValor()));
                element.getChild("Categoria").setText(obj.getCategoria());
            }
        }
        XMLOutputter xmlOutputter = new XMLOutputter();

        xmlOutputter.setFormat(Format.getPrettyFormat());
        xmlOutputter.output(doc, output);

        try(FileWriter fileWriter =
                    new FileWriter(FILENAME)){
            xmlOutputter.output(doc, fileWriter);
        }
    }
    public void cargar_datos() throws IOException, JDOMException {
        System.out.println("Ingreso al sector carga de datos");
        try {

            //Element rootNode = doc.getRootElement();

            Element rootNode = doc.getRootElement();
            List<Element> lista_activos = rootNode.getChildren();

            System.out.println(rootNode.getName() + " padre\n");

            System.out.println(lista_activos.size());

            for (Element element : lista_activos) {
                System.out.println(element.getChild("Codigo").getText());
                String codigo = element.getChild("Codigo").getText();
                String nombre = element.getChild("Nombre").getText();
                int fabric = Integer.valueOf(element.getChild("Fabricacion").getText());
                double valor = Double.valueOf(element.getChild("Valor").getText());
                String categ = element.getChild("Categoria").getText();
                System.out.println("Objeto Recuperado" + codigo + nombre + fabric + valor + categ);


                this.save(new Activo(codigo, nombre, categ, fabric, valor));
            }
            this.limpiar_xml();

        }catch (IOException | JDOMException e) {
            System.out.println("Excepcion lanzada");
            throw new RuntimeException(e);
        }

    }
    public void limpiar_xml(){
        Element rootNode = doc.getRootElement();
        rootNode.removeChildren("Activo");
    }

    public void guardar_datos() throws IOException, JDOMException {
        System.out.println("Ingreso a guardado de datos");

        for (int i = 0; i < lista.getList().size(); i++){
            System.out.printf("Guardando obj: " + lista.getList().get(i).toString());
            this.write_activo(lista.getList().get(i), System.out);
        }

    }
}
