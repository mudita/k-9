package com.mudita.mail.ui.usecase.signIn

import androidx.compose.ui.test.assertCountEquals
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onAllNodesWithContentDescription
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import com.mudita.mail.repository.providers.model.ProviderTile
import com.mudita.mail.repository.providers.model.ProviderType
import com.mudita.mail.ui.theme.MuditaTheme
import com.mudita.mail.ui.usecase.signIn.view.SignInScreen
import com.mudita.mail.ui.usecase.signIn.viewModel.SignInViewModel
import com.mudita.mail.ui.usecase.signIn.viewModel.UiState
import com.mudita.mail.ui.util.stringKey.StringKey
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.flow.MutableStateFlow
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class SignInScreenTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    private lateinit var signInViewModel: SignInViewModel

    @Before
    fun setup() {
        signInViewModel = mockk {
            every { uiState } returns MutableStateFlow(
                UiState(
                    listOf(
                        ProviderTile(
                            StringKey.PROVIDER__GMAIL_NAME,
                            StringKey.PROVIDER__GMAIL_DESCRIPTION,
                            ProviderType.GMAIL
                        ),
                        ProviderTile(
                            StringKey.PROVIDER__ICLOUD_NAME,
                            StringKey.PROVIDER__ICLOUD_DESCRIPTION,
                            ProviderType.ICLOUD
                        ),
                        ProviderTile(
                            StringKey.PROVIDER__OUTLOOK_NAME,
                            StringKey.PROVIDER__OUTLOOK_DESCRIPTION,
                            ProviderType.OUTLOOK
                        ),
                        ProviderTile(
                            StringKey.PROVIDER__SETUP_MANUALLY_NAME,
                            StringKey.PROVIDER__SETUP_MANUALLY_DESCRIPTION,
                            ProviderType.MANUAL
                        )
                    )
                )
            )
        }
        composeTestRule.setContent {
            MuditaTheme {
                SignInScreen(viewModel = signInViewModel)
            }
        }
    }

    @Test
    fun screen_rendering_should_result_in_4_elements_in_list_of_providers() {
        composeTestRule.onAllNodesWithContentDescription("Provider logo").assertCountEquals(4)
    }

    @Test
    fun tap_on_one_of_providers_should_result_in_viewModels_call_with_given_provider_type() {
        every { signInViewModel.selectProvider(ProviderType.GMAIL) } returns Unit
        composeTestRule.onNodeWithText("Google mail").performClick()
        verify(exactly = 1) { signInViewModel.selectProvider(ProviderType.GMAIL) }
    }
}