package Model;


import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.output.Format;
import org.jdom2.output.XMLOutputter;
import org.jdom2.input.SAXBuilder;

import javax.swing.*;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.util.List;

public class Model {

    private Document doc;
    private Activo current;

    private List_Activos lista;
    private static final String FILENAME = "c:\\Users\\Familia Leiva Salas\\IdeaProjects\\Examen_Practica\\src\\data.xml";
    public Model() throws IOException, JDOMException {
        this.lista = new List_Activos();
        this.init_xml_file();
    }

    public void save(Activo obj) throws IOException, JDOMException {
        current = seleccionar_instrumento_codigo(obj.getCodigo());
        if (current == null) {
            lista.agregar(obj);
            System.out.printf(obj.toString());
            this.write_activo(obj, System.out);

        }
        else actualizar_activo(obj, System.out);
    }

    public void init_xml_file() throws IOException, JDOMException {
            SAXBuilder sax = new SAXBuilder();
            try {
                doc = sax.build(new File(FILENAME));
            } catch (JDOMException e) {
                throw new RuntimeException(e);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            doc.setRootElement(new Element("Activos"));
        }


        public Activo seleccionar_instrumento_codigo(String serie){
            return lista.obtener(serie);
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

    public void actualizar_activo(Activo obj, OutputStream output) throws IOException {
        Element rootNode = doc.getRootElement();

        List<Element> lista_activos = rootNode.getChildren("Activo");

        for (Element element : lista_activos){
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
}
