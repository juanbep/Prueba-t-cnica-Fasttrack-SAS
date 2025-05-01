import { useEffect, useState } from "react";
import { listarEstudiantes } from "../../services/soap/estudiantes/listarEstudiantes";

const ListarEstudiantes = () => {
  const [estudiantes, setEstudiantes] = useState([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState(null);
  const [estudianteSeleccionado, setEstudianteSeleccionado] = useState(null);

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
    const { ...datosParaActualizar } = estudianteSeleccionado;
    console.log("Actualizar estudiante con datos:", datosParaActualizar);
    // lógica para actualizar
  };

  if (loading)
    return <div className="p-4 text-center">Cargando estudiantes...</div>;
  if (error) return <div className="p-4 text-red-500">Error: {error}</div>;

  return (
    <div className="container mx-auto p-6 text-center">
      <div className="flex flex-col sm:flex-row sm:items-center sm:justify-between mb-6 gap-4 text-center">
        <h1 className="text-2xl font-bold text-gray-800 w-full sm:w-auto">
          Listado de Estudiantes
        </h1>
        <div className="flex justify-center sm:justify-end gap-2 w-full sm:w-auto">
          <button className="bg-blue-600 text-white px-4 py-2 rounded-md shadow hover:bg-blue-700 transition duration-200 cursor-pointer">
            Registrar
          </button>
          <button
            className={`px-4 py-2 rounded-md shadow transition duration-200 ${
              estudianteSeleccionado
                ? "bg-yellow-500 text-white hover:bg-yellow-600 cursor-pointer"
                : "bg-gray-500 text-white cursor-not-allowed"
            }`}
            disabled={!estudianteSeleccionado}
            onClick={handleActualizar}
          >
            Actualizar
          </button>
          <button
            className={`px-4 py-2 rounded-md shadow transition duration-200 ${
              estudianteSeleccionado
                ? "bg-red-500 text-white hover:bg-red-600 cursor-pointer"
                : "bg-gray-500 text-white cursor-not-allowed"
            }`}
            disabled={!estudianteSeleccionado}
            onClick={handleEliminar}
          >
            Eliminar
          </button>
        </div>
      </div>

      <div className="overflow-x-auto shadow rounded-lg">
        <table className="min-w-full bg-white border border-gray-300 rounded-md">
          <thead>
            <tr className="bg-gray-100 text-gray-700">
              <th className="py-3 px-4 border border-gray-300 text-center">
                ID
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
                    ? "bg-blue-100"
                    : "hover:bg-gray-50"
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
