package com.webserva.wings.android.pokemonzukan

import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.annotation.WorkerThread
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.google.gson.Gson
import com.google.gson.JsonParser
import com.webserva.wings.android.pokemonzukan.models.Pokemon
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext
import org.w3c.dom.Text
import java.io.BufferedReader
import java.io.InputStream
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.SocketTimeoutException
import java.net.URL
import java.nio.charset.StandardCharsets


class PokemonListFragment : Fragment() {

    companion object {

        private const val POKEMON_URL_BASE = "https://pokeapi.co/api/v2/pokemon/";
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        activity?.title = "一覧"
        return inflater.inflate(R.layout.fragment_pokemon_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val _rv = view.findViewById<RecyclerView>(R.id.recyclerView);

        val layoutManager = GridLayoutManager(
            requireContext(),
            2,
            RecyclerView.VERTICAL,
            false);
        _rv.layoutManager = layoutManager;

        CoroutineScope(Dispatchers.Main).launch{

            val pokemonList = getPokemonList();
            val adapter = RecyclerAdapter(pokemonList);

            _rv.adapter = adapter;
        }
    }

    private inner class RecyclerViewHolder(val view: View): RecyclerView.ViewHolder(view) {

        var id: TextView
        var name: TextView
        var img: ImageView

        init {
            // 引数で渡されたリスト1行分の画面部品中から表示に使われるTextViewを取得。
            id = itemView.findViewById(R.id.tvId)
            name = itemView.findViewById(R.id.tvName)
            img = itemView.findViewById(R.id.imageView)
        }
    }
    private inner class RecyclerAdapter(private val _listData: MutableList<MutableMap<String, String>>) : RecyclerView.Adapter<RecyclerViewHolder>(){

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerViewHolder {

            val layoutInflater = LayoutInflater.from(parent.context)
            val item = layoutInflater.inflate(R.layout.ball, parent, false)

            return RecyclerViewHolder(item)
        }

        override fun getItemCount(): Int {

            return _listData.size
        }

        override fun onBindViewHolder(holder: RecyclerViewHolder, position: Int) {
            holder.view.let{

                val item = _listData[position]

                val id = item["id"] as String
                val name = item["name"] as String
                val imagePath = item["imagePath"] as String

                holder.id.text = "No. $id"
                holder.name.text = name
                Glide.with(this@PokemonListFragment)
                    .load(imagePath)
                    .into(holder.img)
                holder.img.tag = imagePath

                it.setOnClickListener(ListItemClickListener());
            }
        }
    }

    private inner class ListItemClickListener: View.OnClickListener {
        override fun onClick(view: View?) {

            view?.let{

                val LinearLayout = it.findViewById<LinearLayout>(R.id.LinearLayout) ?: return;

                val tvId = LinearLayout.findViewById<TextView>(R.id.tvId)
                val tvName = LinearLayout.findViewById<TextView>(R.id.tvName)
                val imageView = LinearLayout.findViewById<ImageView>(R.id.imageView)

                val bundle = Bundle();

                bundle.putString("id", tvId.text.toString())
                bundle.putString("name", tvName.text.toString())
                bundle.putString("imagePath", imageView.tag.toString())

                val transaction  = parentFragmentManager.beginTransaction();
                transaction.setReorderingAllowed(true)
                transaction.addToBackStack("pokemonList")
                transaction.replace(R.id.fragmentMainContainer, PokemonDetailFragment::class.java, bundle);

                transaction.commit();
            }
        }
    }

    @WorkerThread
    private suspend fun getPokemonList(): MutableList<MutableMap<String, String>> {

        val result = withContext(Dispatchers.IO){

            var result: String = "";

            val gson = Gson()
            val pokemonList: MutableList<MutableMap<String, String>> = mutableListOf()

            for (i in 1..151) {

                val url = URL(POKEMON_URL_BASE + i)

                val json = url.readText();
                val jsonObject = JsonParser.parseString(json).asJsonObject

                val forms = jsonObject.getAsJsonArray("forms")[0].asJsonObject ;
                val name = forms.getAsJsonPrimitive("name").asString

                val id = jsonObject.getAsJsonPrimitive("id").asString

                val sprites = jsonObject.getAsJsonObject("sprites");
                val frontImgPath = sprites.getAsJsonPrimitive("front_default").asString

                var menu = mutableMapOf("id" to id, "imagePath" to frontImgPath, "name" to name)
                pokemonList.add(menu)
            }

            pokemonList;
        }

        return result;
    }

    private fun is2String(stream: InputStream): String {
        val sb = StringBuilder()
        val reader = BufferedReader(InputStreamReader(stream, StandardCharsets.UTF_8))
        var line = reader.readLine()
        while(line != null) {
            sb.append(line)
            line = reader.readLine()
        }
        reader.close()
        return sb.toString()
    }
}