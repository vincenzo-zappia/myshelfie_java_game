package it.polimi.ingsw.util;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.*;
import java.util.ArrayList;

public class ToolXML {

    //region CONSTANT
    private static final ClassLoader classLoader = ToolXML.class.getClassLoader();
    private static final String basePath = classLoader.getResource("").getPath();
    private static final String commandListPath = basePath + "\\config\\CommandList.xml";
    private static final String privateGoalPath = basePath + "\\config\\PrivateGoals.xml";
    //endregion

    //region PRIVATE METHODS
    private static Element getRootDocElement(File file){
        Document doc;
        try {
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            doc = dBuilder.parse(file);
            doc.getDocumentElement().normalize();
        } catch (ParserConfigurationException | IOException | SAXException e) {
            throw new RuntimeException(e);
        }

        return doc.getDocumentElement();
    }

    private static Document createDoc() throws ParserConfigurationException {
        // Crea un oggetto DocumentBuilder per creare il documento XML
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();

        // Crea un oggetto Document vuoto
        return builder.newDocument();
    }
    //endregion

    //region PRIVATE GOAL
    public static SpecialCell[] getSpecialCells(int id){
        SpecialCell[] specialCell = new SpecialCell[6];
        File file = new File(privateGoalPath);
        Element root = getRootDocElement(file);
        NodeList goal = root.getElementsByTagName("Goal");

        Node cellsNode = goal.item(id);
        if(cellsNode.getNodeType() == Node.ELEMENT_NODE){
            Element cells = (Element) cellsNode;
            for(int i=0; i<6; i++){

                int row = Integer.parseInt(cells.getAttribute("row")),
                column = Integer.parseInt(cells.getAttribute("column"));

                CardType type = CardType.valueOf(cells.getElementsByTagName("Card").item(i).getTextContent());

                specialCell[i] = new SpecialCell(row, column, type);
            }
        }

        return specialCell;

    }
    //endregion

    //region COMMAND LIST
    public static ArrayList<String> getCommandList(){
        ArrayList<String> result = new ArrayList<>();
        File file = new File(commandListPath);
        Element root = getRootDocElement(file);
        NodeList commands = root.getElementsByTagName("command");

        for(int i=0; i< commands.getLength(); i++){
            Element element = (Element) commands.item(i);
            result.add(element.getTextContent());
        }

        return result;
    }
    //endregion

}
