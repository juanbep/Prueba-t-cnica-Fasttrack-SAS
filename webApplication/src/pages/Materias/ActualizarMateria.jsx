import { useState, useEffect } from "react";
import { IoMdClose } from "react-icons/io";
import { actualizarMateria } from "../../services/soap/materias/ActualizarMateria";

const ActualizarMateria = ({
  visible,
  onClose,
  datosParaActualizar,
  onSuccess,
}) => {
  const [formData, setFormData] = useState({
    id: "",
    nombre: "",
    codigo: "",
  });

  // eslint-disable-next-line no-unused-vars
  const [error, setError] = useState("");

  useEffect(() => {
    if (visible && datosParaActualizar) {
      console.log(datosParaActualizar);
      setFormData({
        id: datosParaActualizar.id || "",
        nombre: datosParaActualizar.nombre || "",
        codigo: datosParaActualizar.codigo || "",
      });
    } else if (!visible) {
      setFormData({
        id: "",
        nombre: "",
        codigo: "",
      });
    }
  }, [visible, datosParaActualizar]);

  const handleChange = (e) => {
    const { name, value } = e.target;
    setFormData((prev) => ({ ...prev, [name]: value }));
  };

  const handleSubmit = async (e) => {
    e.preventDefault();

    console.log("Materia a actualizar:", formData);

    // Llamada al servicio SOAP para actualiar una materia
    const { exito, mensaje } = await actualizarMateria(formData);

    if (exito) {
      console.log("Materia actualizada:", mensaje);
      alert(mensaje);
      onSuccess();
      onClose();
    } else {
      setError(mensaje || "Error al actualizar materia");
      alert(mensaje);
    }
  };

  if (!visible) return null;

  return (
    <div className="fixed inset-0 z-50 flex items-center justify-center bg-gray-500/80">
      <div className="bg-white rounded-lg shadow-lg w-full max-w-md p-6 relative">
        {/* Header */}
        <div className="flex justify-between items-center border-b pb-3">
          <h3 className="text-xl font-semibold text-gray-900">
            Actualizar Materia
          </h3>
          <button
            onClick={onClose}
            className="text-gray-400 hover:text-gray-700 rounded-lg text-sm p-1 cursor-pointer"
          >
            <IoMdClose size={24} />
          </button>
        </div>

        {/* Formulario */}
        <form onSubmit={handleSubmit} className="mt-4 space-y-4">
          <div>
            <label className="block text-sm font-medium text-gray-700">
              Nombre
            </label>
            <input
              type="text"
              name="nombre"
              value={formData.nombre}
              onChange={handleChange}
              required
              className="mt-1 w-full border border-gray-300 rounded-md px-3 py-2 focus:outline-none focus:ring focus:ring-blue-200"
            />
          </div>
          <div>
            <label className="block text-sm font-medium text-gray-700">
              Código
            </label>
            <input
              type="text"
              name="codigo"
              value={formData.codigo}
              onChange={handleChange}
              required
              className="mt-1 w-full border border-gray-300 rounded-md px-3 py-2 focus:outline-none focus:ring focus:ring-blue-200"
            />
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
              type="submit"
              className="px-4 py-2 bg-blue-700 text-white rounded hover:bg-blue-800 cursor-pointer"
            >
              Actualizar
            </button>
          </div>
        </form>
      </div>
    </div>
  );
};

export default ActualizarMateria;
