import { SoapRequest } from "./EstudianteMateriaClient";

/**
 * Parsea la respuesta XML del servicio.
 */
const parseObjMateria = (node) => ({
  id: node.getElementsByTagName("ns2:id")[0].textContent,
  nombre: node.getElementsByTagName("ns2:nombre")[0].textContent,
  codigo: node.getElementsByTagName("ns2:codigo")[0].textContent,
});

/**
 * @returns {Promise<{exito: boolean, mensaje: string}>}
 */
export const listarMaterias = async (idEstudiante) => {
  const soapBody = `
    <soapenv:Envelope xmlns:soapenv="http://schemas.xmlsoap.org/soap/envelope/"
                  xmlns:app="http://fasttrack.com/application/estudiante_materia">
    <soapenv:Header/>
    <soapenv:Body>
        <app:ListarMateriasEstudianteRequest>
            <app:idEstudiante>${idEstudiante}</app:idEstudiante>
        </app:ListarMateriasEstudianteRequest>
    </soapenv:Body>
    </soapenv:Envelope>
  `;

  const xmlDoc = await Promise.resolve(SoapRequest(soapBody));
  const listaMaterias = xmlDoc.getElementsByTagName("ns2:materia");

  return Array.from(listaMaterias).map(parseObjMateria);
};
