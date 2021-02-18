const URL = '/restPrueba/api/v2/facturas/';

window.onload = async function() {
	var factur = listar();
	await fetch(URL, { method: 'POST', body: JSON.stringify(factur), headers: new Headers({'content-type': 'application/json'})});
}

function listar() {
	var factura = {}
	factura.id = 1
	factura.fecha = "2021-01-01"
	
	var cliente = {}
	cliente.id = 100
	cliente.nombre = "cr"
	cliente.apellidos = "7"
	factura.cliente = cliente
	
	var ventas = []
	
	var venta = {}
	venta.id = 1
	venta.cantidad = 2
	var producto = {}
	producto.id = 1
	producto.nombre = "ratón"
	producto.precio = 2.75
	venta.producto = producto
	
	var venta2 = {}
	venta2.id = 1
	venta2.cantidad = 2
	var producto2 = {}
	producto2.id = 1
	producto2.nombre = "pantalla"
	producto2.precio = 25.5
	venta2.producto2 = producto
		
	ventas.push(venta)
	ventas.push(venta2)
	
	factura.ventas = ventas
	
	return factura
}
