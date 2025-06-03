import React, { useState } from 'react';
import { addProductSOAP } from '../services/produtc'; // Asegúrate de que la ruta sea correcta

export default function AddProductForm() {
  const [product, setProduct] = useState({
    name: '',
    description: '',
    price: '',
    category: 'TECHNOLOGICAL'
  });

  const handleSubmit = async (e) => {
    e.preventDefault();
    const response = await addProductSOAP(product)  ;
    console.log('Respuesta SOAP:', response);
    alert("Producto agregado");
  };

  return (
    <form onSubmit={handleSubmit} className='flex flex-col gap-6 p-4 bg-gray-100 rounded shadow-md'>
      <input type="text" placeholder="Nombre" value={product.name}
        onChange={(e) => setProduct({ ...product, name: e.target.value })} />
      <input type="text" placeholder="Descripción" value={product.description}
        onChange={(e) => setProduct({ ...product, description: e.target.value })} />
      <input type="number" placeholder="Precio" value={product.price}
        onChange={(e) => setProduct({ ...product, price: e.target.value })} />
      <select value={product.category}
        onChange={(e) => setProduct({ ...product, category: e.target.value })}>
        <option value="TECHNOLOGICAL">TECHNOLOGICAL</option>
        <option value="HOGAR">HOGAR</option>
        <option value="OTROS">OTROS</option>
      </select>
      <button type="submit"  className='bg-blue-500 p-4 rounded-2 text-white rounded-lg'>Agregar Producto</button>
    </form>
  );
}
