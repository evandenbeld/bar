package nl.tumbolia.bar.badmin.cocktail

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import org.springframework.data.annotation.PersistenceConstructor
import com.fasterxml.jackson.annotation.JsonCreator
import org.springframework.data.mongodb.repository.MongoRepository

data class Cocktail (@Id val name: String, val description: String)
//FIXME ingredients

interface CocktailRepository : MongoRepository<Cocktail, String> 