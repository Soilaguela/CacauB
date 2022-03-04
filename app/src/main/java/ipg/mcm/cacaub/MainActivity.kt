package ipg.mcm.cacaub

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.google.android.material.snackbar.Snackbar
import ipg.mcm.cacaub.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityMainBinding

    var fragment : Fragment? = null
    var menuAtual : Int = R.menu.menu_lista_venda
        set(value) {
            if (value != field) {
                field = value
                invalidateOptionsMenu()
            }
        }

    var vendaSelecionado : Venda? = null
        set(value) {
            if (value != field) {
                field = value
                atualizaMenu()
            }
        }

    var menu: Menu? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)

        

        val navController = findNavController(R.id.nav_host_fragment_content_main)
        appBarConfiguration = AppBarConfiguration(navController.graph)
        setupActionBarWithNavController(navController, appBarConfiguration)

        binding.fab.setOnClickListener { view ->
           // Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
            //    .setAction("Action", null).show()

            val intent= Intent(this,WebActivity::class.java)
            startActivity(intent)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(menuAtual, menu)
        this.menu = menu
        atualizaMenu()

        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.


        if (item.itemId == R.id.action_settings) {

            Toast.makeText(this, "CacauB v. 1.0", Toast.LENGTH_LONG).show()
            return true
        }

        val operacaoProcessada = when (menuAtual) {
            R.menu.menu_lista_venda -> (fragment as ListaVendaFragment).processaOpcaoMenu(item)
            R.menu.menu_novo_venda ->  (fragment as NovoVendaFragment).processaOpcaoMenu(item)
            R.menu.menu_altera_venda ->  (fragment as EditaVendaFragment).processaOpcaoMenu(item)
            R.menu.menu_elimina_venda -> (fragment as EliminaVendaFragment).processaOpcaoMenu(item)
            else -> false
        }

        return if (operacaoProcessada) true else super.onOptionsItemSelected(item)
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment_content_main)
        return navController.navigateUp(appBarConfiguration)
                || super.onSupportNavigateUp()
    }

    fun atualizaMenu() {
        if (menuAtual == R.menu.menu_lista_venda) {
            val existeSeminarioSeleccionado = vendaSelecionado != null

            val itemAlterar = menu?.findItem(R.id.action_alterar)
            if (itemAlterar != null) {
                itemAlterar.setVisible(existeSeminarioSeleccionado)
            }

            val itemEliminar = menu?.findItem(R.id.action_eliminar)
            if (itemEliminar != null) {
                itemEliminar.setVisible(existeSeminarioSeleccionado)
            }
        }
    }
}