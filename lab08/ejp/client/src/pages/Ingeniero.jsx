import { useEffect, useState } from 'react';
import {
  getIngenieros,
  createIngeniero,
  updateIngeniero,
  deleteIngeniero
} from '../service/ingenieroService';

export default function IngenierosPage() {
  const [ingenieros, setIngenieros] = useState([]);
  const [form, setForm] = useState({ namIng: '', carIng: '', espIng: '' });
  const [editingId, setEditingId] = useState(null);

  useEffect(() => {
    refresh();
  }, []);

  const refresh = async () => {
    const res = await getIngenieros();
    setIngenieros(res.data);
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    if (editingId) {
      await updateIngeniero(editingId, form);
    } else {
      await createIngeniero(form);
    }
    setForm({ namIng: '', carIng: '', espIng: '' });
    setEditingId(null);
    refresh();
  };

  const handleEdit = (item) => {
    setForm(item);
    setEditingId(item.idIng);
  };

  const handleDelete = async (id) => {
    await deleteIngeniero(id);
    refresh();
  };

  return (
    <div className="max-w-3xl mx-auto py-8 px-4">
      <h2 className="text-2xl font-bold mb-4 text-center">Gestión de Ingenieros</h2>

      <form onSubmit={handleSubmit} className="bg-white p-4 rounded shadow mb-6 space-y-4">
        <input
          type="text"
          placeholder="Nombre"
          value={form.namIng}
          onChange={(e) => setForm({ ...form, namIng: e.target.value })}
          className="border border-gray-300 p-2 rounded-md w-full focus:ring-2 focus:ring-blue-500 focus:border-transparent"
          required
        />
        <input
          type="text"
          placeholder="Cargo"
          value={form.carIng}
          onChange={(e) => setForm({ ...form, carIng: e.target.value })}
          className="border border-gray-300 p-2 rounded-md w-full focus:ring-2 focus:ring-blue-500 focus:border-transparent"
          required
        />
        <input
          type="text"
          placeholder="Especialidad"
          value={form.espIng}
          onChange={(e) => setForm({ ...form, espIng: e.target.value })}
          className="border border-gray-300 p-2 rounded-md w-full focus:ring-2 focus:ring-blue-500 focus:border-transparent"
          required
        />
        <button
          type="submit"
          className="bg-blue-600 text-white px-4 py-2 rounded hover:bg-blue-700"
        >
          {editingId ? 'Actualizar' : 'Crear'}
        </button>
      </form>

      <ul className="space-y-4">
        {ingenieros.map((i) => (
          <li key={i.idIng} className="bg-gray-100 p-4 rounded shadow flex justify-between items-start">
            <div>
              <p><span className="font-semibold">Nombre:</span> {i.namIng}</p>
              <p><span className="font-semibold">Cargo:</span> {i.carIng}</p>
              <p><span className="font-semibold">Especialidad:</span> {i.espIng}</p>
            </div>
            <div className="flex flex-col gap-2">
              <button
                onClick={() => handleEdit(i)}
                className="bg-blue-500 text-white px-3 py-1 rounded hover:bg-blue-600"
              >
                Editar
              </button>
              <button
                onClick={() => handleDelete(i.idIng)}
                className="bg-red-500 text-white px-3 py-1 rounded hover:bg-red-700"
              >
                Eliminar
              </button>
            </div>
          </li>
        ))}
      </ul>
    </div>
  );
}
