# 1. Subir un proyecto existente a GitHub usando IntelliJ (método gráfico)

## Paso 1: Crear un repositorio vacío en GitHub

- En GitHub → botón New repository.
- Pon un nombre.
- Importante: NO marques README, .gitignore ni licencia. (Para evitar conflictos con tu proyecto local).

## Paso 2: Abrir el proyecto en IntelliJ

Abre IntelliJ y carga el proyecto que ya tenías hecho.

## Paso 3: Inicializar Git en IntelliJ (si tu proyecto no está integrado con Git )

En IntelliJ:

- Menú VCS → Enable Version Control Integration. Solo saldrá esta opción si el proyecto no es Git.
- Elige Git.

![alt text](image-5.png)

Ahora tu proyecto ya está bajo Git.

## Paso 4: Hacer el primer commit

- Abre Commit.
- Selecciona todos los archivos.
- Escribe un mensaje: "Primer commit del proyecto"
- Pulsa Commit.

![alt text](image.png)

## Paso 5: Conectar tu repositorio local con GitHub

- Menú Git → Manage Remotes.

![alt text](image-1.png)

- Añade un nuevo remoto con la URL de GitHub.
- Aceptar.

![alt text](image-2.png)


## Paso 6: Hacer Push al remoto

- Menú Git → Push.
- IntelliJ mandará tu proyecto al repositorio en GitHub.
- ¡Ya está online en tu repositorio!

![alt text](image-3.png)

![alt text](image-4.png)

---

# 2. Proyecto común en clase

Cada alumno debe crear su propia copia (fork).

✔️ **Pasos para hacer un Fork**

1. Entra en el repositorio del profesor en GitHub.
2. Arriba a la derecha → botón Fork.
3. Se crea automáticamente una copia del proyecto en tu GitHub.

✔️ **Clonar el fork en IntelliJ**

1. En IntelliJ → File → New → Project from Version Control.
2. Pega la URL de tu fork (no el del profesor).
3. IntelliJ descargará el proyecto.

## Trabajar en la rama development (colaboración)

El profesor tendrá una rama llamada development.

Los alumnos deben trabajar SIEMPRE en esa rama.

✔️ **Paso 1: Cambiar a la rama development**

En IntelliJ:

- Abajo a la derecha, pincha en la rama (normalmente main).
- Elige development.

Si no aparece:

- Click en Remote branches
- Selecciona origin/development
- Escoge: Checkout as new local branch

✔️ **Paso 2: Crear tu propia rama de trabajo (recomendado)**

Para no pisaros entre vosotros:

1. Abajo a la derecha → Git: development
2. New Branch
3. Nómbrala según tu funcionalidad, ejemplo: endpoint/xxx

✔️ **Paso 3: Hacer tu código y tus commits**

Cada vez que avances:

1. Abre la ventana Commit.
2. Selecciona cambios.
3. Mensaje claro.
4. Pulsa Commit & Push.

## Crear un Pull Request para enviar tus cambios al proyecto del profesor

Cuando termines tu funcionalidad:

✔️ **Paso 1: Haz push de tu rama al fork**

- Menú Git → Push.
- Tu rama aparecerá en tu GitHub personal.

✔️ **Paso 2: Crear el Pull Request desde GitHub**

1. Ve a tu repositorio forkeado en GitHub.
2. Te saldrá un banner amarillo → Compare & Pull Request.
3. Asegúrate de que pone:
    - Base repository: profesor → development
    - Head repository: tu fork → tu rama feature/...
4. Escribe un mensaje explicando tu cambio.
5. Pulsa Create Pull Request.

El profesor revisará tu propuesta y decidirá:

- Hacer merge (aceptar).
- Pedir cambios.
- Rechazar si rompe el proyecto.