package nl.tumbolia.badmin.glasses.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import nl.tumbolia.badmin.glasses.domain.Glass;
import nl.tumbolia.badmin.glasses.domain.GlassIcon;
import nl.tumbolia.badmin.glasses.domain.Unit;
import nl.tumbolia.badmin.glasses.repository.GlassIconRepository;
import nl.tumbolia.badmin.glasses.repository.GlassRepository;


/**
 * @author erwin
 */
public class GlassServiceImplTest
{
    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    @InjectMocks
    private GlassServiceImpl glassServiceImpl;

    @Mock
    private GlassRepository mockGlassRepository;

    @Mock
    private GlassIconRepository mockGlassIconRepository;

    private Glass newGlass;
    private Glass existingGlass;

    @Before
    public void setup()
    {
        MockitoAnnotations.initMocks(this);

        newGlass = new Glass("glass1", 10);
        newGlass.setUnit(Unit.CUBIC_CENTIMETRE);

        existingGlass = new Glass("glass1", 10);
        existingGlass.setId(1L);
        existingGlass.setUnit(Unit.CUBIC_CENTIMETRE);
        GlassIcon glassIcon = new GlassIcon(new byte[] {});
        glassIcon.setId(1L);
        existingGlass.setIcon(glassIcon);
    }

    @Test
    public void testGetAllGlasses()
    {
		List<Glass> expectedGlasses = Arrays.asList(
                Mockito.mock(Glass.class), Mockito.mock(Glass.class));
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
    public void testGetGlassNotFound()
    {
        assertFalse(glassServiceImpl.getGlass(0).isPresent());
        Mockito.verify(mockGlassRepository, Mockito.only()).findOne(0L);
    }

    @Test
    public void testAddGlassMissingName()
    {
        expectedException.expect(GlassServiceUserException.class);
        expectedException
                .expectMessage(GlassServiceUserException.GlassServiceUserError.MISSING_REQUIRED_ITEM
                        .toString());

        newGlass.setName(null);

        glassServiceImpl.addGlass(newGlass);

        assertNonSaved();
    }

    @Test
    public void testAddGlassInvalidName()
    {
        expectedException.expect(GlassServiceUserException.class);
        expectedException
                .expectMessage(GlassServiceUserException.GlassServiceUserError.MISSING_REQUIRED_ITEM
                        .toString());

        newGlass.setName("");

        glassServiceImpl.addGlass(newGlass);

        assertNonSaved();
    }

    @Test
    public void testAddGlassInvalidUnit()
    {
        expectedException.expect(GlassServiceUserException.class);
        expectedException
                .expectMessage(GlassServiceUserException.GlassServiceUserError.MISSING_REQUIRED_ITEM
                        .toString());

        newGlass.setUnit(null);

        glassServiceImpl.addGlass(newGlass);

        assertNonSaved();
    }

    @Test
    public void testAddGlassIconInvalid()
    {
        expectedException.expect(GlassServiceUserException.class);
        expectedException
                .expectMessage(GlassServiceUserException.GlassServiceUserError.MISSING_REQUIRED_ITEM
                        .toString());

        newGlass.setIcon(new GlassIcon());

        glassServiceImpl.addGlass(newGlass);

        assertNonSaved();
    }

    @Test
    public void testAddGlassNotNew()
    {
        expectedException.expect(GlassServiceUserException.class);
        expectedException
                .expectMessage(GlassServiceUserException.GlassServiceUserError.INVALID_ITEM
                        .toString());

        glassServiceImpl.addGlass(existingGlass);

        assertNonSaved();
    }

    @Test
    public void testAddGlassIconNotNew()
    {
        expectedException.expect(GlassServiceUserException.class);
        expectedException.expectMessage(GlassServiceUserException.GlassServiceUserError.INVALID_ITEM.toString());

        GlassIcon icon = new GlassIcon(new byte[] {});
        icon.setId(2L);
        newGlass.setIcon(icon);

        glassServiceImpl.addGlass(newGlass);

        assertNonSaved();
    }

    @Test
    public void testAddGlassNameExists()
    {
        expectedException.expect(GlassServiceUserException.class);
        expectedException
                .expectMessage(GlassServiceUserException.GlassServiceUserError.DUPLICATE
                        .toString());

        Mockito.when(mockGlassRepository.findByName(newGlass.getName()))
                .thenReturn(existingGlass);

        glassServiceImpl.addGlass(newGlass);

        assertNonSaved();
    }

    @Test
    public void testAddGlass()
    {
        glassServiceImpl.addGlass(newGlass);

        Mockito.verify(mockGlassRepository).save(newGlass);
    }

    @Test
    public void testUpdateGlassMissingName()
    {
        expectedException.expect(GlassServiceUserException.class);
        expectedException
                .expectMessage(GlassServiceUserException.GlassServiceUserError.MISSING_REQUIRED_ITEM
                        .toString());

        existingGlass.setName(null);

        glassServiceImpl.updateGlass(existingGlass);

        assertNonSaved();
    }

    @Test
    public void testUpdateGlassInvalidName()
    {
        expectedException.expect(GlassServiceUserException.class);
        expectedException
                .expectMessage(GlassServiceUserException.GlassServiceUserError.MISSING_REQUIRED_ITEM
                        .toString());

        existingGlass.setName("");

        glassServiceImpl.updateGlass(existingGlass);

        assertNonSaved();
    }

    @Test
    public void testUpdateGlassInvalidUnit()
    {
        expectedException.expect(GlassServiceUserException.class);
        expectedException
                .expectMessage(GlassServiceUserException.GlassServiceUserError.MISSING_REQUIRED_ITEM
                        .toString());

        existingGlass.setUnit(null);

        glassServiceImpl.updateGlass(existingGlass);

        assertNonSaved();
    }

    @Test
    public void testUpdateGlassIconInvalid()
    {
        expectedException.expect(GlassServiceUserException.class);
        expectedException
                .expectMessage(GlassServiceUserException.GlassServiceUserError.INVALID_ITEM
                        .toString());

        existingGlass.setIcon(new GlassIcon());

        glassServiceImpl.updateGlass(existingGlass);

        assertNonSaved();
    }

    @Test
    public void testUpdateGlassNew()
    {
        expectedException.expect(GlassServiceUserException.class);
        expectedException
                .expectMessage(GlassServiceUserException.GlassServiceUserError.INVALID_ITEM
                        .toString());

        glassServiceImpl.updateGlass(newGlass);
    }

    @Test
    public void testUpdateGlassIconNew()
    {
        expectedException.expect(GlassServiceUserException.class);
        expectedException
                .expectMessage(GlassServiceUserException.GlassServiceUserError.INVALID_ITEM
                        .toString());

        existingGlass.getIcon().setId(null);

        glassServiceImpl.updateGlass(existingGlass);

        assertNonSaved();
    }

    public void testUpdateGlassNameExists()
    {
        expectedException.expect(GlassServiceUserException.class);
        expectedException
                .expectMessage(GlassServiceUserException.GlassServiceUserError.DUPLICATE
                        .toString());

        Mockito.when(mockGlassRepository.exists(existingGlass.getId()))
                .thenReturn(true);
        Mockito.when(mockGlassRepository.findByName(existingGlass.getName()))
                .thenReturn(newGlass);

        glassServiceImpl.updateGlass(existingGlass);
    }

    @Test
    public void testUpdateGlassDoesNotExist()
    {

        expectedException.expect(GlassServiceUserException.class);
        expectedException
                .expectMessage(GlassServiceUserException.GlassServiceUserError.NOT_FOUND
                        .toString());

        Mockito.when(mockGlassRepository.exists(existingGlass.getId()))
                .thenReturn(false);

        glassServiceImpl.updateGlass(existingGlass);

        assertNonSaved();
    }

    @Test
    public void testUpdateGlass()
    {
        Mockito.when(mockGlassRepository.exists(existingGlass.getId()))
                .thenReturn(true);
        Mockito.when(mockGlassRepository.findByName(existingGlass.getName()))
                .thenReturn(existingGlass);

        glassServiceImpl.updateGlass(existingGlass);

        Mockito.verify(mockGlassRepository).save(existingGlass);
    }

    @Test
    public void testUpdateGlassNameUpdated()
    {
        Mockito.when(mockGlassRepository.exists(existingGlass.getId()))
                .thenReturn(true);

        glassServiceImpl.updateGlass(existingGlass);

        Mockito.verify(mockGlassRepository).save(existingGlass);
    }

    private void assertNonSaved()
    {
        Mockito.verify(mockGlassRepository, Mockito.never()).save(
                Mockito.any(Glass.class));
    }
}
