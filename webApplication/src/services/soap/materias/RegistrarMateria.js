import { SoapRequest } from "./MateriaClient";

/**
 * Parsea la respuesta XML del servicio RegistrarMateria.
 */
const parseRegistroResponse = (xmlDoc) => {
  const exito = xmlDoc.getElementsByTagName("ns2:exito")[0].textContent;
  const mensaje = xmlDoc.getElementsByTagName("ns2:mensaje")[0].textContent;
  return { exito, mensaje };
};

/**
 * Registra un nueva materia.
 * @returns {Promise<{exito: boolean, mensaje: string}>}
 */
export const registrarMateria = async (materia) => {
  const soapBody = `
    <soapenv:Envelope xmlns:soapenv="http://schemas.xmlsoap.org/soap/envelope/" 
    xmlns:app="http://fasttrack.com/application/materia">
        <soapenv:Header/>
        <soapenv:Body>
            <app:RegistrarMateriaRequest>
                <app:nombre>${materia.nombre}</app:nombre>
                <app:codigo>${materia.codigo}</app:codigo>
            </app:RegistrarMateriaRequest>
        </soapenv:Body>
    </soapenv:Envelope>
  `;

  try {
    const xmlDoc = await Promise.resolve(SoapRequest(soapBody));
    return parseRegistroResponse(xmlDoc);
  } catch (error) {
    console.error("Error al registrar materia:", error);
    return {
      exito: false,
      mensaje: "Error en la conexión con el servidor.",
    };
  }
};
