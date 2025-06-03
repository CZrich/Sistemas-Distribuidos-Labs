

import './App.css'
import { BrowserRouter as Router, Routes, Route, Link } from 'react-router-dom';
import  MostrarProductos from './componets/MostrarProductos';
import AgregarProducto from './componets/AgregarProducto';
function App() {
   return (
    <Router>
      <nav className="p-4 bg-gray-200">
        <Link to="/productos" className="mr-4">Ver productos</Link>
        <Link to="/agregar">Agregar producto</Link>
      </nav>

      <Routes>
        <Route path="/productos" element={<MostrarProductos />} />
        <Route path="/agregar" element={<AgregarProducto />} />
      </Routes>
    </Router>
  );

}

export default App
