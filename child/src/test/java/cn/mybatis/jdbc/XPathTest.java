package cn.mybatis.jdbc;

import cn.mybatis.domain.QueryVo;
import cn.mybatis.domain.UserCustom;
import org.apache.ibatis.builder.xml.XMLMapperEntityResolver;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.SqlSource;
import org.apache.ibatis.parsing.XNode;
import org.apache.ibatis.parsing.XPathParser;
import org.apache.ibatis.scripting.xmltags.XMLScriptBuilder;
import org.apache.ibatis.session.Configuration;
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
import java.util.ArrayList;
import java.util.List;

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

    @Test
    public void testXPathParser(){
        InputStream in=ClassLoader.getSystemClassLoader().getResourceAsStream("mybatis-comfig.xml");
        XPathParser xPathParser=new XPathParser(in, true, null, new XMLMapperEntityResolver());

        XNode xNode=xPathParser.evalNode("/configuration");

        System.out.println(xNode.getChildren().get(0).getChildren().get(0).getChildren());
    }

    /**
     parse dynamic sql

     **** selective learning ****

     */
    @Test
    public void testParseSqlNode(){
        InputStream in=ClassLoader.getSystemClassLoader().getResourceAsStream("cn/mybatis/dao/UserMapper.xml");
        XPathParser pathParser=new XPathParser(in, true, null, new XMLMapperEntityResolver());
        XNode xNode=pathParser.evalNode("mapper/select");

        Configuration configuration=new Configuration();
        XMLScriptBuilder xmlScriptBuilder=new XMLScriptBuilder(configuration,xNode);

        SqlSource sqlSource=xmlScriptBuilder.parseScriptNode();
        UserCustom userCustom=new UserCustom();
        userCustom.setSex("1");
        userCustom.setUsername("陈小明");
        QueryVo queryVo=new QueryVo();
        queryVo.setUserCustom(userCustom);
        BoundSql boundSql=sqlSource.getBoundSql(queryVo);
        System.out.println(boundSql.getSql());
    }

    /**
     parse dynamic sql

     test  forEach tag

     <forEach collection="" open=""  close=""  items="" ></forEach>

     */
    @Test
    public void testParseForEachSqlNode() throws Exception{
        InputStream in=ClassLoader.getSystemClassLoader().getResourceAsStream("cn/mybatis/dao/UserMapper.xml");
        XPathParser pathParser=new XPathParser(in, true, null, new XMLMapperEntityResolver());
        XNode xNode=pathParser.evalNode("mapper/select");

        Configuration configuration=new Configuration();
        XMLScriptBuilder xmlScriptBuilder=new XMLScriptBuilder(configuration,xNode);

        SqlSource sqlSource=xmlScriptBuilder.parseScriptNode();
        UserCustom userCustom=new UserCustom();
        userCustom.setSex("1");
        userCustom.setUsername("陈小明");
        QueryVo queryVo=new QueryVo();
        queryVo.setUserCustom(userCustom);

        List<Integer> ids=new ArrayList<Integer>();
        ids.add(16);
        ids.add(25);
        queryVo.setIds(ids);

        BoundSql boundSql=sqlSource.getBoundSql(queryVo);
        System.out.println(boundSql.getSql());
    }
}
