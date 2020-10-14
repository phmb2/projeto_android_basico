package com.phmb.projeto_android_basico.ui

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.phmb.projeto_android_basico.R
import com.phmb.projeto_android_basico.model.Movie
import kotlinx.android.synthetic.main.activity_new_movie.*

open class NewMovieActivity : AppCompatActivity() {

    var movieId = -1;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new_movie)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_new_movie, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return when (item.itemId) {
            R.id.menu_action_save -> {
                registerMovie()
                return true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    // Create new movie
    private fun registerMovie() {
        val movieTitle: String = editTextTitle.text.toString()
        val movieReleaseDate: String = editTextReleaseDate.text.toString()
        val movieDirection: String = editTextDirection.text.toString()

        if (isValid(movieTitle) && isValid(movieReleaseDate)) {
            val movie = Movie(movieTitle, movieReleaseDate, movieDirection, (0..7).random())

            val intent = Intent()
            intent.putExtra("newMovie", movie)
            intent.putExtra("movieId", movieId)

            setResult(Activity.RESULT_OK, intent)

            finish()
        } else {
            Toast.makeText(this, resources.getString(R.string.new_movie_activity_toast), Toast.LENGTH_LONG).show()
        }
    }

    private fun isValid(value: String): Boolean = value.isNotEmpty()
}