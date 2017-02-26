package nl.tumbolia.bar.badmin.cocktail

import org.springframework.boot.CommandLineRunner
import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.context.annotation.Bean
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController
import javax.validation.Valid

@RestController
class CocktailController (val cocktails:CocktailRepository) 
{
	//FIXME authz /oauth
	
	@GetMapping("/")
	fun findAll() = cocktails.findAll()
	
	@GetMapping("/{name}")
	fun findByName(@PathVariable name:String) = cocktails.findOne(name)
	
	@PostMapping
	fun addCocktail(@RequestBody @Valid cocktail: Cocktail) 
	{
	    if(cocktails.exists(cocktail.name)) 
	    {
	       throw CocktailExistsException()
	    }
	    cocktails.insert(cocktail)
	}
	
	@PutMapping("/{name}")
	fun updateCocktail(@RequestBody @Valid cocktail: Cocktail, @PathVariable name:String) 
	{
		
		if(!cocktail.name.equals(name))
		{
			throw IllegalArgumentException()
		}
		
		if(!cocktails.exists(name)) 
		{
	       throw CocktailNotFoundException()
	    }
		
		cocktails.save(cocktail);
	}
	
	@DeleteMapping("/{name}")
	fun deleteCocktail(@PathVariable name:String) 
	{
	    if(!cocktails.exists(name)) 
	    {
	        throw CocktailNotFoundException()
	    }
	    cocktails.delete(name)
	}
}

@ResponseStatus(value=HttpStatus.NOT_FOUND, reason="Cocktail not found")
class CocktailNotFoundException : Exception ()
	
@ResponseStatus(value=HttpStatus.CONFLICT, reason="Cocktail allready exists")
class CocktailExistsException : Exception ()

@SpringBootApplication
open class Application  {
	
	companion object {
		@JvmStatic fun main(args: Array<String>) {
			SpringApplication.run(Application::class.java, *args)
		}
	}		
}