package nl.tumbolia.badmin.glasses.service;

import static org.junit.Assert.*;

import java.util.List;
import java.util.Optional;

import nl.tumbolia.badmin.glasses.domain.Glass;
import nl.tumbolia.badmin.glasses.domain.GlassIcon;
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
 *
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
	public void testGetGlassIconFound() 
	{
		GlassIcon mockGlassIcon = Mockito.mock(GlassIcon.class);
		Mockito.when(mockGlassIconRepository.findOne(1L)).thenReturn(mockGlassIcon);

		Optional<GlassIcon> icon = glassServiceImpl.getGlassIcon(1L);
		
		assertTrue(icon.isPresent());
		assertEquals(Optional.of(mockGlassIcon), icon);		
		Mockito.verify(mockGlassIconRepository, Mockito.only()).findOne(1L);
	}
	
	@Test
	public void testGetGlassIconnotFound() 
	{
		assertFalse(glassServiceImpl.getGlassIcon(0).isPresent());		
		Mockito.verify(mockGlassIconRepository, Mockito.only()).findOne(0L);
	}
}
