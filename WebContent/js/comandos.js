const URL = '/restPrueba/api/v2/clientes/';

window.busc = function(selector) {
	var selectorType = 'querySelectorAll';

	if (selector.indexOf('#') === 0) {
		selectorType = 'getElementById';
		selector = selector.substr(1, selector.length);
	}

	return document[selectorType](selector);
}

window.onload = function() {
	listar();
	
	busc('form')[0].onsubmit = function(e) {
		e.preventDefault();
		
		const id = parseInt(busc('#iId').value);
		const nombre = busc('#iNombre').value;
		const apellidos = busc('#iApellidos').value;
		
		const cliente = {id, nombre, apellidos};

		if (id) {
			fetch(URL + id, { method: 'PUT', body: JSON.stringify(cliente), headers: new Headers({'content-type': 'application/json'})}).then(
				function (resp) {
					if (resp.ok) {
						listar();
					} else {
						mostrarErrores(resp);
					}			
				});
		} else {
			fetch(URL, { method: 'POST', body: JSON.stringify(cliente), headers: new Headers({'content-type': 'application/json'})}).then(listar());
		}
	}
	
	busc('#btnInsertar').onclick = function(e) {
		e.preventDefault();
		
		busc('#iId').value = '';
		busc('#iNombre').value = '';
		busc('#iApellidos').value = '';
	}
}

async function listar() {
	const respuesta = await fetch(URL);
	const clientes = await respuesta.json();
	
	const tbody = busc('tbody')[0];
	
	tbody.innerHTML = '';
	
	let fila;
	
	clientes.forEach( function(cliente) {
		fila = document.createElement('tr');
		fila.innerHTML = `
            <td>${cliente.id}</td>
            <td>${cliente.nombre}</td>
            <td>${cliente.apellidos}</td>
            <td>
				<a href="javascript:editar(${cliente.id})">Editar</a>
				<a href="javascript:borrar(${cliente.id})">Borrar</a>
			</td>`;
		
		tbody.appendChild(fila);
	});
}

async function editar(id) {
	const respuesta = await fetch(URL + id);
	const cliente = await respuesta.json();

	busc('#iId').value = cliente.id;
	busc('#iNombre').value = cliente.nombre;
	busc('#iApellidos').value = cliente.apellidos;
}
    
function borrar(id) {
    fetch(URL + id, { method: 'DELETE' }).then(listar());
}

async function mostrarErrores(resp) {
	const errores = await resp.json();
	
	// TODO que se muestre por boostrap (onload)
	busc('#if1').value = errores;
}

