package edu.umd.peripatos;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;

import java.io.IOException;
import java.io.StringReader;

import org.jdom.Document;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.Namespace;
import org.jdom.input.SAXBuilder;
import org.junit.Test;

import edu.umd.peripatos.xmlUtils.XMLResponseFactory;

public class TestXMLBuilder {
	
	@Test
	public void testXMLBuilder(){
		String xml ="<?xml version=\"1.0\" ?><fetch_assignments xmlns=\"http://www.cs.umd.edu/class/fall2011/cmsc436/peripatos\">" +
				"<user_credentials><username>student</username><password>password</password></user_credentials></fetch_assignments>";
		
		StringReader reader = new StringReader(xml);
		SAXBuilder builder = new SAXBuilder();
		
		Document doc;
		Namespace ns = Namespace.getNamespace(XMLResponseFactory.PERIPATOS_NAMESPACE);
		try {
			doc = builder.build(reader);
			assertNotNull(doc);
			
			Element root = doc.getRootElement();
			
			assertNotNull(root);
			
			Element userCred = root.getChild("user_credentials", ns);
			
			assertNotNull(userCred);
			
			Element username = userCred.getChild("username", ns);
			
			assertNotNull(username);
			
			assertNotNull(username.getText());
			
		} catch (JDOMException e) {
			e.printStackTrace();
			fail();
		} catch (IOException e) {
			e.printStackTrace();
			fail();
		}
		
	}
}
