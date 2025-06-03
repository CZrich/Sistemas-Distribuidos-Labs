// src/components/AgregarProducto.jsx
import React from 'react';
import AddProductForm from '../pages/form'; // suponiendo que tienes el formulario separado

export default function AgregarProducto() {
  return (
    <div className="p-6">
      <h2 className="text-2xl font-bold mb-4">Agregar nuevo producto</h2>
      <AddProductForm />
    </div>
  );
}
