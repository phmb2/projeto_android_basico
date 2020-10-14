package com.phmb.projeto_android_basico.ui

import android.os.Bundle
import com.phmb.projeto_android_basico.R
import com.phmb.projeto_android_basico.model.Movie
import kotlinx.android.synthetic.main.activity_new_movie.*

class EditMovieActivity : NewMovieActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if(intent != null)
        {
            val movie = intent.getParcelableExtra<Movie>("editMovie")
            movieId = intent.getIntExtra("movieId", 0)

            subtitle_info.text = resources.getString(R.string.edit_movie_activity_edit_movie)

            if (movie != null) {
                editTextTitle.setText(movie.title)
                editTextReleaseDate.setText(movie.releaseDate)
                editTextDirection.setText(movie.direction)
            }
        }
    }
}