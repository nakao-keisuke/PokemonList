package com.webserva.wings.android.pokemonzukan.view.activity

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import com.webserva.wings.android.pokemonzukan.R

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        supportActionBar?.setHomeActionContentDescription("一覧")
        supportActionBar?.setBackgroundDrawable(ColorDrawable(Color.rgb(231, 78, 127)))
    }

    fun setupBackButton(enableBackButton: Boolean) {
        // アクションバーを取得
        val actionBar: ActionBar? = supportActionBar
        // アクションバーに戻るボタン「←」をセット（引数が true: 表示、false: 非表示）
        actionBar?.setDisplayHomeAsUpEnabled(enableBackButton)
    }
}