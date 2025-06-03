// src/components/MostrarProductos.jsx
import React, { useEffect, useState } from 'react';
import { fetchAllProductsSOAP } from '../services/produtc'; // necesitarás implementar esto
import { getProductByName } from "../services/produtc";


export default function MostrarProductos() {
    const [productos, setProductos] = useState([]);
    const [productName, setProductName] = useState("");
    const [productInfo, setProductInfo] = useState(null);


   const handleSearch = async (e) => {
    e.preventDefault();

    if (!productName.trim()) {
        console.warn("Por favor ingresa un nombre de producto.");
        return;
    }

    try {
        console.log("Buscando producto:", productName);
        const response = await getProductByName(productName);
        console.log("Respuesta SOAP completa:", response);

        const body = response["Envelope"]["Body"];
        const product = body?.["getProductResponse"]?.["product"];

        if (product) {
            setProductInfo(product);
        } else {
            console.warn("Producto no encontrado.");
            setProductInfo(null); // Limpiar si no se encontró
        }
    } catch (error) {
        console.error("Error fetching product:", error);
        setProductInfo(null);
    }
};


    useEffect(() => {
        const cargar = async () => {
            const data = await fetchAllProductsSOAP();
            setProductos(data);
            console.log("Productos cargados:", data);
        };
        cargar();
    }, []);

    return (
        <div className="p-6">




            <form onSubmit={handleSearch} className="mb-4">
                <input
                    value={productName}
                    onChange={(e) => setProductName(e.target.value)}
                    placeholder="Enter product name"
                    className="border p-2 mr-2"
                />
                <button type="submit" className="bg-blue-500 text-white p-2 rounded">
                    Buscar
                </button>
            </form>

            {productInfo && (
                <div className="mt-4">
                    <h3 className="font-bold text-lg">Product Info:</h3>
                    <pre>{JSON.stringify(productInfo, null, 2)}</pre>
                </div>
            )}






            <h2 className="text-2xl font-bold mb-4">Lista de Productos</h2>
            <ul>
                {productos.map((p) => (
                    <li key={p.name} className="mb-2 border-b">
                        <strong>{p.name}</strong> - {p.category} - S/{p.price}
                        <p>{p.description}</p>
                    </li>
                ))}
            </ul>
        </div>
    );
}
