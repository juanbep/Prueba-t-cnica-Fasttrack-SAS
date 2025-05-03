import { SoapRequest } from "../../api";

/**
 * Parse the XML Data
 */
const parseObjEstudiante = (node) => ({
  id: node.getElementsByTagName("ns2:id")[0].textContent,
  nombre: node.getElementsByTagName("ns2:nombre")[0].textContent,
  codigo: node.getElementsByTagName("ns2:codigo")[0].textContent,
});

export const listarMaterias = async () => {
  const soapBody = `
    <soapenv:Envelope xmlns:soapenv="http://schemas.xmlsoap.org/soap/envelope/"
                  xmlns:app="http://fasttrack.com/application/materia">
        <soapenv:Header/>
        <soapenv:Body>
            <app:ListarMateriasRequest/>
        </soapenv:Body>
    </soapenv:Envelope>
  `;

  const xmlDoc = await Promise.resolve(SoapRequest(soapBody));
  const listaEstudiantes = xmlDoc.getElementsByTagName("ns2:materia");

  return Array.from(listaEstudiantes).map(parseObjEstudiante);
};
