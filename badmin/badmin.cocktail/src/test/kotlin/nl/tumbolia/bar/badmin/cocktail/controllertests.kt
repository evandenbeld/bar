package nl.tumbolia.bar.badmin.cocktail

import org.junit.runner.RunWith
import org.springframework.test.context.junit4.SpringRunner
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.test.web.servlet.MockMvc
import org.junit.Test
import org.mockito.BDDMockito
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultMatchers
import java.util.ArrayList
import com.mongodb.util.JSON
import org.junit.Before
import java.util.Arrays
import kotlin.reflect.jvm.internal.impl.load.kotlin.JvmType.Array
import org.mockito.Mockito

@RunWith(SpringRunner::class)
@WebMvcTest(CocktailController::class)
open class CocktailControllerTest
{
	@Autowired
    lateinit  var mockMvc: MockMvc
	
	@MockBean
    lateinit  var mockCocktailRepository : CocktailRepository
	
	var cocktail: Cocktail = Cocktail("bloudymary", "name", "description", "recipe", ArrayList())
	
	@Before
	fun setup()
	{
		BDDMockito.given(this.mockCocktailRepository.exists("bloudymary")).willReturn(true);
		BDDMockito.given(this.mockCocktailRepository.findOne("bloudymary")).willReturn(cocktail);
	}

    @Test
    fun testFindAll()
	{
    	val cocktails: MutableList<Cocktail> = mutableListOf(cocktail)
    	BDDMockito.given(this.mockCocktailRepository.findAll()).willReturn(cocktails);
		
        mockMvc.perform(MockMvcRequestBuilders.get("/api").accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk()).andExpect(MockMvcResultMatchers.content().string("[{\"id\":\"bloudymary\",\"name\":\"name\",\"description\":\"description\",\"recipe\":\"recipe\",\"ingredients\":[]}]"));
    }
	
	@Test
    fun testFindAllEmpty()
	{   
        mockMvc.perform(MockMvcRequestBuilders.get("/api").accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk()).andExpect(MockMvcResultMatchers.content().string("[]"))
    }
	
	@Test
    fun testFindOne()
	{    		
        mockMvc.perform(MockMvcRequestBuilders.get("/api/bloudymary").accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk()).andExpect(MockMvcResultMatchers.content().string("{\"id\":\"bloudymary\",\"name\":\"name\",\"description\":\"description\",\"recipe\":\"recipe\",\"ingredients\":[]}"))
    }
	
	@Test
    fun testFindNone()
	{   
        mockMvc.perform(MockMvcRequestBuilders.get("/api/unknown").accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk()).andExpect(MockMvcResultMatchers.content().string(""))
    }
	
	@Test
	fun testUpdateCocktailInvalid()
	{
		mockMvc.perform(MockMvcRequestBuilders.put("/api/invalid").accept(MediaType.APPLICATION_JSON).contentType(MediaType.APPLICATION_JSON).content("{\"id\":\"bossprinter\",\"name\":\"bos sprinter\",\"description\":\"De enige echte!\",\"recipe\":\"Gooi maar bij elkaar\",\"ingredients\":[]}"))
				.andExpect(MockMvcResultMatchers.status().isBadRequest()).andExpect(MockMvcResultMatchers.content().string(""))
		
		Mockito.verify(mockCocktailRepository,Mockito.never()).save(Mockito.any(Cocktail::class.java))
	}
	
	@Test
	fun testUpdateCocktailNotFound()
	{
		mockMvc.perform(MockMvcRequestBuilders.put("/api/bossprinter").accept(MediaType.APPLICATION_JSON).contentType(MediaType.APPLICATION_JSON).content("{\"id\":\"bossprinter\",\"name\":\"bos sprinter\",\"description\":\"De enige echte!\",\"recipe\":\"Gooi maar bij elkaar\",\"ingredients\":[]}"))
				.andExpect(MockMvcResultMatchers.status().isNotFound()).andExpect(MockMvcResultMatchers.content().string(""))
		Mockito.verify(mockCocktailRepository,Mockito.never()).save(Mockito.any(Cocktail::class.java));
	}
	
	@Test
	fun testUpdateCocktail()
	{
		mockMvc.perform(MockMvcRequestBuilders.put("/api/bloudymary").accept(MediaType.APPLICATION_JSON).contentType(MediaType.APPLICATION_JSON).content("{\"id\":\"bloudymary\",\"name\":\"name\",\"description\":\"description updated\",\"recipe\":\"recipe\",\"ingredients\":[]}"))
				.andExpect(MockMvcResultMatchers.status().isOk()).andExpect(MockMvcResultMatchers.content().string(""))
		
		Mockito.verify(mockCocktailRepository,Mockito.times(1)).save(Mockito.any(Cocktail::class.java));
	}
	
	@Test
	fun testAddCocktailExists()
	{
		mockMvc.perform(MockMvcRequestBuilders.post("/api").accept(MediaType.APPLICATION_JSON).contentType(MediaType.APPLICATION_JSON).content("{\"id\":\"bloudymary\",\"name\":\"bos sprinter\",\"description\":\"De enige echte!\",\"recipe\":\"Gooi maar bij elkaar\",\"ingredients\":[]}"))
				.andExpect(MockMvcResultMatchers.status().isConflict()).andExpect(MockMvcResultMatchers.content().string(""))
		Mockito.verify(mockCocktailRepository,Mockito.never()).insert(Mockito.any(Cocktail::class.java));
	}
	
	@Test
	fun testAddCocktail()
	{
		mockMvc.perform(MockMvcRequestBuilders.post("/api").accept(MediaType.APPLICATION_JSON).contentType(MediaType.APPLICATION_JSON).content("{\"id\":\"gintonic\",\"name\":\"name\",\"description\":\"description updated\",\"recipe\":\"recipe\",\"ingredients\":[]}"))
				.andExpect(MockMvcResultMatchers.status().isCreated()).andExpect(MockMvcResultMatchers.content().string(""))
		
		Mockito.verify(mockCocktailRepository,Mockito.times(1)).insert(Mockito.any(Cocktail::class.java));
	}
	
	@Test
	fun testDeleteCocktailNotFound()
	{
		mockMvc.perform(MockMvcRequestBuilders.delete("/api/bossprinter").accept(MediaType.APPLICATION_JSON))
				.andExpect(MockMvcResultMatchers.status().isNotFound()).andExpect(MockMvcResultMatchers.content().string(""))
		Mockito.verify(mockCocktailRepository,Mockito.never()).delete(Mockito.anyString());
	}
	
	@Test
	fun testDeleteCocktail()
	{
		mockMvc.perform(MockMvcRequestBuilders.delete("/api/bloudymary").accept(MediaType.APPLICATION_JSON))
				.andExpect(MockMvcResultMatchers.status().isOk()).andExpect(MockMvcResultMatchers.content().string(""))
		
		Mockito.verify(mockCocktailRepository,Mockito.times(1)).delete("bloudymary");
	}

}