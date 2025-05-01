import axios from "axios";

const SOAP_URL = "/api/ws";

/**
 * Petición SOAP genérica
 * @param {string} soapBody - Cuerpo XML de la petición
 * @returns {Document} XML document
 */
export const SoapRequest = async (soapBody) => {
  try {
    const { data } = await axios.post(SOAP_URL, soapBody, {
      headers: {
        "Content-Type": "text/xml;charset=UTF-8",
      },
      responseType: "text",
    });

    const parser = new DOMParser();
    return parser.parseFromString(data, "text/xml");
  } catch (error) {
    console.error("Error en petición SOAP:", error);
    throw error;
  }
};