package nl.tumbolia.bar.badmin.cocktail

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import org.springframework.data.annotation.PersistenceConstructor
import com.fasterxml.jackson.annotation.JsonCreator
import org.springframework.data.mongodb.repository.MongoRepository
import javax.validation.constraints.Size
import javax.validation.constraints.NotNull

data class Cocktail(
		@Id @Size(min = 2, max = 20) val id: String,
		@NotNull @Size(max = 30) val name: String,
		val description: String,
		val recipe: String,
		val ingredients: MutableList<Ingredient>
)

data class Ingredient(
		@Id @Size(min = 2, max = 20) val id: String,
		@NotNull @Size(max = 30) val name: String,
		val amount: Int,
		val unit: String
)
	
interface CocktailRepository : MongoRepository<Cocktail, String> 