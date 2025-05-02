import { useEffect, useRef, useState } from "react";
import { listarMaterias } from "../../services/soap/materias/listarMaterias";
import { eliminarMateria } from "../../services/soap/materias/EliminarMateria";
import RegistrarMateria from "./RegistrarMateria";
import ActualizarMateria from "./ActualizarMateria";
//import MateriasEstudiante from "./MateriasEstudiante";

const ListarMaterias = () => {
  const [materias, setMaterias] = useState([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState(null);
  const [materiaSeleccionada, setMateriaSeleccionada] = useState(null);
  const [datosMateria, setDatosMateria] = useState(null);
  const [modalRVisible, setModalRVisible] = useState(false);
  const [modalAVisible, setModalAVisible] = useState(false);
  //const [modalMEVisible, setModalMEVisible] = useState(false);
  const tablaRef = useRef(null);
  const botonesRef = useRef(null);

  useEffect(() => {
    const cargarDatos = async () => {
      try {
        const data = await listarMaterias();
        setMaterias(data);
      } catch (err) {
        setError(err.message);
      } finally {
        setLoading(false);
      }
    };
    cargarDatos();
  }, []);

  //callback para cuando se agerga una materia
  const actualizarLista = async () => {
    setLoading(true);
    try {
      const data = await listarMaterias();
      setMaterias(data);
    } catch (err) {
      setError(err.message);
    } finally {
      setLoading(false);
    }
  };

  useEffect(() => {
    const handleClickFuera = (e) => {
      const fueraDeTabla =
        tablaRef.current && !tablaRef.current.contains(e.target);
      const fueraDeBotones =
        botonesRef.current && !botonesRef.current.contains(e.target);
      if (fueraDeTabla && fueraDeBotones) {
        setMateriaSeleccionada(null);
      }
    };
    document.addEventListener("mousedown", handleClickFuera);
    return () => {
      document.removeEventListener("mousedown", handleClickFuera);
    };
  }, []);

  const handleSeleccion = (materia) => {
    setMateriaSeleccionada(
      materiaSeleccionada?.id === materia.id ? null : materia
    );
    console.log(materiaSeleccionada);
  };

  const handleEliminar = async () => {
    if (!materiaSeleccionada) return;
    const id = materiaSeleccionada.id;
    console.log("Eliminar materia con ID:", id);
    // petición soap
    try {
      const response = await eliminarMateria(id);

      if (response.exito === "true") {
        // actualizar la tabla
        setMaterias((prev) => prev.filter((m) => m.id !== id));
        setMateriaSeleccionada(null);
      } else {
        // mostrar mensaje al usuario
        alert(`No se pudo eliminar la materia: ${response.mensaje}`);
      }
    } catch (error) {
      console.error("Error al eliminar:", error);
      alert("Ocurrió un error al eliminar la materia.");
    }
  };

  const handleActualizar = () => {
    if (!materiaSeleccionada) return;

    const { ...datosMateria } = materiaSeleccionada;
    console.log("Actualizar Materia con datos:", datosMateria);
    setDatosMateria(datosMateria);
    setModalAVisible(true);
  };

  const handleMateriasEstudiante = () => {};

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
        <p className="mt-4 text-lg text-gray-700">Cargando Materias...</p>
      </div>
    );
  if (error) return <div className="p-4 text-red-500">Error: {error}</div>;

  return (
    <div className="container mx-auto p-6 text-center">
      <div className="flex flex-col sm:flex-row sm:items-center sm:justify-between mb-6 gap-4 text-center">
        <h1 className="text-2xl font-bold text-gray-800 w-full sm:w-auto">
          Listado de Materias
        </h1>
        <div className="flex justify-center sm:justify-end gap-2 w-full sm:w-auto">
          <button
            onClick={() => setModalRVisible(true)}
            className="bg-blue-700  text-white hover:bg-blue-800 px-4 py-2 rounded-md shadow transition duration-200 cursor-pointer"
          >
            Registrar
          </button>
          <RegistrarMateria
            visible={modalRVisible}
            onClose={() => setModalRVisible(false)}
            onSuccess={actualizarLista}
          />
          <div className="flex gap-2" ref={botonesRef}>
            <button
              className={`px-4 py-2 rounded-md shadow transition duration-200 ${
                materiaSeleccionada
                  ? "bg-green-700 text-white hover:bg-green-800 cursor-pointer"
                  : "bg-gray-500 text-white cursor-not-allowed"
              }`}
              disabled={!materiaSeleccionada}
              onClick={handleActualizar}
            >
              Actualizar
            </button>
            <ActualizarMateria
              visible={modalAVisible}
              onClose={() => setModalAVisible(false)}
              datosParaActualizar={datosMateria}
              onSuccess={actualizarLista}
            />
            <button
              className={`px-4 py-2 rounded-md shadow transition duration-200 ${
                materiaSeleccionada
                  ? "bg-red-700 text-white hover:bg-red-800 cursor-pointer"
                  : "bg-gray-500 text-white cursor-not-allowed"
              }`}
              disabled={!materiaSeleccionada}
              onClick={handleEliminar}
            >
              Eliminar
            </button>
            <button
              className={`px-4 py-2 rounded-md shadow transition duration-200 ${
                materiaSeleccionada
                  ? "bg-gray-700 text-white hover:bg-gray-900 cursor-pointer"
                  : "bg-gray-500 text-white cursor-not-allowed"
              }`}
              disabled={!materiaSeleccionada}
              onClick={handleMateriasEstudiante}
            >
              Estudiantes asignados
            </button>
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
                Código
              </th>
            </tr>
          </thead>
          <tbody>
            {materias.map((est) => (
              <tr
                key={est.id}
                onClick={() => handleSeleccion(est)}
                className={`cursor-pointer transition text-center ${
                  materiaSeleccionada?.id === est.id
                    ? "bg-blue-300"
                    : "hover:bg-gray-200"
                }`}
              >
                <td className="py-2 px-4 border border-gray-300">{est.id}</td>
                <td className="py-2 px-4 border border-gray-300">
                  {est.nombre}
                </td>
                <td className="py-2 px-4 border border-gray-300">
                  {est.codigo}
                </td>
              </tr>
            ))}
          </tbody>
        </table>
      </div>
    </div>
  );
};

export default ListarMaterias;
