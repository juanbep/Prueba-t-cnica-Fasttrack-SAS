import { SoapRequest } from "../../api";

/**
 * Parsea la respuesta XML del servicio RegistrarEstudiante.
 */
const parseRegistroResponse = (xmlDoc) => {
  const exito = xmlDoc.getElementsByTagName("ns2:exito")[0].textContent;
  const mensaje = xmlDoc.getElementsByTagName("ns2:mensaje")[0].textContent;
  return { exito, mensaje };
};

/**
 * Registra un nuevo estudiante.
 * @param {Long} idEstudiante
 * @param {Long} idMateria 
 * @returns {Promise<{exito: boolean, mensaje: string}>}
 */
export const desasignarMateria = async (idEstudiante, idMateria) => {
  const soapBody = `
    <soapenv:Envelope xmlns:soapenv="http://schemas.xmlsoap.org/soap/envelope/" 
    xmlns:app="http://fasttrack.com/application/estudiante_materia">
    <soapenv:Header/>
    <soapenv:Body>
        <app:DesasignarMateriaRequest>
            <app:idEstudiante>${idEstudiante}</app:idEstudiante>
            <app:idMateria>${idMateria}</app:idMateria>
        </app:DesasignarMateriaRequest>
    </soapenv:Body>
    </soapenv:Envelope>
  `;

  try {
    const xmlDoc = await Promise.resolve(SoapRequest(soapBody));
    return parseRegistroResponse(xmlDoc);
  } catch (error) {
    console.error(error);
    return {
      exito: false,
      mensaje: "500 (Internal Server Error)",
    };
  }
};