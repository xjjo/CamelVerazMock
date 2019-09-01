package veraz_cfx_service_mock.org;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Map;
import java.util.stream.Collectors;

import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.apache.camel.Exchange;
import org.apache.camel.Message;
import org.apache.camel.TypeConverter;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

public class VerazResponseHandlerBean {
	private static final String RESOURCES_PATHS = "src/main/resources/response-veraz/";
	
	public String mensajeRequest(Exchange exchange, Message message, TypeConverter converter){
		
		/*
		org.apache.camel.Exchange
		org.apache.camel.Message
		org.apache.camel.CamelContext
		org.apache.camel.TypeConverter
		org.apache.camel.spi.Registry
		java.lang.Exception
		*/
		// (String) message.getHeader("x_body");
		String response = ""; 
	
			try {
				String request = convert((java.io.InputStream) exchange.getIn().getBody(), Charset.forName("UTF-8"));
				message.setHeader("xRequest", request);
				if(request != null && request.contains("ETAPA1")){
					if(request.contains("<documento>32387912</documento>")){
						return new String(Files.readAllBytes(Paths.get(RESOURCES_PATHS.concat("etapa_1-dni_bloqueado.xml"))));
					}
					response = new String(Files.readAllBytes(Paths.get(RESOURCES_PATHS.concat("etapa_1-success.xml"))));
					
				}else if(request != null && request.contains("ETAPA2")) {
					try {
						String xml_variables = request.substring(request.lastIndexOf("<variables>"),(request.lastIndexOf("</variables>")+"</variables>".length()));
						//xml_variables = <variables><cuestionario>11</cuestionario><respuesta><id_pregunta>171</id_pregunta><id_respuesta>0</id_respuesta></respuesta><respuesta><id_pregunta>172</id_pregunta><id_respuesta>0</id_respuesta></respuesta><respuesta><id_pregunta>179</id_pregunta><id_respuesta>1</id_respuesta></respuesta><respuesta><id_pregunta>190</id_pregunta><id_respuesta>1</id_respuesta></respuesta><respuesta><id_pregunta>184</id_pregunta><id_respuesta>1</id_respuesta></respuesta></variables>
						
						//Get XPath
				        XPathFactory xpf = XPathFactory.newInstance();
				        XPath xpath = xpf.newXPath();
				        InputSource inputXML = new InputSource( new StringReader( xml_variables ) );

						//String value = xpath.evaluate("/variables/respuesta/id_respuesta/text()", inputXML);
						 NodeList nodes = (NodeList) xpath.evaluate("/variables/respuesta/id_respuesta/text()", inputXML, XPathConstants.NODESET);
						 int j = 0;
						 for (int i = 0; i < nodes.getLength(); i++) {
					            if ("0".equalsIgnoreCase(nodes.item(i).getNodeValue())){
					            	j++;
					            }
					     }
						 if(j>=4) {
							 return new String(Files.readAllBytes(Paths.get(RESOURCES_PATHS.concat("etapa_2-success.xml"))));
						 }else {
							 return new String(Files.readAllBytes(Paths.get(RESOURCES_PATHS.concat("etapa_2-resupuestas_erroneas.xml"))));
						 }
						  
					} catch (Exception e) {
						
						e.printStackTrace();
					}
			        
					response = new String(Files.readAllBytes(Paths.get(RESOURCES_PATHS.concat("etapa_2-success.xml"))));
				}else {
					response = new String(Files.readAllBytes(Paths.get(RESOURCES_PATHS.concat("etapa_1-success.xml"))));
				}
				return response;
				
			} catch (IOException e) {
				e.printStackTrace();	 
			}
		
		return response;
		
	}
	public String convert(InputStream inputStream, Charset charset) throws IOException {
		 
		StringBuilder stringBuilder = new StringBuilder();
		String line = null;
		
		try (BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, charset))) {	
			while ((line = bufferedReader.readLine()) != null) {
				stringBuilder.append(line);
			}
		}
	 
		return stringBuilder.toString();
	}
	
}
