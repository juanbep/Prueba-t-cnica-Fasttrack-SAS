import { FaEdit, FaTrash } from "react-icons/fa";

export default function EstudianteTable({ estudiantes, onEdit, onDelete }) {
  return (
    <table className="w-full border-collapse">
      <thead>
        <tr className="bg-gray-100">
          <th className="p-3 text-left">ID</th>
          <th className="p-3 text-left">Nombre</th>
          <th className="p-3 text-left">Apellido</th>
          <th className="p-3 text-left">País</th>
          <th className="p-3 text-left">Correo</th>
          <th className="p-3 text-left">Acciones</th>
        </tr>
      </thead>
      <tbody>
        {estudiantes.map((est) => (
          <tr key={est["ns2:id"][0]} className="border-b hover:bg-gray-50">
            <td className="p-3">{est["ns2:id"][0]}</td>
            <td className="p-3">{est["ns2:primerNombre"][0]}</td>
            <td className="p-3">{est["ns2:primerApellido"][0]}</td>
            <td className="p-3">{est["ns2:pais"][0]}</td>
            <td className="p-3">{est["ns2:correo"][0]}</td>
            <td className="p-3 flex gap-2">
              <button
                onClick={() => onEdit(est["ns2:id"][0])}
                className="text-blue-500 hover:text-blue-700"
              >
                <FaEdit />
              </button>
              <button
                onClick={() => onDelete(est["ns2:id"][0])}
                className="text-red-500 hover:text-red-700"
              >
                <FaTrash />
              </button>
            </td>
          </tr>
        ))}
      </tbody>
    </table>
  );
}
