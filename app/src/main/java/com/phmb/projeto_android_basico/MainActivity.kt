package com.phmb.projeto_android_basico

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.phmb.projeto_android_basico.model.Movie
import com.phmb.projeto_android_basico.adapter.MovieAdapter
import com.phmb.projeto_android_basico.ui.EditMovieActivity
import com.phmb.projeto_android_basico.ui.NewMovieActivity
import com.phmb.projeto_android_basico.utils.SwipeDeleteCallback
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.content_main.*

class MainActivity : AppCompatActivity() {

    private val NEW_MOVIE_ACTIVITY_REQUEST_CODE = 1
    private val EDIT_MOVIE_ACTIVITY_REQUEST_CODE = 2

    private var moviesArrayList = arrayListOf<Movie>()

    private val mMovieAdapter = MovieAdapter(this, moviesArrayList, this :: onMovieClickListener)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(findViewById(R.id.toolbar))

        if (savedInstanceState != null)
            moviesArrayList = savedInstanceState.getParcelableArrayList<Movie>("movies") as ArrayList<Movie>

        setupRecyclerview()
        configureFAB()
    }

    private fun setupRecyclerview() {
        recyclerView.adapter = mMovieAdapter
        val layoutManager = LinearLayoutManager(this)
        recyclerView.layoutManager = layoutManager

        val swipeHandler = object : SwipeDeleteCallback(this) {
            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                mMovieAdapter.removeItem(viewHolder.adapterPosition)
            }
        }
        val itemTouchHelper = ItemTouchHelper(swipeHandler)
        itemTouchHelper.attachToRecyclerView(recyclerView)

        // Show/hide floatingActionButton
        recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                if (dy > 0)
                    fab.hide()
                else if (dy < 0)
                    fab.show()
            }
        })
    }

    private fun configureFAB() {
        // Start NewMovieActivity
        fab.setOnClickListener {
            val intent = Intent(this, NewMovieActivity::class.java)
            startActivityForResult(intent, NEW_MOVIE_ACTIVITY_REQUEST_CODE)
        }
    }

    private fun onMovieClickListener(movie: Movie) {
        // Start EditMovieActivity
        val intent = Intent(this, EditMovieActivity::class.java)
        intent.putExtra("editMovie", movie)
        intent.putExtra("movieId", mMovieAdapter.getPosition(movie))
        startActivityForResult(intent, EDIT_MOVIE_ACTIVITY_REQUEST_CODE)
    }

    override fun onSaveInstanceState(outState: Bundle) {
        outState.putParcelableArrayList("movies", ArrayList<Movie>(moviesArrayList))
        super.onSaveInstanceState(outState)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        // Receive data from NewMovieActivity or EditMovieActivity
        if (resultCode == Activity.RESULT_OK) {
            val movie: Movie? = data?.getParcelableExtra("newMovie")
            if (requestCode == NEW_MOVIE_ACTIVITY_REQUEST_CODE) {
                if (movie != null) {
                    moviesArrayList.add(movie)
                    mMovieAdapter.notifyItemInserted(moviesArrayList.lastIndex)
                }
            } else if (requestCode == EDIT_MOVIE_ACTIVITY_REQUEST_CODE) {
                val movieIndex: Int = data?.getIntExtra("movieId", 0) ?: 0
                if (movie != null) {
                    mMovieAdapter.updateItem(movieIndex, movie)
                }
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return when (item.itemId) {
            R.id.menu_action_ordination_az -> {
                filterAZ()
                return true
            }
            R.id.menu_action_ordination_most_recent -> {
                filterMostRecent()
                return true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun filterAZ() {
        moviesArrayList.sortByDescending { it.title }
        mMovieAdapter.notifyDataSetChanged()
    }

    private fun filterMostRecent() {
        moviesArrayList.sortByDescending { it.releaseDate }
        mMovieAdapter.notifyDataSetChanged()
    }

}