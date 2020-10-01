/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ntctk4saxparser;


import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

/**
 *
 * @author ntctk4
 */
public class XmlSAXParser {
    
    
    static XMLNode root = new XMLNode();
    static ArrayList<XMLNode> stack = new ArrayList<XMLNode>();
    static XMLNode currentNode = null;
    
    //parses the xml into a DOM
    public static XMLNode parseXML(File xmlFile)
    {
        
//        String currentElementName;
//        String currentElementData = "";
        
        
        try 
        {
            //create the sax parser
            SAXParserFactory factory = SAXParserFactory.newInstance();
            SAXParser saxParser = factory.newSAXParser();
            
            
            DefaultHandler handler;
            handler = new DefaultHandler() {
                
                //this method is called everytime there is a start of an element
                @Override
                public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
                    
                    //start collecting the node data
                    XMLNode node = new XMLNode();
                    node.name = qName;

                    //if the node has any attributes, store them in the node
                    if(attributes.getLength() > 0)
                    {
                        node.attributes = new HashMap<>();
                    }
                    for(int i = 0; i < attributes.getLength(); i++)
                    {
                        String attributeName = attributes.getLocalName(i);
                        String value = attributes.getValue(i);
                        node.attributes.put(attributeName, value);
                    }

                    //now add the node to the stack
                    stack.add(node);
                    //if there is no current node, make this node the current node
                    if(currentNode != null)
                    {
                        //if this node has any children
                        if(currentNode.properties != null)
                        {
                            //if child not repeated, add new node to arraylist inside hashmap
                            if(currentNode.properties.get(qName) != null)
                            {
                                currentNode.properties.get(qName).add(node);
                            }
                            //else create a new spot in the hashmap to store the node
                            else
                            {
                                currentNode.properties.put(qName, new ArrayList<XMLNode>());
                                currentNode.properties.get(qName).add(node);
                            }
                        }
                        //if currentNode has no children, create a new hashmap and add the node to it
                        else
                        {
                            currentNode.properties = new HashMap<>();
                            currentNode.properties.put(qName, new ArrayList<XMLNode>());
                            currentNode.properties.get(qName).add(node);
                        }
                    }
                    //make the newly created node the current node    
                    currentNode = node;
                }
                
                //this method is called when there is a tag end 
                @Override
                public void endElement(String uri, String localName, String qName) throws SAXException {
                    
                    //pop the last node off the stack
                    XMLNode poppedNode = stack.remove(stack.size()-1);
                    poppedNode.content = poppedNode.content.trim();
                    //if the stackis empty the popped node is the root
                    if(stack.isEmpty())
                    {
                        root = poppedNode;
                        currentNode = null;
                    }
                    //otherwise the current node is the last node on the stack
                    else
                    {
                        currentNode = stack.get(stack.size()-1);
                    }
                }
                
                //called for the values inside the xml tags ex: <tag>value</tag>
                @Override
                public void characters(char ch[], int start, int length) throws SAXException {
                    //add the characters in the content value of the node
                    currentNode.content += new String(ch, start, length);
                }
                
                
                
            };
            
            //tell the sax parser to parse
            saxParser.parse(xmlFile.getAbsoluteFile(), handler);
            
           // return root;
            
        }
        catch(Exception ex)
        {
            System.out.println(ex);
        }
        
        //return the root which is the newly created DOM
        return root;
    }
    
}
