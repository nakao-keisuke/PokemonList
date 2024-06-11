package com.webserva.wings.android.pokemonzukan

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
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
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

        private const val POKEMON_URL = "https://pokeapi.co/api/v2/pokemon/1";
    }

    private val adapter: RecyclerAdapter;

    init{
        adapter = RecyclerAdapter(getPokemonList());
    }

    override fun onStart() {
        super.onStart()

        var result = "";

        val url = URL(POKEMON_URL);
        val con = url.openConnection() as HttpURLConnection;
        // 接続に使ってもよい時間を設定。
        con.connectTimeout = 1000
        // データ取得に使ってもよい時間。
        con.readTimeout = 1000
        // HTTP接続メソッドをGETに設定。
        con.requestMethod = "GET"

//        try {
//            // 接続。
//            con.connect()
//            // HttpURLConnectionオブジェクトからレスポンスデータを取得。
//            val stream = con.inputStream
//            // レスポンスデータであるInputStreamオブジェクトを文字列に変換。
//            result = is2String(stream)
//            // InputStreamオブジェクトを解放。
//            stream.close()
//        }
//        catch(ex: SocketTimeoutException) {
//            Log.w("pokemon list 情報取得失敗", "通信タイムアウト", ex)
//        }

        //リストのデータ用意処理
        view?.let {

//            val _rv = it.findViewById<RecyclerView>(R.id.recyclerView);
//
//            val layoutManager = GridLayoutManager(
//                requireContext(),
//                2,
//                RecyclerView.VERTICAL,
//                false);
//            _rv.layoutManager = layoutManager;
//
//            val pokemonList = getPokemonList();
//            val adapter = RecyclerAdapter(pokemonList);
//
//            _rv.adapter = adapter;
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

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

        val pokemonList = getPokemonList();
        val adapter = RecyclerAdapter(pokemonList);

        _rv.adapter = adapter;
    }

    private inner class RecyclerViewHolder(val view: View): RecyclerView.ViewHolder(view) {

        var id: TextView
        var name: TextView

        init {
            // 引数で渡されたリスト1行分の画面部品中から表示に使われるTextViewを取得。
            id = itemView.findViewById(R.id.tvId)
            name = itemView.findViewById(R.id.tvName)
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
                // メニュー名文字列を取得。
                val id = item["id"] as String
                // メニュー金額を取得。
                val name = item["name"] as String

                holder.id.text = "No. $id"
                holder.name.text = name

                it.setOnClickListener(ListItemClickListener());
            }
        }

        fun getItem(position: Int): MutableMap<String, String> {
            return _listData[position]
        }
    }

    private inner class ListItemClickListener: View.OnClickListener {
        override fun onClick(view: View?) {

            view?.let{

                val LinearLayout = it.findViewById<LinearLayout>(R.id.LinearLayout) ?: return;

                val id = LinearLayout.findViewById<TextView>(R.id.tvId)
                val name = LinearLayout.findViewById<TextView>(R.id.tvName)

                val bundle = Bundle();

                bundle.putString("id", id.text.toString())
                bundle.putString("name", name.text.toString())

                val transaction  = parentFragmentManager.beginTransaction();
                transaction.setReorderingAllowed(true)
                transaction.addToBackStack("pokemonList")
                transaction.replace(R.id.fragmentMainContainer, PokemonDetailFragment::class.java, bundle);

                transaction.commit();
            }
        }
    }

    private fun getPokemonList(): MutableList<MutableMap<String, String>> {

        val pokemonList: MutableList<MutableMap<String, String>> = mutableListOf()

		var menu = mutableMapOf<String, String>("id" to "1", "name" to "aaa")
        pokemonList.add(menu)
		menu = mutableMapOf("id" to "2", "name" to "bbb")
        pokemonList.add(menu)
		menu = mutableMapOf("id" to "2", "name" to "bbb")
        pokemonList.add(menu)
		menu = mutableMapOf("id" to "2", "name" to "bbb")
        pokemonList.add(menu)
		menu = mutableMapOf("id" to "2", "name" to "bbb")
        pokemonList.add(menu)
		menu = mutableMapOf("id" to "2", "name" to "bbb")
        pokemonList.add(menu)
		menu = mutableMapOf("id" to "2", "name" to "bbb")
        pokemonList.add(menu)
		menu = mutableMapOf("id" to "2", "name" to "bbb")
        pokemonList.add(menu)
		menu = mutableMapOf("id" to "2", "name" to "bbb")
        pokemonList.add(menu)
		menu = mutableMapOf("id" to "2", "name" to "ccc")
        pokemonList.add(menu)

//        val imageView: ImageView = view.findViewById(R.id.imageView)
//        val imageUrl = "https://img.wallpaper.sc/ipad/images/2448x2448/ipad-2448x2448-wallpaper_01438.jpg"
//
//        Glide.with(this)
//            .load(imageUrl)
//            .into(imageView)

        return pokemonList;
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