package ipg.mcm.cacaub

import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import ipg.mcm.cacaub.databinding.FragmentEliminaSeminarioBinding

class EliminaVendaFragment : Fragment() {
    private var _binding: FragmentEliminaSeminarioBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    private lateinit var venda : Venda

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentEliminaSeminarioBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val activity = activity as MainActivity
        activity.fragment = this
        activity.menuAtual = R.menu.menu_elimina_venda

        venda = activity.vendaSelecionado!!

        binding.textViewTitulo.setText(venda.descricao)
        binding.textViewSumario.setText(venda.valor)
        binding.textViewOrador.setText(venda.nomeEmpresa)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    fun voltaListaSeminarios() = findNavController().navigate(R.id.action_eliminaVendaFragment_to_ListaVendaFragment)

    fun eliminar() {
        val uriSeminario = Uri.withAppendedPath(
            ContentProviderCacauB.ENDERECO_VENDA,
            venda.id.toString()
        )

        val registos = requireActivity().contentResolver.delete(
            uriSeminario,
            null,
            null
        )

        if (registos != 1) {
            Toast.makeText(activity, getString(R.string.erro_eliminar_seminario), Toast.LENGTH_LONG).show()
            return
        }

        Toast.makeText(activity, getString(R.string.seminario_eliminado), Toast.LENGTH_LONG).show()
        voltaListaSeminarios()
    }

    fun processaOpcaoMenu(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_eliminar -> eliminar()
            R.id.action_cancelar_eliminar -> voltaListaSeminarios()
            else -> return false
        }

        return true
    }
}