// src/services/soapClient.ts
import axios from "axios";
//import { parseStringPromise } from "xml2js";

import { XMLParser } from 'fast-xml-parser';
const parser = new XMLParser({
  ignoreAttributes: false,
  removeNSPrefix: true

}
);
const SOAP_ENDPOINT = "http://localhost:8080/ws";

export async function getProductByName(name) {
  const soapEnvelope = `
        <soapenv:Envelope xmlns:soapenv="http://schemas.xmlsoap.org/soap/envelope/"
                      xmlns:gs="http://spring.io/guides/gs-producing-web-service">
            <soapenv:Header/>
            <soapenv:Body>
                <gs:getProductRequest>
                    <gs:name>${name}</gs:name>
                </gs:getProductRequest>
            </soapenv:Body>
        </soapenv:Envelope>
    `;
  /* <soapenv:Envelope xmlns:soapenv="http://schemas.xmlsoap.org/soap/envelope/"
                                 xmlns:gs="http://spring.io/guides/gs-producing-web-service">
  <soapenv:Header/>
  <soapenv:Body>
     <gs:getProductRequest>
        <gs:name>Sofa</gs:name>
     </gs:getProductRequest>
  </soapenv:Body>
</soapenv:Envelope>
*/


  const response = await axios.post(SOAP_ENDPOINT, soapEnvelope, {
    headers: {
      'Content-Type': 'text/xml;charset=UTF-8',
      'SOAPAction': '' // Ajusta según tu servicio
    }
  });
  const xmlText = response.data; // con axios no necesitas .text()

  console.log("Respuesta SOAP:", xmlText);

 const json = await parser.parse(xmlText); // parsea la respuesta XML a JSON

  // accede al producto en el JSON
 // const product = xmlText['SOAP-ENV:Envelope']['SOAP-ENV:Body']?.['ns2:getProductResponse']?.['ns2:product'];

 // if (!product) {
   // throw new Error("Producto no encontrado o formato incorrecto");
  //}

  return json; // 👈 aquí retornas solo el producto
}


export async function addProductSOAP(product) {
  const xml = `
    <soapenv:Envelope xmlns:soapenv="http://schemas.xmlsoap.org/soap/envelope/"
                      xmlns:gs="http://spring.io/guides/gs-producing-web-service">
       <soapenv:Header/>
       <soapenv:Body>
          <gs:AddProductRequest>
             <gs:product>
                <gs:name>${product.name}</gs:name>
                <gs:description>${product.description}</gs:description>
                <gs:price>${product.price}</gs:price>
                <gs:category>${product.category}</gs:category>
             </gs:product>
          </gs:AddProductRequest>
       </soapenv:Body>
    </soapenv:Envelope>
  `;

  const response = await axios.post(SOAP_ENDPOINT, xml, {
    headers: {
      'Content-Type': 'text/xml',
      'SOAPAction': '', // a veces requerido, puede dejarse vacío o usar el namespace de la operación
    }
  });

  return response.data;
}






export async function fetchAllProductsSOAP() {
  const xmlBody = `
    <soapenv:Envelope xmlns:soapenv="http://schemas.xmlsoap.org/soap/envelope/" xmlns:gs="http://spring.io/guides/gs-producing-web-service">
      <soapenv:Header/>
      <soapenv:Body>
        <gs:getAllProductsRequest />
      </soapenv:Body>
    </soapenv:Envelope>
  `;

  try {
    const response = await axios.post(SOAP_ENDPOINT, xmlBody, {
      headers: {
        'Content-Type': 'text/xml;charset=UTF-8',
        SOAPAction: '',
      },
    });

    const json = parser.parse(response.data);
    console.log("Respuesta SOAP parseada:", JSON.stringify(json, null, 2));

    const products = json.Envelope?.Body?.getAllProductsResponse?.product || [];
    console.log("Productos obtenidos:", products);
    return Array.isArray(products) ? products : [products];

  } catch (error) {
    console.error('Error al obtener productos SOAP:', error);
    return [];
  }
}
