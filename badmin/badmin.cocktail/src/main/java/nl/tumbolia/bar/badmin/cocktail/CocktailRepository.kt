package nl.tumbolia.bar.badmin.cocktail

import org.springframework.data.mongodb.repository.MongoRepository

interface CocktailRepository : MongoRepository<Cocktail, String> 