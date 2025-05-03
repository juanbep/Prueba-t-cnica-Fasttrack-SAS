import { SoapRequest } from "../../api";

/**
 * Parsea la respuesta XML del servicio.
 */
const parseObjEstudiante = (node) => ({
  id: node.getElementsByTagName("ns2:id")[0].textContent,
  primerNombre: node.getElementsByTagName("ns2:primer_nombre")[0].textContent,
  primerApellido: node.getElementsByTagName("ns2:primer_apellido")[0]
    .textContent,
});

/**
 * @returns {Promise<{exito: boolean, mensaje: string}>}
 */
export const listarEstudiantes = async (idMateria) => {
  const soapBody = `
    <soapenv:Envelope xmlns:soapenv="http://schemas.xmlsoap.org/soap/envelope/"
                  xmlns:app="http://fasttrack.com/application/estudiante_materia">
        <soapenv:Header/>
        <soapenv:Body>
            <app:ListarEstudiantesMateriaRequest>
                <app:idMateria>${idMateria}</app:idMateria>
            </app:ListarEstudiantesMateriaRequest>
        </soapenv:Body>
    </soapenv:Envelope>
  `;

  const xmlDoc = await Promise.resolve(SoapRequest(soapBody));
  const listaEstudiantes = xmlDoc.getElementsByTagName("ns2:estudiante");

  return Array.from(listaEstudiantes).map(parseObjEstudiante);
};
