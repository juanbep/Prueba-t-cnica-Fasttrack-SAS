import "./App.css";
import { BrowserRouter as Router, Routes, Route } from "react-router-dom";
import ListarEstudiantes from "./pages/Estudiantes/ListarEstudiantes";

function App() {
  return (
    <>
      <Router>
        <Routes>
          <Route path="/" element={<ListarEstudiantes />} />
          {/*
        <Route path="/nuevo" element={<NuevoEstudiante />} />
        <Route path="/editar/:id" element={<EditarEstudiante />} />
        */}
        </Routes>
      </Router>
    </>
  );
}

export default App;
