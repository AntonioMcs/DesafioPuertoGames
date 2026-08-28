class Articulo {
    var codigo: String = ""
    var nombre: String = ""
    var categoria: String = ""

    private var precio: Int = 0
    private var stock: Int = 0

    fun definirPrecio(nuevoPrecio: Int) {
        if (nuevoPrecio > 0) {
            precio = nuevoPrecio
        }
    }

    fun cargarStock(cantidad: Int) {
        if (cantidad > 0) {
            stock = cantidad
        }
    }

    fun vender(cantidad: Int): Boolean {
        if (cantidad <= 0 || cantidad > stock) {
            return false
        }
        stock -= cantidad
        return true
    }

    fun obtenerStock(): Int = stock

    fun valorTotal(): Int = precio * stock

    fun valorCompra(cantidad: Int): Int = precio * cantidad

    fun precioFormateado(): String {
        return "$" + "%,d".format(precio).replace(",", ".")
    }

    fun detalle(): String {
        return "$codigo / $nombre / $categoria / ${precioFormateado()} / $stock Unidades"
    }
}

fun main() {
    println("BIENVENIDO A PUERTO GAMES")

    val productos = mutableListOf<Articulo>().apply {
        add(Articulo().apply {
            codigo = "C001"
            nombre = "Play Station 2"
            categoria = "Consola"
            definirPrecio(89990)
            cargarStock(2)
        })
        add(Articulo().apply {
            codigo = "C002"
            nombre = "Sega Genesis"
            categoria = "Consola"
            definirPrecio(59990)
            cargarStock(3)
        })
        add(Articulo().apply {
            codigo = "C003"
            nombre = "Super Nintendo"
            categoria = "Consola"
            definirPrecio(79990)
            cargarStock(2)
        })
        add(Articulo().apply {
            codigo = "A001"
            nombre = "Mouse Gamer"
            categoria = "Accesorio"
            definirPrecio(19990)
            cargarStock(6)
        })
    }

    val articuloNuevo = Articulo().apply {
        codigo = "A002"
        nombre = "Audifonos Gamer"
        categoria = "Accesorio"
        definirPrecio(49990)
        cargarStock(5)
    }.also {
        println("Articulo registrado: ${it.nombre}")
    }

    productos.add(articuloNuevo)
    productos.also { println("Catalogo creado con ${it.size} productos") }

    var opcion = ""
    while (opcion != "4") {
        println("\nMENU PUERTO GAMES")
        println("1. Ver catalogo")
        println("2. Buscar producto y comprar")
        println("3. Ver resumen del inventario")
        println("4. Salir")
        println("Selecciona una opcion:")
        opcion = readln().trim()

        when (opcion) {
            "1" -> with(productos) {
                println("CATALOGO COMPLETO")
                forEachIndexed { index, producto ->
                    println("${index + 1}. ${producto.detalle()}")
                }
            }
            "2" -> {
                println("BUSCAR PRODUCTO")
                println("Ingresa el ID, codigo o nombre del producto")
                val busqueda = readln().trim()
                val productoEncontrado = productos.find {
                    it.codigo.equals(busqueda, ignoreCase = true) ||
                            it.nombre.equals(busqueda, ignoreCase = true)
                }

                productoEncontrado?.let {
                    println("Producto encontrado: ${it.nombre}")
                    println("Stock disponible: ${it.obtenerStock()}")
                    var cantidad = 0
                    while (cantidad <= 0 || cantidad > it.obtenerStock()) {
                        println("Cuantas unidades quieres comprar?")
                        cantidad = readln().trim().toIntOrNull() ?: 0
                        if (cantidad <= 0 || cantidad > it.obtenerStock()) {
                            println("Error: ingresa una cantidad entre 1 y ${it.obtenerStock()}")
                        }
                    }

                    if (it.vender(cantidad)) {
                        it.run {
                            println("Compra realizada: $nombre")
                            println("Unidades compradas: $cantidad")
                            println("Stock restante: ${obtenerStock()}")
                            println("Total a pagar: $${"%,d".format(valorCompra(cantidad)).replace(",", ".")}")
                        }
                    }
                } ?: println("Producto no encontrado, intenta nuevamente")
            }
            "3" -> with(productos) {
                val valorTotalInventario = sumOf { it.valorTotal() }
                println("RESUMEN DE PUERTO GAMES")
                println("Cantidad de articulos: $size")
                println("Valor total actualizado del inventario: $${"%,d".format(valorTotalInventario).replace(",", ".")}")
            }
            "4" -> println("Gracias por usar Puerto Games")
            else -> println("Opcion no valida, intenta nuevamente")
        }
    }
}
