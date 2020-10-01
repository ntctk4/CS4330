/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ntctk4saxparser;

import java.util.ArrayList;
import java.util.HashMap;

/**
 *
 * @author ntctk4
 */
//just a class that stores all the node values for the xml parsing
//nodes can store other nodes, like a tree structure
public class XMLNode {
    public String name = "";
    public String content = "";
    public HashMap<String, ArrayList<XMLNode>> properties = null;
    public HashMap<String, String> attributes = null;
    
}
