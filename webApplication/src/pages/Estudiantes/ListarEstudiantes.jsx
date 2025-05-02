import { useEffect, useRef, useState } from "react";
import { listarEstudiantes } from "../../services/soap/estudiantes/listarEstudiantes";
import RegistrarEstudiante from "./RegistrarEstudiante";
import ActualizarEstudiante from "./ActualizarEstudiante";
import MateriasEstudiante from "./MateriasEstudiante";

const ListarEstudiantes = () => {
  const [estudiantes, setEstudiantes] = useState([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState(null);
  const [estudianteSeleccionado, setEstudianteSeleccionado] = useState(null);
  const [datosEstudiante, setDatosEstudiante] = useState(null);
  const [modalRVisible, setModalRVisible] = useState(false);
  const [modalAVisible, setModalAVisible] = useState(false);
  const [modalMEVisible, setModalMEVisible] = useState(false);
  const tablaRef = useRef(null);
  const botonesRef = useRef(null);

  useEffect(() => {
    const cargarDatos = async () => {
      try {
        const data = await listarEstudiantes();
        setEstudiantes(data);
      } catch (err) {
        setError(err.message);
      } finally {
        setLoading(false);
      }
    };
    cargarDatos();
  }, []);

  useEffect(() => {
    const handleClickFuera = (e) => {
      const fueraDeTabla =
        tablaRef.current && !tablaRef.current.contains(e.target);
      const fueraDeBotones =
        botonesRef.current && !botonesRef.current.contains(e.target);
      if (fueraDeTabla && fueraDeBotones) {
        setEstudianteSeleccionado(null);
      }
    };
    document.addEventListener("mousedown", handleClickFuera);
    return () => {
      document.removeEventListener("mousedown", handleClickFuera);
    };
  }, []);

  const handleSeleccion = (estudiante) => {
    setEstudianteSeleccionado(
      estudianteSeleccionado?.id === estudiante.id ? null : estudiante
    );
  };

  const handleEliminar = () => {
    if (!estudianteSeleccionado) return;
    const id = estudianteSeleccionado.id;
    console.log("Eliminar estudiante con ID:", id);
    // lógica para eliminar
  };

  const handleActualizar = () => {
    if (!estudianteSeleccionado) return;
    // eslint-disable-next-line no-unused-vars
    const { correo, ...datosEstudiante } = estudianteSeleccionado;
    //console.log("Actualizar estudiante con datos:", datosParaActualizar);
    setDatosEstudiante(datosEstudiante);
    setModalAVisible(true);
  };

  const handleMateriasEstudiante = () => {
    if (!estudianteSeleccionado) return;
    // eslint-disable-next-line no-unused-vars
    const { correo, ...datosEstudiante } = estudianteSeleccionado;
    //console.log("Actualizar estudiante con datos:", datosParaActualizar);
    setDatosEstudiante(datosEstudiante);
    setModalMEVisible(true);
  };

  if (loading)
    return (
      <div className="fixed inset-0 flex flex-col items-center justify-center bg-gray-100/30 z-50">
        <div role="status">
          <svg
            aria-hidden="true"
            className="w-12 h-12 text-gray-200 animate-spin fill-blue-600"
            viewBox="0 0 100 101"
            fill="none"
            xmlns="http://www.w3.org/2000/svg"
          >
            <path
              d="M100 50.5908C100 78.2051 77.6142 100.591 50 100.591C22.3858 100.591 0 78.2051 0 50.5908C0 22.9766 22.3858 0.59082 50 0.59082C77.6142 0.59082 100 22.9766 100 50.5908ZM9.08144 50.5908C9.08144 73.1895 27.4013 91.5094 50 91.5094C72.5987 91.5094 90.9186 73.1895 90.9186 50.5908C90.9186 27.9921 72.5987 9.67226 50 9.67226C27.4013 9.67226 9.08144 27.9921 9.08144 50.5908Z"
              fill="currentColor"
            />
            <path
              d="M93.9676 39.0409C96.393 38.4038 97.8624 35.9116 97.0079 33.5539C95.2932 28.8227 92.871 24.3692 89.8167 20.348C85.8452 15.1192 80.8826 10.7238 75.2124 7.41289C69.5422 4.10194 63.2754 1.94025 56.7698 1.05124C51.7666 0.367541 46.6976 0.446843 41.7345 1.27873C39.2613 1.69328 37.813 4.19778 38.4501 6.62326C39.0873 9.04874 41.5694 10.4717 44.0505 10.1071C47.8511 9.54855 51.7191 9.52689 55.5402 10.0491C60.8642 10.7766 65.9928 12.5457 70.6331 15.2552C75.2735 17.9648 79.3347 21.5619 82.5849 25.841C84.9175 28.9121 86.7997 32.2913 88.1811 35.8758C89.083 38.2158 91.5421 39.6781 93.9676 39.0409Z"
              fill="currentFill"
            />
          </svg>
          <span className="sr-only">Loading...</span>
        </div>
        <p className="mt-4 text-lg text-gray-700">Cargando estudiantes...</p>
      </div>
    );
  if (error) return <div className="p-4 text-red-500">Error: {error}</div>;

  return (
    <div className="container mx-auto p-6 text-center">
      <div className="flex flex-col sm:flex-row sm:items-center sm:justify-between mb-6 gap-4 text-center">
        <h1 className="text-2xl font-bold text-gray-800 w-full sm:w-auto">
          Listado de Estudiantes
        </h1>
        <div className="flex justify-center sm:justify-end gap-2 w-full sm:w-auto">
          <button
            onClick={() => setModalRVisible(true)}
            className="bg-blue-700  text-white hover:bg-blue-800 px-4 py-2 rounded-md shadow transition duration-200 cursor-pointer"
          >
            Registrar
          </button>
          <RegistrarEstudiante
            visible={modalRVisible}
            onClose={() => setModalRVisible(false)}
          />
          <div className="flex gap-2" ref={botonesRef}>
            <button
              className={`px-4 py-2 rounded-md shadow transition duration-200 ${
                estudianteSeleccionado
                  ? "bg-green-700 text-white hover:bg-green-800 cursor-pointer"
                  : "bg-gray-500 text-white cursor-not-allowed"
              }`}
              disabled={!estudianteSeleccionado}
              onClick={handleActualizar}
            >
              Actualizar
            </button>
            <ActualizarEstudiante
              visible={modalAVisible}
              onClose={() => setModalAVisible(false)}
              datosParaActualizar={datosEstudiante}
            />
            <button
              className={`px-4 py-2 rounded-md shadow transition duration-200 ${
                estudianteSeleccionado
                  ? "bg-red-700 text-white hover:bg-red-800 cursor-pointer"
                  : "bg-gray-500 text-white cursor-not-allowed"
              }`}
              disabled={!estudianteSeleccionado}
              onClick={handleEliminar}
            >
              Eliminar
            </button>
            <button
              className={`px-4 py-2 rounded-md shadow transition duration-200 ${
                estudianteSeleccionado
                  ? "bg-gray-700 text-white hover:bg-gray-900 cursor-pointer"
                  : "bg-gray-500 text-white cursor-not-allowed"
              }`}
              disabled={!estudianteSeleccionado}
              onClick={handleMateriasEstudiante}
            >
              Materias asignadas
            </button>
            <MateriasEstudiante
              visible={modalMEVisible}
              onClose={() => setModalMEVisible(false)}
              datosEstudiante={datosEstudiante}
            />
          </div>
        </div>
      </div>

      <div className="overflow-x-auto shadow rounded-lg" ref={tablaRef}>
        <table className="min-w-full bg-white border border-gray-300 rounded-md">
          <thead>
            <tr className="bg-gray-100 text-gray-700">
              <th className="py-3 px-4 border border-gray-300 text-center">
                Nro.
              </th>
              <th className="py-3 px-4 border border-gray-300 text-center">
                Nombre
              </th>
              <th className="py-3 px-4 border border-gray-300 text-center">
                Apellido
              </th>
              <th className="py-3 px-4 border border-gray-300 text-center">
                País
              </th>
              <th className="py-3 px-4 border border-gray-300 text-center">
                Correo
              </th>
            </tr>
          </thead>
          <tbody>
            {estudiantes.map((est) => (
              <tr
                key={est.id}
                onClick={() => handleSeleccion(est)}
                className={`cursor-pointer transition text-center ${
                  estudianteSeleccionado?.id === est.id
                    ? "bg-blue-300"
                    : "hover:bg-gray-200"
                }`}
              >
                <td className="py-2 px-4 border border-gray-300">{est.id}</td>
                <td className="py-2 px-4 border border-gray-300">
                  {est.primerNombre}
                </td>
                <td className="py-2 px-4 border border-gray-300">
                  {est.primerApellido}
                </td>
                <td className="py-2 px-4 border border-gray-300">{est.pais}</td>
                <td className="py-2 px-4 border border-gray-300">
                  {est.correo}
                </td>
              </tr>
            ))}
          </tbody>
        </table>
      </div>
    </div>
  );
};

export default ListarEstudiantes;
