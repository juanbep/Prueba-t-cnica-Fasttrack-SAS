import { IoMdClose } from "react-icons/io";
import { useState, useRef, useEffect } from "react";
import { listarMaterias } from "../../services/soap/materias/listarMaterias";
import { asignarMateria } from "../../services/soap/estudiante_materia/AsignarMateria";

const MateriasEstudiante = ({ visible, onClose, datosEstudianteAsignar }) => {
  const [materias, setMaterias] = useState([]);
  const [materiaSeleccionada, setMateriaSeleccionada] = useState(null);
  // eslint-disable-next-line no-unused-vars
  const [error, setError] = useState(null);
  const [loading, setLoading] = useState(true);
  const modalRef = useRef(null);

  // Petición SOAP
  useEffect(() => {
    const cargarDatos = async () => {
      setLoading(true);

      const delay = new Promise((resolve) => setTimeout(resolve, 400));
      const fetchData = listarMaterias();

      try {
        const [data] = await Promise.all([fetchData, delay]);
        setMaterias(data);
      } catch (err) {
        setError(err.message);
      } finally {
        setLoading(false);
      }
    };

    if (visible) {
      cargarDatos();
      setMateriaSeleccionada(null);
    }
  }, [datosEstudianteAsignar, visible]);

  useEffect(() => {
    const handleClickOutside = (event) => {
      if (modalRef.current && !modalRef.current.contains(event.target)) {
        setMateriaSeleccionada(null);
      }
    };

    document.addEventListener("mousedown", handleClickOutside);
    return () => document.removeEventListener("mousedown", handleClickOutside);
  }, []);

  const handleSeleccion = (materia) => {
    //console.log("Materia seleccionada:", materia);
    setMateriaSeleccionada(materia);
  };

  const handleDesasignar = async () => {
    //console.log("Si entro al metodo:", materiaSeleccionada);
    if (!materiaSeleccionada) return;

    const { id, codigo, nombre } = materiaSeleccionada;

    console.log("Materia Asignada:", { id, codigo, nombre });

    try {
      const response = await asignarMateria(datosEstudianteAsignar.id, id);

      if (response.exito === "true") {
        // actualizar la tabla
        //setMaterias((prev) => prev.filter((m) => m.id !== id));
        alert(`Materia asignada con exito: ${response.mensaje}`);
        setMateriaSeleccionada(null);
      } else {
        // mostrar mensaje al usuario
        alert(`No se pudo asignar la materia: ${response.mensaje}`);
      }
    } catch (error) {
      console.error("Error al asignar:", error);
      alert("Ocurrió un error inesperado al asignar la materia.");
    }
  };

  if (!visible) return null;

  return (
    <div className="fixed inset-0 z-50 flex items-center justify-center bg-gray-500/80">
      <div
        ref={modalRef}
        className="bg-white rounded-lg shadow-lg w-full max-w-md p-6 relative"
      >
        {/* Header */}
        <div className="flex justify-between items-center border-b pb-3">
          <h3 className="text-xl font-semibold text-gray-900">
            Materias Disponibles
          </h3>
          <button
            onClick={onClose}
            className="text-gray-400 hover:text-gray-700 rounded-lg text-sm p-1 cursor-pointer"
          >
            <IoMdClose size={24} />
          </button>
        </div>

        {/* Contenido */}
        <div className="mt-4 pb-4 max-h-60 overflow-y-auto">
          {loading ? (
            <div className="flex justify-center items-center h-40">
              <div className="w-8 h-8 border-4 border-blue-500 border-t-transparent rounded-full animate-spin"></div>
            </div>
          ) : (
            <table className="min-w-full text-sm text-left text-gray-700">
              <thead className="bg-gray-100 sticky top-0">
                <tr>
                  <th className="px-4 py-2">Código</th>
                  <th className="px-4 py-2">Nombre</th>
                </tr>
              </thead>
              <tbody>
                {materias.map((materia) => (
                  <tr
                    key={materia.id}
                    onClick={() => handleSeleccion(materia)}
                    className={`cursor-pointer hover:bg-blue-100 ${
                      materiaSeleccionada?.id === materia.id
                        ? "bg-blue-200"
                        : ""
                    }`}
                  >
                    <td className="px-4 py-2">{materia.codigo}</td>
                    <td className="px-4 py-2">{materia.nombre}</td>
                  </tr>
                ))}
              </tbody>
            </table>
          )}
        </div>

        {/* Footer */}
        <div className="flex justify-end gap-2 pt-4 border-t">
          <button
            type="button"
            onClick={onClose}
            className="px-4 py-2 bg-gray-200 text-gray-700 rounded hover:bg-gray-300 cursor-pointer"
          >
            Cancelar
          </button>
          <button
            type="button"
            onClick={handleDesasignar}
            className={`px-4 py-2 rounded text-white ${
              materiaSeleccionada
                ? "bg-blue-700 hover:bg-blue-800 cursor-pointer"
                : "bg-blue-300 cursor-not-allowed"
            }`}
            disabled={!materiaSeleccionada}
          >
            Asignar
          </button>
        </div>
      </div>
    </div>
  );
};

export default MateriasEstudiante;
