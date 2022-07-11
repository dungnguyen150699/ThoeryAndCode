/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.viettel.mtracking.utils;

//import com.sun.org.apache.xerces.internal.parsers.DOMParser;
import java.io.IOException;
import java.io.StringReader;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

/**
 *
 * @author TienPH2
 */
public class XmlUtil {

    public static String getTagValue(String tag, Element element) {
        NodeList nodeList = element.getElementsByTagName(tag).item(0).getChildNodes();
        Node node = (Node) nodeList.item(0);
        return node.getNodeValue();
    }
//
//    /**
//     * Get all node with tagName
//     * @param input
//     * @return
//     * @throws SAXException
//     * @throws IOException
//     */
//    public static NodeList getNodeListByTag(String input, String tagName) throws SAXException, IOException {
//        DOMParser parser = new DOMParser();
//        parser.parse(new InputSource(new java.io.StringReader(input)));
//        Document doc = parser.getDocument();
//        NodeList nodeList = doc.getElementsByTagName(tagName);
//        return nodeList;
//    }

    public static Document loadXMLString(String response) throws  SAXException, ParserConfigurationException, IOException
    {
        DocumentBuilderFactory dbf =DocumentBuilderFactory.newInstance();
        DocumentBuilder db = dbf.newDocumentBuilder();
        InputSource is = new InputSource(new StringReader(response));

        return db.parse(is);
    }

    public static NodeList getNodeListByTag(String response, String tagName) throws ParserConfigurationException,SAXException, IOException {
        Document xmlDoc = loadXMLString(response);
        NodeList nodeList = xmlDoc.getElementsByTagName(tagName);
        return nodeList;
    }

    public static void main(String[] args) {
        try{
            String response = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
                    "<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns=\"http://soap.sforce.com/2006/04/metadata\">\n" +
                    "   <soapenv:Body>\n" +
                    "      <listMetadataResponse>\n" +
                    "         <result>\n" +
                    "            <createdById>1</createdById>\n" +
                    "            <createdByName>Hariprasath Thanarajah</createdByName>\n" +
                    "            <ExpDate>1970-01-01T00:00:00.000Z</ExpDate>\n" +
                    "            <fileName>objects/EmailMessage.object</fileName>\n" +
                    "            <fullName>EmailMessage</fullName>\n" +
                    "            <id />\n" +
                    "            <lastModifiedById>00528000001m5RRAAY</lastModifiedById>\n" +
                    "            <lastModifiedByName>Hariprasath Thanarajah</lastModifiedByName>\n" +
                    "            <lastModifiedDate>1970-01-01T00:00:00.000Z</lastModifiedDate>\n" +
                    "            <namespacePrefix />\n" +
                    "            <type>CustomObject</type>\n" +
                    "         </result>\n" +
                    "      </listMetadataResponse>\n" +
                    "   </soapenv:Body>\n" +
                    "</soapenv:Envelope>";

            NodeList nodeList = getNodeListByTag(response,"result");

            for (int i = 0; i < nodeList.getLength(); i++) {
                Element element = (Element) nodeList.item(i);
                int baltypeId = Integer.valueOf(XmlUtil.getTagValue("createdById", element));
                // Tai khoan chinh
                if (baltypeId == 1) {
                    String expDateStr = XmlUtil.getTagValue("ExpDate", element);
                    System.out.println(expDateStr);
                    break;
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
