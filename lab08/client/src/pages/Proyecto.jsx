// 📁 src/pages/ProyectosPage.jsx
import { useState, useEffect } from 'react';
import {
  getProyectos,
  createProyecto,
  updateProyecto,
  deleteProyecto,
  getIngenierosByProyecto
} from '../service/proyectoService';
import { getDepartamentos } from '../service/departamentoService';
import { getIngenieros } from '../service/ingenieroService';

export default function ProyectosPage() {
  const [proyectos, setProyectos] = useState([]);
  const [departamentos, setDepartamentos] = useState([]);
  const [ingenieros, setIngenieros] = useState([]);
  const [form, setForm] = useState({
    nomProy: '',
    iniFechProy: '',
    terFechProy: '',
    departamento: { idDep: '' },
    ingenieros: []
  });
  const [editingId, setEditingId] = useState(null);
  const [modalProy, setModalProy] = useState(null);
  const [modalIng, setModalIng] = useState([]);
  const [ingenieroSeleccionado, setIngenieroSeleccionado] = useState('');

  useEffect(() => {
    refresh();
    getDepartamentos().then(res => setDepartamentos(res.data));
    getIngenieros().then(res => setIngenieros(res.data));
  }, []);

  const refresh = async () => {
    const res = await getProyectos();
    setProyectos(res.data);
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    /*
    *const payload = {
      nomProy: form.nomProy,
      iniFechProy: form.iniFechProy,
      terFechProy: form.terFechProy,
      departamento: { idDep: form.departamento.idDep },
      ingenieros: form.ingenieros.map(id => ({ idIng: id }))
    };*/

    const payload = {
      nomProy: form.nomProy,
      iniFechProy: form.iniFechProy,
      terFechProy: form.terFechProy,
      idDep: form.departamento.idDep,
      ingenieroIds: form.ingenieros // Aquí también asegúrate que estás enviando un array de IDs, no objetos
    };

    if (editingId) {
      await updateProyecto(editingId, payload);
      console.log('Proyecto actualizado:', payload);
    } else {
      await createProyecto(payload);
      console.log('Proyecto creado:', payload);
    }

    setForm({ nomProy: '', iniFechProy: '', terFechProy: '', departamento: { idDep: '' }, ingenieros: [] });
    setIngenieroSeleccionado('');
    setEditingId(null);
    refresh();
  };

  const handleEdit = (p) => {
    setForm({
      nomProy: p.nomProy,
      iniFechProy: p.iniFechProy,
      terFechProy: p.terFechProy,
      departamento: { idDep: p.departamento?.idDep || '' },
      ingenieros: p.ingenieros?.map(i => i.idIng) || []
    });

    /*setForm({
      nomProy: p.nomProy,
      iniFechProy: p.iniFechProy,
      terFechProy: p.terFechProy,
      idDep: p.departamento?.idDep || '',
      ingenieros: p.ingenieros?.map(i => i.idIng) || []
    });*/
    setIngenieroSeleccionado('');
    setEditingId(p.idProy);
  };

  const handleDelete = async (id) => {
    await deleteProyecto(id);
    refresh();
  };

  const openIngenieros = async (idProy) => {
    const res = await getIngenierosByProyecto(idProy);
    setModalIng(res.data);
    setModalProy(idProy);
  };

  const addIngeniero = () => {
    if (ingenieroSeleccionado && !form.ingenieros.includes(ingenieroSeleccionado)) {
      setForm({
        ...form,
        ingenieros: [...form.ingenieros, ingenieroSeleccionado]
      });
      setIngenieroSeleccionado('');
    }
  };

  const removeIngeniero = (id) => {
    setForm({
      ...form,
      ingenieros: form.ingenieros.filter(i => i !== id)
    });
  };

  return (
    <div className="p-4">
      <h2 className="text-xl font-bold mb-2">Proyectos</h2>

      <form onSubmit={handleSubmit} className="space-y-2">
        <input
          className="border p-1 w-full"
          placeholder="Nombre"
          value={form.nomProy}
          onChange={e => setForm({ ...form, nomProy: e.target.value })}
        />
        <input
          className="border p-1 w-full"
          placeholder="Fecha Inicio"
          type="date"
          value={form.iniFechProy}
          onChange={e => setForm({ ...form, iniFechProy: e.target.value })}
        />
        <input
          className="border p-1 w-full"
          placeholder="Fecha Fin"
          type="date"
          value={form.terFechProy}
          onChange={e => setForm({ ...form, terFechProy: e.target.value })}
        />

        <select
          className="border p-1 w-full"
          value={form.departamento.idDep}
          onChange={e => setForm({ ...form, departamento: { idDep: e.target.value } })}
        >
          <option value="">Seleccione Departamento</option>
          {departamentos.map(dep => (
            <option key={dep.idDep} value={dep.idDep}>{dep.nomDep}</option>
          ))}
        </select>

        <div className="border-t pt-2">




          <div className="mt-2">
            <h4 className="font-medium mb-1">Ingenieros asignados:</h4>

            {/* Select para elegir un ingeniero */}
            <div className="flex gap-2 mb-2">
              <select
                className="border p-1 flex-1"
                value={ingenieroSeleccionado}
                onChange={e => setIngenieroSeleccionado(e.target.value)}
              >
                <option value="">Seleccione Ingeniero</option>
                {ingenieros
                  .filter(ing => !form.ingenieros.includes(ing.idIng))
                  .map(ing => (
                    <option key={ing.idIng} value={ing.idIng.toString()}>
                      {ing.namIng} - {ing.espIng}
                    </option>
                  ))}
              </select>
              <button
                type="button"
                className="bg-blue-500 text-white px-3 rounded"
                onClick={() => {
                  const idNum = parseInt(ingenieroSeleccionado);
                  if (
                    ingenieroSeleccionado &&
                    !form.ingenieros.includes(idNum)
                  ) {
                    setForm(prev => ({
                      ...prev,
                      ingenieros: [...prev.ingenieros, idNum]
                    }));
                    setIngenieroSeleccionado('');
                  }
                }}
              >
                Añadir
              </button>
            </div>

            {/* Lista de ingenieros asignados */}
            <ul className="space-y-1">
              {form.ingenieros.map(id => {
                const ing = ingenieros.find(i => i.idIng === id);
                if (!ing) return null; // No renderiza si no encuentra
                return (
                  <li key={ing.idIng} className="flex justify-between items-center border p-1 rounded">
                    <span>{ing.namIng} - {ing.espIng}</span>
                    <button
                      type="button"
                      onClick={() =>
                        setForm(prev => ({
                          ...prev,
                          ingenieros: prev.ingenieros.filter(i => i !== id)
                        }))
                      }
                      className="bg-red-500 text-white px-2 py-0.5 rounded"
                    >
                      Eliminar
                    </button>
                  </li>
                );
              })}
            </ul>
          </div>
        </div>

        <button type="submit" className="bg-green-600 text-white px-3 py-1 rounded">
          {editingId ? 'Actualizar' : 'Crear'}
        </button>
      </form>

      <ul className="mt-4 space-y-2">
        {proyectos.map(p => (
          <li key={p.idProy} className="border p-2 rounded flex justify-between items-center">
            <span>{p.nomProy}</span>
            <div className="space-x-2">
              <button onClick={() => handleEdit(p)} className="bg-yellow-400 px-2 py-1 rounded">Editar</button>
              <button onClick={() => handleDelete(p.idProy)} className="bg-red-400 px-2 py-1 rounded">Eliminar</button>
              <button onClick={() => openIngenieros(p.idProy)} className="bg-green-400 px-2 py-1 rounded">Ver Ingenieros</button>
            </div>
          </li>
        ))}
      </ul>

      {modalProy && (
        <div className="fixed inset-0 backdrop-blur-sm bg-black/30 flex justify-center items-center">
          <div className="bg-white p-4 rounded w-96">
            <h3 className="text-lg font-bold mb-2">Ingenieros del Proyecto</h3>
            <ul className="space-y-1">
              {modalIng.map(i => (
                <li key={i.idIng}>{i.namIng} - {i.espIng}</li>
              ))}
            </ul>
            <button
              onClick={() => setModalProy(null)}
              className="mt-3 bg-blue-500 text-white px-3 py-1 rounded"
            >
              Cerrar
            </button>
          </div>
        </div>
      )}
    </div>
  );
}
