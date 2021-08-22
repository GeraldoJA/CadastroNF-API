package com.Geraldo.cadastroNF.resouces;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.Geraldo.cadastroNF.domain.Cliente;
import com.Geraldo.cadastroNF.dtos.ClienteDTO;
import com.Geraldo.cadastroNF.service.ClienteService;

@RestController
@RequestMapping(value = "/clientes")
public class ClienteResource {

	@Autowired
	private ClienteService service;
	
	@GetMapping(value = "/{id}")
	public ResponseEntity<Cliente> findById(@PathVariable Integer id) {	
		Cliente obj = service.findById(id);
		
		return ResponseEntity.ok().body(obj);
	}	
	
	/*
	@GetMapping
	public ResponseEntity< List<Cliente> > findAll() {
		
		List<Cliente> list = service.findAll();	
		
		return ResponseEntity.ok().body(list);
	}
	*/	
	@GetMapping
	public ResponseEntity< List<ClienteDTO> > findAll() {
		
		List<Cliente> list = service.findAll();
		List<ClienteDTO> listDTO = list.stream().map( obj -> new ClienteDTO(obj) ).collect( Collectors.toList() );
		
		return ResponseEntity.ok().body(listDTO);
	}
	
	@PostMapping
	public ResponseEntity<Cliente> create( @RequestBody Cliente obj ) {
		
		obj= service.create(obj);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand( obj.getId() ).toUri();
		return ResponseEntity.created(uri).build();
	}
	
	@PutMapping(value = "/{id}")
	public ResponseEntity<Cliente> update( @PathVariable Integer id, @RequestBody Cliente obj ) {
		
		Cliente newObj = service.update(id, obj);

		return ResponseEntity.ok().body( newObj );
	}
	
	@PatchMapping(value = "/{id}")
	public ResponseEntity<Cliente> updatePatch( @PathVariable Integer id, @RequestBody Cliente obj ) {
		
		Cliente newObj = service.updatePatch(id, obj);
		
		return ResponseEntity.ok().body( newObj );
	}
	
	@DeleteMapping(value = "/{id}")
	public ResponseEntity<Void> delete( @PathVariable Integer id ){
		
		service.delete(id);
		
		return ResponseEntity.noContent().build();
	}
	
}

//localhost:8080/clientes/1