package com.webserva.wings.android.pokemonzukan

import android.os.Bundle
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        supportActionBar?.setHomeActionContentDescription("一覧")
    }

    fun setupBackButton(enableBackButton: Boolean) {
        // アクションバーを取得
        val actionBar: ActionBar? = supportActionBar
        // アクションバーに戻るボタン「←」をセット（引数が true: 表示、false: 非表示）
        actionBar?.setDisplayHomeAsUpEnabled(enableBackButton)
    }
}