import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StringWriter;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

public class App {
    public static void main(String[] args) throws TransformerException, ParserConfigurationException, IOException {
        
        /*
        Creates an array which stores the values used in the xml file, it passes the values as arguments to the method in Alien.java as
        the x, y and Name values.
        */
        Alien[] aliens = {new Alien(0, 0, "Aiden"), new Alien(0, 10, "John")};

        //Assigns the name of the root node
        String root = "Aliens";

        //Methods used to create the file and creates the root node of the file
        DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
        Document document = documentBuilder.newDocument();
        Element rootElement = document.createElement(root);
        document.appendChild(rootElement);

        //For loop creates multiple "instances" of the alienElement node ("Alien") and assigns the x, y and alienName values
        //One "Alien" node is created for each value included in the aliens array that was created at the beginning
        for(int i = 0; i < aliens.length; i++){
            //Creates the Alien node
            Element alienElement = document.createElement("Alien");
            //Creates the x node
            Element x = document.createElement("x");
            //Assigns the x variable the x value listed in the aliens array/Alien Method
            x.appendChild(document.createTextNode(Integer.toString(aliens[i].x)));
            //Nests the node as a child of alienElement ("Alien")
            alienElement.appendChild(x);

            //Same proccess is repeated for the y value....
            Element y = document.createElement("y");            
            y.appendChild(document.createTextNode(Integer.toString(aliens[i].y)));
            alienElement.appendChild(y);

            //....And the alienName value,
            Element alienName = document.createElement("alienName");
            /*
            Only difference is that in this line the value given to alienName is a remains a string since the value within the aliens array is 
            already a string
            */
            alienName.appendChild(document.createTextNode(aliens[i].alienName));
            alienElement.appendChild(alienName);
            //Nests the alienElement node directly underneath the root node
            rootElement.appendChild(alienElement);

        }

        //The transformerFactory and Transformer libraries are xml classes that help format the naturally messy structure of the code
        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        Transformer transformer = transformerFactory.newTransformer();
        DOMSource source = new DOMSource(document);

        //This code uses the transformer objects to implement the formatting/indenting
        StreamResult result = new StreamResult(new StringWriter());
        //This "yes" value is essentially a boolean that will decide whether to indent the code or not
        transformer.setOutputProperty(OutputKeys.INDENT, "yes");
        //Number value ("5") determines the amount by which it will indent
        transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "5");
        transformer.transform(source, result);

        //Creates the object for the fileOutputStream, which is how we write to the file, and for the file that we will be creating
        FileOutputStream fileOutputStream = null;
        File file;

        try{
        //Creates the aliens text file
        file = new File("aliens.txt");
        fileOutputStream = new FileOutputStream(file);

        if(!file.exists()){
            file.createNewFile();
        }

        //Creates the output variable and 
        String xmlString = result.getWriter().toString();
        //Prints all info that was in the file
        System.out.println(xmlString);
        //Creats an array of bytes to store the data that will be written to the file
        byte[] contentsInBytes = xmlString.getBytes();

        //Writes the byten values to the file using the fileOutputStream
        fileOutputStream.write(contentsInBytes);
        fileOutputStream.flush();
        //Closes the writer
        fileOutputStream.close();

        //Shows user file reading is finished
        System.out.println("Done");

      //Catch statement  
    } catch (IOException e){
        e.printStackTrace();
        //Finally statement that will always run after the try statement
    } finally {
        try {
            if (fileOutputStream != null){
                fileOutputStream.close();
            } 
            }catch(IOException e){
                e.printStackTrace();
        }
        
    }
            
        
        
    }
}