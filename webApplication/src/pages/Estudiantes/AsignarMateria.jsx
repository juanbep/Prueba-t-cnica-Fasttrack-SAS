import { IoMdClose } from "react-icons/io";
import { useState, useRef, useEffect } from "react";
import { listarMaterias } from "../../services/soap/materias/listarMaterias";
import { asignarMateria } from "../../services/soap/estudiante_materia/AsignarMateria";

const MateriasEstudiante = ({ visible, onClose, datosEstudianteAsignar }) => {
  const [materias, setMaterias] = useState([]);
  const [materiaSeleccionada, setMateriaSeleccionada] = useState(null);
  const [materiasAsignadas, setMateriasAsignadas] = useState([]);
  const [isSubmitting, setIsSubmitting] = useState(false);
  const [loading, setLoading] = useState(true);
  const modalRef = useRef(null);

  useEffect(() => {
    const cargarDatos = async () => {
      setLoading(true);

      const delay = new Promise((resolve) => setTimeout(resolve, 400));
      const fetchData = listarMaterias(); // Petición SOAP

      try {
        const [data] = await Promise.all([fetchData, delay]);
        setMaterias(data);
      } catch (error) {
        alert(`${error?.message || error}`);
      } finally {
        setLoading(false);
      }
    };

    if (visible) {
      cargarDatos();
      setMateriaSeleccionada(null);
    } else {
      setMateriasAsignadas(() => []);
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
    setMateriaSeleccionada(materia);
  };

  const handleAsignarMateria = async () => {
    if (isSubmitting || !materiaSeleccionada) return;

    const { id } = materiaSeleccionada;

    setIsSubmitting(true);

    try {
      const response = await asignarMateria(datosEstudianteAsignar.id, id);

      if (response.exito === "true") {
        alert(`${response.mensaje}`);
        setMateriasAsignadas((prev) => [...prev, materiaSeleccionada]);
        setMateriaSeleccionada(null);
      } else {
        alert(`${response.mensaje}`);
      }
    } catch (error) {
      alert(`Error al asignar materia: ${error?.message || error}`);
    } finally {
      setIsSubmitting(false);
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
                    } ${
                      materiasAsignadas.some((m) => m.id === materia.id)
                        ? "bg-green-200"
                        : ""
                    }`}
                  >
                    <td className="px-4 py-2">{materia.codigo}</td>
                    <td className="px-4 py-2 ">{materia.nombre}</td>
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
            onClick={handleAsignarMateria}
            className={`px-4 py-2 rounded text-white transition ${
              materiaSeleccionada && !isSubmitting
                ? "bg-blue-700 hover:bg-blue-800 cursor-pointer"
                : "bg-blue-300 cursor-not-allowed"
            }`}
            disabled={!materiaSeleccionada || isSubmitting}
          >
            {isSubmitting ? "Asignando..." : "Asignar"}
          </button>
        </div>
      </div>
    </div>
  );
};

export default MateriasEstudiante;
