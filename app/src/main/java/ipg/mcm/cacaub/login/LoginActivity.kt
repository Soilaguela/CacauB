package ipg.mcm.cacaub.login

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.RelativeLayout
import androidx.appcompat.widget.AppCompatButton
import androidx.appcompat.widget.AppCompatTextView
import androidx.core.widget.NestedScrollView
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import ipg.mcm.cacaub.R

class LoginActivity : AppCompatActivity() , View.OnClickListener {
    private val activity = this@LoginActivity
     private lateinit var relativeLayout: RelativeLayout
    private lateinit var textInputLayoutEmail: TextInputLayout
    private lateinit var textInputLayoutPassword: TextInputLayout
    private lateinit var textInputEditTextEmail: TextInputEditText
    private lateinit var textInputEditTextPassword: TextInputEditText
    private lateinit var appCompatButtonLogin: AppCompatButton
    private lateinit var textViewLinkRegister: AppCompatTextView
    private lateinit var inputValidation:Tabelauser
    private lateinit var databaseHelper: DadosOpenHelp
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        // hiding the action bar
        supportActionBar!!.hide()
        // initializing the views
        initViews()
        // initializing the listeners
        initListeners()
        // initializing the objects
        initObjects()
    }
    /**
     * This method is to initialize views
     */
    private fun initViews() {
        relativeLayout = findViewById(R.id.relativeLayout) as RelativeLayout
        textInputLayoutEmail = findViewById(R.id.textInputLayoutEmail) as TextInputLayout
        textInputLayoutPassword = findViewById(R.id.textInputLayoutPassword) as TextInputLayout
        textInputEditTextEmail = findViewById(R.id.textInputEditTextEmail) as TextInputEditText
        textInputEditTextPassword = findViewById(R.id.textInputEditTextPassword) as TextInputEditText
        appCompatButtonLogin = findViewById(R.id.appCompatButtonLogin) as AppCompatButton
        textViewLinkRegister = findViewById(R.id.textViewLinkRegister) as AppCompatTextView
    }
    /**
     * This method is to initialize listeners
     */
    private fun initListeners() {
        appCompatButtonLogin!!.setOnClickListener(this)
        textViewLinkRegister!!.setOnClickListener(this)
    }
    /**
     * This method is to initialize objects to be used
     */
    private fun initObjects() {
        databaseHelper = DadosOpenHelp(activity)
        inputValidation = Tabelauser(activity)
    }
    /**
     * This implemented method is to listen the click on view
     *
     * @param v
     */
    override fun onClick(v: View) {
        when (v.id) {
            R.id.appCompatButtonLogin -> verifyFromSQLite()
            R.id.textViewLinkRegister -> {
                // Navigate to RegisterActivity
                val intentRegister = Intent(applicationContext, RegisterActivity::class.java)
                startActivity(intentRegister)
            }
        }
    }
    /**
     * This method is to validate the input text fields and verify login credentials from SQLite
     */
    private fun verifyFromSQLite() {
        if (!inputValidation!!.isInputEditTextFilled(textInputEditTextEmail!!, textInputLayoutEmail!!, getString(R.string.error_message_email))) {
            return
        }
        if (!inputValidation!!.isInputEditTextEmail(textInputEditTextEmail!!, textInputLayoutEmail!!, getString(R.string.error_message_email))) {
            return
        }
        if (!inputValidation!!.isInputEditTextFilled(textInputEditTextPassword!!, textInputLayoutPassword!!, getString(R.string.error_message_email))) {
            return
        }
      /*  if (databaseHelper!!.checkUser(textInputEditTextEmail!!.text.toString().trim { it <= ' ' }, textInputEditTextPassword!!.text.toString().trim { it <= ' ' })) {
            val accountsIntent = Intent(activity, UsersListActivity::class.java)
            accountsIntent.putExtra("EMAIL", textInputEditTextEmail!!.text.toString().trim { it <= ' ' })
            emptyInputEditText()
            startActivity(accountsIntent)
        }*/ else {
            // Snack Bar to show success message that record is wrong
            Snackbar.make(relativeLayout!!, getString(R.string.error_valid_email_password), Snackbar.LENGTH_LONG).show()
        }
    }
    /**
     * This method is to empty all input edit text
     */
    private fun emptyInputEditText() {
        textInputEditTextEmail!!.text = null
        textInputEditTextPassword!!.text = null
    }
}