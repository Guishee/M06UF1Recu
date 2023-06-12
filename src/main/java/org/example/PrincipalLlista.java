package org.example;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.w3c.dom.Element;
import org.xml.sax.SAXException;


import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.*;
import java.util.ArrayList;


public class PrincipalLlista {
    public static void main(String[] args) throws IOException {
        int num_art=1;
        //Array con los datos que leemos en la funcion + generarXML + serializar
        ArrayList<ArticlesCompra> listaCompra = leerItems();
        generadorXML(listaCompra);
        serializarLista(listaCompra);
        System.out.println("");


        System.out.println("****************//Apartado extra: Deserializacion//***************");
        // Deserializacion de la lista de compra con la funcion
        ArrayList<ArticlesCompra> listaDeserializada = deserializarLista();

        // sysOut de la lista deserializda
        System.out.println("Lista de la compra deserializada:");
        for (ArticlesCompra article : listaDeserializada) {
            System.out.println("");
            System.out.println("Articulo nº"+num_art);
            num_art++;
            System.out.println("Descripcion:"+article.getDescripcion());
            System.out.println("Cantidad:"+article.getCantidad());
            System.out.println("Unidad:"+article.getUnidad());
            System.out.println("Seccion:"+article.getSeccion());
            System.out.println("Precio:"+article.getPrecio());
            System.out.println("__________________________________________________");
        }

        System.out.println("");
        num_art=1;
        System.out.println("****************//Apartado extra: LecturaXML//***************");

        // leer el archivoXML con la funcion
        ArrayList<ArticlesCompra> listaXML = leerXML();

        // sysOut de la lista leida
        System.out.println("Lista de compra leída desde XML:");
        for (ArticlesCompra article : listaXML) {
            System.out.println("");
            System.out.println("Articulo nº"+num_art);
            num_art++;
            System.out.println("Descripcion:"+article.getDescripcion());
            System.out.println("Cantidad:"+article.getCantidad());
            System.out.println("Unidad:"+article.getUnidad());
            System.out.println("Seccion:"+article.getSeccion());
            System.out.println("Precio:"+article.getPrecio());
            System.out.println("__________________________________________________");
        }



    }


    //funcion para pedir por consola los datos de la lista
    private static ArrayList<ArticlesCompra> leerItems() throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        ArrayList<ArticlesCompra> listaCompra = new ArrayList<>();

        while (true) {
            System.out.print("Descripcion del articulo (Escribe 'salir' para terminar): ");
            String descripcion = reader.readLine();
            if (descripcion.equalsIgnoreCase("salir")) {
                break;
            }

            System.out.print("Cantidad(Numero de articulos o peso): ");
            double cantidad = Double.parseDouble(reader.readLine());

            System.out.print("Unidad(ej:Kg, g, Litros, unidades, etc...): ");
            String unidad = reader.readLine();

            System.out.print("Seccion(ej:Alimentacion, Ropa, Electrodomesticos, etc...): ");
            String seccion = reader.readLine();

            System.out.print("Precio(en 'double' del articulo): ");
            double precio = Double.parseDouble(reader.readLine());

            ArticlesCompra article = new ArticlesCompra(descripcion, cantidad, unidad, seccion, precio);
            listaCompra.add(article);
        }

        return listaCompra;
    }

    //funcion para generar XML
    private static void generadorXML(ArrayList<ArticlesCompra> listaCompra) {
        try {
            File file = new File("lista_compra.xml");
            FileWriter writer = new FileWriter(file);

            writer.write("<llistacompra>\n");

            for (ArticlesCompra article : listaCompra) {
                writer.write("  <article>\n");
                writer.write("        <descripcio>" + article.getDescripcion() + "</descripcio>\n");
                writer.write("        <quantitat>" + article.getCantidad() + "</quantitat>\n");
                writer.write("        <unitat>" + article.getUnidad() + "</unitat>\n");
                writer.write("        <seccio>" + article.getSeccion() + "</seccio>\n");
                writer.write("        <precio>" + article.getPrecio() + "</precio>\n");
                writer.write("  </article>\n");
            }

            writer.write("</llistacompra>");
            writer.close();

            System.out.println("Archivo XML generado correctamente");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    //Funcion para leer XML
    private static ArrayList<ArticlesCompra> leerXML() {
        ArrayList<ArticlesCompra> listaCompra = new ArrayList<>();

        try {
            File file = new File("lista_compra.xml");
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document document = builder.parse(file);

            NodeList nodeList = document.getElementsByTagName("article");

            for (int i = 0; i < nodeList.getLength(); i++) {
                Node node = nodeList.item(i);
                if (node.getNodeType() == Node.ELEMENT_NODE) {
                    Element element = (Element) node;

                    String descripcion = element.getElementsByTagName("descripcio").item(0).getTextContent();
                    double cantidad = Double.parseDouble(element.getElementsByTagName("quantitat").item(0).getTextContent());
                    String unidad = element.getElementsByTagName("unitat").item(0).getTextContent();
                    String seccion = element.getElementsByTagName("seccio").item(0).getTextContent();
                    double precio = Double.parseDouble(element.getElementsByTagName("precio").item(0).getTextContent());

                    ArticlesCompra article = new ArticlesCompra(descripcion, cantidad, unidad, seccion, precio);
                    listaCompra.add(article);
                }
            }

            System.out.println("Archivo XML leído correctamente");
        } catch (ParserConfigurationException | SAXException | IOException e) {
            e.printStackTrace();
        }

        return listaCompra;
    }


    //Funcion para serializar
    private static void serializarLista(ArrayList<ArticlesCompra> listaCompra) {
        try {
            FileOutputStream fileOut = new FileOutputStream("lista_compra.ser");
            ObjectOutputStream objectOut = new ObjectOutputStream(fileOut);
            objectOut.writeObject(listaCompra);
            objectOut.close();

            System.out.println("Archivo serializado generado correctamente");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //Funcion para deserializar
    private static ArrayList<ArticlesCompra> deserializarLista() {
        ArrayList<ArticlesCompra> listaCompra = new ArrayList<>();

        try {
            FileInputStream fileIn = new FileInputStream("lista_compra.ser");
            ObjectInputStream objectIn = new ObjectInputStream(fileIn);
            listaCompra = (ArrayList<ArticlesCompra>) objectIn.readObject();
            objectIn.close();

            System.out.println("Archivo deserializado correctamente");
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }

        return listaCompra;
    }



}
