package it.polimi.ingsw;

import java.io.File;
import java.io.*;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import it.polimi.ingsw.entities.*;
import it.polimi.ingsw.server.model.entities.*;
import org.w3c.dom.*;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

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
    private static Element getRootDocElement(String xml){
        InputSource inputSource = new InputSource(new StringReader(xml));

        Document document;
        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            document = builder.parse(inputSource);
        } catch (ParserConfigurationException | IOException | SAXException e) {
            throw new RuntimeException(e);
        }

        return document.getDocumentElement();
    }
    private static Document createDoc() throws ParserConfigurationException {
        // Crea un oggetto DocumentBuilder per creare il documento XML
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();

        // Crea un oggetto Document vuoto
        return builder.newDocument();
    }
    //endregion

    //region PLAYER
    public static Player xmlToPlayer(String xml) {

        Element root = getRootDocElement(xml);

        //Return user
        String username = root.getElementsByTagName("Username").item(0).getTextContent();
        int gameId = Integer.parseInt(root.getElementsByTagName("GameId").item(0).getTextContent());
        return new Player(username, gameId);
    }
    public static String playerToXml(Player p) {
        String xml="";

        Document doc;
        try {
            doc = createDoc();
        } catch (ParserConfigurationException e) {
            return null;
        }

//        Crea l'elemento radice del documento
        Element root = doc.createElement("person");
        doc.appendChild(root);

//        Crea gli elementi figlio


        return xml;
    }
    //endregion

    //region REMOVE BOARD CARDS
    //endregion


    //region BOARD
    public static BoardCell[][] xmlToBoard(String xml) {
        Element root = getRootDocElement(xml);
        NodeList nodeList = root.getElementsByTagName("Card");

        BoardCell[][] matrix = new BoardCell[9][9];
        for (int i=0; i<nodeList.getLength(); i++){
            Element e = (Element) nodeList.item(i);
            int row = Integer.parseInt(e.getAttribute("row"));
            int column = Integer.parseInt(e.getAttribute("column"));
            String[] data = e.getTextContent().split(";");

            matrix[row][column].setCellActive();
            //matrix[row][column].setCard(new Card(data[0], Integer.parseInt(data[1]))); TODO: risolvere questo problemino
        }
        return matrix;
    }
    public static String boardToXml(BoardCell[][] matrix) {
        String xml = "";
        return xml;
    }
    //endregion

    //region TEST
    //TODO: rename to "xmlToPosition()" (?)
    public static int[][] posizioniByXml(String P) {

        String xml;
        try {
            BufferedReader breader = new BufferedReader(new FileReader("XML/player.xml"));
            xml = breader.readLine();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        Element root = getRootDocElement(xml);

        NodeList nodeList = root.getElementsByTagName(P);
        int row = nodeList.getLength();
        int[][] pos = new int[row][2];

        for(int i=0; i<row; i++){
            Element e = (Element) nodeList.item(i);
            pos[i][0] = Integer.parseInt(e.getAttribute("row"));
            pos[i][1] = Integer.parseInt(e.getAttribute("column"));
        }

        return pos;
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

                TileType type = TileType.valueOf(cells.getElementsByTagName("Card").item(i).getTextContent());

                specialCell[i] = new SpecialCell(row, column, type);
            }
        }

        return specialCell;

    }
    //endregion

    public static Card xmlToCard(String xml) {
        int id;
        TileType type = TileType.BOOKS;
        String imgName;

        Element root = getRootDocElement(xml);

        id = Integer.parseInt(root.getElementsByTagName("ID").item(0).getTextContent());
        //type = Integer.parseInt(root.getElementsByTagName("Color").item(0).getTextContent()); TODO: risolvere questo problemino
        imgName = root.getElementsByTagName("ImgName").item(0).getTextContent();

        return new Card(imgName, type);
    }

}
