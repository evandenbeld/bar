package nl.tumbolia.bar.badmin.cocktail

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
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.RequestMapping

@RestController
@RequestMapping("/api")
class CocktailController(val cocktails: CocktailRepository) {
	//FIXME authz /oauth

	@GetMapping
	fun findAll() = cocktails.findAll()

	@GetMapping("/{id}")
	fun findByName(@PathVariable id: String) = cocktails.findOne(id)

	@PostMapping
	fun addCocktail(@RequestBody @Valid cocktail: Cocktail): ResponseEntity<Cocktail> {
		if (cocktails.exists(cocktail.id)) {
			return ResponseEntity.status(HttpStatus.CONFLICT).body(null)
		}
		return ResponseEntity.status(HttpStatus.CREATED).body(cocktails.insert(cocktail))
	}

	@PutMapping("/{id}")
	fun updateCocktail(@RequestBody @Valid cocktail: Cocktail, @PathVariable id: String): ResponseEntity<Cocktail> {

		if (!cocktail.id.equals(id)) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null)
		}

		if (!cocktails.exists(id)) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null)
		}

		return ResponseEntity.ok(cocktails.save(cocktail))
	}

	@DeleteMapping("/{id}")
	fun deleteCocktail(@PathVariable id: String): ResponseEntity<Void> {
		if (!cocktails.exists(id)) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null)
		}
		cocktails.delete(id)
		return ResponseEntity.ok(null)
	}
}