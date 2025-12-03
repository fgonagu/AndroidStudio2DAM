# Calculadora de Peso Ideal (IMC)

Aplicación para Android desarrollada en **Java** que permite calcular su peso ideal utilizando tres fórmulas diferentes, considerando la altura, el año de nacimiento y el sexo.

---

## Características

* **Fórmulas:** Permite elegir entre los métodos de cálculo de peso ideal más conocidos:
  * **Fórmula de Broca:** La más simple, basada solo en la altura.
  * **Fórmula de Perrault:** Considera la altura y la edad.
  * **Fórmula de Wan Der Vael:** Considera la altura y el sexo (hombres y mujeres)
* **Compatibilidad:** Utiliza `java.util.Calendar` para obtener el año actual, garantizando la compatibilidad con dispositivos con API **24 (Android 7.0)** y superiores.
* **Efecto Visual:** Muestra una iágen ("feliz" o "enojado") dependiendo de si el peso actual del usuario se encuentra dentro del rango aceptable (+-10% del peso ideal calculado).


## Uso
1. Introduce tu **Altura** en centímetros.
2. Introduce tu **Año de Nacimiento**.
3. Introduce tu **Peso Actual** en kilogramos.
4. Selecciona tu **Sexo** (Mujer o Hombre).
5. Selecciona el **Método de Cálculo** (Broca, Perrault o Wan Der Vael).
6. Haz clic en el botón **CALCULAR**

La aplicación mostrará el peso ideal resultante y una imagen que indica si tu peso actual está cerca o lejos del ideal calculado.

## Autor / Contacto
* **Nombre:** [Francisco Manuel González Aguilera]
* **Curso:** [2º DAM]
* **Fecha:** [DICIEMBRE 2025]