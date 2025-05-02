import React from "react";
import { FaUserGraduate, FaBook } from "react-icons/fa";
import { useNavigate } from "react-router-dom";

const FastTrackApp = () => {
  const navigate = useNavigate();

  return (
    <div className="absolute top-1/2 left-1/2 transform -translate-x-1/2 -translate-y-1/2 border rounded-lg p-8 w-full max-w-md">
      <h1 className="text-2xl font-light text-center mb-8">
        FASTTRACK APPLICATION
      </h1>

      <div className="space-y-4">
        <button
          onClick={() => navigate("/listarEstudiantes")}
          className="w-full flex items-center justify-center space-x-3 border py-3 px-4 rounded hover:bg-gray-100 transition cursor-pointer"
        >
          <FaUserGraduate className="text-lg" />
          <span>Gestion Estudiantes</span>
        </button>

        <button
          onClick={() => navigate("/listarMaterias")}
          className="w-full flex items-center justify-center space-x-3 border py-3 px-4 rounded hover:bg-gray-100 transition cursor-pointer"
        >
          <FaBook className="text-lg" />
          <span>Gestion Materias</span>
        </button>
      </div>
    </div>
  );
};

export default FastTrackApp;
