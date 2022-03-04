package ipg.mcm.cacaub

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import ipg.mcm.cacaub.arrayJSON.ArrayJSONActivity
import ipg.mcm.cacaub.databinding.ActivityMain2Binding


import ipg.mcm.cacaub.nestedJSON.NestedJSONActivity
import ipg.mcm.cacaub.simpleJSON.SimpleJSONActivity

class ButtonActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMain2Binding
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main2)

        binding.simpleJsonButton.setOnClickListener {
            val intent = Intent(this@ButtonActivity, SimpleJSONActivity::class.java)
            this@ButtonActivity.startActivity(intent)
        }

        binding.arrayJsonButton.setOnClickListener {
            val intent = Intent(this@ButtonActivity, ArrayJSONActivity::class.java)
            this@ButtonActivity.startActivity(intent)
        }

        binding.nestedJsonButton.setOnClickListener {
            val intent = Intent(this@ButtonActivity, NestedJSONActivity::class.java)
            this@ButtonActivity.startActivity(intent)
        }

    }


    }

