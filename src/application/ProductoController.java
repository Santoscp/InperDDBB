package application;

import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;

public class ProductoController {
    @FXML
    private TextField nameField; // Campo para mostrar el nombre del producto seleccionado
    @FXML
    private ListView<Producto> productoListView; // ListView que muestra la lista de productos
    @FXML
    private Button deleteButton; // Botón para eliminar productos coincidentes
    @FXML
    private Button deleteSelectedButton; // Botón para eliminar el producto seleccionado
    @FXML
    private TextField searchField; // Campo de búsqueda para filtrar productos

    // Lista observable para almacenar los productos
    private ObservableList<Producto> productoList = FXCollections.observableArrayList();

    @FXML
    public void initialize() {
        loadProduct(); // Cargar productos al iniciar la aplicación
        productoListView.setItems(productoList); // Asignar la lista de productos al ListView

        // Acción para el botón de eliminar todos los productos que coincidan
        deleteButton.setOnAction(e -> deleteMatchingProducts());

        // Acción para eliminar el producto seleccionado
        deleteSelectedButton.setOnAction(e -> deleteSelectedProduct());

        // Listener para el campo de búsqueda
        searchField.textProperty().addListener((obs, oldText, newText) -> filterProducts(newText));

        // Listener para el ListView: cuando se selecciona un producto, se muestra su nombre en el campo
        productoListView.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                nameField.setText(newSelection.getNombre());
            }
        });
    }

    // Método para cargar productos desde la base de datos
    private void loadProduct() {
        List<Producto> product = DatabaseManager.getAllProduct(); // Obtener todos los productos
        productoList.clear(); // Limpiar la lista actual
        productoList.addAll(product); // Agregar los productos obtenidos a la lista
    }

    // Método que se llama al presionar el botón de eliminar todos los productos que coincidan
    private void deleteMatchingProducts() {
        String nombreABorrar = searchField.getText(); // Obtener el nombre del producto ingresado en el campo de búsqueda
        if (nombreABorrar != null && !nombreABorrar.isEmpty()) {
            int deletedCount = DatabaseManager.deleteProductsByName(nombreABorrar); // Llama al método que borra por nombre
            if (deletedCount > 0) {
                loadProduct(); // Recarga la lista después de eliminar
            }
            searchField.clear(); // Limpia el campo de búsqueda
        }
    }

    // Método para eliminar el producto seleccionado
    private void deleteSelectedProduct() {
        Producto selectedProduct = productoListView.getSelectionModel().getSelectedItem(); // Obtener el producto seleccionado
        if (selectedProduct != null) {
            int deletedCount = DatabaseManager.deleteProductsByName(selectedProduct.getNombre()); // Llama al método que borra por nombre
            if (deletedCount > 0) {
                loadProduct(); // Recarga la lista después de eliminar
            }
            nameField.clear(); // Limpia el campo de nombre del producto seleccionado
        }
    }

    // Método para filtrar productos según el texto ingresado
    private void filterProducts(String filter) {
        ObservableList<Producto> filteredList = FXCollections.observableArrayList();
        for (Producto producto : DatabaseManager.getAllProduct()) {
            if (producto.getNombre().toLowerCase().contains(filter.toLowerCase())) {
                filteredList.add(producto);
            }
        }
        productoListView.setItems(filteredList);
    }
}

