package ipg.mcm.cacaub.login

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.RelativeLayout
import androidx.appcompat.widget.AppCompatButton
import androidx.appcompat.widget.AppCompatTextView
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import ipg.mcm.cacaub.R

class RegisterActivity : AppCompatActivity() , View.OnClickListener {
    private val activity = this@RegisterActivity
    private lateinit var relativeLayout: RelativeLayout
    private lateinit var textInputLayoutName: TextInputLayout
    private lateinit var textInputLayoutEmail: TextInputLayout
    private lateinit var textInputLayoutPassword: TextInputLayout
    private lateinit var textInputLayoutConfirmPassword: TextInputLayout
    private lateinit var textInputEditTextName: TextInputEditText
    private lateinit var textInputEditTextEmail: TextInputEditText
    private lateinit var textInputEditTextPassword: TextInputEditText
    private lateinit var textInputEditTextConfirmPassword: TextInputEditText
    private lateinit var appCompatButtonRegister: AppCompatButton
    private lateinit var appCompatTextViewLoginLink: AppCompatTextView
    private lateinit var inputValidation:Tabelauser
    private lateinit var databaseHelper: DadosOpenHelp
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)
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
        textInputLayoutName = findViewById(R.id.textInputLayoutName) as TextInputLayout
        textInputLayoutEmail = findViewById(R.id.textInputLayoutEmail) as TextInputLayout
        textInputLayoutPassword = findViewById(R.id.textInputLayoutPassword) as TextInputLayout
        textInputLayoutConfirmPassword = findViewById(R.id.textInputLayoutConfirmPassword) as TextInputLayout
        textInputEditTextName = findViewById(R.id.textInputEditTextName) as TextInputEditText
        textInputEditTextEmail = findViewById(R.id.textInputEditTextEmail) as TextInputEditText
        textInputEditTextPassword = findViewById(R.id.textInputEditTextPassword) as TextInputEditText
        textInputEditTextConfirmPassword = findViewById(R.id.textInputEditTextConfirmPassword) as TextInputEditText
        appCompatButtonRegister = findViewById(R.id.appCompatButtonRegister) as AppCompatButton
        appCompatTextViewLoginLink = findViewById(R.id.appCompatTextViewLoginLink) as AppCompatTextView
    }
    /**
     * This method is to initialize listeners
     */
    private fun initListeners() {
        appCompatButtonRegister!!.setOnClickListener(this)
        appCompatTextViewLoginLink!!.setOnClickListener(this)
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
            R.id.appCompatButtonRegister -> postDataToSQLite()
            R.id.appCompatTextViewLoginLink -> finish()
        }
    }
    /**
     * This method is to validate the input text fields and post data to SQLite
     */
    private fun postDataToSQLite() {
        if (!inputValidation!!.isInputEditTextFilled(textInputEditTextName, textInputLayoutName, getString(R.string.error_message_name))) {
            return
        }
        if (!inputValidation!!.isInputEditTextFilled(textInputEditTextEmail, textInputLayoutEmail, getString(R.string.error_message_email))) {
            return
        }
        if (!inputValidation!!.isInputEditTextEmail(textInputEditTextEmail, textInputLayoutEmail, getString(R.string.error_message_email))) {
            return
        }
        if (!inputValidation!!.isInputEditTextFilled(textInputEditTextPassword, textInputLayoutPassword, getString(R.string.error_message_password))) {
            return
        }
        if (!inputValidation!!.isInputEditTextMatches(textInputEditTextPassword, textInputEditTextConfirmPassword,
                textInputLayoutConfirmPassword, getString(R.string.error_password_match))) {
            return
        }
        if (!databaseHelper!!.checkUser(textInputEditTextEmail!!.text.toString().trim())) {
            var user = User(name = textInputEditTextName!!.text.toString().trim(),
                email = textInputEditTextEmail!!.text.toString().trim(),
                password = textInputEditTextPassword!!.text.toString().trim())
            databaseHelper!!.addUser(user)
            // Snack Bar to show success message that record saved successfully
            Snackbar.make(relativeLayout!!, getString(R.string.success_message), Snackbar.LENGTH_LONG).show()
            emptyInputEditText()
        } else {
            // Snack Bar to show error message that record already exists
            Snackbar.make(relativeLayout!!, getString(R.string.error_email_exists), Snackbar.LENGTH_LONG).show()
        }
    }
    /**
     * This method is to empty all input edit text
     */
    private fun emptyInputEditText() {
        textInputEditTextName!!.text = null
        textInputEditTextEmail!!.text = null
        textInputEditTextPassword!!.text = null
        textInputEditTextConfirmPassword!!.text = null
    }
}