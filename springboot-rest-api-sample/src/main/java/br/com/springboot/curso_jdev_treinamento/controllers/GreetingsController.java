package br.com.springboot.curso_jdev_treinamento.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import br.com.springboot.curso_jdev_treinamento.model.Usuario;
import br.com.springboot.curso_jdev_treinamento.repository.UsuarioRepository;


/**
 *
 * A sample greetings controller to return greeting text
 */
@RestController
public class GreetingsController {
	
	
	@Autowired // ic/cd ou cdi - Injeção de dependencia*/
	private UsuarioRepository usuarioRepository;
	
	
    /**
     *
     * @param name the name to greet
     * @return greeting text
     */
    @RequestMapping(value = "/{name}", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public String greetingText(@PathVariable String name) {
        return "Curso Spring Boot API " + name + "!";
    }
    
    @RequestMapping(value = "/olamundo/{nome}", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public String retornarOlaMundo(@PathVariable String nome) {
    	
    	Usuario usuario = new Usuario();
    	usuario.setNome(nome);
    	
    	usuarioRepository.save(usuario); // vai gravar no banco de dados
    	
    	return "ola mundo " + nome;
    	
    }
    
    @GetMapping(value = "listatodos") // Primeiro metodo de API 
    @ResponseBody // Retorna os dados para o corpo da reposta
    public ResponseEntity<List<Usuario>> listaUsuario(){
    	
    	
    	List<Usuario> usuarios = usuarioRepository.findAll(); //executa a consulta no banco de dados
    	
    	return new ResponseEntity<List<Usuario>>(usuarios, HttpStatus.OK); // retorna a lista em JASON
    	
    }
    
    
    @PostMapping(value = "salvar") // mapeia a url
    @ResponseBody //descricao da resposta
    public ResponseEntity<Usuario> salvar(@RequestBody Usuario usuario) { //Recebe os dados para salvar
    	
    	Usuario user = usuarioRepository.save(usuario);
    	
    	return new ResponseEntity<Usuario>(user, HttpStatus.CREATED);
    }
    
    @PostMapping(value = "delete") // mapeia a url
    @ResponseBody //descricao da resposta
    public ResponseEntity<String> delete(@RequestParam Long iduser) { //deleta datdos
    	
    	usuarioRepository.deleteById(iduser);
    	
    	return new ResponseEntity<String>("usuario deletado com sucesso", HttpStatus.OK);
    	
    	
    }
    
    
    @GetMapping(value = "buscaruserid") // mapeia a url
    @ResponseBody //descricao da resposta
    public ResponseEntity<Usuario> buscaruserid(@RequestParam(name = "iduser") Long iduser) { //deleta datdos
    	
    	Usuario usuario = usuarioRepository.findById(iduser).get();
    	
    	return new ResponseEntity<Usuario>(usuario, HttpStatus.OK);
    	
    	
    }
    
    @PostMapping(value = "atualizar") // mapeia a url
    @ResponseBody //descricao da resposta
    public ResponseEntity<?> atualizar(@RequestBody Usuario usuario) { //Recebe os dados para salvar
    	
    	if(usuario.getId() == null) {
    		
    		return new ResponseEntity<String>("ID não foi informado para atualização", HttpStatus.OK);
    		
    	}
    	
    	Usuario user = usuarioRepository.saveAndFlush(usuario);
    	
    	return new ResponseEntity<Usuario>(user, HttpStatus.OK);
    }
    
    
    @GetMapping(value = "buscaPorNome") // mapeia a url
    @ResponseBody //descricao da resposta
    public ResponseEntity<List<Usuario>> buscaPorNome(@RequestParam(name = "name") String name) { //deleta datdos
    	
    	List<Usuario> usuario = usuarioRepository.buscarPorNome(name.trim().toUpperCase());
    	
    	return new ResponseEntity<List<Usuario>>(usuario, HttpStatus.OK);
    	
    	
    }
    
}
