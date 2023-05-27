package it.polimi.ingsw.mechanics;

import it.polimi.ingsw.Main;
import it.polimi.ingsw.entities.goals.PrivateGoal;
import it.polimi.ingsw.entities.util.CardType;
import it.polimi.ingsw.entities.util.SpatialTile;
import org.w3c.dom.*;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.*;
import java.util.ArrayList;

public class ToolXML {

    //region CONSTANT
    private static final String basePath = Main.getResourcePath();
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
    //endregion

    //region PRIVATE GOAL
    public static PrivateGoal getPrivateGoal(int id){

        SpatialTile[] spatialTile = new SpatialTile[6];
        String fileName = "";

        File file = new File(privateGoalPath);
        Element root = getRootDocElement(file);

        NodeList goal = root.getElementsByTagName("Goal");
        Node cellsNode = goal.item(id);
        if(cellsNode.getNodeType() == Node.ELEMENT_NODE){
            Element cells = (Element) cellsNode;
            fileName = cells.getAttribute("filename");

            for(int i=0; i<6; i++){
                Element cell = (Element) cells.getElementsByTagName("Card").item(i);

                int row = Integer.parseInt(cell.getAttribute("row")),
                column = Integer.parseInt(cell.getAttribute("column"));

                CardType type = CardType.valueOf(cell.getTextContent());

                spatialTile[i] = new SpatialTile(row, column, type);
            }
        }

        return new PrivateGoal(spatialTile, fileName);

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
