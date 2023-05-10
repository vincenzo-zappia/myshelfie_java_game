package it.polimi.ingsw.util;

import it.polimi.ingsw.entities.*;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.*;

public class ToolXML {

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
        File file = new File("");
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

    //endregion

}
