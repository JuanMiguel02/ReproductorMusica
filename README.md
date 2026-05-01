# Reproductor de Música - Patrón Iterator

Este proyecto es una aplicación de escritorio desarrollada en JavaFX que implementa un reproductor de música funcional, enfocándose en la aplicación del patrón de diseño **Iterator**.

## 🚀 Funcionalidades

- **Reproducción de Audio:** Soporte para archivos `.mp3` y `.wav`.
- **Gestión de Listas de Reproducción:** Permite navegar secuencialmente por las canciones cargadas.
- **Historial de Reproducción:** Registro automático de las canciones escuchadas, permitiendo navegar desde la más reciente a la más antigua (LIFO).
- **Búsqueda y Filtrado:** Filtrado dinámico de canciones por nombre, artista o álbum.
- **Gestión de Biblioteca:** Formulario para añadir nuevas canciones detectando automáticamente su duración.
- **Interfaz Moderna:** Diseño oscuro con controles personalizados y barra de progreso sincronizada.

## 🛠️ Tecnologías Utilizadas

- **Lenguaje:** Java 17+
- **Framework UI:** JavaFX
- **Gestor de Dependencias:** Maven
- **Librerías de Iconos:** Ikonli (AntDesignIcons)
- **Pruebas:** JUnit 5

## 📐 Patrón de Diseño: Iterator

El corazón de este proyecto es el patrón **Iterator**, que se utiliza para desacoplar las colecciones de canciones de la lógica de navegación.

### Estructura:
- **`SongIterator` (Interfaz):** Define las operaciones de navegación (`hasNext`, `getNext`, `hasPrevious`, `getPrevious`).
- **`SongCollection` (Interfaz):** Define la interfaz para crear un iterador.
- **Implementaciones Concretas:**
    - **`PlaylistIterator`:** Recorre una `Playlist` de forma secuencial (primera a última).
    - **`HistoryIterator`:** Recorre el `HistoryLog` en orden inverso (más reciente a más antigua).

## 📂 Estructura del Proyecto

- `src/main/java`: Código fuente organizado en paquetes (model, controller, viewController, repository, services).
- `src/main/resources`: Archivos FXML, CSS, iconos y archivos de audio de ejemplo.
- `src/main/test`: Pruebas unitarias para validar la lógica de los iteradores.

## ⚙️ Configuración y Ejecución

1. Asegúrate de tener instalado **Java 17** o superior y **Maven**.
2. Clona el repositorio.
3. Ejecuta el proyecto usando Maven:
   ```bash
   mvn javafx:run
   ```

## 🧪 Pruebas

Para ejecutar las pruebas unitarias y verificar el funcionamiento de los iteradores:
```bash
mvn test
```
