package nl.tumbolia.badmin.glasses.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.List;
import java.util.Optional;

import nl.tumbolia.badmin.glasses.domain.Glass;
import nl.tumbolia.badmin.glasses.repository.GlassIconRepository;
import nl.tumbolia.badmin.glasses.repository.GlassRepository;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import com.google.common.collect.ImmutableList;

/**
 * @author erwin
 */
public class GlassServiceImplTest 
{
	@InjectMocks
	private GlassServiceImpl glassServiceImpl;

	@Mock
	private GlassRepository mockGlassRepository;

	@Mock
	private GlassIconRepository mockGlassIconRepository;
	
	@Before
	public void setup() 
	{
		MockitoAnnotations.initMocks(this);
	}

	@Test
	public void testGetAllGlasses() 
	{
		List<Glass> expectedGlasses = ImmutableList.of(Mockito.mock(Glass.class), Mockito.mock(Glass.class));
		Mockito.when(mockGlassRepository.findAll()).thenReturn(expectedGlasses);
		
		List<Glass> glasses = glassServiceImpl.getAllGlasses();
		
		assertNotNull(glasses);
		assertEquals(expectedGlasses, glasses);
		Mockito.verify(mockGlassRepository, Mockito.only()).findAll();
	}

	@Test
	public void testGetGlassFound() 
	{
		Glass mockGlass = Mockito.mock(Glass.class);
		Mockito.when(mockGlassRepository.findOne(1L)).thenReturn(mockGlass);

		Optional<Glass> glass = glassServiceImpl.getGlass(1L);
		
		assertTrue(glass.isPresent());
		assertEquals(Optional.of(mockGlass), glass);		
		Mockito.verify(mockGlassRepository, Mockito.only()).findOne(1L);
	}
	
	@Test
	public void testGetGlassIconnotFound() 
	{
		assertFalse(glassServiceImpl.getGlass(0).isPresent());		
		Mockito.verify(mockGlassRepository, Mockito.only()).findOne(0L);
	}
}
