import { SoapRequest } from "./EstudianteClient";

/**
 * Parsea la respuesta XML del servicio RegistrarEstudiante.
 */
const parseRegistroResponse = (xmlDoc) => {
  const fault = xmlDoc.getElementsByTagName("SOAP-ENV:Fault")[0];

  if (fault) {
    const detalles = xmlDoc.getElementsByTagName("spring-ws:ValidationError");
    const errores = [];

    /**
     * Esta logica captura las respuestas de error enviadas por el servidor
     * y construye un mensaje de error generico.
     * Se hace así porque el mensaje que devuelve el servicio no tiene una estructura legible
     * por lo que no seria adecuado mostrarlo al usuario
     * lo ideal sería crear mensajes de error personalizados en el backend para las validaciones
     */
    for (let i = 0; i < detalles.length; i++) {
      const errorTexto = detalles[i]?.textContent?.trim();
      if (errorTexto) {
        if (errorTexto.includes("cvc-type.3.1.3")) {
          const match = errorTexto.match(
            /El valor '(.*)' del elemento '(.*)' no es válido\./
          );
          if (match) {
            const valor = match[1];
            const campo = match[2];

            let campoNombre = "";
            if (campo.includes("primerNombre")) campoNombre = "Nombre";
            else if (campo.includes("primerApellido")) campoNombre = "Apellido";
            else campoNombre = "Campo";

            errores.push(
              `El valor '${valor}' en el campo ${campoNombre} no es válido.`
            );
          }
        }
      }
    }

    const mensajeGenerico =
      "\n\nSolo letras mayúsculas (A-Z), sin acentos ni Ñ. Máximo 30 caracteres.";

    const mensajeCompleto = errores.join("\n\n") + mensajeGenerico;

    return { exito: false, mensaje: mensajeCompleto };
  }

  const exito = xmlDoc.getElementsByTagName("ns2:exito")[0].textContent;
  const mensaje = xmlDoc.getElementsByTagName("ns2:mensaje")[0].textContent;
  return { exito: exito === "true", mensaje };
};

/**
 * Registra un nuevo estudiante.
 * @param {Object} estudiante - Datos del estudiante { primerNombre, primerApellido, pais }
 * @returns {Promise<{exito: boolean, mensaje: string}>}
 */
export const actualizarEstudiante = async (estudiante) => {
  const soapBody = `
    <soapenv:Envelope xmlns:soapenv="http://schemas.xmlsoap.org/soap/envelope/"
                      xmlns:app="http://fasttrack.com/application/estudiante">
      <soapenv:Header/>
      <soapenv:Body>
        <app:ActualizarEstudianteRequest>
          <app:id>${estudiante.id}</app:id>
          <app:primerNombre>${estudiante.primerNombre}</app:primerNombre>
          <app:primerApellido>${estudiante.primerApellido}</app:primerApellido>
          <app:pais>${estudiante.pais}</app:pais>
        </app:ActualizarEstudianteRequest>
      </soapenv:Body>
    </soapenv:Envelope>
  `;

  try {
    const xmlDoc = await Promise.resolve(SoapRequest(soapBody));
    return parseRegistroResponse(xmlDoc);
  } catch (error) {
    console.error("Error al actualizar estudiante:", error);
    return {
      exito: false,
      mensaje: "Error en la conexión con el servidor.",
    };
  }
};
