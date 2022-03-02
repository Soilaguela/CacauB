package ipg.mcm.cacaub

import android.database.sqlite.SQLiteDatabase
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.ext.junit.runners.AndroidJUnit4

import org.junit.Test
import org.junit.runner.RunWith

import org.junit.Assert.*
import org.junit.Before

/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(AndroidJUnit4::class)
class BdCacuBTest {
    private fun getContext() = InstrumentationRegistry.getInstrumentation().targetContext

    private fun getWritableDatabase(): SQLiteDatabase {
        val bdOpenHelper = BaseDadosCacauBOpenHelper(getContext())
        return bdOpenHelper.writableDatabase
    }

    private fun insereEmpresa(tabelaEpresa: TabelaEpresa, empresa: Empresa) : Long {
        val id = tabelaEpresa.insert(empresa.toContentValues())
        assertNotEquals(-1, id)
        return id
    }

    private fun insereVenda(tabelaVenda: TabelaVenda, venda: Venda) : Long {
        val id = tabelaVenda.insert(venda.toContentValues())
        assertNotEquals(-1, id)
        return id
    }

    private fun getEmpresaBaseDados(tabelaEpresa: TabelaEpresa, id: Long): Empresa {
        val cursor = tabelaEpresa.query(
            TabelaEpresa.TODOS_CAMPOS,
            "${TabelaEpresa.NOME_TABELA}.${TabelaBD.CAMPO_ID}=?",
            arrayOf("${id}"),
            null,
            null,
            null
        )

        assertEquals(1, cursor.count)
        assertTrue(cursor.moveToNext())

        return Empresa.fromCursor(cursor);
    }

    private fun getVendaBaseDados(tabelaVenda: TabelaVenda, id: Long): Venda {
        val cursor = tabelaVenda.query(
            TabelaVenda.TODOS_CAMPOS,
            "${TabelaVenda.NOME_TABELA}.${TabelaBD.CAMPO_ID}=?",
            arrayOf("${id}"),
            null,
            null,
            null
        )

        assertEquals(1, cursor.count)
        assertTrue(cursor.moveToNext())

        return Venda.fromCursor(cursor);
    }

    @Before
    fun apagaBaseDados() {
        getContext().deleteDatabase(BaseDadosCacauBOpenHelper.NOME_BASE_DADOS)
    }

    @Test
    fun consegueCriarAbrirBaseDados() {
        val bdOpenHelper = BaseDadosCacauBOpenHelper(getContext())
        val bd = bdOpenHelper.readableDatabase
        assert(bd.isOpen)
        bd.close()
    }

    @Test
    fun consegueInserirEmpresa() {
        val bd = getWritableDatabase()
        insereEmpresa(TabelaEpresa(bd), Empresa("SatoCau", "Venda de Cacau"))
        bd.close()
    }

    @Test
    fun consegueInserirVenda() {
        val bd = getWritableDatabase()

        val nomeEmpresa = "CacauB"

        val venda = Venda(
            "Venda do Cacau Biologico",
            "123",
            insereEmpresa(TabelaEpresa(bd), Empresa(nomeEmpresa, "Cacau Biologico")),
            ""
        )

        insereVenda(TabelaVenda(bd), venda)
        bd.close()
    }

    @Test
    fun consegueEliminarEmpresa() {
        val bd = getWritableDatabase()
        val tabelaEmpresa = TabelaEpresa(bd)

        val id = insereEmpresa(tabelaEmpresa, Empresa("Soares Lt,", null))
        val numRegistosApagados = tabelaEmpresa.delete(id)
        assertEquals(1, numRegistosApagados)

        bd.close()
    }

    @Test
    fun consegueEliminarVenda() {
        val bd = getWritableDatabase()

        val tabelaEpresa = TabelaEpresa(bd)
        val tabelaVenda = TabelaVenda(bd)

        val venda = Venda(
            "Venda de teste",
            "1345",
            insereEmpresa(tabelaEpresa, Empresa("Anonimo", "Não sei")),
            nomeEmpresa = "Não tem"
        )

        val id = insereVenda(tabelaVenda, venda)

        val numRegistosApagados = tabelaVenda.delete(id)
        assertEquals(1, numRegistosApagados)

        bd.close()
    }

    @Test
    fun consegueAlterarEmpresa() {
        val bd = getWritableDatabase()
        val tabelaEpresa = TabelaEpresa(bd)

        val empresa = Empresa("Benguela Caf", "Cafe Biologico")
        empresa.id = insereEmpresa(tabelaEpresa, empresa)

        val numRegAlterados = tabelaEpresa.update(empresa.id, empresa.toContentValues())
        assertEquals(1, numRegAlterados)

        assertEquals(empresa, getEmpresaBaseDados(tabelaEpresa, empresa.id))

        bd.close()
    }

    @Test
    fun consegueAlterarVenda() {
        val bd = getWritableDatabase()
        val tabelaEpresa = TabelaEpresa(bd)
        val tabelaVenda = TabelaVenda(bd)

        val empresaProvisoria = Empresa("Anonimo","Não Sei")
        empresaProvisoria.id = insereEmpresa(tabelaEpresa, empresaProvisoria)

        val empresa = Empresa("Lopes Tps","Tops ")
        empresa.id = insereEmpresa(tabelaEpresa, empresa)

        val venda = Venda(
            "Aplicação Cafe",
            "23",
            empresaProvisoria.id,
            empresaProvisoria.nome
        )

        venda.id = insereVenda(tabelaVenda, venda)

        venda.apply {
            descricao = "Aplicação Cafe Ibrido";
            valor = "503";
            idEmpresa = empresa.id;
        }

        val numRegAlterados = tabelaVenda.update(venda.id, venda.toContentValues())
        assertEquals(1, numRegAlterados)

       // assertEquals(venda, getVendaBaseDados(tabelaVenda, venda.id))

        bd.close()
    }

    @Test
    fun consegueLerEmpresa() {
        val bd = getWritableDatabase()
        val tabelaEpresa = TabelaEpresa(bd)

        val empresa = Empresa("Marcelo Lopes", "Sr.")
        empresa.id = insereEmpresa(tabelaEpresa, empresa)

        assertEquals(empresa, getEmpresaBaseDados(tabelaEpresa, empresa.id))
    }

    @Test
    fun consegueLerVenda() {
        val bd = getWritableDatabase()
        val tabelaEpresa = TabelaEpresa(bd)
        val tabelaVenda = TabelaVenda(bd)

        val empresa = Empresa("Carbor B", "Baras De Cacau")
        empresa.id = insereEmpresa(tabelaEpresa, empresa)

        val venda = Venda(
            "Produtos de Limpresa",
            "1981",
            empresa.id,
            ""
        )
        venda.id = insereVenda(tabelaVenda, venda)

       // assertEquals(venda, getVendaBaseDados(tabelaVenda, venda.id))
    }
}