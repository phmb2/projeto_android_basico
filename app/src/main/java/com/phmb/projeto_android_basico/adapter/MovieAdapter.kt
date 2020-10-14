package com.phmb.projeto_android_basico.adapter

import android.content.Context
import android.content.res.TypedArray
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.phmb.projeto_android_basico.R
import com.phmb.projeto_android_basico.model.Movie
import kotlinx.android.synthetic.main.item_movie.view.*


class MovieAdapter(private val context: Context, private val movies: ArrayList<Movie>,
                   private val callback: (Movie) -> Unit) : RecyclerView.Adapter<MovieAdapter.ViewHolder>() {

    private val posters: TypedArray by lazy {
        context.resources.obtainTypedArray(R.array.posters)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.item_movie, parent, false)
        val vh = ViewHolder(v)
        vh.itemView.setOnClickListener {
            val movie = movies[vh.adapterPosition]
            callback(movie)
        }
        return vh
    }

    override fun getItemCount() = movies.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val (title, releaseDate, direction, poster) = movies[position]
        holder.txtTitle.text = title
        holder.releaseDate.text = releaseDate
        holder.imagePoster.setImageDrawable(posters.getDrawable(poster))
        holder.direction.text = direction
    }

    fun removeItem(position: Int) {
        movies.removeAt(position)
        notifyItemRemoved(position)
    }

    fun updateItem(position: Int, movie: Movie) {
        if (position == -1) return
        movies[position] = movie
        notifyItemChanged(position)
    }

    fun getPosition(movie: Movie): Int = movies.indexOf(movie)

    class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        val imagePoster : ImageView = itemView.iv_movie_poster
        val txtTitle: TextView = itemView.txt_movie_title
        val releaseDate: TextView = itemView.txt_movie_release_date
        val direction: TextView = itemView.txt_movie_direction
    }
}