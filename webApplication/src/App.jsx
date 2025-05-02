import "./App.css";
import { BrowserRouter as Router, Routes, Route } from "react-router-dom";
import ListarEstudiantes from "./pages/Estudiantes/ListarEstudiantes";
import ListarMaterias from "./pages/Materias/ListarMaterias";
import Home from "./pages/Home";

function App() {
  return (
    <>
      <Router>
        <Routes>
          <Route path="/" element={<Home />} />
          <Route path="/listarEstudiantes" element={<ListarEstudiantes />} />
          <Route path="/listarMaterias" element={<ListarMaterias />} />
        </Routes>
      </Router>
    </>
  );
}

export default App;
