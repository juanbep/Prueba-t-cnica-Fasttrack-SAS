import { SoapRequest } from "../../api";

/**
 * Parsea la respuesta XML del servicio EliminarEstudiante.
 */
const parseRegistroResponse = (xmlDoc) => {
  const exito = xmlDoc.getElementsByTagName("ns2:exito")[0].textContent;
  const mensaje = xmlDoc.getElementsByTagName("ns2:mensaje")[0].textContent;
  return { exito: exito, mensaje };
};

/**
 * Elimina un estudiante.
 * @returns {Promise<{exito: boolean, mensaje: string}>}
 */
export const eliminarMateria = async (idMateria) => {
  const soapBody = `
    <soapenv:Envelope xmlns:soapenv="http://schemas.xmlsoap.org/soap/envelope/"
                  xmlns:app="http://fasttrack.com/application/materia">
        <soapenv:Header/>
        <soapenv:Body>
            <app:EliminarMateriaRequest>
                <app:id>${idMateria}</app:id>
            </app:EliminarMateriaRequest>
        </soapenv:Body>
    </soapenv:Envelope>
  `;

  try {
    const xmlDoc = await Promise.resolve(SoapRequest(soapBody));
    return parseRegistroResponse(xmlDoc);
  } catch (error) {
    console.error("Error al eliminar materia:", error);
    return {
      exito: false,
      mensaje: "Error en la conexión con el servidor.",
    };
  }
};
