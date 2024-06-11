package com.webserva.wings.android.pokemonzukan

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide


class PokemonListFragment : Fragment() {

    companion object {


    }

    override fun onStart() {
        super.onStart()

        //リストのデータ用意処理
        view?.let {

            val _rv = it.findViewById<RecyclerView>(R.id.recyclerView);

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
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        return inflater.inflate(R.layout.fragment_pokemon_list, container, false)
    }

    private inner class RecyclerViewHolder(val view: View): RecyclerView.ViewHolder(view) {

        var s1: TextView
        var s2: TextView

        init {
            // 引数で渡されたリスト1行分の画面部品中から表示に使われるTextViewを取得。
            s1 = itemView.findViewById(R.id.s1)
            s2 = itemView.findViewById(R.id.s2)
        }
    }
    private inner class RecyclerAdapter(private val _listData: MutableList<MutableMap<String, Any>>) : RecyclerView.Adapter<RecyclerViewHolder>(){

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
                val menuName = item["s1"] as String
                // メニュー金額を取得。
                val menuPrice = item["s2"] as Int
                // 表示用に金額を文字列に変換。
                val menuPriceStr = menuPrice.toString()
                // メニュー名と金額をビューホルダ中のTextViewに設定。
                holder.s1.text = menuName
                holder.s2.text = menuPriceStr
            }
        }
    }



    private fun getPokemonList(): MutableList<MutableMap<String, Any>> {

        val pokemonList: MutableList<MutableMap<String, Any>> = mutableListOf()
		// 「から揚げ定食」のデータを格納するMapオブジェクトの用意とmenuListへのデータ登録。
		var menu = mutableMapOf<String, Any>("s1" to "から揚げ定食", "s2" to 800)
        pokemonList.add(menu)
		// 「ハンバーグ定食」のデータを格納するMapオブジェクトの用意とmenuListへのデータ登録。
		menu = mutableMapOf("s1" to "ハンバーグ定食", "s2" to 850)
        pokemonList.add(menu)
//        val imageView: ImageView = view.findViewById(R.id.imageView)
//        val imageUrl = "https://img.wallpaper.sc/ipad/images/2448x2448/ipad-2448x2448-wallpaper_01438.jpg"
//
//        Glide.with(this)
//            .load(imageUrl)
//            .into(imageView)

        return pokemonList;
    }
}