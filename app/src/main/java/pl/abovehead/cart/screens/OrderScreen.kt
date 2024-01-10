package pl.abovehead.cart.screens

import android.util.Patterns
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import pl.abovehead.R
import pl.abovehead.cart.screens.domain.FrameColor
import pl.abovehead.cart.screens.domain.FrameSize
import pl.abovehead.cart.screens.domain.OrderData

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun OrderScreen(makeOrder: (data: OrderData) -> Unit) {

    // A state object to hold the user input
    val formState = remember { mutableStateOf(FormState()) }

    // A function to validate the user input and return an error message if any
    @Composable
    fun validateInput(): String? {
        val state = formState.value
        return when {
            state.name.isEmpty() -> stringResource(R.string.please_enter_your_name)
            state.surname.isEmpty() -> stringResource(R.string.please_enter_your_surname)
            state.phone.isEmpty() -> stringResource(R.string.please_enter_your_phone_number)
            !Patterns.PHONE.matcher(state.phone)
                .matches() -> stringResource(R.string.please_enter_a_valid_phone_number)

            state.email.isEmpty() -> stringResource(R.string.please_enter_your_email_address)
            !Patterns.EMAIL_ADDRESS.matcher(state.email)
                .matches() -> stringResource(R.string.please_enter_a_valid_email_address)

            !state.agreement -> stringResource(R.string.please_agree_to_the_terms_of_use_and_privacy_policy)
            else -> null
        }
    }

    // A state object to hold the error message
    val errorMessage = remember { mutableStateOf<String?>(null) }

    fun onSubmit(error: String?) {
        errorMessage.value = error

        // If there is no error, navigate to the next screen or perform other actions
        if (error == null) {
            makeOrder(
                OrderData(
                    formState.value.name,
                    formState.value.surname,
                    formState.value.phone,
                    formState.value.email,
                    formState.value.addLogo,
                    formState.value.addTitle
                )
            )
        }
    }

    // A column layout to display the form elements
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        // A text field for the name
        OutlinedTextField(
            value = formState.value.name,
            onValueChange = { formState.value = formState.value.copy(name = it) },
            label = { Text(stringResource(R.string.name)) },
            singleLine = true
        )

        // A text field for the surname
        OutlinedTextField(
            value = formState.value.surname,
            onValueChange = { formState.value = formState.value.copy(surname = it) },
            label = { Text(stringResource(R.string.surname)) },
            singleLine = true
        )

        // A text field for the phone number
        OutlinedTextField(
            value = formState.value.phone,
            onValueChange = { formState.value = formState.value.copy(phone = it) },
            label = { Text(stringResource(R.string.phone)) },
            singleLine = true,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone)
        )

        // A text field for the email address
        OutlinedTextField(
            value = formState.value.email,
            onValueChange = { formState.value = formState.value.copy(email = it) },
            label = { Text(stringResource(R.string.email)) },
            singleLine = true,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email)
        )
        LogoAndTitleOptions()

        // A checkbox for the agreement
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Checkbox(checked = formState.value.agreement,
                onCheckedChange = { formState.value = formState.value.copy(agreement = it) })
            Text(
                text = stringResource(R.string.i_agree_to_the_terms_of_use_and_privacy_policy),
                modifier = Modifier.padding(start = 8.dp)
            )
        }
        // Validate the input and show the error message if any
        val error = validateInput()
        // A button to submit the form
        Button(
            onClick = { onSubmit(error) }, modifier = Modifier.align(Alignment.End)
        ) {
            Text(stringResource(R.string.submit))
        }

        // A text to show the error message if any
        errorMessage.value?.let {
            Text(
                text = it,
                color = MaterialTheme.colorScheme.error,
                modifier = Modifier.align(Alignment.CenterHorizontally)
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LogoAndTitleOptions( formState: MutableState<FormState>) {
    // Use state variables to store the selected options and the expanded status of the drop-downs
    var size by remember { mutableStateOf(FrameSize.Big) }
    var color by remember { mutableStateOf(FrameColor.White) }
    var sizeExpanded by remember { mutableStateOf(true) }
    var colorExpanded by remember { mutableStateOf(true) }

    formState.value.s

    // Use a column to arrange the drop-downs vertically
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        // Use an ExposedDropdownMenuBox for each drop-down
        ExposedDropdownMenuBox(
            expanded = sizeExpanded,
            onExpandedChange = { sizeExpanded = it }
        ) {
            // Use a TextField to display the selected option and a trailing icon to indicate the drop-down status
            TextField(
                value = size.name,
                onValueChange = {},
                label = { Text(stringResource(id = R.string.size)) },
                readOnly = true,
                trailingIcon = {
                    ExposedDropdownMenuDefaults.TrailingIcon(
                        expanded = sizeExpanded
                    )
                },
                modifier = Modifier.menuAnchor()
            )
            // Use a DropdownMenu to display the options when expanded
            DropdownMenu(
                expanded = sizeExpanded,
                onDismissRequest = { sizeExpanded = false }
            ) {
                FrameSize.entries.forEach { option ->
                    DropdownMenuItem(
                        text = {
                            Text(option.name)
                        } ,
                        onClick = {
                            size = option
                            sizeExpanded = false
                        }
                    )
                }
            }
        }
        ExposedDropdownMenuBox(
            expanded = colorExpanded,
            onExpandedChange = { colorExpanded = it }
        ) {
            TextField(
                value = color.name,
                onValueChange = {},
                label = { Text(stringResource(id = R.string.color)) },
                readOnly = true,
                trailingIcon = {
                    ExposedDropdownMenuDefaults.TrailingIcon(
                        expanded = colorExpanded
                    )
                },
                modifier = Modifier.menuAnchor()
            )
            DropdownMenu(
                expanded = colorExpanded,
                onDismissRequest = { colorExpanded = false }
            ) {
                FrameColor.entries.forEach { option ->
                    DropdownMenuItem(
                        text = {
                            Text(option.name)
                        },
                        onClick = {
                            color = option
                            colorExpanded = false
                        }
                    )
                }
            }
        }
    }
}

// A data class to hold the form state
data class FormState(
    val name: String = "",
    val surname: String = "",
    val phone: String = "",
    val email: String = "",
    val agreement: Boolean = false,
    val addLogo: Boolean = true,
    val addTitle: Boolean = true,
)