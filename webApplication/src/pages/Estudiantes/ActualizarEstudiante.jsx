import { useState, useEffect, useRef } from "react";
import { IoMdClose } from "react-icons/io";
import { IoIosArrowDropdownCircle } from "react-icons/io";
import { paises } from "../../utils/Paises";
import { actualizarEstudiante } from "../../services/soap/estudiantes/ActualizarEstudiante";

const ActualizarEstudiante = ({
  visible,
  onClose,
  datosParaActualizar,
  onSuccess,
}) => {
  const [formData, setFormData] = useState({
    id: "",
    primerNombre: "",
    primerApellido: "",
    pais: "",
  });

  const [errorPais, setErrorPais] = useState("");
  const [dropdownOpen, setDropdownOpen] = useState(false);
  const dropdownRef = useRef(null);

  useEffect(() => {
    if (visible && datosParaActualizar) {
      console.log(datosParaActualizar);
      setFormData({
        id: datosParaActualizar.id || "",
        primerNombre: datosParaActualizar.primerNombre || "",
        primerApellido: datosParaActualizar.primerApellido || "",
        pais: "",
      });
    } else if (!visible) {
      setFormData({
        id: "",
        primerNombre: "",
        primerApellido: "",
        pais: "",
      });
      setErrorPais("");
    }
  }, [visible, datosParaActualizar]);

  useEffect(() => {
    const handleClickOutside = (event) => {
      if (dropdownRef.current && !dropdownRef.current.contains(event.target)) {
        setDropdownOpen(false);
      }
    };

    document.addEventListener("mousedown", handleClickOutside);
    return () => {
      document.removeEventListener("mousedown", handleClickOutside);
    };
  }, []);

  const handleChange = (e) => {
    const { name, value } = e.target;
    setFormData((prev) => ({ ...prev, [name]: value }));
  };

  const handleKeyDown = (e) => {
    if (e.key === " " && e.target.selectionStart === 0) {
      e.preventDefault();
    }
  };

  const handleSelectPais = (pais) => {
    setFormData((prev) => ({ ...prev, pais }));
    setDropdownOpen(false);
  };

  const handleSubmit = async (e) => {
    e.preventDefault();

    
    if (!formData.pais) {
      setErrorPais("NULL");
      return;
    }

    setErrorPais("");

    const datosLimpios = {
      ...formData,
      primerNombre: formData.primerNombre.trim(),
      primerApellido: formData.primerApellido.trim(),
    };
    // Llamada al servicio SOAP para actualiar un estudiante
    const { exito, mensaje } = await actualizarEstudiante(datosLimpios);

    if (exito) {
      alert(mensaje);
      onSuccess();
      onClose();
    } else {
      alert(mensaje || "Error al registrar estudiante");
    }
  };

  if (!visible) return null;

  return (
    <div className="fixed inset-0 z-50 flex items-center justify-center bg-gray-500/80">
      <div className="bg-white rounded-lg shadow-lg w-full max-w-md p-6 relative">
        {/* Header */}
        <div className="flex justify-between items-center border-b pb-3">
          <h3 className="text-xl font-semibold text-gray-900">
            Actualizar Estudiante
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
              Primer Nombre
            </label>
            <input
              type="text"
              name="primerNombre"
              value={formData.primerNombre}
              onChange={handleChange}
              onKeyDown={handleKeyDown}
              required
              className="mt-1 w-full border border-gray-300 rounded-md px-3 py-2 focus:outline-none focus:ring focus:ring-blue-200"
            />
          </div>
          <div>
            <label className="block text-sm font-medium text-gray-700">
              Primer Apellido
            </label>
            <input
              type="text"
              name="primerApellido"
              value={formData.primerApellido}
              onChange={handleChange}
              onKeyDown={handleKeyDown}
              required
              className="mt-1 w-full border border-gray-300 rounded-md px-3 py-2 focus:outline-none focus:ring focus:ring-blue-200"
            />
          </div>
          {/* Dropdown País */}
          <label className="block text-sm font-medium text-gray-700 mb-1">
            País
          </label>
          <div className="relative" ref={dropdownRef}>
            <button
              type="button"
              onClick={() => {
                setDropdownOpen(!dropdownOpen);
                setErrorPais("");
              }}
              className={`mt-1 w-full border ${
                errorPais ? "border-red-500" : "border-gray-300"
              } rounded-md px-3 py-2 focus:outline-none focus:ring focus:ring-blue-200 inline-flex items-center justify-between cursor-pointer`}
            >
              {formData.pais || "Seleccionar país"}
              <IoIosArrowDropdownCircle size={24} />
            </button>
            {dropdownOpen && (
              <div className="absolute z-10 mt-2 w-full bg-white divide-y divide-gray-100 border border-gray-300 rounded-md shadow-lg overflow-hidden">
                <ul className="py-2 text-sm max-h-40 overflow-y-auto scrollbar-thin scrollbar-thumb-gray-300 scrollbar-track-gray-100">
                  {paises.map((pais) => (
                    <li key={pais}>
                      <button
                        type="button"
                        onClick={() => handleSelectPais(pais)}
                        className="w-full text-left px-4 py-2 hover:bg-gray-100 cursor-pointer"
                      >
                        {pais}
                      </button>
                    </li>
                  ))}
                </ul>
              </div>
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

export default ActualizarEstudiante;
