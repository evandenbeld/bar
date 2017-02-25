package nl.tumbolia.bar.badmin.cocktail

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import org.springframework.data.annotation.PersistenceConstructor

class Cocktail constructor(@Id val name: String, val description: String)
{
	 override fun toString(): String = java.lang.String.format("%s, %sf", name, description)
}