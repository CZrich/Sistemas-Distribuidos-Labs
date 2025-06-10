package gui;

import controller.CategoriaController;
import model.Categoria;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.List;

public class VentanaPrincipal extends JFrame {
    private JTextField txtId, txtNombre, txtDescripcion;
    private JButton btnPrimero, btnAnterior, btnSiguiente, btnUltimo;
    private JButton btnInsertar, btnModificar, btnEliminar, btnActualizar;

    private CategoriaController controller;
    private List<Categoria> categorias;
    private int posicion = 0;

    public VentanaPrincipal() {
        setTitle("Gestión de Categorías");
        setSize(450, 280);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        controller = new CategoriaController();
        categorias = controller.listar();

        initUI();
        mostrarCategoria(posicion);
    }

    private void initUI() {
        txtId = new JTextField(20); txtId.setEditable(false);
        txtNombre = new JTextField(20);
        txtDescripcion = new JTextField(20);

        btnPrimero = new JButton("Primero");
        btnAnterior = new JButton("Anterior");
        btnSiguiente = new JButton("Siguiente");
        btnUltimo = new JButton("Último");

        btnInsertar = new JButton("Insertar");
        btnModificar = new JButton("Modificar");
        btnEliminar = new JButton("Eliminar");
        btnActualizar = new JButton("Actualizar");

        // Panel campos
        JPanel campos = new JPanel(new GridLayout(3, 2));
        campos.add(new JLabel("ID:")); campos.add(txtId);
        campos.add(new JLabel("Nombre:")); campos.add(txtNombre);
        campos.add(new JLabel("Descripción:")); campos.add(txtDescripcion);

        // Panel CRUD
        JPanel crud = new JPanel();
        crud.add(btnInsertar); crud.add(btnModificar);
        crud.add(btnActualizar); crud.add(btnEliminar);

        // Panel navegación
        JPanel nav = new JPanel();
        nav.add(btnPrimero); nav.add(btnAnterior);
        nav.add(btnSiguiente); nav.add(btnUltimo);

        setLayout(new BorderLayout());
        add(campos, BorderLayout.CENTER);
        add(crud, BorderLayout.NORTH);
        add(nav, BorderLayout.SOUTH);

        eventos();
    }

    private void eventos() {
        btnPrimero.addActionListener(e -> {
            posicion = 0;
            mostrarCategoria(posicion);
        });

        btnAnterior.addActionListener(e -> {
            if (posicion > 0) {
                posicion--;
                mostrarCategoria(posicion);
            }
        });

        btnSiguiente.addActionListener(e -> {
            if (posicion < categorias.size() - 1) {
                posicion++;
                mostrarCategoria(posicion);
            }
        });

        btnUltimo.addActionListener(e -> {
            posicion = categorias.size() - 1;
            mostrarCategoria(posicion);
        });

        btnInsertar.addActionListener(e -> {
            Categoria nueva = new Categoria();
            nueva.setNombre(txtNombre.getText());
            nueva.setDescripcion(txtDescripcion.getText());

            if (controller.insertar(nueva)) {
                JOptionPane.showMessageDialog(this, "Registro insertado");
                recargar();
            } else {
                JOptionPane.showMessageDialog(this, "Error al insertar");
            }
        });

        btnModificar.addActionListener(e -> {
            Categoria actual = new Categoria();
            actual.setId(Integer.parseInt(txtId.getText()));
            actual.setNombre(txtNombre.getText());
            actual.setDescripcion(txtDescripcion.getText());

            if (controller.actualizar(actual)) {
                JOptionPane.showMessageDialog(this, "Registro modificado");
                recargar();
            } else {
                JOptionPane.showMessageDialog(this, "Error al modificar");
            }
        });

        btnEliminar.addActionListener(e -> {
            int id = Integer.parseInt(txtId.getText());
            int confirm = JOptionPane.showConfirmDialog(this, "¿Eliminar registro?", "Confirmar", JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {
                if (controller.eliminar(id)) {
                    JOptionPane.showMessageDialog(this, "Registro eliminado");
                    recargar();
                } else {
                    JOptionPane.showMessageDialog(this, "Error al eliminar");
                }
            }
        });

        btnActualizar.addActionListener(e -> {
            recargar();
            JOptionPane.showMessageDialog(this, "Datos actualizados");
        });
    }

    private void mostrarCategoria(int i) {
        if (categorias.isEmpty()) {
            txtId.setText(""); txtNombre.setText(""); txtDescripcion.setText("");
            return;
        }
        Categoria cat = categorias.get(i);
        txtId.setText(String.valueOf(cat.getId()));
        txtNombre.setText(cat.getNombre());
        txtDescripcion.setText(cat.getDescripcion());
    }

    private void recargar() {
        categorias = controller.listar();
        posicion = 0;
        mostrarCategoria(posicion);
    }
}
