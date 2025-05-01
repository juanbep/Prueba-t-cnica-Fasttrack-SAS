import { SoapRequest } from "./EstudianteClient";

/**
 * Parse the XML Data
 */
const parseObjEstudiante = (node) => ({
  id: node.getElementsByTagName("ns2:id")[0].textContent,
  primerNombre: node.getElementsByTagName("ns2:primerNombre")[0].textContent,
  primerApellido: node.getElementsByTagName("ns2:primerApellido")[0].textContent,
  pais: node.getElementsByTagName("ns2:pais")[0].textContent,
  correo: node.getElementsByTagName("ns2:correo")[0].textContent,
});

export const listarEstudiantes = async () => {
  const soapBody = `
    <soapenv:Envelope xmlns:soapenv="http://schemas.xmlsoap.org/soap/envelope/"
                      xmlns:app="http://fasttrack.com/application/estudiante">
      <soapenv:Header/>
      <soapenv:Body>
        <app:ListarEstudiantesRequest/>
      </soapenv:Body>
    </soapenv:Envelope>
  `;

  const xmlDoc = await Promise.resolve(SoapRequest(soapBody));
  const listaEstudiantes = xmlDoc.getElementsByTagName("ns2:estudiante");
  
  return Array.from(listaEstudiantes).map(parseObjEstudiante);
};