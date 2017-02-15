package cn.mybatis.jdbc;

import org.apache.ibatis.builder.xml.XMLMapperEntityResolver;
import org.junit.Test;
import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.xml.sax.ErrorHandler;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathFactory;
import java.io.InputStream;

/**
 * Created by free on 17-2-15.
 */
public class XPathTest {

    private XPath xpath;

    @Test
    public void testParse() throws Exception{
        // jdk substratum  class
        XPathFactory pathFactory = XPathFactory.newInstance();
        this.xpath = pathFactory.newXPath();

        // create  document
        InputStream in=ClassLoader.getSystemClassLoader().getResourceAsStream("mybatis-comfig.xml");
        InputSource inputSource=new InputSource(in);
        Document document = null;
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        factory.setValidating(true);

        factory.setNamespaceAware(false);
        factory.setIgnoringComments(true);
        factory.setIgnoringElementContentWhitespace(false);
        factory.setCoalescing(false);
        factory.setExpandEntityReferences(true);

        DocumentBuilder builder = factory.newDocumentBuilder();
        builder.setEntityResolver(new XMLMapperEntityResolver());
        builder.setErrorHandler(new ErrorHandler() {
            public void error(SAXParseException exception) throws SAXException {
                throw exception;
            }

            public void fatalError(SAXParseException exception) throws SAXException {
                throw exception;
            }

            public void warning(SAXParseException exception) throws SAXException {
            }
        });
        document=builder.parse(inputSource);
        Node node= (Node) xpath.evaluate("/configuration" , document , XPathConstants.NODE);


        System.out.println(node.getNodeName());
        NamedNodeMap attributeNodes = node.getAttributes();
        System.out.println(attributeNodes.getLength());
        System.out.println(node.getNodeType());
        in.close();
    }
}
