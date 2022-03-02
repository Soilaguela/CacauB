package ipg.mcm.cacaub

import android.database.Cursor
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.SimpleCursorAdapter
import android.widget.Toast
import androidx.loader.app.LoaderManager
import androidx.loader.content.CursorLoader
import androidx.loader.content.Loader
import androidx.navigation.fragment.findNavController
import ipg.mcm.cacaub.databinding.FragmentEditaVendaBinding

/**
 * A simple [Fragment] subclass.
 * Use the [EditaVendaFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class EditaVendaFragment : Fragment(), LoaderManager.LoaderCallbacks<Cursor> {
    private var _binding: FragmentEditaVendaBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    private lateinit var venda : Venda

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentEditaVendaBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val activity = activity as MainActivity
        activity.fragment = this
        activity.menuAtual = R.menu.menu_altera_venda

        venda = activity.vendaSelecionado!!

        binding.editTextTitulo.setText(venda.descricao)
        binding.editTextValor.setText(venda.valor)

        LoaderManager.getInstance(this)
            .initLoader(ID_LOADER_EMPRESA, null, this)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    fun voltaListaVenda() = findNavController().navigate(R.id.action_editaVendaFragment_to_ListaVendaFragment)

    fun guardar() {
        venda.descricao = binding.editTextTitulo.text.toString()
        if (venda.descricao.isBlank()) {
            binding.editTextTitulo.setError(getString(R.string.titulo_obrigatorio))
            binding.editTextTitulo.requestFocus()
            return
        }

        venda.valor = binding.editTextValor.text.toString()
        if (venda.valor.isBlank()) {
            binding.editTextValor.setError(getString(R.string.valor_obrigatorio))
            binding.editTextValor.requestFocus()
            return
        }

        venda.idEmpresa = binding.spinnerEmpresa.selectedItemId

        val uriVenda = Uri.withAppendedPath(
            ContentProviderCacauB.ENDERECO_VENDA,
            venda.id.toString()
        )

        val registos = requireActivity().contentResolver.update(
            uriVenda,
            venda.toContentValues(),
            null,
            null
        )

        if (registos != 1) {
            Toast.makeText(activity, getString(R.string.erro_guardar_seminario), Toast.LENGTH_LONG).show()
            return
        }

        Toast.makeText(activity, getString(R.string.venda_alterado), Toast.LENGTH_LONG).show()
        voltaListaVenda()
    }

    fun processaOpcaoMenu(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_guardar_novo -> guardar()
            R.id.action_cancelar_novo -> voltaListaVenda()
            else -> return false
        }

        return true
    }

    /**
     * Instantiate and return a new Loader for the given ID.
     *
     *
     * This will always be called from the process's main thread.
     *
     * @param id The ID whose loader is to be created.
     * @param args Any arguments supplied by the caller.
     * @return Return a new Loader instance that is ready to start loading.
     */
    override fun onCreateLoader(id: Int, args: Bundle?): Loader<Cursor>  =
        CursorLoader(
            requireContext(),
            ContentProviderCacauB.ENDERECO_EMPRESA,
            TabelaEpresa.TODOS_CAMPOS,
            null,
            null,
            TabelaEpresa.CAMPO_NOME
        )

    /**
     * Called when a previously created loader has finished its load.  Note
     * that normally an application is *not* allowed to commit fragment
     * transactions while in this call, since it can happen after an
     * activity's state is saved.  See [ FragmentManager.openTransaction()][androidx.fragment.app.FragmentManager.beginTransaction] for further discussion on this.
     *
     *
     * This function is guaranteed to be called prior to the release of
     * the last data that was supplied for this Loader.  At this point
     * you should remove all use of the old data (since it will be released
     * soon), but should not do your own release of the data since its Loader
     * owns it and will take care of that.  The Loader will take care of
     * management of its data so you don't have to.  In particular:
     *
     *
     *  *
     *
     *The Loader will monitor for changes to the data, and report
     * them to you through new calls here.  You should not monitor the
     * data yourself.  For example, if the data is a [android.database.Cursor]
     * and you place it in a [android.widget.CursorAdapter], use
     * the [android.widget.CursorAdapter.CursorAdapter] constructor *without* passing
     * in either [android.widget.CursorAdapter.FLAG_AUTO_REQUERY]
     * or [android.widget.CursorAdapter.FLAG_REGISTER_CONTENT_OBSERVER]
     * (that is, use 0 for the flags argument).  This prevents the CursorAdapter
     * from doing its own observing of the Cursor, which is not needed since
     * when a change happens you will get a new Cursor throw another call
     * here.
     *  *  The Loader will release the data once it knows the application
     * is no longer using it.  For example, if the data is
     * a [android.database.Cursor] from a [android.content.CursorLoader],
     * you should not call close() on it yourself.  If the Cursor is being placed in a
     * [android.widget.CursorAdapter], you should use the
     * [android.widget.CursorAdapter.swapCursor]
     * method so that the old Cursor is not closed.
     *
     *
     *
     * This will always be called from the process's main thread.
     *
     * @param loader The Loader that has finished.
     * @param data The data generated by the Loader.
     */
    override fun onLoadFinished(loader: Loader<Cursor>, data: Cursor?) {
        binding.spinnerEmpresa.adapter = SimpleCursorAdapter(
            requireContext(),
            android.R.layout.simple_list_item_1,
            data,
            arrayOf<String>(TabelaEpresa.CAMPO_NOME),
            intArrayOf(android.R.id.text1),
            0
        )

        atualizaEmpresaSelecionado()
    }

    /**
     * Called when a previously created loader is being reset, and thus
     * making its data unavailable.  The application should at this point
     * remove any references it has to the Loader's data.
     *
     *
     * This will always be called from the process's main thread.
     *
     * @param loader The Loader that is being reset.
     */
    override fun onLoaderReset(loader: Loader<Cursor>) {
        if (_binding == null) return
        binding.spinnerEmpresa.adapter = null
    }

    private fun atualizaEmpresaSelecionado() {
        val ultimaEmpresa = binding.spinnerEmpresa.count - 1
        for (i in 0..ultimaEmpresa) {
            if (venda.idEmpresa == binding.spinnerEmpresa.getItemIdAtPosition(i)) {
                binding.spinnerEmpresa.setSelection(i)
                return
            }
        }
    }

    companion object {
        const val ID_LOADER_EMPRESA = 0
    }
}