package testsservicios;

import main.common.ElementNotFoundException;
import main.dao.DaoArtist;
import main.domain.modelo.Artist;
import main.domain.servicios.ServiciosArtist;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.HashMap;
import java.util.List;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ServiciosArtistTest {
    @InjectMocks
    ServiciosArtist serviciosArtist;

    @Mock
    DaoArtist daoArtist;

    @BeforeAll
    static void beforeAllTests() {
        System.out.println("Bienvenido a mis Pruebas Unitarias de los Servicios de Artistas");
    }

    @AfterAll
    static void afterAllTests() {
        System.out.println("Finalización de mis Pruebas Unitarias de los Servicios de Artistas");
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

    @DisplayName("Añadir artista")
    @Order(2)
    @Nested
    class AddArtist {
        @DisplayName("Añadir artistas con nombres vacíos")
        @ParameterizedTest
        @CsvSource({",true", ",false",})
        void addArtistReceivesEmpty(String nombre, boolean tour) {
            //given
            Artist artist = new Artist(nombre, tour);
            //when
            String result = serviciosArtist.insertArtist(artist);
            System.out.println(result);
            //then
            assertEquals("El nombre del artista no puede estar vacío", result);
        }

        @DisplayName("Añadir artistas con nombres")
        @ParameterizedTest
        @CsvSource({"nombre,true", "nombre,false"})
        void addArtistReceivesCorrectArtist(String nombre, boolean tour) {
            //given
            Artist artist = new Artist(nombre, tour);
            //when
            when(daoArtist.insertArtist(artist)).thenReturn(true);
            String result = serviciosArtist.insertArtist(artist);
            //then
            assertEquals("Artista insertado correctamente", result);
        }
    }

    @DisplayName("Listar Artistas")
    @Test
    @Order(1)
    void getListaArtists() {
        //given
        HashMap<Integer, Artist> artistList = new HashMap<>();
        artistList.put(0, new Artist("n1"));
        artistList.put(1, new Artist("n2"));
        //when
        when(daoArtist.getAllArtists()).thenReturn(artistList.values().stream().toList());
        List<Artist> result = serviciosArtist.getAllArtists();
        //then
        assertAll(
                () -> assertThat(result).isEqualTo(artistList.values().stream().toList()),
                () -> assertThat(result).isNotNull());
        System.out.println("Lista de artistas: " + result);
    }

    @DisplayName("Actualizar artistas")
    @Nested
    @Order(3)
    class UpdateArtists {
        @DisplayName("Actualizar artistas con distintas posibilidades (Method Source)")
        @ParameterizedTest
        @MethodSource("artists")
        void updateArtist(Artist artist) throws ElementNotFoundException {
            //when
            when(daoArtist.updateArtist(artist)).thenReturn(true);
            String result = serviciosArtist.updateArtist(artist);
            System.out.println(result);
            assertEquals("Artista actualizado correctamente", result);
        }

        public static Stream<Artist> artists() {
            return Stream.of(new Artist("nombre", false),
                    new Artist(0, "nombre", true),
                    new Artist(1, "nombre", false));
        }

        @DisplayName("Actualizar artistas throws ElementNotFoundException")
        @Test
        void updateArtistThrowsException() throws ElementNotFoundException {
            //given
            Artist artist = new Artist(0, "nombre", false);
            //when
            when(daoArtist.updateArtist(artist)).thenThrow(ElementNotFoundException.class);
            //then
            assertThrows(ElementNotFoundException.class, () -> serviciosArtist.updateArtist(artist));
        }
    }

    @DisplayName("Eliminar artistas")
    @Nested
    @Order(4)
    class DeleteArtists {
        @DisplayName("Eliminar artistas con distintas posibilidades (Value Source)")
        @ParameterizedTest
        @ValueSource(ints = {0, 1, 10})
        void deleteArtist(int id) throws ElementNotFoundException {
            //given
            Artist artist = new Artist(id, "nombre", false);
            //when
            when(daoArtist.deleteArtist(artist)).thenReturn(true);
            String result = serviciosArtist.deleteArtist(artist);
            System.out.println(result);
            //then
            assertEquals("Artista eliminado correctamente", result);
        }

        @DisplayName("Eliminar artistas throws ElementNotFoundException")
        @Test
        void deleteArtistThrowsException() throws ElementNotFoundException {
            //given
            Artist artist = new Artist(0, "nombre", false);
            //when
            when(daoArtist.deleteArtist(artist)).thenThrow(ElementNotFoundException.class);
            //then
            assertThrows(ElementNotFoundException.class, () -> serviciosArtist.deleteArtist(artist));
        }

        @DisplayName("Eliminar artistas gives null message")
        @Test
        void deleteArtistThrowsExceptionWithMessage() throws ElementNotFoundException {
            //given
            Artist artist = new Artist(0, "nombre", false);
            //when
            when(daoArtist.deleteArtist(artist)).thenThrow(ElementNotFoundException.class);
            //then
            ElementNotFoundException exception = assertThrows(ElementNotFoundException.class, () -> serviciosArtist.deleteArtist(artist));
            assertNull(exception.getMessage());
        }

        @DisplayName("Eliminar artistas por id con distintas posibilidades (Value Source)")
        @ParameterizedTest
        @ValueSource(ints = {0, 1, 10})
        void deleteArtistByID(int id) throws ElementNotFoundException {
            //when
            when(daoArtist.deleteArtistByID(id)).thenReturn(true);
            boolean result = serviciosArtist.deleteArtistByID(id);
            //then
            assertThat(result).isTrue();
        }

        @DisplayName("Eliminar artistas por id throws ElementNotFoundException")
        @Test
        void deleteArtistByIDThrowsException() throws ElementNotFoundException {
            //when
            when(daoArtist.deleteArtistByID(0)).thenThrow(ElementNotFoundException.class);
            //then
            assertThrows(ElementNotFoundException.class, () -> serviciosArtist.deleteArtistByID(0));
        }

        @DisplayName("Eliminar artistas por id con un id no existente")
        @Test
        void deleteArtistByNonExistentID() throws ElementNotFoundException {
            //given
            int id = 1000;
            //when
            when(daoArtist.deleteArtistByID(id)).thenReturn(false);
            //then
            assertThat(serviciosArtist.deleteArtistByID(id)).isFalse();
        }

        @DisplayName("Eliminar artistas por id con un id negativo")
        @Test
        void deleteArtistByNegativeID() throws ElementNotFoundException {
            //given
            int id = -1;
            //when
            when(daoArtist.deleteArtistByID(id)).thenReturn(false);
            //then
            assertThat(serviciosArtist.deleteArtistByID(id)).isFalse();
        }
    }

    @DisplayName("Eliminar artista")
    @Test
    void eliminarArtista() throws ElementNotFoundException {
        //given
        Artist artist = new Artist("nombre", false);
        //when
        serviciosArtist.deleteArtist(artist);
        //then
        verify(daoArtist, times(1)).deleteArtist(artist);
    }

    @DisplayName("Eliminar artista por id")
    @Test
    void eliminarArtistaPorID() throws ElementNotFoundException {
        //given
        int id = 0;
        //when
        serviciosArtist.deleteArtistByID(id);
        //then
        verify(daoArtist, times(1)).deleteArtistByID(id);
    }

    @DisplayName("Actualizar artista")
    @Test
    void actualizarArtista() throws ElementNotFoundException {
        //given
        Artist artist = new Artist("nombre", false);
        //when
        serviciosArtist.updateArtist(artist);
        //then
        verify(daoArtist, times(1)).updateArtist(artist);
    }

    @DisplayName("Listar artistas")
    @Test
    void listarArtistas() {
        //when
        serviciosArtist.getAllArtists();
        //then
        verify(daoArtist, times(1)).getAllArtists();
    }
}