package ipg.mcm.cacaub

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import ipg.mcm.cacaub.arrayJSON.ArrayJSONActivity
import ipg.mcm.cacaub.nestedJSON.NestedJSONActivity
import ipg.mcm.cacaub.simpleJSON.SimpleJSONActivity

class WebActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_web)



       /* binding.simpleJsonButton.setOnClickListener {
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
        }*/
    }
    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater: MenuInflater = menuInflater
        inflater.inflate(R.menu.menu_voltar, menu)
        return true
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle item selection
        return when (item.itemId) {
            R.id.action_voltar -> {
                val intent = Intent(this, MainActivity::class.java)
                this.startActivity(intent)
                true
            }

            else -> super.onOptionsItemSelected(item)
        }
    }
    fun SimpleJSONActivity(view: View) {
        val intent = Intent(this, SimpleJSONActivity::class.java)
        this.startActivity(intent)
    }
    fun ArrayJSONActivity(view: View) {
        val intent = Intent(this, ArrayJSONActivity::class.java)
        this.startActivity(intent)
    }
    fun NestedJSONActivity(view: View) {
        val intent = Intent(this, NestedJSONActivity::class.java)
        this.startActivity(intent)
    }
}