# Veraz Mock


##Objetive
Mock veraz webservice with some basic logic 

## wsdl endpoint 
http://localhost:8080/services/idvalidator?wsdl
## endpoint
http://localhost:8080/services/idvalidator

## How to Run
Run > Application.java

## Rules
si la operacion SOAP es != a "mensajeRequest"
	--> return "Not soported operation"
si no:
	si "messageRequest" contiene ETAPA1
		si DN1 = 32387912 --> return etapa_1-dni_bloqueado.xml
		si DNI != 32387912 --> return etapa_1-success.xml
	si "messageRequest" contiene ETAPA2
		y contiene 4 o mas "id_respuesta" con valor a "0" --> etapa_2-success.xml
		si no --> etapa_2-respuestas_erroneas.xml
		
## TODO
1. Move dataFormat=MESSAGE to dataFormat=POJO or dataFormat=PAYLOAD
2. Improve code logic
3. Improve how to get sub xml 
	String xml_variables = request.substring(request.lastIndexOf("<variables>"),(request.lastIndexOf("</variables>")+"</variables>".length()));