package com.phmb.projeto_android_basico

import com.phmb.projeto_android_basico.model.Movie
import org.junit.Test

class MovieUnitTest {

    private var moviesArrayList = arrayListOf<Movie>()

    @Test
    fun `When adding a movie the list size should increase by one`() {
        val oldSize =  moviesArrayList.size
        moviesArrayList.add(Movie("Movie 1", "14/07/2010", "Fulano", 1))
        val newSize = moviesArrayList.size
        assert(newSize == (oldSize+1))
    }

    @Test
    fun `When an movie is added, it should be inside the movies list`() {
        val newMovie = Movie("Movie 1", "14/07/2010", "Fulano", 1)
        moviesArrayList.add(newMovie)
        assert(moviesArrayList.contains(newMovie))
    }

    @Test
    fun `When an movie is removed, it should not be inside the movies list`() {
        val newMovie = Movie("Movie 2", "10/10/2015", "Sicrano", 2)
        moviesArrayList.add(newMovie)
        moviesArrayList.remove(newMovie)
        assert(!moviesArrayList.contains(newMovie))
    }

}
