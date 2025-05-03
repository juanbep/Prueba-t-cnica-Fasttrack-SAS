import { SoapRequest } from "../../api";

/**
 * Parsea la respuesta XML del servicio ActualizarMateria.
 */
const parseRegistroResponse = (xmlDoc) => {
  const exito = xmlDoc.getElementsByTagName("ns2:exito")[0].textContent;
  const mensaje = xmlDoc.getElementsByTagName("ns2:mensaje")[0].textContent;
  return { exito, mensaje };
};

/**
 * Registra una nueva materia.
 * @returns {Promise<{exito: boolean, mensaje: string}>}
 */
export const actualizarMateria = async (materia) => {
  const soapBody = `
    <soapenv:Envelope xmlns:soapenv="http://schemas.xmlsoap.org/soap/envelope/"
                  xmlns:app="http://fasttrack.com/application/materia">
        <soapenv:Header/>
        <soapenv:Body>
            <app:ActualizarMateriaRequest>
                <app:id>${materia.id}</app:id>
                <app:nombre>${materia.nombre}</app:nombre>
                <app:codigo>${materia.codigo}</app:codigo>
            </app:ActualizarMateriaRequest>
        </soapenv:Body>
    </soapenv:Envelope>
  `;

  try {
    const xmlDoc = await Promise.resolve(SoapRequest(soapBody));
    return parseRegistroResponse(xmlDoc);
  } catch (error) {
    console.error("Error al actualizar materia:", error);
    return {
      exito: false,
      mensaje: "Error en la conexión con el servidor.",
    };
  }
};
