package testsdao;

import main.common.ElementNotFoundException;
import main.dao.DaoArtist;
import main.dao.DataBase;
import main.domain.modelo.Artist;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class DaoArtistTest {

    public static final String NOMBRE = "nombre";
    @InjectMocks
    DaoArtist daoArtist;
    @Mock
    DataBase dataBase;

    @BeforeAll
    static void beforeAllTests() {
        System.out.println("Bienvenido a mis Pruebas Unitarias del Dao de Artistas");
    }

    @AfterAll
    static void afterAllTests() {
        System.out.println("Finalización de mis Pruebas Unitarias del Dao de Artistas");
    }

    @BeforeEach
    void beforeEachTest() {
        System.out.println("----Inicio Test----");
    }

    @AfterEach
    public void afterEachTest() {
        System.out.println("----Fin Test-------");
        System.out.println("-------------------");
    }

    @DisplayName("Insertar artistas")
    @Nested
    @Order(2)
    class AddArtist {
        @DisplayName("Insertar artista con nombre")
        @Test
        void addArtistsReturnsTrue() {
            //given
            Artist artist = new Artist(NOMBRE);
            //when
            when(dataBase.insertArtist(artist)).thenReturn(true);
            boolean result = daoArtist.insertArtist(artist);
            if (result) {
                System.out.println("El artista se ha insertado correctamente");
            }
            //then
            assertTrue(result);
        }

        @DisplayName("Insertar artista vacío")
        @Test
        void addArtistsReturnsFalse() {
            //given
            Artist artist = new Artist();
            //when
            when(dataBase.insertArtist(artist)).thenReturn(false);
            boolean result = daoArtist.insertArtist(artist);
            if (!result) {
                System.out.println("El artista no se ha podido insertar porque el introducido está vacío");
            }
            //then
            assertThat(result).isFalse();
        }
    }

    @DisplayName("Listar artistas")
    @Test
    @Order(1)
    void getListaArtists() {
        //given
        List<Artist> artistList = new ArrayList<>();
        artistList.add(new Artist("n1"));
        artistList.add(new Artist("n2"));
        //when
        when(dataBase.getAllArtists()).thenReturn(artistList);
        List<Artist> result = daoArtist.getAllArtists();
        //then
        assertAll(
                () -> assertThat(result).isEqualTo(artistList),
                () -> assertThat(result).isNotNull());
        System.out.println("La lista de artistas es: " + result);
    }

    @DisplayName("Actualizar artistas")
    @Nested
    @Order(3)
    class UpdateArtist {
        @DisplayName("Actualizar artista con nombre")
        @Test
        void updateArtistReturnsTrue() throws ElementNotFoundException {
            //given
            Artist artist = new Artist(NOMBRE);
            //when
            when(dataBase.updateArtist(artist)).thenReturn(true);
            boolean result = daoArtist.updateArtist(artist);
            if (result) {
                System.out.println("El artista se ha actualizado correctamente");
            }
            //then
            assertThat(result).isTrue();
        }

        @DisplayName("Actualizar artista vacío")
        @Test
        void updateArtistReturnsFalse() throws ElementNotFoundException {
            //given
            Artist artist = new Artist(NOMBRE, true);
            //when
            when(dataBase.updateArtist(artist)).thenReturn(true);
            boolean result = !daoArtist.updateArtist(artist);
            if (!result) {
                System.out.println("El artista no se ha podido actualizar porque el introducido está vacío");
            }
            //then
            assertFalse(result);
        }
    }

    @DisplayName("Eliminar artistas")
    @Nested
    @Order(4)
    class DeleteArtist {
        @DisplayName("Eliminar artista con nombre")
        @Test
        void deleteArtistReturnsTrue() throws ElementNotFoundException {
            //given
            Artist artist = new Artist(NOMBRE);
            //when
            when(dataBase.deleteArtist(artist)).thenReturn(true);
            boolean result = daoArtist.deleteArtist(artist);
            if (result) {
                System.out.println("El artista se ha eliminado correctamente");
            }
            //then
            assertTrue(result);
        }

        @DisplayName("Eliminar artista vacío")
        @Test
        void deleteArtistReturnsFalse() throws ElementNotFoundException {
            //given
            Artist artist = new Artist(NOMBRE, true);
            //when
            when(dataBase.deleteArtist(artist)).thenReturn(true);
            boolean result = daoArtist.deleteArtist(artist);
            if (!result) {
                System.out.println("El artista no se ha podido eliminar porque el introducido está vacío");
            }
            //then
            assertTrue(result);
        }
    }

    @DisplayName("Obtener HashMap")
    @Test
    @Order(5)
    void getHashMap() {
        //given
        List<Artist> artistList = new ArrayList<>();
        artistList.add(new Artist("n1"));
        artistList.add(new Artist("n2"));
        //when
        when(dataBase.getAllArtists()).thenReturn(artistList);
        List<Artist> result = daoArtist.getAllArtists();
        //then
        assertAll(
                () -> assertThat(result).isEqualTo(artistList),
                () -> assertThat(result).isNotNull());
        System.out.println("La lista de artistas es: " + result.stream().map(Artist::getNombre).toList());
    }

    @DisplayName("Lista de artistas is empty")
    @Order(6)
    @Test
    void listArtistsIsEmpty() {
        //given
        List<Artist> artistList = new ArrayList<>();
        //when
        when(dataBase.getAllArtists()).thenReturn(artistList);
        List<Artist> result = daoArtist.getAllArtists();
        //then
        assertAll(
                () -> verify(dataBase, times(0)).insertArtist(new Artist()),
                () -> assertThat(result).isEmpty()
        );
        System.out.println("La lista de artistas es: " + artistList);
    }

    @DisplayName("Lista de artistas is null")
    @Order(7)
    @Test
    void listArtistsIsNull() {
        //when
        when(dataBase.getAllArtists()).thenReturn(null);
        List<Artist> result = daoArtist.getAllArtists();
        //then
        assertAll(
                () -> verify(dataBase, times(0)).insertArtist(new Artist()),
                () -> assertThat(result).isNull()
        );
        System.out.println("La lista de artistas es: " + null);
    }

    @DisplayName("Delete with exception not thrown")
    @Order(8)
    @Test
    void deleteWithExceptionNotThrown() throws ElementNotFoundException {
        //given
        Artist artist = new Artist(NOMBRE);
        //when
        when(dataBase.deleteArtist(artist)).thenReturn(true);
        //then
        assertDoesNotThrow(() -> {
            daoArtist.deleteArtist(artist);
        });
    }
}