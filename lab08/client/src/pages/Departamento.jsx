
import { useState, useEffect } from 'react';
import {
  getDepartamentos,
  createDepartamento,
updateDepartamento,
  deleteDepartamento,
  getDepartamentoById,
} from '../service/departamentoService';

export default function DepartamentosPage() {
  const [departamentos, setDepartamentos] = useState([]);
  const [form, setForm] = useState({ nomDep: '', telDep: '', faxDep: '' });
  const [editingId, setEditingId] = useState(null);
  const [modalOpen, setModalOpen] = useState(false);
  const [selectedProjects, setSelectedProjects] = useState([]);

  useEffect(() => {
    refresh();
  }, []);

  const refresh = async () => {
    const res = await getDepartamentos();
    setDepartamentos(res.data);
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    if (editingId) await updateDepartamento(editingId, form);
    else await createDepartamento(form);
    setForm({ nomDep: '', telDep: '', faxDep: '' });
    setEditingId(null);
    refresh();
  };

  const handleEdit = (item) => {
    setForm({
      nomDep: item.nomDep,
      telDep: item.telDep,
      faxDep: item.faxDep,
    });
    setEditingId(item.idDep);
  };

  const handleDelete = async (id) => {
    await deleteDepartamento(id);
    refresh();
  };

  const handleShowProjects = async (idDep) => {
    const res = await getDepartamentoById(idDep);
    setSelectedProjects(res.data.proyectos || []);
    setModalOpen(true);
  };

  return (
    <div className="p-6 max-w-3xl mx-auto">
      <h2 className="text-2xl font-bold mb-4">Departamentos</h2>
      <form
        onSubmit={handleSubmit}
        className="flex flex-col gap-4 bg-white p-4 rounded shadow"
      >
        <input
          className="border p-2 rounded"
          placeholder="Nombre"
          value={form.nomDep}
          onChange={(e) => setForm({ ...form, nomDep: e.target.value })}
        />
        <input
          className="border p-2 rounded"
          placeholder="Teléfono"
          value={form.telDep}
          onChange={(e) => setForm({ ...form, telDep: e.target.value })}
        />
        <input
          className="border p-2 rounded"
          placeholder="Fax"
          value={form.faxDep}
          onChange={(e) => setForm({ ...form, faxDep: e.target.value })}
        />
        <button
          type="submit"
          className="bg-blue-600 text-white py-2 px-4 rounded hover:bg-blue-700"
        >
          {editingId ? 'Actualizar' : 'Crear'}
        </button>
      </form>

      <ul className="mt-6 space-y-4">
        {departamentos?.map((d) => (
          <li
            key={d.idDep}
            className="bg-gray-100 p-4 rounded shadow flex flex-col gap-2"
          >
            <p>
              <strong>Nombre:</strong> {d.nomDep} <br />
              <strong>Teléfono:</strong> {d.telDep} <br />
              <strong>Fax:</strong> {d.faxDep}
            </p>
            <div className="flex gap-2">
              <button
                className="bg-yellow-500 text-white px-3 py-1 rounded hover:bg-yellow-600"
                onClick={() => handleEdit(d)}
              >
                Editar
              </button>
              <button
                className="bg-red-500 text-white px-3 py-1 rounded hover:bg-red-600"
                onClick={() => handleDelete(d.idDep)}
              >
                Eliminar
              </button>
              <button
                className="bg-indigo-600 text-white px-3 py-1 rounded hover:bg-indigo-700"
                onClick={() => handleShowProjects(d.idDep)}
              >
                Ver proyectos
              </button>
            </div>
          </li>
        ))}
      </ul>

     
      {modalOpen && (
    <div className="fixed inset-0 backdrop-blur-sm bg-black/30 flex justify-center items-center">
          <div className="bg-white rounded-lg p-6 w-[90%] max-w-lg relative">
            <h3 className="text-lg font-semibold mb-4">Proyectos del Departamento</h3>
            {selectedProjects.length === 0 ? (
              <p>No hay proyectos registrados.</p>
            ) : (
              <ul className="space-y-2">
                {selectedProjects?.map((proy) => (
                  <li
                    key={proy.idProy}
                    className="border p-2 rounded bg-gray-50"
                  >
                    <strong>{proy.nomProy}</strong>
                    <p>Inicio: {proy.iniFechProy}</p>
                    <p>Fin: {proy.terFechProy}</p>
                  </li>
                ))}
              </ul>
            )}
            <button
              className="absolute top-2 right-2 text-gray-500 hover:text-red-500"
              onClick={() => setModalOpen(false)}
            >
              ✕
            </button>
          </div>
        </div>
      )}
    </div>
  );
}
