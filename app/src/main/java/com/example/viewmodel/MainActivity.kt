package com.example.viewmodel

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.viewmodel.data.DataForm
import com.example.viewmodel.data.DataSource.jenis
import com.example.viewmodel.data.DataSource.status
import com.example.viewmodel.ui.theme.ViewModelTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ViewModelTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    TampilLayout()
                }
            }
        }
    }
}

@Composable
fun TampilLayout(modifier: Modifier = Modifier){
    Card (
        modifier = modifier,
        elevation = CardDefaults.cardElevation(defaultElevation = 5.dp)
    ){
        Row (
            Modifier.height(60.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ){

            Text(text = "Register")
        }
        Divider()
        Column (
            verticalArrangement = Arrangement.spacedBy(2.dp),
            modifier = Modifier.padding(15.dp)
        ){
            Tampilan()
        }

    }
}
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Tampilan(cobaViewModel: CobaViewModel = viewModel()) {
    var textNama by remember { mutableStateOf("") }
    var textTlp by remember { mutableStateOf("") }
    var textEmail by remember { mutableStateOf("") }
    var textAlamat by remember { mutableStateOf("") }
    val context = LocalContext.current
    val dataForm: DataForm
    val uiState by cobaViewModel.uiState.collectAsState()
    dataForm = uiState;
    Text(
        text = "Create Your Account"
    )
    OutlinedTextField(
        value = textNama,
        singleLine = true,
        shape = MaterialTheme.shapes.large,
        modifier = Modifier.fillMaxWidth(),
        label = {
            Text(text = "Nama lengkap")
        },
        onValueChange = {
            textNama = it
        }
    )
    OutlinedTextField(
        value = textTlp,
        singleLine = true,
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
        shape = MaterialTheme.shapes.large,
        modifier = Modifier.fillMaxWidth(),
        label = {
            Text(text = "Nomor telepon")
        },
        onValueChange = { textTlp = it }
    )
    OutlinedTextField(
        value = textEmail,
        singleLine = true,
        shape = MaterialTheme.shapes.large,
        modifier = Modifier.fillMaxWidth(),
        label = {
            Text(text = "Email")
        },
        onValueChange = { textEmail = it }
    )
    Text(
        modifier = Modifier
            .padding(10.dp),
        text = "Jenis Kelamin :"
    )
    SelectJK(
        options = jenis.map { id -> context.resources.getString(id) },
        onSelectionChanged = { cobaViewModel.setJenisK(it) }
    )
    Text(text = "Status :")
    SelectStatus(
        options = status.map { id -> context.resources.getString(id) },
        onSelectionChanged = { cobaViewModel.setStatus(it) }
    )
    OutlinedTextField(
        value = textAlamat,
        singleLine = true,
        shape = MaterialTheme.shapes.large,
        modifier = Modifier.fillMaxWidth(),
        label = {
            Text(text = "Alamat")
        },
        onValueChange = { textAlamat = it }
    )
    Button(
        modifier = Modifier.fillMaxWidth(),
        onClick = { cobaViewModel.insertData(textNama, textTlp, textEmail, dataForm.sex, dataForm.stat, textAlamat) }
    ) {
        Text(text = stringResource(R.string.submit), fontSize = 16.sp)
    }
    Spacer(modifier = Modifier.height(10.dp))
    TextResult(
        namanya = cobaViewModel.namaUsr,
        telponnya = cobaViewModel.noTlp,
        jenisnya = cobaViewModel.jenisKl,
        emailnya = cobaViewModel.eMail,
        alamatnya = cobaViewModel.alamatU,
        statusnya = cobaViewModel.statusM
    )
}

@Composable
fun TextResult(namanya: String, telponnya: String, jenisnya: String, emailnya: String, statusnya: String, alamatnya: String) {
    ElevatedCard(
        elevation = CardDefaults.cardElevation(defaultElevation = 6.dp),
        modifier = Modifier.fillMaxWidth()
    ) {
        Text(
            text = "Jenis kelamin : " + jenisnya,
            modifier = Modifier.padding(horizontal = 10.dp, vertical = 4.dp)
        )
        Text(
            text = "Status : " + statusnya,
            modifier = Modifier.padding(horizontal = 10.dp, vertical = 4.dp)
        )
        Text(
            text = "Alamat : " + alamatnya,
            modifier = Modifier.padding(horizontal = 10.dp, vertical = 4.dp)
        )
        Text(
            text = "Email : " + emailnya,
            modifier = Modifier.padding(horizontal = 10.dp, vertical = 4.dp)
        )
    }
}

@Composable
fun SelectJK(
    options: List<String>,
    onSelectionChanged: (String) -> Unit = {}
) {
    var selectedValue by rememberSaveable { mutableStateOf("") }
    Row (
        modifier = Modifier
            .padding(10.dp)
    ){
        options.forEach{
            item ->
            Row(
                modifier = Modifier
                    .selectable(
                        selected = selectedValue == item,
                        onClick = {
                            selectedValue = item
                            onSelectionChanged(item)
                        }
                    ),
                verticalAlignment = Alignment.CenterVertically
            ) {
                RadioButton(selected = selectedValue == item,
                    onClick = {
                        selectedValue = item
                        onSelectionChanged(item)
                    }
                )
                Text(item)
            }
        }
    }
}

@Composable
fun SelectStatus(
    options: List<String>,
    onSelectionChanged: (String) -> Unit = {}
) {
    var selectedValue by rememberSaveable { mutableStateOf("") }
    Row (
        modifier = Modifier
            .padding(10.dp)
    ){
        options.forEach{
                item ->
            Row(
                modifier = Modifier
                    .selectable(
                        selected = selectedValue == item,
                        onClick = {
                            selectedValue = item
                            onSelectionChanged(item)
                        }
                    ),
                verticalAlignment = Alignment.CenterVertically
            ) {
                RadioButton(selected = selectedValue == item,
                    onClick = {
                        selectedValue = item
                        onSelectionChanged(item)
                    }
                )
                Text(item)
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    ViewModelTheme {
        TampilLayout()
    }
}