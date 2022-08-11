package com.example.simplecalccomposeviewmodel

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.simplecalccomposeviewmodel.ui.theme.SimpleCalcComposeViewModelTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            SimpleCalcComposeViewModelTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    CalculatorScreen()
                }
            }
        }
    }
}

@Composable
fun CalculatorScreen(vm: MainViewModel = viewModel()) {
    val context = LocalContext.current

    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Spacer(modifier = Modifier.height(30.dp))
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically
        ) {
            NumberInputField(
                value = vm.input1,
                label = "Zahl 1",
                onTextChange =  { newtext -> vm.input1 = newtext},
                modifier = Modifier.width(100.dp)
            )

            Text(vm.selectedOption, style = MaterialTheme.typography.h3)

            NumberInputField(
                value = vm.input2,
                label = "Zahl 2",
                onTextChange =  { newtext -> vm.input2 = newtext},
                modifier = Modifier.width(100.dp)
            )

        }
        Text("=", modifier = Modifier.padding(30.dp), style = MaterialTheme.typography.h3)

        Text(vm.result, style = MaterialTheme.typography.h3)

        Spacer(modifier = Modifier.height(30.dp))

        RadioButtonGroup(
            radioOptions = vm.radioOptions,
            selectedOption = vm.selectedOption,
            onOptionSelected = { selection -> vm.setSelection(selection) }
        )

        Button(
            onClick = {
                if (vm.validate()) vm.calculate()
                else {
                    Toast.makeText(
                        context,
                        "kein gültiger Input",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            },
        ) {
            Text("Berechne")
        }
    }

}
@Composable
fun NumberInputField(
    value: String,
    label: String = "",
    onTextChange: (String)->Unit,
    modifier: Modifier = Modifier
) {
    OutlinedTextField(
        value = value,
        onValueChange = onTextChange,
        label = {Text(label)},
        singleLine = true,
        modifier = modifier,
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
        textStyle = LocalTextStyle.current.copy(textAlign = TextAlign.Center)
    )
}

@Composable
fun RadioButtonGroup(
    radioOptions: List<String>,
    selectedOption: String,
    onOptionSelected: (String) -> Unit
) {

   Row(Modifier.selectableGroup()) {
        radioOptions.forEach { text ->
            Row(
                Modifier
                    //.fillMaxWidth()
                    .height(56.dp)
                    .selectable(
                        selected = (text == selectedOption),
                        onClick = { onOptionSelected(text) },
                        role = Role.RadioButton
                    )
                    .padding(horizontal = 16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                RadioButton(
                    selected = (text == selectedOption),
                    onClick = null // null recommended for accessibility with screenreaders
                )
                Text(
                    text = text,
                    style = MaterialTheme.typography.body1.merge(),
                    modifier = Modifier.padding(start = 16.dp)
                )
            }
        }
    }
}
