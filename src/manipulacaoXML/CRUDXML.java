package manipulacaoXML;

import java.io.File;
import java.io.IOException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class CRUDXML {

	public static void main(String args[]) {

		try {
			String enderecoDoXMLDeEntrada = "src/exemplo.xml";
			String enderecoDoXMLDeSaida = "src/exemplo2.xml";
			DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
			Document doc = docBuilder.parse(enderecoDoXMLDeEntrada);

			// Pega da raiz do Elemento
			Node company = doc.getFirstChild();

			// Se o elemento tiver caracter especial ou espaços
			// e melhor vc usar esse metodo
			// getElementsByTagName() retorna direto.
			// Node staff = company.getFirstChild();

			// pega a tag do attribute, se precisar navegar pelo XML eh aqui vc procura a
			// tag
			Node staff = doc.getElementsByTagName("atributo").item(0);

			// update atributo attribute
			NamedNodeMap attr = staff.getAttributes();
			Node nodeAttr = attr.getNamedItem("id");
			nodeAttr.setTextContent("2");

			// acrescentar um elemento
			Element idade = doc.createElement("idade");
			idade.appendChild(doc.createTextNode("30"));
			staff.appendChild(idade);

			// ler todo xml
			NodeList list = staff.getChildNodes();

			for (int i = 0; i < list.getLength(); i++) {

				Node node = list.item(i);

				// alterar o valor de um elemento
				if ("altura".equals(node.getNodeName())) {
					node.setTextContent("2.00");
				}

				// remove celular
				if ("celular".equals(node.getNodeName())) {
					staff.removeChild(node);
				}

			}

			// reescreve o XML no local indicado
			TransformerFactory transformerFactory = TransformerFactory.newInstance();
			Transformer transformer = transformerFactory.newTransformer();
			DOMSource source = new DOMSource(doc);
			StreamResult result = new StreamResult(new File(enderecoDoXMLDeSaida));
			transformer.transform(source, result);

			System.out.println("Done");

		} catch (ParserConfigurationException pce) {
			pce.printStackTrace();
		} catch (TransformerException tfe) {
			tfe.printStackTrace();
		} catch (IOException ioe) {
			ioe.printStackTrace();
		} catch (SAXException sae) {
			sae.printStackTrace();
		}
	}

}
