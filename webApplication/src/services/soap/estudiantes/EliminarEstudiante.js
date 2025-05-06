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
 * Elimina un estudiante.
 * @returns {Promise<{exito: boolean, mensaje: string}>}
 */
export const eliminarEstudiante = async (idEstudiante) => {
  const soapBody = `
    <soapenv:Envelope xmlns:soapenv="http://schemas.xmlsoap.org/soap/envelope/"
                  xmlns:app="http://fasttrack.com/application/estudiante">
    <soapenv:Header/>
    <soapenv:Body>
        <app:EliminarEstudianteRequest>
            <app:id>${idEstudiante}</app:id>
        </app:EliminarEstudianteRequest>
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
