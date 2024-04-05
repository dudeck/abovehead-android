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
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import pl.abovehead.R
import pl.abovehead.cart.screens.domain.OrderData

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun OrderScreen(makeOrder: (data: OrderData) -> Unit) {
    val formState = remember { mutableStateOf(FormState()) }

    @Composable
    fun validateInput(): String? {
        val state = formState.value
        return when {
            state.name.isEmpty() -> stringResource(R.string.please_enter_your_name)
            state.surname.isEmpty() -> stringResource(R.string.please_enter_your_surname)
            state.phone.isEmpty() -> stringResource(R.string.please_enter_your_phone_number)
            !Patterns.PHONE.matcher(state.phone)
                .matches() -> stringResource(R.string.please_enter_a_valid_phone_number)

            !state.agreement -> stringResource(R.string.please_agree_to_the_terms_of_use_and_privacy_policy)
            else -> null
        }
    }

    val errorMessage = remember { mutableStateOf<String?>(null) }

    fun onSubmit(error: String?) {
        errorMessage.value = error

        if (error == null) {
            makeOrder(
                OrderData(
                    formState.value.name,
                    formState.value.surname,
                    formState.value.phone,
                    formState.value.promoCode,
                    formState.value.addLogo,
                    formState.value.addTitle
                )
            )
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        errorMessage.value?.let {
            Text(
                text = it,
                color = MaterialTheme.colorScheme.error,
                modifier = Modifier.align(Alignment.CenterHorizontally)
            )
        }
        OutlinedTextField(
            value = formState.value.name,
            onValueChange = { formState.value = formState.value.copy(name = it) },
            label = { Text(stringResource(R.string.name)) },
            singleLine = true
        )

        OutlinedTextField(
            value = formState.value.surname,
            onValueChange = { formState.value = formState.value.copy(surname = it) },
            label = { Text(stringResource(R.string.surname)) },
            singleLine = true
        )

        OutlinedTextField(
            value = formState.value.phone,
            onValueChange = { formState.value = formState.value.copy(phone = it) },
            label = { Text(stringResource(R.string.phone)) },
            singleLine = true,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone)
        )

        OutlinedTextField(
            value = formState.value.promoCode,
            onValueChange = { formState.value = formState.value.copy(promoCode = it) },
            label = { Text(stringResource(R.string.promo_code)) },
            singleLine = true,
        )
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Checkbox(checked = formState.value.addLogo,
                onCheckedChange = { formState.value = formState.value.copy(addLogo = it) })
            Text(
                text = stringResource(R.string.addLogo),
                modifier = Modifier.padding(start = 8.dp)
            )
        }
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Checkbox(checked = formState.value.addTitle,
                onCheckedChange = { formState.value = formState.value.copy(addTitle = it) })
            Text(
                text = stringResource(R.string.addTitle),
                modifier = Modifier.padding(start = 8.dp)
            )
        }
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
        val error = validateInput()
        Button(
            onClick = { onSubmit(error) }, modifier = Modifier.align(Alignment.End)
        ) {
            Text(stringResource(R.string.submit))
        }
    }
}

data class FormState(
    val name: String = "",
    val surname: String = "",
    val phone: String = "",
    val promoCode: String = "",
    val agreement: Boolean = false,
    val addLogo: Boolean = true,
    val addTitle: Boolean = true,
)