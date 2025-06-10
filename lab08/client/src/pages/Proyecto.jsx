// 📁 src/pages/ProyectosPage.jsx
import { useState, useEffect } from 'react';
import { getProyectos, createProyecto, updateProyecto, deleteProyecto,getIngenierosByProyecto } from '../service/proyectoService';

import { getDepartamentos } from '../service/departamentoService';
import { getIngenieros } from '../service/ingenieroService';

export default function ProyectosPage() {
  const [proyectos, setProyectos] = useState([]);
  const [departamentos, setDepartamentos] = useState([]);
  const [ingenieros, setIngenieros] = useState([]);
  const [form, setForm] = useState({ nomProy: '', iniFechProy: '', terFechProy: '', departamento: { idDep: '' }, ingenieros: [] });
  const [editingId, setEditingId] = useState(null);
  const [modalProy, setModalProy] = useState(null);
  const [modalIng, setModalIng] = useState([]);

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
    const payload = {
      nomProy: form.nomProy,
      iniFechProy: form.iniFechProy,
      terFechProy: form.terFechProy,
      departamento: { idDep: form.departamento.idDep },
      ingenieros: form.ingenieros.map(id => ({ idIng: id }))
    };
    if (editingId) {
      await updateProyecto(editingId, payload);
      console.log('Proyecto actualizado:', payload);
    } else {
      await createProyecto(payload);
      console.log('Proyecto creado:', payload);
    }
    setForm({ nomProy: '', iniFechProy: '', terFechProy: '', departamento: { idDep: '' }, ingenieros: [] });
    setEditingId(null);
    refresh();
  };

  const handleEdit = (p) => {
    setForm({
      nomProy: p.nomProy,
      iniFechProy: p.iniFechProy,
      terFechProy: p.terFechProy,
      departamento: { idDep: p.departamento?.idDep || '' },
      ingenieros: []
    });
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

  return (
    <div className="p-4">
      <h2 className="text-xl font-bold mb-2">Proyectos</h2>
      <form onSubmit={handleSubmit} className="space-y-2">
        <input className="border p-1 w-full" placeholder="Nombre" value={form.nomProy} onChange={e => setForm({ ...form, nomProy: e.target.value })} />
        <input className="border p-1 w-full" placeholder="Fecha Inicio" type="date" value={form.iniFechProy} onChange={e => setForm({ ...form, iniFechProy: e.target.value })} />
        <input className="border p-1 w-full" placeholder="Fecha Fin" type="date" value={form.terFechProy} onChange={e => setForm({ ...form, terFechProy: e.target.value })} />
        <select className="border p-1 w-full" value={form.departamento.idDep} onChange={e => setForm({ ...form, departamento: { idDep: e.target.value } })}>
          <option value="">Seleccione Departamento</option>
          {departamentos.map(dep => <option key={dep.idDep} value={dep.idDep}>{dep.nomDep}</option>)}
        </select>
        <label className="block">Ingenieros</label>
        <div className="grid grid-cols-2 gap-2">
          {ingenieros.map(ing => (
            <label key={ing.idIng} className="flex items-center gap-1">
              <input type="checkbox" checked={form.ingenieros.includes(ing.idIng)} onChange={e => {
                const selected = form.ingenieros.includes(ing.idIng)
                  ? form.ingenieros.filter(id => id !== ing.idIng)
                  : [...form.ingenieros, ing.idIng];
                setForm({ ...form, ingenieros: selected });
              }} />
              {ing.namIng}
            </label>
          ))}
        </div>
        <button type="submit" className="bg-blue-500 text-white px-3 py-1 rounded">{editingId ? 'Actualizar' : 'Crear'}</button>
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
            <ul>
              {modalIng.map(i => (
                <li key={i.idIng}>{i.namIng} - {i.espIng}</li>
              ))}
            </ul>
            <button onClick={() => setModalProy(null)} className="mt-2 bg-blue-500 text-white px-3 py-1 rounded">Cerrar</button>
          </div>
        </div>
      )}
    </div>
  );
}
